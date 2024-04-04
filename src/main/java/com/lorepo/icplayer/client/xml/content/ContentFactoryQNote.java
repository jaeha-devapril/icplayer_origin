package com.lorepo.icplayer.client.xml.content;

import java.util.ArrayList;

import com.google.gwt.xml.client.*;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.lorepo.icf.utils.XMLUtils;
import com.lorepo.icplayer.client.module.api.player.IChapter;
import com.lorepo.icplayer.client.utils.Utils;
import com.lorepo.icplayer.client.model.Content;
import com.lorepo.icplayer.client.model.page.Page;
import com.lorepo.icplayer.client.model.page.PageList;
import com.lorepo.icplayer.client.module.api.player.IContentNode;
import com.lorepo.icplayer.client.xml.IProducingLoadingListener;
import com.lorepo.icplayer.client.xml.IXMLFactory;
import com.lorepo.icplayer.client.xml.RequestFinishedCallback;
import com.lorepo.icplayer.client.xml.XMLVersionAwareFactoryQNote;
import com.lorepo.icplayer.client.xml.content.parsers.ContentParser_v0;
import com.lorepo.icplayer.client.xml.content.parsers.ContentParser_v1;
import com.lorepo.icplayer.client.xml.content.parsers.ContentParser_v2;
import com.lorepo.icplayer.client.xml.content.parsers.ContentParser_v3;
import com.lorepo.icplayer.client.xml.content.parsers.ContentParser_v4;
import com.lorepo.icplayer.client.xml.content.parsers.IContentParser;

public class ContentFactoryQNote extends XMLVersionAwareFactoryQNote {
	private ArrayList<Integer> pagesSubset;
	private Content mContent;
	
	protected ContentFactoryQNote(ArrayList<Integer> pagesSubset) {
		try {
//			Utils.consoleLog("pagesSubset: " + pagesSubset.size());
//			Utils.consoleLog("pagesSubset: " + pagesSubset);
		} catch (Exception e) {
			Utils.consoleLog("ContentFactory getInstance : " + e);
		}
		;

		this.setPagesSubset(pagesSubset);
		this.addParser(new ContentParser_v0());
		this.addParser(new ContentParser_v1());
		this.addParser(new ContentParser_v2());
		this.addParser(new ContentParser_v3());
		this.addParser(new ContentParser_v4());
	}
	
	public void setPagesSubset(ArrayList<Integer> pagesSubset) {
		this.pagesSubset = pagesSubset;
	}
	
	private void addParser(IContentParser parser) {
		parser.setPagesSubset(this.pagesSubset);
		super.addParser(parser);
	}

	public static IXMLFactory getInstance(ArrayList<Integer> pagesSubset) {
		return new ContentFactoryQNote(pagesSubset);
	}
	
	public static IXMLFactory getInstanceWithAllPages() {
		return ContentFactoryQNote.getInstance(new ArrayList<Integer>());
	}
	
	@Override
	protected RequestFinishedCallback getContentLoadCallback(final IProducingLoadingListener listener) {
		return new RequestFinishedCallback() {
			@Override
			public void onResponseReceived(String fetchURL, Request request, Response response) {
//				Utils.consoleLog("getContentLoadCallback response.getStatusCode() : " + fetchURL + ", " + response.getStatusCode());
				if (response.getStatusCode() == 200 || response.getStatusCode() == 0) {

//					Content content = null;// = produce(response.getText(), fetchURL);

//					Utils.consoleLog("fetchURL : " + fetchURL);
					if (loadedCount == 0) {
						mContent = produce(response.getText(), fetchURL);
//						Utils.consoleLog("mContent1 : " + response.getText());
					} else {
						Utils.consoleLog("mContent1 : " + response.getText());
						try {
							Content content = produce(response.getText(), fetchURL);
							addPage(content.getTableOfContents(), loadedCount);
//							IContentNode page = content.getTableOfContentByHref(fetchUrlPages[loadedCount]);
//							if( page != null ){
//								addPage(page, loadedCount);
//							}else {
//								addPage(content.getTableOfContents(), loadedCount);
//							}
						} catch (Exception e) {
							Utils.consoleLog("trace e : " + e);
						}
					}
					;

					Utils.consoleLog(
							"load loadedCount : " + loadedCount + ", " + pagesCount + ", " + fetchUrls[loadedCount]);

					if ((loadedCount + 1) < pagesCount) {
						++loadedCount;
						send(fetchUrls[loadedCount], listener);
					} else if ((loadedCount + 1) == pagesCount) {
						Content content = produce(response.getText(), fetchURL);
//						Utils.consoleLog("getContentLoadCallback mContent.toXML() : " + mContent.toXML());
						mContent = produce(mContent.toXML(), fetchUrls[0]);
//						Utils.consoleLog("getContentLoadCallback mContent.toXML() : " + mContent.toXML());
						listener.onFinishedLoading(mContent);
						++loadedCount;
					}
					;

//					Content content = produce(response.getText(), fetchURL);
//					listener.onFinishedLoading(content);
				} else {
					// Handle the error. Can get the status text from response.getStatusText()
					listener.onError("Wrong status: " + response.getText());
				}
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				listener.onFinishedLoading(null);
			}
		};
	}

