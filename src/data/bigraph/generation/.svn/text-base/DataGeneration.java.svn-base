package data.bigraph.generation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.configInfo.ConfigInfo;
import model.configInfo.parser.ConfigInfoXMLParser;
import data.bigraph.BigraphDataModel;
import data.bigraph.DataModelElement;
import data.bigraph.PropertyElement;
import data.bigraph.parser.DataModelXMLParser;

public class DataGeneration {
	private List<Integer> loopParams;
	private int counter;
	private ConfigInfo configInfo;
	private String outputText;
	private List<PropertyElement> propertyList;
	
	public DataGeneration(){
		ConfigInfoXMLParser configParser = new ConfigInfoXMLParser();
		this.configInfo = configParser.parserXml();
		this.loopParams = new ArrayList<Integer>();
		this.counter = 1;
		initPropertyListAndOutputText();
	}
	
	public void generateTestDataLoop(int loopTime){
		int nextLoopTime = -1;
		if(loopTime > 0){
			if(loopParams.isEmpty()){
				for(int i = 0; i <= loopTime; i++){
					loopParams.add(-1);
				}
			}
			nextLoopTime = loopTime - 1;
		}
		for(int i = 0; i < 5; i++){
			loopParams.set(loopParams.size() - 1 - loopTime, i);
			if(nextLoopTime >= 0){
				generateTestDataLoop(nextLoopTime);
			}else{
				this.outputText += counter + "\t";
				System.out.print(counter);
				counter++;
				for(int j = 0; j < loopParams.size(); j++){	
					int currentParam = loopParams.get(j);
					PropertyElement currentPE = this.getPropertyList().get(j);
					String lowerBound = currentPE.getLowerBound();
					String upperBound = currentPE.getUpperBound();
					this.outputText += generateInt(lowerBound, upperBound, currentParam) + "\t";
					System.out.print("\t" + generateInt(lowerBound, upperBound, currentParam));
				}
				this.outputText += "\n";
				System.out.println("");
			}
		}
		saveTestDataFile();
	}
	
	private void saveTestDataFile(){
		String tdPath = this.configInfo.getTestCaseOutputPath();
		PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(tdPath)));
			out.println(this.outputText);
			out.close();
		} catch (IOException e) {
			System.out.println("DataGeneration saveTestDataFile Error: " + e.getMessage());
		}       
	}

	public String[] generateDataByType(String... parameters){
		if(parameters.length == 0){
			System.out.println("number of parameter(s) error");
		} else if(parameters.length == 1 && parameters[0].equals("boolean")){
			String booleanData = generateBooleanData();
			return new String[]{booleanData};
		} else if(parameters.length == 3 && parameters[0].equals("int")){
			String[] intData = generateIntData(parameters[1], parameters[2]);
			return intData;
		}
		return null;
	}
	
	private String generateBooleanData(){
		Random rc = new Random();
		int flag = rc.nextInt();
		if(flag % 2 == 0){
			return "true";
		} else{
			return "false";			
		}
	}
	
	private String[] generateIntData(String lowerBound, String upperBound){
		String[] intData = new String[5];
		intData[1] = lowerBound;
		intData[3] = upperBound;
		Random rc = new Random();
		if(Integer.parseInt(lowerBound) == 0){
			lowerBound += 1;
		}
		intData[0] = rc.nextInt(Integer.parseInt(lowerBound)) + "";
		intData[2] = rc.nextInt(Integer.parseInt(upperBound) - Integer.parseInt(lowerBound)) + Integer.parseInt(lowerBound) + "";
		intData[4] = rc.nextInt(Integer.parseInt(upperBound)) + Integer.parseInt(upperBound) + "";
		for(String s : intData){
			System.out.print(s + " ");
		}
		System.out.println("\n");
		return intData;
	}
	
	public String generateInt(String lowerBound, String upperBound, int index){
		Random rc = new Random();
		String intData = null;
		if(index == 0){
			if(Integer.parseInt(lowerBound) == 0){
				lowerBound += 1;
			}
			intData = rc.nextInt(Integer.parseInt(lowerBound)) + "";
		} else if(index == 1){
			intData = lowerBound;
		} else if(index == 2){
			if(Integer.parseInt(lowerBound) == 0){
				lowerBound += 1;
			}
			intData = rc.nextInt(Integer.parseInt(upperBound) - Integer.parseInt(lowerBound))
					+ Integer.parseInt(lowerBound) + "";
		} else if(index == 3){
			intData = upperBound;
		} else if(index == 4){
			intData = rc.nextInt(Integer.parseInt(upperBound)) + Integer.parseInt(upperBound) + "";
		} else{
			System.out.println("Error: Index of int data is wrong!");
		}
		return intData;
	}
	
	private void initPropertyListAndOutputText(){
		this.propertyList = new ArrayList<PropertyElement>();
		this.outputText = "No.\t";
		String bdmPath = this.configInfo.getBigraphDataModelFilePath();
		DataModelXMLParser parser = new DataModelXMLParser();
		BigraphDataModel bigraphDataModel = parser.parserXml(bdmPath);
		for(DataModelElement dme : bigraphDataModel.getDataModels()){
			List<PropertyElement> properties = dme.getProperties();
			for(PropertyElement pe : properties){
				String paramName = pe.getPropertyName();
				this.outputText += paramName + "\t";
				this.propertyList.add(pe);
			}	
		}
		this.outputText += "\n";
	}

	public List<Integer> getLoopParams() {
		return loopParams;
	}

	public void setLoopParams(List<Integer> loopParams) {
		this.loopParams = loopParams;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public ConfigInfo getConfigInfo() {
		return configInfo;
	}

	public void setConfigInfo(ConfigInfo configInfo) {
		this.configInfo = configInfo;
	}

	public String getOutputText() {
		return outputText;
	}

	public void setOutputText(String outputText) {
		this.outputText = outputText;
	}

	public List<PropertyElement> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<PropertyElement> propertyList) {
		this.propertyList = propertyList;
	}

	public static void main(String[] args){
		DataGeneration dataGeneration = new DataGeneration();
		dataGeneration.generateTestDataLoop(dataGeneration.getPropertyList().size() - 1);
	}
}
