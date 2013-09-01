package data.bigraph;

import java.util.ArrayList;
import java.util.List;

public class BigraphDataModel {
	private List<DataModelElement> dataModels;
	
	public BigraphDataModel(){
		this.dataModels = new ArrayList<DataModelElement>();
	}

	public List<DataModelElement> getDataModels() {
		return dataModels;
	}

	public void setDataModels(List<DataModelElement> dataModels) {
		this.dataModels = dataModels;
	}
	
	public void addDataModel(DataModelElement dataModel){
		this.dataModels.add(dataModel);
	}
	
	public void generateDataModels(List<String> controlNames){
		for(DataModelElement dme: dataModels){
			for(int i = 0; i < controlNames.size(); i++){
				DataModelElement newdme = dme.generateDataModelElement(controlNames.get(i));
				dataModels.add(newdme);
				newdme.printDataModelElement();
			}
		}
	}
	
	public void printBigraphDataModel(){
		System.out.println("***********************BigraphDataModel*****************************");
		for(DataModelElement element : this.dataModels){
			System.out.println("**************DataModelElement*****************");
			element.printDataModelElement();
		}
	}
}
