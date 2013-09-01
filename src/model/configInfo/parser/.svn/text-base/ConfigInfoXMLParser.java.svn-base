package model.configInfo.parser;

import model.configInfo.ConfigInfo;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import parser.XMLParser;

public class ConfigInfoXMLParser {

	public ConfigInfo parserXml() {
		ConfigInfo configInfo = new ConfigInfo();
		XMLParser xmlParser = new XMLParser("doc/ConfigInfo.xml");
		Document document = xmlParser.getDocument();
		Node serverIP = document.getElementsByTagName("ServerIP").item(0);
		configInfo.setServerIP(getAttriByName(serverIP, "ip"));
		Node bigraphFile = document.getElementsByTagName("BigraphModel").item(0);
		configInfo.setBigraphModelFilePath(getAttriByName(bigraphFile, "filepath"));
		configInfo.setBigraphServerPort(Integer.parseInt(getAttriByName(bigraphFile, "serverport")));
		Node bigraphDataFile = document.getElementsByTagName("BigraphDataModel").item(0);
		configInfo.setBigraphDataModelFilePath(getAttriByName(bigraphDataFile, "filepath"));
		Node efsmFile = document.getElementsByTagName("EFSMModel").item(0);
		configInfo.setEFSMModelFilePath(getAttriByName(efsmFile, "filepath"));
		Node petrinetFile = document.getElementsByTagName("PetrinetModel").item(0);
		configInfo.setPetrinetModelFilePath(getAttriByName(petrinetFile, "filepath"));
		Node serviceFile = document.getElementsByTagName("ServiceModel").item(0);
		configInfo.setServiceModelFilePath(getAttriByName(serviceFile, "filepath"));
		configInfo.setServiceServerPort(Integer.parseInt(getAttriByName(serviceFile, "serverport")));
		Node srtcstFile = document.getElementsByTagName("SortingConstraint").item(0);
		configInfo.setSortingConstraintPath(getAttriByName(srtcstFile, "filepath"));
		Node bmgFile = document.getElementsByTagName("BGMFile").item(0);
		configInfo.setBGMFilePath(getAttriByName(bmgFile, "filepath"));
		Node PatternsOutputFile = document.getElementsByTagName("PatternsOutput").item(0);
		configInfo.setPatternsOutputPath(getAttriByName(PatternsOutputFile, "filepath"));
		Node TestCaseOutputFile = document.getElementsByTagName("TestCaseOutput").item(0);
		configInfo.setTestCaseOutputPath(getAttriByName(TestCaseOutputFile, "filepath"));
		Node TomcatRunPath = document.getElementsByTagName("TomcatRunPath").item(0);
		configInfo.setTomcatRunPath(getAttriByName(TomcatRunPath, "path"));
		Node OutputByBigMC = document.getElementsByTagName("OutputByBigMC").item(0);
		configInfo.setOutputByBigMC(getAttriByName(OutputByBigMC, "filepath"));
		Node DefinePathOutputFile = document.getElementsByTagName("DefinePathOutput").item(0);
		configInfo.setDefinePathOutputPath(getAttriByName(DefinePathOutputFile, "filepath"));
		return configInfo;
	} 

	private String getAttriByName(Node n, String attriName){
		NamedNodeMap attrs = n.getAttributes();
		if(attrs.getNamedItem(attriName) != null){
			return attrs.getNamedItem(attriName).getNodeValue();
		}
		return null;
	}
}
