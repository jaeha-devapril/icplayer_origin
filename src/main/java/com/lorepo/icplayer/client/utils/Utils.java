package com.lorepo.icplayer.client.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.lorepo.icplayer.client.module.api.player.IPlayerServices;
import com.lorepo.icplayer.client.module.api.player.IScoreService;
import com.lorepo.icplayer.client.module.text.GapInfo;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import java.io.ByteArrayInputStream;

//import org.w3c.dom.Document;
//import org.xml.sax.SAXException;


public class Utils {

	public static String delemiter = "!@#";
	private static int moduleCnt = 0;
	private static String userAgent = "";
	public static boolean isCrossWalkWebview = true;
	public static boolean isSafari = false;
	
	public static String[] exceptFonts = {"VAGRounded BT", "VAGRoundedBT"};
	public static boolean isExistsExceptFont = false;
    public static double fontSize = 0;
    public static String xmlBRReplacer = "[xmlBRReplacer]";
    public static String imgBRReplacer = "[xmlImgReplacer]";
    public static String xmPReplacer = ">[xmlPReplacer]</p>";
//	public static double fontSize = Math.round(40 * 100/3.07) / 100.0;
    public static boolean isQNote = true;
	public static String baseURL = "";

	public static native void consoleLog(String message) /*-{
															 //console.log( "====> " + message );
															}-*/;

	/**
	 * Gets the navigator.appName.
	 *
	 * @return the window's navigator.appName.
	 */
	public static native String getAppName() /*-{
		return $wnd.navigator.userAgent;
	}-*/;

	public static String[] getUrls(String url) {

		try {
			consoleLog("getUrls : " + url);
			JSONValue parsed = JSONParser.parseStrict(url);
			JSONArray jsonArray = parsed.isArray();

			String[] arr = new String[jsonArray.size()];
			for (int i = 0; i < arr.length; i++) {
				try {

					arr[i] = UriUtils.encode(jsonArray.get(i).toString().replace("\"", ""));
					if (arr[i].indexOf("/pages/") > -1) {
						arr[i] = arr[i].split("pages/")[0] + "pages/main.xml";
					}
					consoleLog("jsonArray.get(i).toString(); : " + arr[i]);
				} catch (Exception e) {
					consoleLog("getUrls e : " + e);
				}
			}
			consoleLog("arr : " + arr.length);
			return arr;
		} catch (Exception e) {
			return new String[] { url };
		}
		// return url.split(delemiter);
	}

	public static String[] getUrlPages(String url) {

		try {
			consoleLog("getUrls : " + url);
			JSONValue parsed = JSONParser.parseStrict(url);
			JSONArray jsonArray = parsed.isArray();

			String[] arr = new String[jsonArray.size()];
			for (int i = 0; i < arr.length; i++) {
				try {

					arr[i] = UriUtils.encode(jsonArray.get(i).toString().replace("\"", ""));
					if (arr[i].indexOf("/pages/") > -1) {
						arr[i] = arr[i].split("/pages/")[1];
						arr[i] = arr[i].split(".xml?")[0] + ".xml";
					}

					consoleLog("getUrlPages jsonArray.get(i).toString(); : " + arr[i]);
				} catch (Exception e) {
					consoleLog("getUrls e : " + e);
				}
			}
			consoleLog("arr : " + arr.length);
			return arr;
		} catch (Exception e) {
			return new String[] { url };
		}
		// return url.split(delemiter);
	}

	public static int getUrlCount(String url) {
		return url.split(delemiter).length;
	}

	public static String addUrl(String url, String addUrl) {
		return addUrl + delemiter + url;
	}

	public static String getPath(String url) {
		String[] urls = url.split("/");

		if (urls.length > 1) {
			String retValue = "";
			for (int i = 0; i < urls.length - 1; ++i) {
				if (urls[i].equals(".."))
					continue;

				retValue += urls[i] + "/";
			}

			return retValue;
		} else {

			return url;
		}
	}

	public static String getFileName(String url) {
		String[] urls = url.split("/");

		if (urls.length > 1) {
			String retValue = "";
			for (int i = 0; i < urls.length - 1; ++i) {
				if (urls[i].equals(".."))
					continue;

				retValue += urls[i] + "/";
			}

			return urls[urls.length - 1];
		} else {

			return url;
		}
	}

