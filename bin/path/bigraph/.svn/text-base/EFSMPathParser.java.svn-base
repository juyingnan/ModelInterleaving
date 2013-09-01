package path.bigraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import path.efsm.PathSet;

import model.configInfo.ConfigInfo;
import model.configInfo.parser.ConfigInfoXMLParser;
import model.efsm.EFSMModel;
import model.efsm.State;
import model.efsm.Transition;
import model.efsm.parser.EFSMXMLParser;
import model.service.Service;
import model.service.ServiceModel;
import model.service.parser.ServicesXMLParser;

public class EFSMPathParser {
	private List<ArrayList<State>> cycleList = null;
	private List<ArrayList<State>> pathList = null;
	private EFSMModel efsmModel = null;
	private List<ArrayList<GSPGChain>> gspgChainss = null;
	private ServiceModel serviceModel = null;
	private HashMap<String, String> relatedPatterns = null;
	private List<String> interestedPatterns = null;
	private ConfigInfo configInfo;

	public EFSMPathParser(){
		ConfigInfoXMLParser configParser = new ConfigInfoXMLParser();
		this.configInfo = configParser.parserXml();
		String efPath = this.configInfo.getEFSMModelFilePath();
		EFSMXMLParser efsmParser = new EFSMXMLParser();
		this.efsmModel = efsmParser.parserXml(efPath);
		this.efsmModel.initStates();
		
		String sfPath = this.configInfo.getServiceModelFilePath();
		String petrifPath = this.configInfo.getPetrinetModelFilePath();
		ServicesXMLParser serviceParser = new ServicesXMLParser();
		this.serviceModel = serviceParser.parserXml(sfPath, petrifPath);
		
		PathSet pathSet = new PathSet(this.efsmModel);
		this.cycleList = pathSet.getCycleList();
		this.pathList = pathSet.getPathList();
		this.gspgChainss = new ArrayList<ArrayList<GSPGChain>>();
		this.relatedPatterns = new HashMap<String, String>();
		this.interestedPatterns = new ArrayList<String>();
		this.findGetServicePutGetChain();
		this.findRelatedPatterns();
		this.findInterestedPatterns();
		this.outputRelatedAndInterestedPatterns();
	}
	
	public void outputRelatedAndInterestedPatterns(){
		PatternsWriter patternsWriter = new PatternsWriter(this.configInfo.getPatternsOutputPath());
		if(!this.relatedPatterns.isEmpty()){
			String[] keySet = this.relatedPatterns.keySet().toArray(new String[this.relatedPatterns.size()]);
			for(String key : keySet){
				patternsWriter.addNextPattern(key, this.relatedPatterns.get(key));
			}
		}
		if(!this.interestedPatterns.isEmpty()){
			for(String rule : this.interestedPatterns){
				patternsWriter.addInterestedPattern(rule);
			}
		}
		patternsWriter.savePatternFile();
	}
	
	public void printInterestedPatterns(){
		System.out.println("In printInterestedPatterns()**********************************");
		for(int i = 0; i < this.interestedPatterns.size(); i++){
			String pattern = this.interestedPatterns.get(i);
			System.out.println("Interested pattern " + i + " is " + pattern);
		}
	}
	
	public void findInterestedPatterns(){
		String[] keySet = this.relatedPatterns.keySet().toArray(new String[this.relatedPatterns.size()]);
		for(String key : keySet){
			if(!this.relatedPatterns.containsValue(key)){
				this.addInterestedPattern(key);
			}
		}
	}
	
	public void printRelatedPatterns(){
		System.out.println("In printRelatedPatterns()*********************************");
		String[] keySet = this.relatedPatterns.keySet().toArray(new String[this.relatedPatterns.size()]);
		for(String key : keySet){
			System.out.println("key : " + key + " value : " + this.relatedPatterns.get(key));
		}
	}

	public void findRelatedPatterns(){
		for(ArrayList<GSPGChain> gspgChains : this.gspgChainss){
			for(GSPGChain gspgChain : gspgChains){
				String currentPattern = gspgChain.getGetTran().getInput().split(":")[1];
				if(currentPattern.contains(" of ")){
					currentPattern = gspgChain.getGetTran().getGuard();
				}
				String nextPattern = (gspgChain.getGetCheckTran().getInput().split(" && ")[0]).split(":")[1];
				this.addRelatedPattern(currentPattern, nextPattern);
			}
		}
	}
	
	public void printGetServicePutGetChain(){
		System.out.println("In printGetServicePutGetChain()************************");
		for(int i = 0; i < this.gspgChainss.size(); i++){
			ArrayList<GSPGChain> gspgChains = this.gspgChainss.get(i);
			System.out.println("In Path " + i + " : ");		
			for(int j = 0; j < gspgChains.size(); j++){
				List<State> states = gspgChains.get(j).getStatesChain();
				System.out.print("GSPGChain " + j + " : ");
				for(int k = 0; k < states.size(); k++){
					System.out.print("M" + states.get(k).getIndex());	
					if(k != (states.size() - 1)){
						System.out.print(" --> ");
					}
				}
				String serviceName = gspgChains.get(j).getService().getName();
				System.out.println("   Service : " + serviceName + " ");	
			}
		}
	}
	
