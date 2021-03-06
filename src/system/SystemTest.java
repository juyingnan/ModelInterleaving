package system;

import interleaving.client.EFSMModelClients;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import data.bigraph.generation.DataGeneration;
import data.efsm.DataGenerationByRandom;

import path.bigraph.EFSMPathParser;
import path.efsm.PathSet;

import model.configInfo.ConfigInfo;
import model.configInfo.parser.ConfigInfoXMLParser;
import model.efsm.EFSMModel;
import model.efsm.State;
import model.efsm.parser.EFSMXMLParser;

public class SystemTest {
	private PathSet pathSet;
	private EFSMModel efsm;
	private String tomcatPath;
	private BufferedReader strin;
	private DataGenerationByRandom dg;
	private LinkedList<State> tpath;
	private int pIndex;
	private String bgmFilePath;
	private String sortingConstraintFilePath;
	private String patternsFilePath;
	private String outputPathByBigMC;
	private String definePathOutputFilePath;
	
	public SystemTest(){
		ConfigInfoXMLParser configParser = new ConfigInfoXMLParser();
		ConfigInfo configInfo = configParser.parserXml();
		String efPath = configInfo.getEFSMModelFilePath();
		this.tomcatPath = configInfo.getTomcatRunPath();
		EFSMXMLParser efsmParser = new EFSMXMLParser();
		this.efsm = efsmParser.parserXml(efPath);
		pathSet = createPathWithoutCycle(efsm);
		this.bgmFilePath = configInfo.getBGMFilePath();
		this.sortingConstraintFilePath = configInfo.getSortingConstraintPath();
		this.patternsFilePath = configInfo.getPatternsOutputPath();
		this.outputPathByBigMC = configInfo.getOutputByBigMC();
		this.definePathOutputFilePath = configInfo.getDefinePathOutputPath();
		
		this.dg = new DataGenerationByRandom();
		
		this.strin = new BufferedReader(new InputStreamReader(System.in));
	}

	public static void main(String[] args) throws IOException {
		SystemTest sysTest = new SystemTest();	
		sysTest.optionsAtTopLevel();
	}
	
	public void exitCurrentProcess() {
		System.out.println("退出当前程序");
		System.exit(0);
	}
	
	public void optionsAtTopLevel() throws IOException{
		/**
		 * 功能点：
		 * 1. 单一模型单元测试
		 * 2. 多模型交互测试
		 */
		System.out.println("请选择功能编号：");
		System.out.println("1 单一模型单元测试");
		System.out.println("2 多模型交互测试");
		System.out.println("q 退出当前程序");
		System.out.println("h 菜单列表信息");
		
		String c = strin.readLine();

		while (!"1".equals(c) && !"2".equals(c) && !"q".equals(c) && !"h".equals(c)) {
			System.err.println("Error：输入功能编号错误，请重新输入");
			c = strin.readLine();
		}
		if (c.equals("q")) {
			exitCurrentProcess();
		} else if (c.equals("h")){
			listAllOptions();
		} else if (c.equals("1")){
			optionsAtLevel1();
		} else {
			optionsAtLevel2();
		}
		optionsAtTopLevel();
	}
	
