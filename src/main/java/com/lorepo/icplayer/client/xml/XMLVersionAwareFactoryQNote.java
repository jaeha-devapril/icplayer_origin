package com.lorepo.icplayer.client.xml;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.lorepo.icf.utils.JavaScriptUtils;
import com.lorepo.icf.utils.XMLUtils;
import com.lorepo.icplayer.client.utils.RequestsUtils;
import com.lorepo.icplayer.client.utils.Utils;

public abstract class XMLVersionAwareFactoryQNote implements IXMLFactory {

	protected HashMap<String, IParser> parsersMap = new HashMap<String, IParser>();
	protected int pagesCount = 1;
	protected int loadedCount = 0;
//	protected String blackPageUrl = "question/blankPage/pages/main.xml";
	protected String[] fetchUrls;
	protected String[] fetchUrlPages;
	protected  IProducingLoadingListener listener;
	
	protected void addParser(IParser parser) {
		this.parsersMap.put(parser.getVersion(), parser);
	}
	
	
	@Override
	public void load(String fetchUrl, IProducingLoadingListener listener) {
		//		fetchUrl = blackPageUrl;
		Utils.consoleLog("load fetchUrs : " + fetchUrl);
		loadedCount = 0;
		this.listener = listener;
		
//		Utils.consoleLog("fetchUrl.contains(blackPageUrl) : "+ fetchUrl.contains(blackPageUrl));
//		if( !fetchUrl.contains(blackPageUrl) ) {
//			fetchUrl = Utils.addUrl(fetchUrl, blackPageUrl); 
//		}
		
		Utils.consoleLog("fetchUrl: "+ fetchUrl);
		
		fetchUrls = Utils.getUrls(fetchUrl);
		fetchUrlPages = Utils.getUrlPages(fetchUrl);

		Utils.consoleLog("fetchUrlPages: " + ", " +  fetchUrlPages);
		pagesCount = fetchUrls.length;
		
//			Utils.consoleLog("fetchUrll : " + fetchUrls[loadedCount]);
//			RequestsUtils.get(fetchUrls[loadedCount], this.getContentLoadCallback(listener));
		
		send(fetchUrls[loadedCount], listener);
//			for( int i=0; i<fetchUrls.length; ++i) {
//				Utils.consoleLog("load url : " + fetchUrls[i]);
//				RequestsUtils.get(fetchUrls[i], this.getContentLoadCallback(listener));
//			};
//			RequestsUtils.get(fetchUrl, this.getContentLoadCallback(listener));
//			Utils.consoleLog("load url : " + fetchUrl);
	}
	
	@Override
	public void unload() {
	
	}
	
	protected void send(String fetchUrl, IProducingLoadingListener listener) {
		try {
			Utils.consoleLog("send load url : " + fetchUrl);
			// https://devapril-qnote.s3.ap-northeast-2.amazonaws.com/contents/trial/Vv7wkY8N5UwhT0Lm7vqE/pages/Vv7wkY8N5UwhT0Lm7vqE.xml?1701993202018

			RequestsUtils.get(fetchUrl, this.getContentLoadCallback(listener));
		}catch (RequestException e) {
			JavaScriptUtils.log("Fetching main data content from server has failed: " + e.toString());
		}
	}

	protected RequestFinishedCallback getContentLoadCallback(final IProducingLoadingListener listener) {
		return new RequestFinishedCallback() {

			@Override
			public void onResponseReceived(String fetchURL, Request request,
					Response response) {
				if (response.getStatusCode() == 200 || response.getStatusCode() == 0) {
//				Utils.consoleLog("getContentLoadCallback");
					Utils.consoleLog("load loadedCount : " + fetchUrls[loadedCount]);
					
					Utils.consoleLog("getContentLoadCallback : " + response.getText() );
					
					Object producedItem = produce(response.getText(), fetchURL);
//					listener.onFinishedLoading(producedItem);
					
					
					
					
					if( (loadedCount +1) < pagesCount) {
						++loadedCount;
						send(fetchUrls[loadedCount], listener);
					}else {
						Utils.consoleLog("getContentLoadCallback end : " + loadedCount);
						listener.onFinishedLoading(producedItem);
						++loadedCount;
					}
					
//					++loadedCount;
					
			} else {
				// Handle the error.  Can get the status text from response.getStatusText()
				listener.onError("Wrong status: " + response.getText());
			}
				
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Utils.consoleLog("onError : " + exception);
				listener.onFinishedLoading(null);		
			}
		};
	}