	public static String format(final String format, final String... args) {
		String[] split = format.split("%s");
		// consoleLog(split);
		consoleLog("split :" + split.length);
		final StringBuffer msg = new StringBuffer();
		for (int pos = 0; pos < split.length - 1; pos += 1) {
			consoleLog("pos : " + pos);
			consoleLog("split[pos] : " + split[pos]);
			consoleLog("args[pos] : " + args[pos]);
			msg.append(split[pos]);
			msg.append(args[pos]);
		}
		msg.append(split[split.length - 1]);
		return msg.toString();
	}

	public static String getModuleID() {
		++moduleCnt;
		return "value_" + moduleCnt;

	}

	// 문자에서 숫자만 추출
	public static int getNumericText(String text) {
		String numericText = "";
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (GapInfo.isDigit(c)) {
				sb.append(c);
			}
		}

		numericText = sb.toString();

		return Integer.parseInt(numericText);
	}

	public static String getPageGroupID(int currentPageIdx, String groupID) {
		String id = currentPageIdx + "_" + groupID;
		consoleLog("getPageGroupID : " + id);
		return id;
	}

	public static boolean checkSameAnswerInGroup(IPlayerServices playerService, String moduleID, String enteredValue) {
		try {
			IScoreService scoreService = playerService.getScoreService();

			// Utils.consoleLog("checkSameAnswerInGroup id : " + id);
			String groupID = scoreService.getTextGroupID().get(moduleID);
			// String id = getPageGroupID(playerService.getCurrentPageIndex(), groupID);

			Utils.consoleLog("checkSameAnswerInGroup groupID : " + groupID);
			if (groupID == null || groupID.length() == 0)
				return false;

			// HashMap<String, String> modules =
			// scoreService.getGroupTexts().get(getPageGroupID(playerService.getCurrentPageIndex(),
			// groupID));
			HashMap<String, String> modules = scoreService.getGroupTexts().get(groupID);

			if (modules == null)
				return false;

			Utils.consoleLog("checkSameAnswerInGroup modules : " + modules);

			for (String key : modules.keySet()) {
				// 나보다 나중에 생긴 모듈만 비교
				Utils.consoleLog("checkSameAnswerInGroup key : " + key + ", moduleID : " + moduleID);
				if (Utils.getNumericText(key) >= Utils.getNumericText(moduleID))
					continue;
				Utils.consoleLog("checkSameAnswerInGroup key numeric : " + Utils.getNumericText(key)
						+ ", Utils.getNumericText(moduleID) : " + Utils.getNumericText(moduleID));

				Utils.consoleLog("checkSameAnswerInGroup modules.get(key) : " + modules.get(key) + ", moduleID : "
						+ enteredValue);
				// 나랑 같은 답이 있다면
				if (modules.get(key).equals(enteredValue))
					return true;
			}

			return false;
		} catch (Exception e) {
			return false;
		}

	}

	public static String removeTag(String value) {
		try {
			HTML html = new HTML(value);
			value = html.getText();
		} catch (Exception e) {
		}
		;

		return value;
	}

	public static boolean isCrossWalkWebView() {
		if (userAgent.equals("")) {
			userAgent = getAppName();
			isCrossWalkWebview = userAgent.contains("Crosswalk");
		}
		return isCrossWalkWebview;
	}
	
	public static boolean isSafari() {
		if (userAgent.equals("")) {
			userAgent = getAppName();
			consoleLog("userAgent : " + userAgent);
			isSafari = userAgent.contains("Safari") && !userAgent.contains("Chrome");
		}
		return isSafari;
	}

	
//	public static Document getDocument(String str) {
//		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder dBuilder;
//		Document xmlDoc = null;
//		
//		try {
//			dBuilder = dbFactory.newDocumentBuilder();
//			xmlDoc = dBuilder.parse(new ByteArrayInputStream(str.getBytes("UTF-8")));
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return xmlDoc;
//	}
	
	
	private static String convertDocumentToString(Element doc) {
//		String str = doc.toString();
		String str = doc.toString();
		consoleLog("convertDocumentToString before : " + str);
		str = str.replace("&lt;", "<");
		str = str.replace("&gt;", ">");
		str = str.replace("&amp;", "&");
		str = str.replace(xmlBRReplacer, "<br>");
		str = str.replace(xmPReplacer, "></p>");
		str = str.replaceAll("<ul[^>]*>/ul>", "");
		str = str.replaceAll("<ul[^>]*>*/>", "");
		
		consoleLog("convertDocumentToString after : " + str);
		return str;
    }
	
	
