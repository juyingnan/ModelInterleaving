package path.efsm;

import model.configInfo.ConfigInfo;
import model.configInfo.parser.ConfigInfoXMLParser;
import model.efsm.EFSMModel;
import model.efsm.parser.EFSMXMLParser;

public class PathSetTest {	
	/* Test for createPath */
	public static void main(String[] args){
		ConfigInfoXMLParser configParser = new ConfigInfoXMLParser();
		ConfigInfo configInfo = configParser.parserXml();
		String efPath = configInfo.getEFSMModelFilePath();
		EFSMXMLParser efsmParser = new EFSMXMLParser();
		EFSMModel efsm = efsmParser.parserXml(efPath);
		efsm.initStates();
		
		PathSet pathSet = new PathSet(efsm);
		pathSet.printInitialAdjMatrix();
	    pathSet.printFindCycle();
	    pathSet.printRemoveCycle();
	    pathSet.printFindPath();
		pathSet.createRandomCyclePath();
	}
}
