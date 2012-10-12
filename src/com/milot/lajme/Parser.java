package com.milot.lajme;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class Parser {
	Context m_Context;
	HttpClient client = null;
	SQLiteDatabase db;
	
	public Parser(Context context) {
		m_Context = context;
		
		//db = (new NewsDatabase(m_Context)).getWritableDatabase();
	}

	
	
	public List<News> fillList(String raw, int sourceId) throws Exception {			
	    	ContentValues cv;
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
	    		cv = new ContentValues();
	    		    		
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
	    		
//	    		cv.put("sourceId", sourceId);
//	    		cv.put("read", 0);
	    		
	    		//db.insert("tblNews", "link", cv);
	    		newsList.add(newsInstance);
	    	}
	    	
	    	return newsList;	
	    }
}