//	private static void applyFontRecursively(Element parent) 
//	{    
//		consoleLog("parsedStr : " + parsedStr);
//		NodeList nodes = parent.getChildNodes();
//	    for(int i = 0; i < nodes.getLength(); i++)
//	    {
//	    	Node currentNode = nodes.item(i);        
//	    	parsedStr += currentNode.toString();
//	    	if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
//	            applyFontRecursively((Element)currentNode);
//	        }           
//	    }
//	}
//	
//	private static String parsedStr = "";
//	private static String convertDocumentToString(Element doc) {
//		
//		parsedStr = "";
//		applyFontRecursively(doc);
//    	
//		return parsedStr;
//	}
	
	 private static Element convertStringToDocument(String xmlString) {
	    	Element xml = null;
	    	try {
	    		consoleLog("xmlString begin : " + xmlString);
	    		xmlString = xmlString.replaceAll("<br>", xmlBRReplacer);
	    		xmlString = xmlString.replaceAll("<br([^>]+)>", xmlBRReplacer);
		    	xmlString = xmlString.replaceAll("&nbsp;", "&#160;");
		    	xmlString = xmlString.replaceAll("></p>", xmPReplacer);
		    	
		    	
		    	consoleLog("xmlString after : " + xmlString);
		    	xml = XMLParser.parse(xmlString).getDocumentElement();
	    	}catch(Exception e) {
	    	}
	    	
	    	return xml;
	    }
	 

