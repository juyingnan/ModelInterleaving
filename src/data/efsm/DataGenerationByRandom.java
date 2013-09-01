package data.efsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import path.efsm.PathSet;

import model.configInfo.ConfigInfo;
import model.configInfo.parser.ConfigInfoXMLParser;
import model.efsm.EFSMModel;
import model.efsm.State;
import model.efsm.Transition;
import model.efsm.parser.EFSMXMLParser;

public class DataGenerationByRandom {
	public static int MIN_VARNUM = 14;
	public EFSMModel efsm;
	
	public void dataGeneration(EFSMModel efsm){
		this.efsm = efsm;
		HashMap<String, String> data = efsm.getData();
		String[] keySet = data.keySet().toArray(new String[data.size()]);
		/*
		System.out.println("*************** key in data ***************");
		for(String s : keySet){
			System.out.println("key : " + s);
		}
		System.out.println("*******************************************");
		*/
		
		ArrayList<ArrayList<String>> varList = new ArrayList<ArrayList<String>>();
		
		PathSet pathSet = new PathSet(efsm);
		int pathNum = pathSet.getPathList().size();
		boolean[] isValid = new boolean[pathNum];

		//generate random number of guards in data
		Random rc = new Random();
		int vNums = 0;
		while(vNums < MIN_VARNUM){
			vNums = rc.nextInt(data.size() + 1) % data.size();			
		}
		System.out.print("number of guard conditions is " + vNums + "£º\n");
		
		//generate index of guards in data
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		for (int i = 0; i < vNums; ) {
			int index = 0;
			while(indexes.contains(index)){
				index = rc.nextInt(data.size() + 1) % data.size();
			}
			System.out.print(index + "\t");
			i++;
			indexes.add(index);
			ArrayList<String> var = new ArrayList<String>();
			System.out.print(keySet[index] + "\n");
			var.add(keySet[index]);
			varList.add(var);
		}
		System.out.print("\n");

		for(int i = 0; i < pathNum; i++){
			//valid if all the guards on path covered by varList
			ArrayList<State> path = pathSet.getPathList().get(i);
			System.out.println("path " + i + " states' number is : " + path.size());
			isValid[i] = false;
			ArrayList<Transition> transOnPath = efsm.getTransOnPath(path);
			ArrayList<ArrayList<String>> guardOnPath = new ArrayList<ArrayList<String>>();
			for(Transition t : transOnPath){
				ArrayList<String> conditions = efsm.getGuardByTransition(t);
				if(!(conditions.size() == 1 && conditions.contains("no guard"))){
					boolean condsValid = false;
					guardOnPath.add(conditions);
					for(String condition : conditions){
						boolean condValid = false;
						for(ArrayList<String> var : varList){
							if(var.get(0).equals(efsm.normalizeGuard(condition))){
								condValid = true;
								break;
							}
						}
						condsValid = condValid;
						if(!condsValid){
							System.out.print("condition does not exist: " + condition + "\n");
							break;
						}
					}
					isValid[i] = condsValid;
					if(!condsValid){
						break;
					}
				}
			}
			//generate data for path in pathSet with guard(s) covered by the varList
			if(isValid[i]){
				System.out.print("var list contains all conditions on path " + i + " : ");					
				for(State s : path){
					System.out.print(s.getIndex() + " ");
				}
				System.out.print("\n");
				
				//create random path with cycles with the path left
				LinkedList<State> tpath = pathSet.createRandomPathWithCycleByBase(path);
				
				ArrayList<Transition> trans = efsm.getTransOnPath(tpath);
				for (Transition t : trans) {
					ArrayList<String> guards = efsm.getGuardByTransition(t);
					if (!(guards.size() == 1 && guards.contains("no guard"))) {
						for (String guard : guards) {
							setDataValueForTest(efsm, guard, varList);
						}
					}
				}
				
				System.out.println("---------------------------Data(Start) : ---------------------------");
				for(ArrayList<String> var : varList){
					System.out.print(var.get(0) + " : ");
					if(var.size() > 1){
						for(int m = 1; m < var.size(); m++){
							System.out.print(var.get(m) + " ");
						}	
					}
					else{
						System.out.print("any");
					}
					System.out.print("\n");
				}
				System.out.println("----------------------------Data(End) : ----------------------------\n");
				
				System.out.println("************************* Target Test Path : ************************");
				
				for(State s : tpath){
					System.out.print(s.getIndex() + "-->");
				}
				System.out.println("\n");
				
				//reset varList
				for(ArrayList<String> var : varList){
					String guard = var.get(0);
					var.clear();
					var.add(guard);
				}	
			}
		}	
	}
	
	public void setDataValueForTest(EFSMModel efsm, String guard, ArrayList<ArrayList<String>> varList){
		String guardNormlized = efsm.normalizeGuard(guard);
		for(ArrayList<String> var : varList){
			if(var.get(0).equals(guardNormlized)){
				if(guard.contains("no change")){
					var.add("false");
				}
				else if(guard.contains("changed")){
					var.add("true");
				}
				else if(guard.contains("== true")){
					var.add("true");
				}
				else if(guard.contains("== false")){
					var.add("false");
				}
				break;
			}
		}
		
	}
	
	public ArrayList<ArrayList<String>> dataGenerationForPath(EFSMModel efsm, PathSet pathSet, int pIndex, LinkedList<State> tpath){
		String[] keySet = efsm.getData().keySet().toArray(new String[efsm.getData().size()]);		
		ArrayList<ArrayList<String>> varList = new ArrayList<ArrayList<String>>();
		for(String key : keySet){
			ArrayList<String> var = new ArrayList<String>();
			var.add(key);
			varList.add(var);
		}
			
		ArrayList<Transition> trans = efsm.getTransOnPath(tpath);
		for (Transition t : trans) {
			ArrayList<String> guards = efsm.getGuardByTransition(t);
			if (!(guards.size() == 1 && guards.contains("no guard"))) {
				for (String guard : guards) {
					setDataValueForTest(efsm, guard, varList);
				}
			}
		}
		
		ArrayList<ArrayList<String>> vars = new ArrayList<ArrayList<String>>();
		System.out.println("---------------------------Data(Start) : ---------------------------");
		for (ArrayList<String> var : varList) {
			if (var.size() > 1) {
				System.out.print(var.get(0) + " : ");
				for (int m = 1; m < var.size(); m++) {
					System.out.print(var.get(m) + " ");
				}
				System.out.print("\n");
				vars.add(var);
			}
		}
		System.out.println("----------------------------Data(End) : ----------------------------\n");

		System.out.println("************************* Target Test Path : ************************");

		for (State s : tpath) {
			System.out.print(s.getIndex() + "-->");
		}
		System.out.println("\n");
		
		return vars;
	}
		
	public static void main(String[] args){
		ConfigInfoXMLParser configParser = new ConfigInfoXMLParser();
		ConfigInfo configInfo = configParser.parserXml();
		String efPath = configInfo.getEFSMModelFilePath();
		EFSMXMLParser efsmParser = new EFSMXMLParser();
		EFSMModel efsm = efsmParser.parserXml(efPath);
		efsm.initStates();
		DataGenerationByRandom dg = new DataGenerationByRandom();
		dg.dataGeneration(efsm);	
	}
	
}
