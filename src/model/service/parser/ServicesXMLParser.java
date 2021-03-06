package model.service.parser;

import model.service.Service;
import model.service.ServiceModel;
import model.service.petrinet.Arc;
import model.service.petrinet.PetrinetModel;
import model.service.petrinet.Place;
import model.service.petrinet.StartPlace;
import model.service.petrinet.parser.ProcessServiceXMLParser;

import org.w3c.dom.Document; 
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node; 
import org.w3c.dom.NodeList; 

import parser.XMLParser;

public class ServicesXMLParser{ 
	
	public ServiceModel parserXml(String serviceFileName, String petrinetFileName) { 
		ServiceModel serviceModel = null;
		XMLParser serviceXMLParser = new XMLParser(serviceFileName);
		Document serviceDocument = serviceXMLParser.getDocument();
		ProcessServiceXMLParser petrinetParser = new ProcessServiceXMLParser();
		NodeList address = serviceDocument.getElementsByTagName("Address");
		Node add = address.item(0);
		String ip = getAttriByName(add, "ip");
		String apath = getAttriByName(add, "path");
		String namespace = getAttriByName(add, "namespace");
		serviceModel = new ServiceModel(ip, apath, namespace);
		NodeList services = serviceDocument.getElementsByTagName("Service");
		for(int i = 0; i < services.getLength(); i++){
			Node s = services.item(i);
			String index = getAttriByName(s, "index");
			String name = getAttriByName(s, "name");
			String path = getAttriByName(s, "path");
			String function = getAttriByName(s, "function");
			String isProcess = getAttriByName(s, "isProcess");
			String endFlag = getAttriByName(s, "endFlag");
			Service service = new Service(index, name, path, function, isProcess, endFlag);
			if(isProcess.equals("true")){
				PetrinetModel petrinetModel = petrinetParser.parserXml(petrinetFileName);
				StartPlace start = petrinetModel.getStart();
				String currentId = start.getId();
				for(int m = 1; m <= petrinetModel.getPlaceList().size(); m++){
					String placeId = getTargetBySourceId(petrinetModel, currentId);
					Place place = getPlaceById(petrinetModel, placeId);
					if(!place.getServiceName().equals("end")){
						Service subService = new Service(m + "",
								place.getServiceName(),
								place.getPath(),
								place.getFunction(),
								"false",
								null);
						service.addSubService(subService);				
					}
					currentId = placeId;
				}
			}
			serviceModel.addService(service);
		}
		return serviceModel;
	} 
	public ServiceModel parserXml(String serviceFileName) { 
		ServiceModel serviceModel = null;
		XMLParser serviceXMLParser = new XMLParser(serviceFileName);
		Document serviceDocument = serviceXMLParser.getDocument();
		NodeList address = serviceDocument.getElementsByTagName("Address");
		Node add = address.item(0);
		String ip = getAttriByName(add, "ip");
		String apath = getAttriByName(add, "path");
		String namespace = getAttriByName(add, "namespace");
		serviceModel = new ServiceModel(ip, apath, namespace);
		NodeList services = serviceDocument.getElementsByTagName("Service");
		for(int i = 0; i < services.getLength(); i++){
			Node s = services.item(i);
			String index = getAttriByName(s, "index");
			String name = getAttriByName(s, "name");
			String path = getAttriByName(s, "path");
			String function = getAttriByName(s, "function");
			String isProcess = getAttriByName(s, "isProcess");
			String endFlag = getAttriByName(s, "endFlag");
			Service service = new Service(index, name, path, function, isProcess, endFlag);

			serviceModel.addService(service);
		}
		return serviceModel;
	} 
	
	private String getAttriByName(Node n, String attriName){
		NamedNodeMap attrs = n.getAttributes();
		if(attrs.getNamedItem(attriName) != null){
			return attrs.getNamedItem(attriName).getNodeValue();
		}
		return null;
	}
	
	private String getTargetBySourceId(PetrinetModel petrinetModel, String sourceId){
		for(Arc arc : petrinetModel.getArcList()){
			if(arc.getSource().equals(sourceId)){
				String targetId = arc.getTarget();
				for(Arc arc1 : petrinetModel.getArcList()){
					if(arc1.getSource().equals(targetId)){
						String targetPlace = arc1.getTarget();
						return targetPlace;
					}
				}
			}
		}
		return null;
	}
	
	private Place getPlaceById(PetrinetModel petrinetModel, String id){
		for(Place place : petrinetModel.getPlaceList()){
			if(place.getId().equals(id)){
				return place;
			}
		}
		return null;
	}
} 