//    private static Element convertStringToDocument(String xmlString) {
//    	Element xml = null;
//    	try {
//    		xmlString = xmlString.replaceAll("<br>", xmlBRReplacer);
//    		xmlString = xmlString.replaceAll("<br([^>]+)>", xmlBRReplacer);
//    		
////    		xmlString = xmlString.replaceAll("<img>", "");
////    		xmlString = xmlString.replaceAll("<img([^>]+)>", "");
//    		
//	    	xmlString = xmlString.replaceAll("&nbsp;", "&#160;");
//	    	
//	    	consoleLog("xmlString : " + xmlString);
//	    	xml = XMLParser.parse(xmlString).getDocumentElement();
//    	}catch(Exception e) {
//    	}
//    	
//    	return xml;
//    }
    
    public static void hasExceptFont(Node node, boolean isKeepfontSize){
    	
    	isExistsExceptFont = false;
    	consoleLog("hasExceptFont node : " + node);
    	
    	if( node == null ) {
    		
//    		if( node.getParentNode() != null ) {
//    			hasExceptFont(node.getParentNode(), false);
//    		}else {
//	    		consoleLog("hasExceptFont node is null");
//	    	
//    		};
    		return;
    	}
    	
    	consoleLog("hasExceptFont getNodeType : " + node.getNodeType());
    	//text인 경우
    	if( node.getNodeType() != Node.ELEMENT_NODE) {
//    		hasExceptFont(node.getParentNode(), false);
    		hasExceptFont(node.getParentNode(), (fontSize != 0));
    		return;
    	}
    	
        try{
            Element element = (Element)node;
//            consoleLog("hasExceptFont : " + isExistsExceptFont);

            
            String style = "";
            String font = "";
            String dataFontSrc = "";
            if( element.hasAttribute("style") ) {
            	style = element.getAttribute("style");
            	consoleLog("hasExceptFont style : " + style);
                String sfSize = "";
                try {
                	if( style.indexOf("font-size:") > -1 ) sfSize = style.split("font-size:")[1];
                }catch(Exception e) {};
                consoleLog("hasExceptFont sfSize : " + sfSize);
                
//                if( !isKeepfontSize ) {
//                	if( sfSize.length() > 0 ) fontSize = Integer.parseInt( sfSize.split("px;")[0].trim());
//                }
               
                
                consoleLog("hasExceptFont fontSize : " + fontSize);
            }
            
            if( element.hasAttribute("face") ) {
            	font = element.getAttribute("face");
            }
            
//            if( element.hasAttribute("data-font-src") ) {
//            	dataFontSrc = element.getAttribute("data-font-src");
//            }
            
            consoleLog("hasExceptFont style : " + style );
        	consoleLog("hasExceptFont face : " + font );
        	consoleLog("hasExceptFont dataFontSrc : " + dataFontSrc );
            
            for( int i=0; i<exceptFonts.length; ++i){  
//            	consoleLog("hasExceptFont style : " + style );
//            	consoleLog("hasExceptFont font : " + font );
                if( style.indexOf(exceptFonts[i]) > -1
                		|| font.indexOf( exceptFonts[i] ) > -1 
                		|| dataFontSrc.indexOf( exceptFonts[i] ) > -1){
                    isExistsExceptFont = true;
                    return;
                }
            }
            
//        	consoleLog("hasExceptFont element : " + element );
//        	consoleLog("hasExceptFont getParentNode : " + element.getParentNode() );
//        	hasExceptFont(element.getParentNode(), true);
            
            if( (style == "" || style.indexOf("font-family") < 0 ) && font == "" && dataFontSrc == "") {
            	consoleLog("hasExceptFont element : " + element );
            	consoleLog("hasExceptFont getParentNode : " + element.getParentNode() );
            
//            	hasExceptFont(element.getParentNode(), true);
            	hasExceptFont(element.getParentNode(), (fontSize != 0));
            }
           
            
//            if( isExistsExceptFont ){
//                //var isExists = true;
////                if ( element.hasAttribute("face") ){
////                    for( int i=0; i<exceptFonts.length; ++i){
////                        // if( element.getAttribute("face").length > 1 ){
////                            if( element.getAttribute("face").indexOf( exceptFonts[i] ) <= 0){
////                                isExistsExceptFont = false;
////                                break;
////                            }
////                        // }
////                    }
////                }
//            	
//            	 
//            	 if ( element.hasAttribute("face") ){
//                     for( int i=0; i<exceptFonts.length; ++i){
//                         // if( element.getAttribute("face").length > 1 ){
//                             if( element.getAttribute("face").indexOf( exceptFonts[i] ) <= 0){
//                                 isExistsExceptFont = false;
//                                 break;
//                             }
//                         // }
//                     }
//                 }else {
//                	 hasExceptFont(element.getParentNode());
//                	 return;
//                 }
//            }
//            
//            for( int i=0; i<exceptFonts.length; ++i){  
//            	consoleLog("style : " + element.getAttribute("style") );
//                if( element.hasAttribute("style") ){
//                    if( element.getAttribute("style").indexOf(exceptFonts[i]) > -1){
//                        isExistsExceptFont = true;
//                        return;
//                    }
//                }
//                
//                if ( element.hasAttribute("face") ){
//                    if( element.getAttribute("face").indexOf( exceptFonts[i] ) > -1){
//                        isExistsExceptFont = true;
//                        return;
//                    }
//                }
//            }
        }catch(Exception e){
        	consoleLog("hasExceptFont e : " + e);
//        	consoleLog("hasExceptFont e node : " + node);
        	consoleLog("hasExceptFont e getParentNode  : " + node.getParentNode());
        	if( node.getParentNode() != null ) {
//     			hasExceptFont(node.getParentNode(), false);
        		hasExceptFont(node.getParentNode(), (fontSize != 0));
     		}
        };
        
        consoleLog("hasExceptFont : " + isExistsExceptFont);
    }
    
    public static void getFontSize(Node node){
    	
    	if( node == null ) {
    		return;
    	}
    	
    	
    	//text인 경우
    	if( node.getNodeType() != Node.ELEMENT_NODE) {
//    		hasExceptFont(node.getParentNode(), false);
    		getFontSize(node.getParentNode());
    		return;
    	}
    	
        try{
            Element element = (Element)node;
//            consoleLog("hasExceptFont : " + isExistsExceptFont);

            
            String style = "";
            String font = "";
            String dataFontSrc = "";
            if( element.hasAttribute("style") ) {
            	style = element.getAttribute("style");
            	consoleLog("getFontSize style : " + style);
                String sfSize = "";
                try {
                	if( style.indexOf("font-size:") > -1 ) sfSize = style.split("font-size:")[1];
                }catch(Exception e) {};
                consoleLog("getFontSize sfSize : " + sfSize);
                
                if( sfSize.length() > 0 ) fontSize = Integer.parseInt( sfSize.split("px;")[0].trim());
               
                
                consoleLog("getFontSize fontSize : " + fontSize);
            }
            
            if( element.hasAttribute("face") ) {
            	font = element.getAttribute("face");
            }
            
//            if( element.hasAttribute("data-font-src") ) {
//            	dataFontSrc = element.getAttribute("data-font-src");
//            }
            
            consoleLog("getFontSize style : " + style );
        	consoleLog("getFontSize face : " + font );
            
            if( fontSize == 0 && node.getParentNode() != null ) {
            	consoleLog("hasExceptFont element : " + element );
            	consoleLog("hasExceptFont getParentNode : " + element.getParentNode() );
            	getFontSize(element.getParentNode());
            }
           
        }catch(Exception e){
        	consoleLog("hasExceptFont e : " + e);
        	consoleLog("hasExceptFont e getParentNode  : " + node.getParentNode());
        	if( fontSize == 0 && node.getParentNode() != null ) {
        		getFontSize(node.getParentNode());
     		}
        };
        
        consoleLog("hasExceptFont : " + isExistsExceptFont);
    }
    