	//	페이지 URL 변경
	private void addPage(IContentNode pageNode, int index) {

		Utils.consoleLog("addPage fetchUrlPages: " + fetchUrlPages[index]);
		Utils.consoleLog("addPage pageNode: " + pageNode.toXML());
		String strPages = "<pages>" + pageNode.toXML() + "</pages>";
		Document xmlPages = XMLParser.parse(strPages);
		Utils.consoleLog("addPage XMLParser.parse(pageNode.toXML()): " + xmlPages.toString());
		Element xml = null;
		if( fetchUrlPages[loadedCount] != "main.xml" ){
			NodeList nodeList = xmlPages.getElementsByTagName("page");
			for (int i = 0; i < nodeList.getLength(); ++i) {
				Element page = (Element) nodeList.item(i);
				Utils.consoleLog("addPage page: " + page.toString());
				Utils.consoleLog("addPage page href: " + page.getAttribute("href"));
				if( page.getAttribute("href") == fetchUrlPages[loadedCount] ){
					xml = XMLParser.parse(page.toString()).getDocumentElement();
					break;
				}
			}
		}else {
			xml = XMLParser.parse(pageNode.toXML()).getDocumentElement();
		}

        Utils.consoleLog("addPage xml: " + xml);
		Utils.consoleLog("addPage xml pageNode: " + pageNode);
		// 페이지명
		String href = XMLUtils.getAttributeAsString(xml, "href", "");
		Utils.consoleLog("addPage href : " + href);
		String urlPath = "../../../" + Utils.getPath(fetchUrls[index]);
		if (fetchUrls[index].startsWith("http") || fetchUrls[index].startsWith("HTTP")) {
			urlPath = Utils.getPath(fetchUrls[index]);
		}
		Utils.consoleLog("fetchUrls[index]t : " + index + ", " + fetchUrls[index] + ", " + urlPath);
//		String fileName = Utils.getFileName( fetchUrls[index] );
//		Utils.consoleLog("urlPath : " + urlPath + ", " + href);
		xml.setAttribute("href", urlPath + href);

//		xml = DynamicQuestionManager.Parser(xml, urlPath);
//		Utils.consoleLog("modifyPageUrl xml3 : " + xml.toString());

		PageList page = new PageList();
//		page.load(xml, null, null, index-1);
		Page p = page.loadPage(xml);
//		Utils.consoleLog("modifyPageUrl page : " + p.toXML());

		mContent.addPage(p);

//		returm (IContentNode)xml;
//		XMLUtils.setBooleanAttribute(element, key, value);
	}

	@Override
	public Content produce(String xmlString, String fetchUrl) {
		try {

			Element xml = XMLParser.parse(xmlString).getDocumentElement();
			String version = XMLUtils.getAttributeAsString(xml, "version", "1");
			Utils.consoleLog("version : " + version);
			Content producedContent = (Content) this.parsersMap.get(version).parse(xml);
//			Utils.consoleLog("producedContent : " + producedContent);
			producedContent.setBaseUrl(fetchUrl);
			
			return producedContent;
		} catch (Exception e) {
			Utils.consoleLog("produce xmlString :" + xmlString);
			Utils.consoleLog("produce fetchUrl :" + fetchUrl);
			Utils.consoleLog("produce e :" + e);
		}

		return null;
	}

	@Override
	public void unload() {
		try {
//			mContent = null;
//			this.listener.onFinishedLoading(mContent);
			Utils.consoleLog("unload");
		} catch (Exception e) {
			Utils.consoleLog("unload e : " + e);
		}
	}
}
