package com.lorepo.icplayer.client.module.choice;

import com.google.gwt.xml.client.CDATASection;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.lorepo.icf.properties.BasicPropertyProvider;
import com.lorepo.icf.properties.IEventProperty;
import com.lorepo.icf.properties.IHtmlProperty;
import com.lorepo.icf.properties.IProperty;
import com.lorepo.icf.utils.StringUtils;
import com.lorepo.icf.utils.XMLUtils;
import com.lorepo.icf.utils.i18n.DictionaryWrapper;
import com.lorepo.icplayer.client.utils.Utils;

public class ChoiceOption extends BasicPropertyProvider{

	private String	text;
	private int		value;
	private String feedback = "";
	private String id;
	private String parentId = "";
	private int 	x = -1;
	private int 	y = -1;
	private int 	absoluteWidth = -1;
	private int 	absoluteHeight = -1;
	private int 	width = -1;
	private int 	height = -1;
	private String  layoutID = defaultLayoutID; 
	private Element element;

	
	
	public ChoiceOption(String id){
		
		super(DictionaryWrapper.get("choice_option"));
		
		this.id = id;
		addPropertyValue();
		addPropertyX();
		addPropertyY();
		addPropertyText();
		addPropertyFeedback();
	}
	

	public void setLayoutID(String layoutID) {
		this.layoutID = layoutID;
		resetXY(element);
	}
	
	public ChoiceOption(String id, String html, int value){

		this(id);
		this.value = value;

		this.text = html;
	}
	
	
	public String getID(){
		return id;
	}
	
	
	public String getText(){
		return text;
	}
	
	
	public int getValue(){
		return value;
	}

	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getAbsoluteWidth(){
		return absoluteWidth;
	}
	
	public int getAbsoluteHeight(){
		return absoluteHeight;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}


	
	public String getFeedback(){
		return feedback;
	}

	public void setParentId(String id) {
		parentId = id;
	}

	public String getParentId() {
		return parentId;
	}

	public boolean isCorrect() {
		return value > 0;
	}

	protected void setFeedback(String feedback){
		this.feedback = feedback;
	}

	private void resetXY(Element element) {
		NodeList textNodes = element.getElementsByTagName("text"); 
		
		try {
			Utils.consoleLog("layoutID  : " + layoutID);
			if(layoutID == defaultLayoutID) {
				x = XMLUtils.getAttributeAsInt(element, "x");
				y = XMLUtils.getAttributeAsInt(element, "y");
				try {
					absoluteWidth = XMLUtils.getAttributeAsInt(element, "absoluteWidth");
					absoluteHeight = XMLUtils.getAttributeAsInt(element, "absoluteHeight");
				}catch(Exception e) {}
				try {
					width = XMLUtils.getAttributeAsInt(element, "width");
					height = XMLUtils.getAttributeAsInt(element, "height");
				}catch(Exception e) {}
				
			}else {
				x = XMLUtils.getAttributeAsInt(element, "x_" + layoutID);
				y = XMLUtils.getAttributeAsInt(element, "y_" + layoutID);
				textNodes = element.getElementsByTagName("text_" + layoutID);
				try {
					absoluteWidth = XMLUtils.getAttributeAsInt(element, "absoluteWidth_" + layoutID);
					absoluteHeight = XMLUtils.getAttributeAsInt(element, "absoluteHeight_" + layoutID);
				}catch(Exception e) {}
				try {
					width = XMLUtils.getAttributeAsInt(element, "width_" + layoutID);
					height = XMLUtils.getAttributeAsInt(element, "height_" + layoutID);
				}catch(Exception e) {}
			}
			
			if(textNodes.getLength() > 0){
				Element textElement = (Element) textNodes.item(0);
				text = XMLUtils.getCharacterDataFromElement(textElement);
				
				Utils.consoleLog("text : " + layoutID + " / " +  text);
				Utils.consoleLog("text : " + layoutID + " / " +  text);
				if(text == null){
					text = XMLUtils.getText(textElement);
					text = StringUtils.unescapeXML(text);
				}
//				if(baseUrl != null){
//					text = StringUtils.updateLinks(text, baseUrl);
//				}
			}
			
		}catch(Exception e) {
			Utils.consoleLog("resetXY e : " + e);
		}
	}
	
	private void addPropertyText() {

		IHtmlProperty property = new IHtmlProperty() {
				
			@Override
			public void setValue(String newValue) {
				text = newValue;
			}
			
			@Override
			public String getValue() {
				return text;
			}
			
			@Override
			public String getName() {
				return DictionaryWrapper.get("choice_item_text");
			}

			@Override
			public String getDisplayName() {
				return DictionaryWrapper.get("choice_item_text");
			}

			@Override
			public boolean isDefault() {
				return false;
			}
		};
		
		addProperty(property);
	}
	
	private void addPropertyValue() {

		IProperty property = new IProperty() {
			
			@Override
			public void setValue(String newValue) {
				value = Integer.parseInt(newValue);
			}
			
			@Override
			public String getValue() {
				return Integer.toString(value);
			}
			
			@Override
			public String getName() {
				return DictionaryWrapper.get("choice_item_value");
			}

			@Override
			public String getDisplayName() {
				return DictionaryWrapper.get("choice_item_value");
			}

			@Override
			public boolean isDefault() {
				return false;
			}
		};
		
		addProperty(property);
	}

