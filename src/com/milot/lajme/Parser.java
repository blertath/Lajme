package com.milot.lajme;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Parser {
	
	public Parser() {
	}

	
	
	public List<News> fillList(String raw, int sourceId) throws Exception {			
	    	List<News> newsList = new ArrayList<News>();
	    	
	    	if(!newsList.isEmpty()) {
	    		newsList.clear();
	    	}
	    	
	    	DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    	Document doc = builder.parse(new InputSource(new StringReader(raw)));
	    	Element root = doc.getDocumentElement();
	    	NodeList items = root.getElementsByTagName("item");
	    		    	
	    	for(int i = 0; i < items.getLength(); i++) {
	    		News newsInstance = new News();
	    		Node item = items.item(i);
	    		NodeList properties = item.getChildNodes();
	    		    		
	    		for(int j = 0; j < properties.getLength(); j++) {
	    			Node property = properties.item(j);
	    			
	    			String name = property.getNodeName();
	    			
	    			if(name.equalsIgnoreCase("title")) {
	    				newsInstance.setTitle(property.getFirstChild().getNodeValue());
	    			} 
	    			else if (name.equalsIgnoreCase("description")) {
	    				newsInstance.setDescription(property.getFirstChild().getNodeValue());
	    			}
	    			else if(name.equalsIgnoreCase("pubDate")) {	    				
	    				newsInstance.setPublishedDate(property.getFirstChild().getNodeValue());
	    			}
	    			else if(name.equalsIgnoreCase("link")) {
	    				newsInstance.setLink(property.getFirstChild().getNodeValue());
	    			}
	    			else if(name.equalsIgnoreCase("imageUrl")) {
	    				newsInstance.setImageUrl(property.getFirstChild().getNodeValue());
	    			}
	    			
	    			else if(name.equalsIgnoreCase("thumbUrl")) {
	    				newsInstance.setThumbnailUrl(property.getFirstChild().getNodeValue());
	    			}
	    		}
	    		
	    		newsList.add(newsInstance);
	    	}
	    	
	    	return newsList;	
	    }
}
