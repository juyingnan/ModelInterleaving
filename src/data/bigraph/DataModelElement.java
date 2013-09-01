package data.bigraph;

import java.util.ArrayList;
import java.util.List;

import data.bigraph.generation.DataGeneration;

public class DataModelElement {
	private String controlName;
	private List<PropertyElement> properties;
	
	public DataModelElement(String name){
		this.controlName = name;
		this.properties = new ArrayList<PropertyElement>();
	}
	
	public DataModelElement generateDataModelElement(String name){
		DataGeneration dg = new DataGeneration();
		DataModelElement newDataModelElement = new DataModelElement(name);
		newDataModelElement.setProperties(this.getProperties());
		for(PropertyElement pe : newDataModelElement.getProperties()){
			pe.setValue(dg.generateInt(pe.getLowerBound(), pe.getUpperBound(), 2));
		}
		return newDataModelElement;
	}

	public String getControlName() {
		return controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	public List<PropertyElement> getProperties() {
		return properties;
	}
	
	public void setProperties(List<PropertyElement> properties) {
		this.properties = properties;
	}
	
	public void addProperty(PropertyElement property){
		this.properties.add(property);
	}
	
	public void printDataModelElement(){
		System.out.println("Element Name: " + this.controlName);
		System.out.println("********PropertyElement****************");
		for(PropertyElement element : this.properties){
			element.printPropertyElement();
		}
	}
}
