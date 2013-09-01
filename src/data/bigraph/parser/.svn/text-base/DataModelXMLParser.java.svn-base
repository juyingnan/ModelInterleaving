package data.bigraph.parser;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import data.bigraph.BigraphDataModel;
import data.bigraph.DataModelElement;
import data.bigraph.PropertyElement;

import parser.XMLParser;

public class DataModelXMLParser {
	public DataModelElement checkDataModelA(BigraphDataModel bigraphDataModel, String controlName){
		for(DataModelElement dme : bigraphDataModel.getDataModels()){
			if(dme.getControlName().equals(controlName)){
				return dme;
			}
		}
		return null;
	}
	
	public PropertyElement checkPropertyA(DataModelElement dataModelElement, String propertyName){
		for(PropertyElement pe : dataModelElement.getProperties()){
			if(pe.getPropertyName().equals(propertyName)){
				return pe;
			}
		}
		return null;
	}
	
	public BigraphDataModel parserXml(String fileName){
		BigraphDataModel bigraphDataModel = new BigraphDataModel();
		XMLParser dataModelXMLParser = new XMLParser(fileName);
		Document dataModelDocument = dataModelXMLParser.getDocument();
		Node bigraphRoot = dataModelDocument.getElementsByTagName("bigraph:root").item(0);
		if(bigraphRoot.hasChildNodes()){
			NodeList dataModels = bigraphRoot.getChildNodes();
			for(int i = 0; i < dataModels.getLength(); i++){
				Node dataModelElement = dataModels.item(i);
				if(dataModelElement.getNodeName().equals("bigraph:node")){
					DataModelElement controlElement = new DataModelElement(getAttriByName(dataModelElement, "control"));
					String valueType = null;
					if(dataModelElement.hasChildNodes()){
						NodeList properties = dataModelElement.getChildNodes();
						for(int j = 0; j < properties.getLength(); j++){
							Node property = properties.item(j);
							if(property.getNodeName().equals("bigraph:node")){
								String proName = getAttriByName(property, "control");
								String upper = null;
								String lower = null;
								String value = null;
								if(property.hasChildNodes()){
									NodeList values = property.getChildNodes();
									for(int k = 0; k < values.getLength(); k++){
										Node valueNode = values.item(k);
										if(valueNode.getNodeName().equals("bigraph:node")){
											if(valueNode.hasChildNodes()){
												NodeList links = valueNode.getChildNodes();
												for(int l = 0; l < links.getLength(); l++){
													Node link = links.item(l);
													if(link.getNodeName().equals("bigraph:port")){
														String linkName = getAttriByName(link, "link");
														if(linkName.contains("value_is")){
															value = getAttriByName(valueNode, "name");
															String controlName = getAttriByName(valueNode, "control");
															valueType = getControlSortByName(dataModelDocument, controlName);
														}else if(linkName.contains("upperBound")){
															upper = getAttriByName(valueNode, "name");
														}else if(linkName.contains("lowerBound")){
															lower = getAttriByName(valueNode, "name");
														}
													}
												}
											}
										}
									}
								}
								PropertyElement propertyElement = new PropertyElement(proName, upper, lower, value, valueType);
								controlElement.addProperty(propertyElement);
							}
						}
					}
					bigraphDataModel.addDataModel(controlElement);
				}
			}
		}
		return bigraphDataModel;
	}
	
	private String getAttriByName(Node n, String attriName){
		NamedNodeMap attrs = n.getAttributes();
		if(attrs.getNamedItem(attriName) != null){
			return attrs.getNamedItem(attriName).getNodeValue();
		}
		return null;
	}
	
	private String getControlSortByName(Document doc, String controlName){
		NodeList controls = doc.getElementsByTagName("signature:control");
		for(int i = 0; i < controls.getLength(); i++){
			Node control = controls.item(i);
			String name = getAttriByName(control, "name");
			if(controlName.equals(name)){
				return getAttriByName(control, "placesort");
			}
		}
		return null;
	}
}
