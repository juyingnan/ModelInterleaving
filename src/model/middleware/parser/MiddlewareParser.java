package model.middleware.parser;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.middleware.Middleware;
import parser.XMLParser;

public class MiddlewareParser
{
	public Middleware ParserXml(String fileName)
	{ 

		Middleware middleware = null;
		XMLParser xmlParser = new XMLParser(fileName);
		Document document = xmlParser.getDocument(); 
		NodeList states = document.getElementsByTagName("State");
		NodeList trans = document.getElementsByTagName("Transition");
		
		return middleware;
		
	}
	
	private String getAttriByName(Node n, String attriName){
		NamedNodeMap attrs = n.getAttributes();
		if(attrs.getNamedItem(attriName) != null){
			return attrs.getNamedItem(attriName).getNodeValue();
		}
		return null;
	}
	
	private int getIndexFromName(Node n, String attriName){
		String name = getAttriByName(n, attriName);
		int index = Integer.parseInt(name.substring(1));
		return index;
	}
}
