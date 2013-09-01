package data.bigraph.parser;

import data.bigraph.BigraphDataModel;
import model.configInfo.ConfigInfo;
import model.configInfo.parser.ConfigInfoXMLParser;

public class DataModelXMLParserTest {
	public static void main(String[] args){
		ConfigInfoXMLParser configParser = new ConfigInfoXMLParser();
		ConfigInfo configInfo = configParser.parserXml();
		String bdmPath = configInfo.getBigraphDataModelFilePath();
		DataModelXMLParser parser = new DataModelXMLParser();
		BigraphDataModel bigraphDataModel = parser.parserXml(bdmPath);
		
		bigraphDataModel.printBigraphDataModel();
	}
}