	public void optionsAtLevel1() throws IOException{
		/**
		 * 1: 无先后顺序的功能点：
		 * 1.1 选择.smd.xml,生成pattern.xml
		 * 1.2 选择.smd.xml,生成测试路径和数据
		 * 1.3 选择DataModel.xml，生成测试数据集
		 */
		System.out.println("请选择功能编号：");
		System.out.println("1.1 选择.smd.xml,生成pattern.xml");
		System.out.println("1.2 选择.smd.xml,生成测试路径和数据");
		System.out.println("1.3 选择DataModel.xml，生成测试数据集");
		System.out.println("r 返回上级菜单");
		System.out.println("q 退出当前程序");
		
		String c = strin.readLine();

		while (!"1.1".equals(c) && !"1.2".equals(c) && !"1.3".equals(c) && !"q".equals(c) && !"r".equals(c)) {
			System.err.println("Error：输入功能编号错误，请重新输入");
			c = strin.readLine();
		}
		if (c.equals("q")) {
			exitCurrentProcess();
		} else if (c.equals("r")){
			optionsAtTopLevel();
		} 
		else {
			String s;
			if(c.equals("1.1")){
				s = "选择.smd.xml,生成pattern.xml";
			} else if(c.equals("1.2")){
				s = "选择.smd.xml,生成测试路径和数据";
			} else{
				s = "选择DataModel.xml，生成测试数据集";
			}
			System.out.println("输入功能编号为： " + c + "." + s);
			
			/**
			 * 选择.smd.xml生成Patterns.xml
			 * 保存至默认位置: /doc/Patterns.xml
			 */
			if (c.equals("1.1")) {
				new EFSMPathParser();
				
				System.out.println("Patterns.xml创建结束，请查看/doc/Patterns.xml");
				
				optionsAtLevel1_1();
			}
			/**
			 * 选择.smd.xml，生成测试路径和数据
			 */
			else if (c.equals("1.2")) {
				pathSet.printFindPath(); //输出无环路径
				
				System.out.println("请选择功能编号：");
				System.out.println("1.2.1 单一无环路径生成测试路径和数据");
				System.out.println("1.2.2 所有无环路径生成测试路径和数据");
				System.out.println("r 返回上级菜单");
				System.out.println("q 退出当前程序");
				
				String pOption = strin.readLine();
				
				while (!"1.2.1".equals(pOption) && !"1.2.2".equals(pOption) && !"q".equals(pOption) && !"r".equals(pOption)) {
					System.err.println("Error：输入功能编号错误，请重新输入");
					pOption = strin.readLine();
				}
				if (pOption.equals("q")) {
					exitCurrentProcess();
				} else if (pOption.equals("r")){
					optionsAtLevel1();
				} 
				else {
					if(pOption.equals("1.2.1")){
						optionsAtLevel1_2_Single();				
					} else{
						optionsAtLevel1_2_All();
					}
				}
				
			}
			/**
			 * 选择DataModel.xml，生成测试数据集
			 */
			else {
				optionsAtLevel1_3();
			}
		}
	}
	
	public void optionsAtLevel1_1() throws IOException{
		/**
		 * 1.1: 生成pattern.xml后，后续功能点：
		 * 1.1.1 选择.bgm;
		 * 1.1.2 选择SortingConstraint.xml；
		 * 1.1.3 选择Patterns.xml
		 * 1.1.4 调用BigMC生成路径集.txt，保存至默认位置: /doc/.txt
		 */
		System.out.println("请选择功能编号：");
		System.out.println("1.1.1 选择.bgm文件");
		System.out.println("1.1.2 选择SortingConstraint.xml");
		System.out.println("1.1.3 选择Patterns.xml");
		System.out.println("1.1.4 调用BigMC生成路径集.txt");
		System.out.println("r 返回上级菜单");
		System.out.println("q 退出当前程序");

		String fOption = strin.readLine();
		while (!"1.1.1".equals(fOption) && !"1.1.2".equals(fOption) && !"1.1.3".equals(fOption) && !"1.1.4".equals(fOption) && !"q".equals(fOption) && !"r".equals(fOption)) {
			System.err.println("Error：输入功能编号错误，请重新输入");
			fOption = strin.readLine();
		}
		if (fOption.equals("q")) {
			exitCurrentProcess();
		} else if (fOption.equals("r")){
			optionsAtLevel1();
		} else {
			/**
			 * 略去文件选择过程，默认选择配置文件中的默认文件:
			 *  /doc/.bgm; /doc/SortingConstraint.xml; /doc/Patterns.xml
			 * 直接进入1.1.4  调用BigMC生成路径集，保存至默认位置: /doc/.txt
			 */
			if("1.1.4".equals(fOption)){
				// 调用BigMC，保存路径集至默认位置: /doc/.txt
				optionsAt1_1_4();
			}
		}
	}
	
