package path.efsm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import model.efsm.EFSMModel;
import model.efsm.State;
import model.efsm.Transition;

public class PathSet {
	private EFSMModel efsm;
	private int statesNum;
	private int adjMatrix[][];
	private boolean hasCycle = false;
	private List<State> stateCycle = new ArrayList<State>();
	private List<String> paths = new ArrayList<String>();
	private List<ArrayList<State>> cycleList = new ArrayList<ArrayList<State>>();
	private List<ArrayList<State>> pathList = new ArrayList<ArrayList<State>>();

	public PathSet(EFSMModel efsm){
		this.efsm = efsm;
		this.statesNum = this.efsm.getStates().length;
		this.adjMatrix = new int[statesNum][statesNum];
		this.createPaths();
	}
	
	public boolean isHasCycle() {
		return hasCycle;
	}
	
	public void setHasCycle(boolean hasCycle) {
		this.hasCycle = hasCycle;
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
	
	public LinkedList<State> createRandomPathWithCycleByBase(ArrayList<State> path){
		int cycleSize = cycleList.size();
		
		//generate random number of times for choosing cycles
		Random rc = new Random();
		int cyNums= rc.nextInt(cycleSize) % cycleSize;
		int[] cyIndexes = new int[cyNums];
		int[] cyTimes = new int[cyNums];
		for(int i = 0; i < cyNums; i++){
			cyIndexes[i] = rc.nextInt(cycleSize) % cycleSize;
			cyTimes[i] = rc.nextInt(5);	
		}
		LinkedList<State> tPath = new LinkedList<State>();
		for(State s : path){
			tPath.add(s);
		}
		for(int i = 0; i < cyNums; i++){
			insertCycleToPath(cycleList.get(cyIndexes[i]), cyTimes[i], tPath);
		}
		return tPath;
	} 
	
	/**
	 * 使用邻接矩阵存储有向图
	 */
	public void initialAdjMatrix(){
		for (int j = 0; j < efsm.getStates().length; j++) {
			for (Transition transition : efsm.getStates()[j].getTransitions()) {
				adjMatrix[j][transition.getNext()] = 1;					
			}
		}
	}
	
	/**
	 * 判断有向图从源点S出发，是否存在环
	 * 若存在，将环中状态节点序列存储在cycleList中
	 * @param s 源点
	 */
	public void findCycle(State s) {
		int j;
		ArrayList<State> tempList;
		
		if ((j = stateCycle.indexOf(s)) != -1) {	
			hasCycle = true;
			tempList = new ArrayList<State>();
			while (j < stateCycle.size()) {
				tempList.add(stateCycle.get(j));
				j++;
			}
			
			//to remove duplicate and invalid cycle which will not be accessible in real application
			if(!(tempList.size() > 1 && (tempList.get(0).getIndex() > tempList.get(1).getIndex())) 
					&& cycleList.indexOf(tempList) == -1){
				cycleList.add(tempList);				
			}
			return;
		}else{
			stateCycle.add(s);			
		}

		for (int k = 0; k < statesNum; k++) {
			if (adjMatrix[s.getIndex()][k] == 1){
				findCycle(efsm.getStates()[k]);				
			}
		}
		stateCycle.remove(stateCycle.size() - 1);
	}
	
	/**
	 * 修改有向图邻接矩阵，从中去掉环
	 * @param listOfCycles
	 */
	public void removeCycle(List<ArrayList<State>> listOfCycles){
		for(ArrayList<State> list : listOfCycles){
	    	State[] states = list.toArray(new State[list.size()]);
	    	adjMatrix[states[list.size() - 1].getIndex()][states[0].getIndex()] = 0;
	    }
	}
	
	/**
	 * 找出无环有向图中从源点start出发到终点end之间的所有路径，
	 * 并将路径存储在pathList中
	 * @param start
	 * @param end
	 * @param res
	 */
	public void findPath(State start, State end, String res){
		start.setVisited(true);
		for(int i = 0; i < statesNum; i++){
			if(adjMatrix[start.getIndex()][i] == 0 || efsm.getStates()[i].isVisited() == true){
				continue;
			}
			if(efsm.getStates()[i] == end){
				paths.add(res+"-->"+end.getIndex());
				continue;
			}
			findPath(efsm.getStates()[i], end, res+"-->"+efsm.getStates()[i].getIndex());
			efsm.getStates()[i].setVisited(false);	
		}
	}
	
	public void transformPath(List<String> paths, List<ArrayList<State>> listOfPath){
		for(String s : paths){
			ArrayList<State> ss = new ArrayList<State>();
			String[] ins = s.split("-->");
			for(String i : ins){
				int index = Integer.parseInt(i);
				ss.add(efsm.getStates()[index]);
			}
			listOfPath.add(ss);
		}
	}
	
	/**
	 * 向路径tpath中插入环cyle，次数为times
	 * @param cycle
	 * @param times
	 * @param tPath
	 * @return
	 */
	public LinkedList<State> insertCycleToPath(ArrayList<State> cycle, int times, LinkedList<State>tPath){	
		boolean doInsert = true;
		if(times == 0){
			doInsert = false;
		}else{
			for(State s : cycle){
				if(!tPath.contains(s)){
					doInsert = false;
					break;
				}
			}
		}
		if(doInsert){
			int endPoint = tPath.indexOf(cycle.get(cycle.size() - 1));
			for(int i = 1; i <= times; i++){
				for(State s : cycle){
					tPath.add(++endPoint, s);
				}				
			}
		}
		return tPath;
	}
	
	/**
	 * 随机选择路径，向其中插入环，生成测试路径
	 * @return
	 */
	public LinkedList<State> createRandomCyclePath(){
		int cycleSize = cycleList.size();
		int pathSize = pathList.size();
		
		//generate random number of times for choosing cycles
		Random rc = new Random();
		int cyNums= rc.nextInt(cycleSize) % cycleSize;
		int[] cyIndexes = new int[cyNums];
		int[] cyTimes = new int[cyNums];
		for(int i = 0; i < cyNums; i++){
			cyIndexes[i] = rc.nextInt(cycleSize) % cycleSize;
			cyTimes[i] = rc.nextInt(5);
			System.out.print("loop " + cyIndexes[i] + "th cycle " + cyTimes[i] + " times : ");
			for(State s : cycleList.get(cyIndexes[i])){
				System.out.print(s.getIndex() + "-->");
			}
			System.out.println("");
		}
		
		//generate random index of target path
		Random rp = new Random();
		int pIndex = rp.nextInt(pathSize) % pathSize;
		System.out.println("on " + pIndex + "th path : ");
		for(State s : pathList.get(pIndex)){
			System.out.print(s.getIndex() + "-->");
		}
		System.out.println("");
		
		LinkedList<State> tPath = new LinkedList<State>();
		ArrayList<State> path = pathList.get(pIndex);
		for(State s : path){
			tPath.add(s);
		}
		for(int i = 0; i < cyNums; i++){
			insertCycleToPath(cycleList.get(cyIndexes[i]), cyTimes[i], tPath);
		}
		
		for(State s : tPath){
			System.out.print(s.getIndex() + "-->");
		}
		System.out.println("");
		return tPath;
	}
	
	public void createPaths(){
		initialAdjMatrix();
		
		findCycle(efsm.getStates()[0]);
	    if(!hasCycle){
	    	System.out.println("No Cycle."); 
	    }
	    
	    removeCycle(cycleList);

	    State start = efsm.getStates()[0];
	    State end = efsm.getStates()[statesNum - 1];
	    ArrayList<State> res = new ArrayList<State>();
	    res.add(start);
	    findPath(start, end, "" + 0);
	    transformPath(paths, pathList);
	}
	
	
	public void printInitialAdjMatrix(){
		// Test for initial Adjacent Matrix
		System.out.println("the Adjcent Matrix of EFSM Model is : ");
		System.out.println("***************************************");
		for(int i = 0; i < statesNum; i++){
			for(int j = 0; j < statesNum; j++){
				System.out.print(adjMatrix[i][j] + " ");				
			}
			System.out.println("");
		}
		System.out.println("***************************************");
	}
	
	public void printFindCycle(){
		// Test for findCycle
	    int count = 0;
	    for(ArrayList<State> list : cycleList){
	    	System.out.print("Cycle : " + ++count + "\t");
	    	State[] states = list.toArray(new State[list.size()]);
	    	for(State s : states){
	    		System.out.print(s.getIndex() + " ");
	    	}
	    	System.out.print("\n");
	    }
	}
	
	public void printRemoveCycle(){
		// Test for cycles removed
		System.out.println("the Adjcent Matrix of EFSM Model is : ");
		System.out.println("***********************************************");
		for(int i = 0; i < statesNum; i++){
			System.out.print("line " + i + "\t");
			for(int j = 0; j < statesNum; j++){
				System.out.print(adjMatrix[i][j] + " ");				
			}
			System.out.println("");
		}
		System.out.println("***********************************************");
	}
	
	public void printFindPath(){
		// Test for findPath
	    int count = 0;
	    for(ArrayList<State> list : pathList){
	    	System.out.print("Path : " + ++count + "\t");
	    	State[] states = list.toArray(new State[list.size()]);
	    	for(State s : states){
	    		System.out.print(s.getIndex() + " ");
	    	}
	    	System.out.print("\n");
	    }
	}
	
}
