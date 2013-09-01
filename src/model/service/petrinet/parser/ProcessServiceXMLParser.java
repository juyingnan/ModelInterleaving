package model.service.petrinet.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.service.petrinet.Arc;
import model.service.petrinet.PetrinetModel;
import model.service.petrinet.Place;
import model.service.petrinet.StartPlace;
import model.service.petrinet.TimedTransition;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Node;

public class ProcessServiceXMLParser {
	
	@SuppressWarnings("unchecked")
	public PetrinetModel parserXml(String petrinetFileName) {
		
		SAXReader reader = new SAXReader();
		org.dom4j.Document serviceDocument;
		File file = new File(petrinetFileName);
		PetrinetModel petrinetModel = new PetrinetModel();
		
		if(!file.exists()){
			return null;
		}
		try {
			serviceDocument = reader.read(file);
			
			ArrayList<Place> placeList = new ArrayList<Place>();
			ArrayList<TimedTransition> transList = new ArrayList<TimedTransition>();
			ArrayList<Arc> arcList = new ArrayList<Arc>();
			
			List<Node> allPlace = serviceDocument.selectNodes("/pnml/net/place");
			List<Node> allTransition = serviceDocument.selectNodes("/pnml/net/transition");
			List<Node> allArc = serviceDocument.selectNodes("/pnml/net/arc");
				
			for(Node place : allPlace){
				String id = place.selectSingleNode("./@id").getStringValue();
				String name = place.selectSingleNode("./name/value").getText();
				
				if(name.contains("start:")){
					StartPlace start = new StartPlace(id, name.split(":")[1]);
					petrinetModel.setStart(start);
				} else if(name.equals("end")){
					Place p = new Place(id, name, null, null);
					placeList.add(p);
				} else{
					String[] parameters = name.split(":");
					Place p = new Place(id, parameters[0], parameters[1], parameters[2]);
					placeList.add(p);
				}
			}
			petrinetModel.setPlaceList(placeList);
			
			for(Node transition : allTransition){
				String id = transition.selectSingleNode("./@id").getStringValue();
				String name = transition.selectSingleNode("./name/value").getText();
				TimedTransition t = new TimedTransition(id, name);
				transList.add(t);
			}
			petrinetModel.setTransitionList(transList);
			
			for(Node arc : allArc){
				String id = arc.selectSingleNode("./@id").getStringValue();
				String source = arc.selectSingleNode("./@source").getStringValue();
				String target = arc.selectSingleNode("./@target").getStringValue();
				Arc a = new Arc(id, source, target);
				arcList.add(a);
			}
			petrinetModel.setArcList(arcList);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
			
		return petrinetModel;
	} 
}