	/**
	 * 从所有的guards中随机选取部分或全部
	 * 针对所有从无环路径生成的带环目标路径生成测试数据
	 */
	public void optionsAtLevel1_2_All() throws IOException{
		dg.dataGeneration(efsm);		
	}
	
	/**
	 * 1.2: 生成无环路径后，后续功能点：
	 * (选取其中一条无环路径，生成目标测试路径和数据)
	 * 1.2.1 选择无环路径;
	 * 1.2.2 生成目标测试路径；
	 * 1.2.3 生成测试数据
	 */
	public void optionsAtLevel1_2_Single() throws IOException{				
		System.out.println("请选择功能编号：");
		System.out.println("1.2.1.1 选择无环路径");
		System.out.println("r 返回上级菜单");
		System.out.println("q 退出当前程序");
		
		String pOption1 = strin.readLine();
		
		while (!"1.2.1.1".equals(pOption1) && !"q".equals(pOption1) && !"r".equals(pOption1)) {
			System.err.println("Error：输入功能编号错误，请重新输入");
			pOption1 = strin.readLine();
		}
		if (pOption1.equals("q")) {
			exitCurrentProcess();
		} else if (pOption1.equals("r")){
			optionsAtLevel1();
		} else {
			optionsAtLevel1_2_1();
		}
	}
	
	public void optionsAtLevel1_2_1() throws IOException{
		System.out.println("请选择功能编号：");
		System.out.println("i 请选择路径编号：");
		System.out.println("r 返回上级菜单");
		System.out.println("q 退出当前程序");
		
		String pOption = strin.readLine();
		
		while (!"i".equals(pOption) && !"q".equals(pOption) && !"r".equals(pOption)) {
			System.err.println("Error：输入功能编号错误，请重新输入");
			pOption = strin.readLine();
		}
		if (pOption.equals("q")) {
			exitCurrentProcess();
		} else if (pOption.equals("r")){
			optionsAtLevel1_2_Single();
		} else {
			System.out.print("输入编号为：");
			try{
				pIndex = Integer.parseInt(strin.readLine());							
				// 判断输入编号数字是否在无环路径集编号中，是：跳到下一级菜单；否：继续选择或退出
				while(!(pIndex > 0 && pIndex <= pathSet.getPathList().size())){
					System.err.println("Error：输入路径编号超出范围，请重新输入！");
					pIndex = Integer.parseInt(strin.readLine());
				}
			} catch(NumberFormatException ne){
				System.err.println("Error：输入路径编号格式错误，请重新输入！");
			}
			
			optionsAtLevel1_2_2();			
		}
		
	}
	
	public void optionsAtLevel1_2_2() throws IOException{
		System.out.println("请选择功能编号：");
		System.out.println("1.2.1.2 生成目标测试路径");
		System.out.println("r 返回上级菜单");
		System.out.println("q.退出当前程序");
		
		String pOption2 = strin.readLine();
		
		while (!"1.2.1.2".equals(pOption2) && !"q".equals(pOption2) && !"r".equals(pOption2)) {
			System.err.println("Error：输入功能编号错误，请重新输入");
			pOption2 = strin.readLine();
		}
		if (pOption2.equals("q")) {
			exitCurrentProcess();
		} else if (pOption2.equals("r")){
			optionsAtLevel1_2_1();
		} else {
			System.out.println("目标测试路径为： ");
			tpath = createRandomPathWithCycleByBase(pathSet, pIndex - 1);
		}
		
		optionsAtLevel1_2_3();
	}
	