	public Object produce(String xmlString, String fetchUrl) {
		Utils.consoleLog("produce");
		
		//Completion_Progress 없으면 추가
//		Utils.consoleLog("produce :" + xmlString.indexOf("Completion_Progress1") );
//		if( xmlString.indexOf("Completion_Progress1") < 0 ) {
//			xmlString = addCompletionProgress1(xmlString);
//			Utils.consoleLog("produce :" + xmlString );
//		}
//
//		if( xmlString.indexOf("ResetButton1") < 0 ) {
//			xmlString = addResetButton1(xmlString);
//			Utils.consoleLog("produce :" + xmlString );
//		}
		
		
		Element xml = XMLParser.parse(xmlString).getDocumentElement();
		String version = XMLUtils.getAttributeAsString(xml, "version", "1");
		Object producedContent = this.parsersMap.get(version).parse(xml);
		
		return producedContent;
	}

	
	private String addCompletionProgress1(String xmlString) {
		try {
			String[] sss = xmlString.split("</modules>");
			return sss[0] + "<addonModule addonId=\"Completion_Progress\" id=\"Completion_Progress1\" isVisible=\"false\" isLocked=\"false\" isModuleVisibleInEditor=\"false\">\n" + 
					"			<layouts>\n" + 
					"				<layout isLocked=\"false\" isModuleVisibleInEditor=\"true\" id=\"default\" isVisible=\"false\">\n" + 
					"					<relative type=\"LTWH\">\n" + 
					"						<left relative=\"\" property=\"left\"/>\n" + 
					"						<top relative=\"\" property=\"top\"/>\n" + 
					"						<right relative=\"\" property=\"right\"/>\n" + 
					"						<bottom relative=\"\" property=\"bottom\"/>\n" + 
					"					</relative>\n" + 
					"					<absolute left=\"0\" right=\"0\" top=\"0\" bottom=\"0\" width=\"45\" height=\"45\" rotate=\"0\"/>\n" + 
					"				</layout>\n" + 
					"				<layout isLocked=\"false\" isModuleVisibleInEditor=\"true\" id=\"vertical\" isVisible=\"false\">\n" + 
					"					<relative type=\"LTWH\">\n" + 
					"						<left relative=\"\" property=\"left\"/>\n" + 
					"						<top relative=\"\" property=\"top\"/>\n" + 
					"						<right relative=\"\" property=\"right\"/>\n" + 
					"						<bottom relative=\"\" property=\"bottom\"/>\n" + 
					"					</relative>\n" + 
					"					<absolute left=\"0\" right=\"0\" top=\"0\" bottom=\"0\" width=\"45\" height=\"45\" rotate=\"0\"/>\n" + 
					"				</layout>\n" + 
					"			</layouts>\n" + 
					"			<properties>\n" + 
					"				<property name=\"Turn off automatic counting\" type=\"boolean\" value=\"false\"/>\n" + 
					"			</properties>\n" + 
					"		</addonModule></modules>" + sss[1];
		}catch(Error e) {};
		
		return xmlString;
	}
	
	private String addResetButton1(String xmlString) {
		try {
			String[] sss = xmlString.split("</modules>");
			return sss[0] + "<buttonModule id=\"ResetButton1\" isTabindexEnabled=\"false\">\n" + 
					"			<layouts>\n" + 
					"				<layout isLocked=\"false\" isModuleVisibleInEditor=\"false\" id=\"default\" isVisible=\"false\">\n" + 
					"					<relative type=\"LTWH\">\n" + 
					"						<left relative=\"\" property=\"left\"/>\n" + 
					"						<top relative=\"\" property=\"top\"/>\n" + 
					"						<right relative=\"\" property=\"right\"/>\n" + 
					"						<bottom relative=\"\" property=\"bottom\"/>\n" + 
					"					</relative>\n" + 
					"					<absolute left=\"0\" right=\"0\" top=\"0\" bottom=\"0\" width=\"1\" height=\"0\" rotate=\"0\"/>\n" + 
					"				</layout>\n" + 
					"				<layout isLocked=\"false\" isModuleVisibleInEditor=\"false\" id=\"vertical\" isVisible=\"false\">\n" + 
					"					<relative type=\"LTWH\">\n" + 
					"						<left relative=\"\" property=\"left\"/>\n" + 
					"						<top relative=\"\" property=\"top\"/>\n" + 
					"						<right relative=\"\" property=\"right\"/>\n" + 
					"						<bottom relative=\"\" property=\"bottom\"/>\n" + 
					"					</relative>\n" + 
					"					<absolute left=\"0\" right=\"0\" top=\"0\" bottom=\"0\" width=\"1\" height=\"0\" rotate=\"0\"/>\n" + 
					"				</layout>\n" + 
					"			</layouts>\n" + 
					"			<button onclick=\"\" type=\"reset\" text=\"\" confirmReset=\"false\" confirmInfo=\"\" confirmYesInfo=\"\" confirmNoInfo=\"\"/>\n" + 
					"		</buttonModule></modules>" + sss[1];
		}catch(Error e) {};
		
		return xmlString;
	}
}