	private void addPropertyX() {

		IProperty property = new IProperty() {
			
			@Override
			public void setValue(String newValue) {
				value = Integer.parseInt(newValue);
			}
			
			@Override
			public String getValue() {
				return Integer.toString(value);
			}
			
			@Override
			public String getName() {
				return DictionaryWrapper.get("choice_item_x");
			}

			@Override
			public String getDisplayName() {
				return DictionaryWrapper.get("choice_item_x");
			}

			@Override
			public boolean isDefault() {
				return false;
			}
		};
		
		addProperty(property);
	}
	
	private void addPropertyY() {

		IProperty property = new IProperty() {
			
			@Override
			public void setValue(String newValue) {
				value = Integer.parseInt(newValue);
			}
			
			@Override
			public String getValue() {
				return Integer.toString(value);
			}
			
			@Override
			public String getName() {
				return DictionaryWrapper.get("choice_item_y");
			}

			@Override
			public String getDisplayName() {
				return DictionaryWrapper.get("choice_item_y");
			}

			@Override
			public boolean isDefault() {
				return false;
			}
		};
		
		addProperty(property);
	}

	private void addPropertyFeedback() {

		IProperty property = new IEventProperty() {
				
			public void setValue(String newValue) {
				feedback = newValue;
			}
			
			public String getValue() {
				return feedback;
			}
			
			public String getName() {
				return DictionaryWrapper.get("choice_item_event");
			}

			@Override
			public String getDisplayName() {
				return DictionaryWrapper.get("choice_item_event");
			}

			@Override
			public boolean isDefault() {
				return false;
			}
		};
		
		addProperty(property);
	}


	public void load(Element element, String baseUrl) {
		this.element = element;
		value = XMLUtils.getAttributeAsInt(element, "value");
		String rawFeedback = "";
		
		NodeList textNodes = element.getElementsByTagName("text"); 
		if( Utils.isQNote ) {
			try {
				if (layoutID == defaultLayoutID) {
					x = XMLUtils.getAttributeAsInt(element, "x");
					y = XMLUtils.getAttributeAsInt(element, "y");
				} else {
					x = XMLUtils.getAttributeAsInt(element, "x_" + layoutID);
					y = XMLUtils.getAttributeAsInt(element, "y_" + layoutID);
					textNodes = element.getElementsByTagName("text_" + layoutID);
				}
			} catch (Exception e) {

			}

			try {
				if (layoutID == defaultLayoutID) {
					absoluteWidth = XMLUtils.getAttributeAsInt(element, "absoluteWidth");
					absoluteHeight = XMLUtils.getAttributeAsInt(element, "absoluteHeight");
				} else {
					absoluteWidth = XMLUtils.getAttributeAsInt(element, "absoluteWidth_" + layoutID);
					absoluteHeight = XMLUtils.getAttributeAsInt(element, "absoluteHeight_" + layoutID);
				}
			} catch (Exception e) {

			}
		};

		
//		NodeList textNodes = element.getElementsByTagName("text"); 
		if(textNodes.getLength() > 0){
			Element textElement = (Element) textNodes.item(0);
			text = XMLUtils.getCharacterDataFromElement(textElement);
			if(text == null){
				text = XMLUtils.getText(textElement);
				text = StringUtils.unescapeXML(text);
			}
			if(baseUrl != null){
				text = StringUtils.updateLinks(text, baseUrl);
			}
			
			NodeList feedbackNodes = element.getElementsByTagName("feedback");
			if(feedbackNodes.getLength() > 0){
				rawFeedback = XMLUtils.getText((Element) feedbackNodes.item(0));
			}
		}
		else{
			text = XMLUtils.getText((Element) element);
		}
		
		if(!rawFeedback.isEmpty()){
			feedback = StringUtils.unescapeXML(rawFeedback);
		}

		text = text.replaceAll("<!--[\\s\\S]*?-->", "");
	}
	
	
	public Element toXML() {
		Element optionElement = XMLUtils.createElement("option");
		if( x > 0 ) {
			XMLUtils.setIntegerAttribute(optionElement, "x", x);
		}
		
		if( y > 0 ) {
			XMLUtils.setIntegerAttribute(optionElement, "y", y);
		};
		
		if( absoluteWidth > 0 ) {
			XMLUtils.setIntegerAttribute(optionElement, "absoluteWidth", absoluteWidth);
		};
		
		if( absoluteHeight > 0 ) {
			XMLUtils.setIntegerAttribute(optionElement, "absoluteHeight", absoluteHeight);
		};
		
		XMLUtils.setIntegerAttribute(optionElement, "value", value);
		
		Element textElement = XMLUtils.createElement("text");
		CDATASection cdataText = XMLUtils.createCDATASection(text);
		textElement.appendChild(cdataText);
		
		Element feedbackElement = XMLUtils.createElement("feedback");		
		feedbackElement.appendChild(XMLUtils.createTextNode(StringUtils.escapeHTML(feedback)));

		optionElement.appendChild(textElement);
		optionElement.appendChild(feedbackElement);
		
		return optionElement;
	}
	
}