	public void findGetServicePutGetChain(){
		for(ArrayList<State> states : this.pathList){
			ArrayList<GSPGChain> gspChains = new ArrayList<GSPGChain>();
			int statesNum = states.size();
			if(statesNum >= 5){
				for(int j = 0; j < (statesNum - 4); j++){
					State state1 = states.get(j);
					State state2 = states.get(j + 1);
					State state3 = states.get(j + 2);
					State state4 = states.get(j + 3);
					State state5 = states.get(j + 4);
					List<Transition> trans = findGSPGChain(state1, state2, state3, state4, state5);
					if(trans != null && trans.size() == 4){
						List<State> gspChainStates = new ArrayList<State>();
						gspChainStates.add(state1);
						gspChainStates.add(state2);
						gspChainStates.add(state3);
						gspChainStates.add(state4);
						gspChainStates.add(state5);
						String serviceName = trans.get(1).getAction().split("call_")[1].split("[(]")[0];
						Service service = this.serviceModel.findServiceByName(serviceName);
						if(service != null && !service.getName().contains("check")){
							GSPGChain gspChain = new GSPGChain(trans.get(0), trans.get(1), trans.get(2), trans.get(3), gspChainStates, service);
							gspChains.add(gspChain);
						}
					}
				}
			}
			this.gspgChainss.add(gspChains);
		}
	}
	
	public List<Transition> findGSPGChain(State s1, State s2, State s3, State s4, State s5){
		List<Transition> trans = new ArrayList<Transition>();
		Transition tran1 = null;
		Transition tran2 = null;
		Transition tran3 = null;
		Transition tran4 = null;
		for(Transition tran : s1.getTransitions()){
			if(tran.getInitial() == s1.getIndex() && tran.getNext() == s2.getIndex()){
				tran1 = tran;
			}
		}
		for(Transition tran : s2.getTransitions()){
			if(tran.getInitial() == s2.getIndex() && tran.getNext() == s3.getIndex()){
				tran2 = tran;
			}
		}
		for(Transition tran : s3.getTransitions()){
			if(tran.getInitial() == s3.getIndex() && tran.getNext() == s4.getIndex()){
				tran3 = tran;
			}
		}
		for(Transition tran : s4.getTransitions()){
			if(tran.getInitial() == s4.getIndex() && tran.getNext() == s5.getIndex()){
				tran4 = tran;
			}
		}
		if(tran1 != null 
				&& tran2 != null 
				&& tran3 != null 
				&& tran4 != null
				&& tran1.getInput() != null 
				&& tran1.getGuard() != null
				&& tran2.getAction() != null 
				&& tran3.getAction() != null 
				&& tran4.getInput() != null){
			if(tran1.getInput().contains("get:") 
//					&& tran1.getGuard().contains(" changed")
					&& tran2.getAction().contains("call_") 
					&& tran3.getAction().contains("put:")
					&& tran4.getInput().contains("get:")){
				trans.add(tran1);
				trans.add(tran2);
				trans.add(tran3);
				trans.add(tran4);
				return trans;
			}
			return null;
		}
		return null;
	}
	
	public List<ArrayList<State>> getCycleList() {
		return cycleList;
	}

	public void setCycleList(List<ArrayList<State>> cycleList) {
		this.cycleList = cycleList;
	}

	public List<ArrayList<State>> getPathList() {
		return pathList;
	}

	public void setPathList(List<ArrayList<State>> pathList) {
		this.pathList = pathList;
	}
	
	public EFSMModel getEfsmModel() {
		return efsmModel;
	}

	public void setEfsmModel(EFSMModel efsmModel) {
		this.efsmModel = efsmModel;
	}

	public List<ArrayList<GSPGChain>> getGspgChainss() {
		return gspgChainss;
	}

	public void setGspgChainss(List<ArrayList<GSPGChain>> gspgChainss) {
		this.gspgChainss = gspgChainss;
	}

	public ServiceModel getServiceModel() {
		return serviceModel;
	}

	public void setServiceModel(ServiceModel serviceModel) {
		this.serviceModel = serviceModel;
	}
	
	public HashMap<String, String> getRelatedPatterns() {
		return relatedPatterns;
	}

	public void setRelatedPatterns(HashMap<String, String> relatedPatterns) {
		this.relatedPatterns = relatedPatterns;
	}
	
	public void addRelatedPattern(String currentPattern, String nextPattern){
		this.relatedPatterns.put(currentPattern, nextPattern);
	}
	
	public String getNextPattern(String currentPattern){
		return this.relatedPatterns.get(currentPattern);
	}
	
	public List<String> getInterestedPatterns() {
		return interestedPatterns;
	}

	public void setInterestedPatterns(List<String> interestedPatterns) {
		this.interestedPatterns = interestedPatterns;
	}
	
	public void addInterestedPattern(String pattern){
		this.interestedPatterns.add(pattern);
	}

	public ConfigInfo getConfigInfo() {
		return configInfo;
	}

	public void setConfigInfo(ConfigInfo configInfo) {
		this.configInfo = configInfo;
	}
}