//    public static void replaceNBSP(Node currentNode) {
//
//        consoleLog("---------------------------------");
//        consoleLog("currentNode : " + currentNode);
//        
//    	hasExceptFont(currentNode);
//        
//        consoleLog("isExistsExceptFont : " + isExistsExceptFont);
//        
//        String parseText = "";
//        
//        try{
//	        if( isExistsExceptFont ){
//	        	consoleLog("doSomething OK : " + currentNode.getNodeName() + " / " +  currentNode.getFirstChild().getNodeValue() + " / " + currentNode.getFirstChild().getNodeValue().indexOf("\u00A0") );
//	            parseText = currentNode.getFirstChild().getNodeValue().replaceAll("\u00A0", "<span style=\"word-spacing: " + Math.round(fontSize * 100/3.07) / 100.0 + "px;\">&nbsp;</span>");
//	        }else {
//	        	consoleLog("doSomething No : " + currentNode.getFirstChild().getNodeValue() + " / " + currentNode.getFirstChild().getNodeValue().indexOf("\u00A0") );
//	            parseText = currentNode.getFirstChild().getNodeValue().replaceAll("\u00A0", "&nbsp;");
//	        }
//	        currentNode.getFirstChild().setNodeValue( parseText );
//        }catch(Exception e) {};
//    }
    
    public static void replaceNBSP(Node cNode) {

        consoleLog("---------------------------------");
//        consoleLog("currentNode : " + cNode);
        
        isExistsExceptFont = false;
        fontSize = 0;
        getFontSize(cNode);
    	hasExceptFont(cNode, false);
        
//        consoleLog("replaceNBSP isExistsExceptFont : " + isExistsExceptFont + " / currentNode : " + cNode);
        
        NodeList nodes = cNode.getChildNodes();
        String parseText = "";
        
       
        for( int i=0; i<nodes.getLength(); i++) {
    		 Node currentNode = nodes.item(i);
    		 
//    		 hasExceptFont(currentNode, false);
//    		 consoleLog("replaceNBSP isExistsExceptFont : " + isExistsExceptFont);
    		 try{
    			
    			if (currentNode.getNodeType() != Node.ELEMENT_NODE) {
    				consoleLog("---------------------------------------------------------------------------------------------");
    				fontSize = 0;
    				getFontSize(cNode);
    				hasExceptFont(currentNode, false);
    				consoleLog("replaceNBSP index : " + i + " : " + isExistsExceptFont + " : " + currentNode.getNodeValue());
    				
//    				double spaceSize = Math.round(fontSize * 100/3.07) * 2 / 100.0;
    				double spaceSize = Math.round(fontSize * 100/3)/ 100.0;
    				if( isExistsExceptFont ){
			        	consoleLog("doSomething before : " +  currentNode.getNodeValue());
			        	consoleLog("doSomething fontSize : " +  fontSize);
			        	parseText = currentNode.getNodeValue().replaceAll("\u00A0", "<span style=\"word-spacing: " + Math.round(fontSize * 100/3.07) / 100.0 + "px;\">&nbsp;</span>");
//			        	parseText = currentNode.getNodeValue().replaceAll("\u00A0 ", "<span style=\"word-spacing: " + spaceSize + "px;\">&nbsp;</span><span> </span>");
//			        	parseText = parseText.replaceAll(" \u00A0", "<span> </span><span style=\"word-spacing: " + spaceSize + "px;\"> &nbsp;</span>");
//			            parseText = parseText.replaceAll("\u00A0", "<span style=\"word-spacing: " + spaceSize + "px;\">&nbsp;</span>");
			            consoleLog("doSomething after : " + parseText);
			        }else {
//			        	consoleLog("doSomething No : " + currentNode.getNodeValue() + " / " + currentNode.getNodeValue().indexOf("\u00A0") );
			            parseText = currentNode.getNodeValue().replaceAll("\u00A0", "&nbsp;");
			        }
			        currentNode.setNodeValue( parseText );
    			}
		       
	        }catch(Exception e) {};
    	}
    }
    
    public static void doSomething(Element element, boolean isStart) {
        // do something with the current node instead of System.out

    	
    	NodeList nodes = element.getChildNodes();
    	if( isStart ) {
    		Node currentNode = element;
    		
//    		consoleLog("doSomething currentNode : " + isStart + " : " +currentNode);
    		
    		replaceNBSP(currentNode);

            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
              //calls this method for all the children which is Element
              doSomething((Element)currentNode, false);
            }
          
    	}else {
    		
	    	for (int i = 0; i < nodes.getLength(); i++) {
	          Node currentNode = nodes.item(i);
	
//	          consoleLog("doSomething currentNode : " + isStart + " : " + currentNode);
	          
	          replaceNBSP(currentNode);
	
	          if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
	              //calls this method for all the children which is Element
	              doSomething((Element)currentNode, false);
	          }
	      }
    	};
    }

    
    // 특정폰트는 사파리일때 word-spacing 추가
 	public static String parseNBSP(String str) {
 		consoleLog("isSafari : " + isSafari());
 		if (!isSafari())
 			return str;

 		
 		for (int i = 0; i < exceptFonts.length; ++i) {
 			consoleLog("exceptFonts : " + str.contains( exceptFonts[i]) );
 			
 			if (str.contains(exceptFonts[i])) {
 			
	 			isExistsExceptFont = false;
	 			fontSize = 0;
	 			Element element = convertStringToDocument(str);
	 			consoleLog("parseNBSP: "+ element);
	 			doSomething(element, true);
	 			str = convertDocumentToString(element);
	 			break;
 			};
 		}
 		
 		return str;
 	}
    
    
	// 특정폰트는 사파리일때 word-spacing 추가
	public static String addWordSpacing(String str) {
		consoleLog("isSafari : " + isSafari());
		if (!isSafari())
			return str;

		 
//		String[] fonts = { "font-family: HeummMyungjo232;", "font-family: HeummMyungjo242;",
//				"font-family: HeummGothic122;", "font-family: TimesNewRomanPSMT;", "font-family: HeummNemogulim142;" };
		
		String[] fonts = { "font-family:VAGRounded BT;" };

		
		for (int i = 0; i < fonts.length; ++i) {
			if (!str.contains(fonts[i]))
				continue;
			
			try {
				String[] arrText = str.split("font-size:");
				String result = arrText[0];
	//	        System.out.println(arrText.length);
		        for( int j=1; j<arrText.length; ++j) {
	//	            System.out.println("---------" + j + "------------");
	//	            System.out.println(arrText[j]);
		            int fSize = Integer.parseInt( arrText[j].split("px;")[0].trim());
	//	            System.out.println("fontSize : " + fSize);
		            double fontSize = Math.round(fSize * 10/3.08) / 10.0;
		            
		            String aaa = arrText[j];
		            
	//	            System.out.println("aaa : " +  aaa);
		            aaa = aaa.replaceAll("&nbsp;", "<span style=\"word-spacing: " + fontSize + "px;\">&nbsp;</span>"); 
		            aaa = aaa.replaceAll("> <", ">&nbsp;<"); 
//		            aaa = aaa.replaceAll("&nbsp;", "<span style=\"word-spacing: " + fontSize + "px;\">&nbsp;</span>"); 
		            // aaa = aaa.replaceAll("&nbsp; ", "<span style=\"word-spacing: " + 6.7 + "px;\">&nbsp;</span>&nbsp;"); 
		            
		            // arrText[j] = "font-size:" +  aaa;
		            arrText[j] = aaa;
	//	            System.out.println("replace : " +   arrText[j]);
		            result += "font-size:" + aaa;
		        }
	
		        str = result;
				
	//			Document doc = getDocument(str);
				
	//			str = str.replace(fonts[i], fonts[i] + " text-rendering: optimizeLegibility;");
	//			 str = str.replace(fonts[i], fonts[i] + " word-spacing: 6.7px;");
	//			str = str.replaceAll("&nbsp;", "&nbsp; ");
	//			str = str.replaceAll("&nbsp;", "&nbsp;&nbsp;");
	//			str = str.replaceAll(" &nbsp;", "&nbsp;");
	//			str = str.replaceAll("&nbsp; ", "&nbsp; ");
	//			str = str.replaceAll("&nbsp;", "&emsp;");
	//			str = str.replaceAll("&nbsp; ", "<span style=\"word-spacing: 6.7px;\">&nbsp;</span>&nbsp;"); 			
			}catch(Exception e) {};
				
			consoleLog("str : " + str);
		}
		//
		// String[] fonts2 = {"font-family: HeummMyungjo232;"};
		//
		// for (int i=0; i<fonts2.length; ++i) {
		// if( !str.contains(fonts2[i]) ) continue;
		//
		// str = str.replace(fonts2[i], fonts2[i] + " word-spacing: 0.018em;");
		// }
		//
		return str;
	}
	// public static String addWordSpacing(String str) {
	// if( !isCrossWalkWebView() ) return str;
	//
	//// String[] fonts = {"font-family: HeummGothic152;", "font-family:
	// HeummNemogothic152;", "font-family: HeummNemogothic142;", "font-family:
	// HeummMyungjo232;",
	//// "font-family: HeummMyungjo242;", "font-family: HeummGothic122;",
	// "font-family: TimesNewRomanPSMT;", "font-family: HeummNemogulim142;",
	// "font-family: Math2Bold;"};
	//
	// consoleLog("a : " + getAppName());
	// String[] fonts = {"font-family: HeummGothic152;", "font-family:
	// HeummNemogothic152;", "font-family: HeummNemogothic142;",
	// "font-family: HeummMyungjo242;", "font-family: HeummGothic122;",
	// "font-family: TimesNewRomanPSMT;", "font-family: HeummNemogulim142;"};
	//
	// for (int i=0; i<fonts.length; ++i) {
	// if( !str.contains(fonts[i]) ) continue;
	//
	// str = str.replace(fonts[i], fonts[i] + " word-spacing: 0.024em;");
	// }
	//
	// String[] fonts2 = {"font-family: HeummMyungjo232;"};
	//
	// for (int i=0; i<fonts2.length; ++i) {
	// if( !str.contains(fonts2[i]) ) continue;
	//
	// str = str.replace(fonts2[i], fonts2[i] + " word-spacing: 0.018em;");
	// }
	//
	// return str;
	// }

	public static String fromUnicode(String unicode) {
		String str = unicode.replace("\\", "");
		String[] arr = str.split("u");
		StringBuffer text = new StringBuffer();
		for (int i = 1; i < arr.length; i++) {
			int hexVal = Integer.parseInt(arr[i], 16);
			text.append(Character.toChars(hexVal));
		}
		return text.toString();
	}

	public static String toUnicode(String text) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			int codePoint = text.codePointAt(i);
			// Skip over the second char in a surrogate pair
			if (codePoint > 0xffff) {
				i++;
			}
			String hex = Integer.toHexString(codePoint);
			sb.append("\\u");
			for (int j = 0; j < 4 - hex.length(); j++) {
				sb.append("0");
			}
			sb.append(hex);
		}
		return sb.toString();
	}
}