	public void optionsAtLevel1_2_3() throws IOException{
		System.out.println("请选择功能编号：");
		System.out.println("1.2.1.3 生成测试数据");
		System.out.println("r 返回上级菜单");
		System.out.println("q 退出当前程序");
		
		String pOption3 = strin.readLine();
		
		while (!"1.2.1.3".equals(pOption3) && !"q".equals(pOption3) && !"r".equals(pOption3)) {
			System.err.println("Error：输入功能编号错误，请重新输入");
			pOption3 = strin.readLine();
		}
		if (pOption3.equals("q")) {
			exitCurrentProcess();
		} else if (pOption3.equals("r")){
			optionsAtLevel1_2_2();
		} else {
			System.out.println("测试数据如下： ");
			generateDataForTargetPath(efsm, pathSet, pIndex - 1, tpath);
		}				
	}
	
	public void optionsAtLevel1_3() throws IOException{
		DataGeneration dataGeneration = new DataGeneration();
		dataGeneration.generateTestDataLoop(dataGeneration.getPropertyList().size() - 1);
	}
	
	public void optionsAtLevel2() throws IOException{
		System.out.println("请选择功能编号：");
		System.out.println("2.1 选择路径集.txt文件");
		System.out.println("2.2 选择.smd.xml文件");
		System.out.println("2.3 选择ServiceModel.xml和petrinet模型.xml文件");
		System.out.println("2.4 多模型交互测试");
		System.out.println("r 返回上级菜单");
		System.out.println("q 退出当前程序");
		
		String interOption = strin.readLine();
		while (!"2.1".equals(interOption) && !"2.2".equals(interOption) && !"2.3".equals(interOption) && !"2.4".equals(interOption) && !"q".equals(interOption) && !"r".equals(interOption)) {
			System.err.println("Error：输入功能编号错误，请重新输入");
			interOption = strin.readLine();
		}
		if (interOption.equals("q")) {
			exitCurrentProcess();
		} else if (interOption.equals("r")){
			optionsAtLevel1();
		} else {
		/**
		 * 略去文件选择过程，默认选择配置文件中的默认文件:
		 *  /doc/.txt：BigMC生成路径集文件; 
		 *  /doc/.smd.xml：EFSM模型描述文件;
		 *  /doc/ServiceModel.xml：Services描述文件
		 *  /doc/processXXXX.xml：流程服务petrinet模型描述文件
		 * 直接进入2.4 多模型交互测试
		 */
			if("2.4".equals(interOption)){
				try {
//					Runtime.getRuntime().exec("cmd.exe   /C   start  tomcat7.exe ",
//												null, 
//												new File(tomcatPath));
					
					Runtime.getRuntime().exec("cmd.exe   /C   start  startup.bat ",
							null, 
							new File(tomcatPath));
					
					try {
						Thread.sleep(5500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				EFSMModelClients EFSMModelClients = new EFSMModelClients();
				EFSMModelClients.EFSMModelClientsRun();	
			}
		}
	}
	
	public PathSet createPathWithoutCycle(EFSMModel efsm){
		efsm.initStates();
		PathSet pathSet = new PathSet(efsm);
		return pathSet;
	}
	
	public LinkedList<State> createRandomPathWithCycleByBase(PathSet pathSet, int index){
		LinkedList<State> tpath = pathSet.createRandomPathWithCycleByBase(pathSet.getPathList().get(index));
		for(State s : tpath){
			System.out.print(s.getIndex() + " ");
		}
		System.out.print("\n");
		return tpath;
	}
	
	public void generateDataForTargetPath(EFSMModel efsm, PathSet pathSet, int pIndex, LinkedList<State> tpath){
		dg.dataGenerationForPath(efsm, pathSet, pIndex, tpath);
	}
	
	public void listAllOptions(){
		System.out.println("请选择功能编号：");
		System.out.println("1 单一模型单元测试");
		System.out.println("\t1.1 选择.smd.xml,生成pattern.xml");
		System.out.println("\t\t1.1.1 选择.bgm文件");
		System.out.println("\t\t1.1.2 选择SortingConstraint.xml");
		System.out.println("\t\t1.1.3 选择Patterns.xml");
		System.out.println("\t\t1.1.4 调用BigMC生成路径集.txt");
		System.out.println("\t1.2 选择.smd.xml,生成测试路径和数据");
		System.out.println("\t\t1.2.1 单一无环路径生成测试路径和数据");
		System.out.println("\t\t\t1.2.1.1 选择无环路径");
		System.out.println("\t\t\t1.2.1.2 生成目标测试路径");
		System.out.println("\t\t\t1.2.1.3 生成测试数据");
		System.out.println("\t\t1.2.2 所有无环路径生成测试路径和数据");
		System.out.println("\t1.3 选择DataModel.xml，生成测试数据集");
		System.out.println("2 多模型交互测试");
		System.out.println("\t2.1 选择路径集.txt文件");
		System.out.println("\t2.2 选择.smd.xml文件");
		System.out.println("\t2.3 选择ServiceModel.xml和petrinet模型.xml文件");
		System.out.println("\t2.4 多模型交互测试");
		System.out.println("q 退出当前程序");
		System.out.println("h 菜单列表信息");
	}
	
	public void optionsAt1_1_4() throws IOException{
		System.out.println("请选择功能编号：");
		System.out.println("1.1.4.1  基本的Bigraph模型检测");
		System.out.println("1.1.4.2  含Sorting约束的Bigraph模型检测");
		System.out.println("1.1.4.3  含数据流分析的Bigraph模型检测");
		System.out.println("1.1.4.4  含关注模式分析的Bigraph模型检测");
		System.out.println("r 返回上级菜单");
		System.out.println("q 退出当前程序");
		
		String bigmcOption = strin.readLine();
		while (!"1.1.4.1".equals(bigmcOption) && !"1.1.4.2".equals(bigmcOption) && !"1.1.4.3".equals(bigmcOption) && !"1.1.4.4".equals(bigmcOption) && !"q".equals(bigmcOption) && !"r".equals(bigmcOption)) {
			System.err.println("Error：输入功能编号错误，请重新输入");
			bigmcOption = strin.readLine();
		}
		if (bigmcOption.equals("q")) {
			exitCurrentProcess();
		} else if (bigmcOption.equals("r")){
			optionsAtLevel1_1();
		}  else if (bigmcOption.equals("1.1.4.1")){
			optionsAtLevel1_1_4_1();
		} else if (bigmcOption.equals("1.1.4.2")){
				optionsAtLevel1_1_4_2();
		} else if (bigmcOption.equals("1.1.4.3")){
			optionsAtLevel1_1_4_3();
		} else {
			optionsAtLevel1_1_4_4();
		}
	}
	
	public void optionsAtLevel1_1_4_1() throws IOException{
		bigMCFunctionSupport(2, new String[]{"-G", outputPathByBigMC, bgmFilePath});
		optionsAt1_1_4();
	}
	
	public void optionsAtLevel1_1_4_2() throws IOException{
		bigMCFunctionSupport(1, new String[]{"-G", outputPathByBigMC, "-sf", sortingConstraintFilePath, bgmFilePath});
		optionsAt1_1_4();
	}
	
	public void optionsAtLevel1_1_4_3() throws IOException{
		bigMCFunctionSupport(2, new String[]{"-D", "alldefs", outputPathByBigMC, definePathOutputFilePath, bgmFilePath});
		optionsAt1_1_4();
	}
	
	public void optionsAtLevel1_1_4_4() throws IOException{
		bigMCFunctionSupport(2, new String[]{"-PF", patternsFilePath, outputPathByBigMC, bgmFilePath});
		optionsAt1_1_4();
	}
	
	/**
	 * @param categoray: 1. bigMC/bigMC with sorting; 2.bigMC with data flow analysis/bigMC with pattern analysis
	 * @param parameters
	 */
	public void bigMCFunctionSupport(int categoray, String... parameters){
		if(categoray == 1){
			int length = parameters.length;
			if(length == 0){
				System.out.println("no parameter(s)");
			} else {
				org.bigraph.bigmc.BigMC.main(parameters);
			}		
		} else{
			org.bigraph.bigmcDF.BigMC.main(parameters);
		}
	}
}
