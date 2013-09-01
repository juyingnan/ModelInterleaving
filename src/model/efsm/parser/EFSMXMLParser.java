package model.efsm.parser;

import model.efsm.EFSMModel;
import model.efsm.Middleware;
import model.efsm.State;
import model.efsm.Transition;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import parser.XMLParser;

public class EFSMXMLParser
{

	public EFSMModel parserXml(String fileName) { 
		EFSMModel efsmModel = null;
		XMLParser xmlParser = new XMLParser(fileName);
		Document document = xmlParser.getDocument(); 
		NodeList states = document.getElementsByTagName("State");
		NodeList trans = document.getElementsByTagName("Transition");
		efsmModel = new EFSMModel(states.getLength(), trans.getLength());
		for(int i = 0; i < states.getLength(); i++){
			Node s = states.item(i);
			int index = getIndexFromName(s, "name");
			State state = new State(index);
			efsmModel.addState(state);
		}
		for(int i = 0; i < trans.getLength(); i++){
			Node t = trans.item(i);			
			NodeList events = t.getChildNodes();
			String input = null;
			String output = null;
			String action = null;
			String guard = null;
			for(int j = 0; j < events.getLength(); j++){
				Node e = events.item(j);
				if("Event".equals(e.getNodeName())){
					String eventName = getAttriByName(e, "name");
					String actionName = null;
					NodeList actions = e.getChildNodes();
					for(int k = 0; k < actions.getLength(); k++){
						Node a = actions.item(k);
						if("Action".equals(a.getNodeName())){
							actionName = getAttriByName(a, "name");
						}
					}
					if("input".equals(eventName)){
						input = actionName;
					}else if("output".equals(eventName)){
						output = actionName;
					}else if("action".equals(eventName)){
						action = actionName;
					}else if("guard".equals(eventName)){
						guard = actionName;
					}
				}
			}
			int tranInit = getIndexFromName(t, "source");			
			int tranNext = getIndexFromName(t, "target");
			String name = getAttriByName(t, "name");
			Transition tran = new Transition(input, output, tranInit, tranNext, guard, action, name);
			efsmModel.initDataByTransition(tran);
			int index = getIndexFromName(t, "name");
			efsmModel.addTran(tran, index);
		}
		return efsmModel;
	}	public Middleware parserXml(String fileName, boolean b)
	{
		Middleware middleware = null;
		XMLParser xmlParser = new XMLParser(fileName);
		Document document = xmlParser.getDocument();
		NodeList states = document.getElementsByTagName("State");
		NodeList trans = document.getElementsByTagName("Transition");
		middleware = new Middleware(states.getLength(), trans.getLength());
		for (int i = 0; i < states.getLength(); i++)
		{
			Node s = states.item(i);
			int index = getIndexFromName(s, "name");
			State state = new State(index);
			middleware.addState(state);
		}
		for (int i = 0; i < trans.getLength(); i++)
		{
			Node t = trans.item(i);
			NodeList events = t.getChildNodes();
			String input = null;
			String output = null;
			String action = null;
			String guard = null;
			for (int j = 0; j < events.getLength(); j++)
			{
				Node e = events.item(j);
				if ("Event".equals(e.getNodeName()))
				{
					String eventName = getAttriByName(e, "name");
					String actionName = null;
					NodeList actions = e.getChildNodes();
					for (int k = 0; k < actions.getLength(); k++)
					{
						Node a = actions.item(k);
						if ("Action".equals(a.getNodeName()))
						{
							actionName = getAttriByName(a, "name");
						}
					}
					if ("input".equals(eventName))
					{
						input = actionName;
					}
					else if ("output".equals(eventName))
					{
						output = actionName;
					}
					else if ("action".equals(eventName))
					{
						action = actionName;
					}
					else if ("guard".equals(eventName))
					{
						guard = actionName;
					}
				}
			}
			int tranInit = getIndexFromName(t, "source");
			int tranNext = getIndexFromName(t, "target");
			String name = getAttriByName(t, "name");
			Transition tran = new Transition(input, output, tranInit, tranNext, guard, action, name);
			middleware.initDataByTransition(tran);
			int index = getIndexFromName(t, "name");
			middleware.addTran(tran, index);
		}
		return middleware;
	}

	private String getAttriByName(Node n, String attriName)
	{
		NamedNodeMap attrs = n.getAttributes();
		if (attrs.getNamedItem(attriName) != null) { return attrs.getNamedItem(attriName).getNodeValue(); }
		return null;
	}

	private int getIndexFromName(Node n, String attriName)
	{
		String name = getAttriByName(n, attriName);
		int index = Integer.parseInt(name.substring(1));
		return index;
	}
}
