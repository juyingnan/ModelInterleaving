package data.efsm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import path.efsm.PathSet;

import model.configInfo.ConfigInfo;
import model.configInfo.parser.ConfigInfoXMLParser;
import model.efsm.EFSMModel;
import model.efsm.State;
import model.efsm.parser.EFSMXMLParser;

public class DataGenerationByGA {

	private ArrayList<String> targetValue;
	private Chromosome[] chromosomes;
	private Chromosome[] nextGeneration;
	private int guardNum;
	private int N;
	private int MAX_GEN;
	private double p_c_t;
	private double p_m_t;
	private double bestFitness;
	private double[] averageFitness;
	private int[] bestGuards;
	
	public DataGenerationByGA(ArrayList<String> targetValues, int n, int max, double p_c, double p_m){
		this.targetValue = targetValues;
		this.guardNum = targetValues.size();
		this.N = n;
		this.MAX_GEN = max;
		this.p_c_t = p_c;
		this.p_m_t = p_m;
		chromosomes = new Chromosome[N];
		nextGeneration = new Chromosome[N];
		bestFitness = 0.0;
		averageFitness = new double[MAX_GEN];
		bestGuards = new int[guardNum];
	}

	public ArrayList<String> getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(ArrayList<String> targetValue) {
		this.targetValue = targetValue;
	}

	public Chromosome[] getChromosomes() {
		return chromosomes;
	}
	
	public void setChromosomes(Chromosome[] chromosomes) {
		this.chromosomes = chromosomes;
	}
	
	public Chromosome[] getNextGeneration() {
		return nextGeneration;
	}
	
	public void setNextGeneration(Chromosome[] nextGeneration) {
		this.nextGeneration = nextGeneration;
	}
	
	public int getGuardNum() {
		return guardNum;
	}
	
	public void setGuardNum(int guardNum) {
		this.guardNum = guardNum;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public int getMAX_GEN() {
		return MAX_GEN;
	}

	public void setMAX_GEN(int mAX_GEN) {
		MAX_GEN = mAX_GEN;
	}

	public double getP_c_t() {
		return p_c_t;
	}

	public void setP_c_t(double p_c_t) {
		this.p_c_t = p_c_t;
	}

	public double getP_m_t() {
		return p_m_t;
	}

	public void setP_m_t(double p_m_t) {
		this.p_m_t = p_m_t;
	}

	public double getBestFitness() {
		return bestFitness;
	}

	public void setBestFitness(double bestFitness) {
		this.bestFitness = bestFitness;
	}

	public double[] getAverageFitness() {
		return averageFitness;
	}

	public void setAverageFitness(double[] averageFitness) {
		this.averageFitness = averageFitness;
	}
	
	public int[] getBestGuards() {
		return bestGuards;
	}

	public void setBestGuards(int[] bestGuards) {
		this.bestGuards = bestGuards;
	}
	
	public void solve(){
		init();
		for(int i = 0; i < MAX_GEN; i++){
			System.out.println("*************** Start Generation " + i + "***************");
			evolve(i);
			System.out.println("*************** end Generation " + i + "***************");	
		}
		printOptimal();
	}
	
	private void init(){
		for(int i = 0; i < N; i++){
			Chromosome chromosome = new Chromosome(getTargetValue());
			chromosome.randomGeneration();
			chromosomes[i] = chromosome;
			//chromosome.print();
		}
		
	}
	
	private void evolve(int g){
		double[] selectP = new double[N];
		double sum = 0.0;
		double tmp = 0.0;
		
		for(int i = 0; i < N; i++){
			sum += chromosomes[i].getFitness();
			if(chromosomes[i].getFitness() > bestFitness){
				bestFitness = chromosomes[i].getFitness();
				for(int j = 0; j < guardNum; j++){
					bestGuards[j] = chromosomes[i].getTestData()[j];
				}
			}
		}
		averageFitness[g] = sum/N;
		System.out.println("The average fitness in " + g + " generation is: " + averageFitness[g] + ", and the best fitness is: " + bestFitness);

		for(int i = 0; i < N; i++){
			tmp += chromosomes[i].getFitness()/sum;
			selectP[i] = tmp;
		}
		
		Random random = new Random(System.currentTimeMillis());
		for(int i = 0; i < N; i = i + 2){
			Chromosome[] children = new Chromosome[2];
			
			for(int j = 0; j < 2; j++){	
				int selected = 0;
				for(int k = 0; k < N - 1; k++){
					double p = random.nextDouble();
					if(p > selectP[k] && p <= selectP[k+1]){
						selected = k;
					}
					if(k == 0 && random.nextDouble() <= selectP[k]){
						selected = 0;
					}
				}
				try{
					children[j] = (Chromosome)chromosomes[selected].clone();
				}catch(CloneNotSupportedException e){
					e.printStackTrace();
				}
			}
			
			if(random.nextDouble() < p_c_t){
				int cutPoint1 = -1;
				int cutPoint2 = -1;
				
				int r1 = random.nextInt(guardNum);
				if(r1 > 0 && r1 < guardNum - 1){
					cutPoint1 = r1;
					
					int r2 = random.nextInt(guardNum);
					if(r2 == 0){
						cutPoint2 = r1 + 1;
					}
					else if(r2 > 0){
						cutPoint2 = r1 + r2;
					}
				}
				
				if(cutPoint1 > 0 && cutPoint2 > 0){
					int[] guards1 = new int[guardNum];
					int[] guards2 = new int[guardNum];
					
					if(cutPoint2 == guardNum - 1){
						for(int j = 0; j < guardNum; j++){
							guards1[j] = children[0].getTestData()[j];
							guards2[j] = children[1].getTestData()[j];
						}
					}
					else{
						for(int j = 0; j < guardNum; j++){
							if(j < cutPoint1){
								guards1[j] = children[0].getTestData()[j];
								guards2[j] = children[1].getTestData()[j];
							}
							else if(j >= cutPoint1 && j < cutPoint1+guardNum-cutPoint2-1){
								guards1[j] = children[0].getTestData()[j+cutPoint2-cutPoint1+1];
								guards2[j] = children[1].getTestData()[j+cutPoint2-cutPoint1+1];
							}
							else{
								guards1[j] = children[0].getTestData()[j-guardNum+cutPoint2+1];
								guards2[j] = children[1].getTestData()[j-guardNum+cutPoint2+1];
							}
						}
					}
					
					/*System.out.println("two data sets are : ");
					for(int j = 0; j < guardNum; j++){
						String data = (guards1[j] % 2 == 0)?"false":"true";
						System.out.print(data + "\t");
					}
					System.out.println();
					
					for(int j = 0; j < guardNum; j++){
						String data = (guards2[j] % 2 == 0)?"false":"true";
						System.out.print(data + "\t");
					}
					System.out.println();*/
					
					for(int j = 0; j < guardNum; j++){
						if(j < cutPoint1 || j > cutPoint2){
							children[0].getTestData()[j] = -1;
							children[1].getTestData()[j] = -1;
						}
						else{
							int tmp1 = children[0].getTestData()[j];
							children[0].getTestData()[j] = children[1].getTestData()[j];
							children[1].getTestData()[j] = tmp1;
						}
					}
					
					/*System.out.println("two data sets are : ");
					for(int j = 0; j < guardNum; j++){
						String data = (guards1[j] % 2 == 0)?"false":"true";
						System.out.print(data + "\t");
					}
					System.out.println();
					
					for(int j = 0; j < guardNum; j++){
						String data = (guards2[j] % 2 == 0)?"false":"true";
						System.out.print(data + "\t");
					}
					System.out.println();*/
					
					if(cutPoint2 == guardNum - 1){
						int position = 0;
						for(int j = 0; j < cutPoint1; j++){
							for(int m = position; m < guardNum; m++){
								boolean flag = true;
								for(int n = 0; n < guardNum; n++){
									if(guards1[m] == children[0].getTestData()[n]){
										flag = false;
										break;
									}
								}
								if(flag){
									children[0].getTestData()[j] = guards1[m];
									position = m + 1;
									break;
								}
							}
						}
						
						position = 0;
						for(int j = 0; j < cutPoint1; j++){
							for(int m = position; m < guardNum; m++){
								boolean flag = true;
								for(int n = 0; n < guardNum; n++){
									if(guards2[m] == children[1].getTestData()[n]){
										flag = false;
										break;
									}
								}
								if(flag){
									children[1].getTestData()[j] = guards2[m];
									position = m + 1;
									break;
								}
							}
						}
					}
					else{
						int position = 0;
						for(int j = cutPoint2 + 1; j < guardNum; j++){
							for(int m = position; m < guardNum; m++){
								boolean flag = true;
								for(int n = 0; n < guardNum; n++){
									if(guards1[m] == children[0].getTestData()[n]){
										flag = false;
										break;
									}
								}
								if(flag){
									children[0].getTestData()[j] = guards1[m];
									position = m + 1;
									break;
								}
							}
						}
						
						for(int j = 0; j < cutPoint1; j++){
							for(int m = position; m < guardNum; m++){
								boolean flag = true;
								for(int n = 0; n < guardNum; n++){
									if(guards1[m] == children[0].getTestData()[n]){
										flag = false;
										break;
									}
								}
								if(flag){
									children[0].getTestData()[j] = guards1[m];
									position = m + 1;
									break;
								}
							}
						}
						
						position = 0;
						for(int j = cutPoint2 + 1; j < guardNum; j++){
							for(int m = position; m < guardNum; m++){
								boolean flag = true;
								for(int n = 0; n < guardNum; n++){
									if(guards2[m] == children[1].getTestData()[n]){
										flag = false;
										break;
									}
								}
								if(flag){
									children[1].getTestData()[j] = guards2[m];
									position = m + 1;
									break;
								}
							}
						}
						for(int j = 0; j < cutPoint1; j ++){
							for(int m = position; m < guardNum; m++){
								boolean flag = true;
								for(int n = 0; n < guardNum; n++){
									if(guards2[m] == children[1].getTestData()[n]){
										flag = false;
										break;
									}
								}
								if(flag){
									children[1].getTestData()[j] = guards2[m];
									position = m + 1;
									break;
								}
							}
						}
					}	
				}
			}
			
			if(random.nextDouble() < p_m_t){
				for(int j = 0; j < 2; j++){
					int cutPoint1 = -1;
					int cutPoint2 = -1;
					
					int r1 = random.nextInt(guardNum);
					if(r1 < 0 && r1 < guardNum - 1){
						cutPoint1 = r1;
						
						int r2 = random.nextInt(guardNum - r1);
						if(r2 == 0){
							cutPoint2 = r1 + 1;
						}
						else if(r2 > 0){
							cutPoint2 = r1 + r2;
						}
					}
					
					if(cutPoint1 > 0 && cutPoint2 > 0){
						List<Integer> testData = new ArrayList<Integer>();
						
						if(cutPoint2 == guardNum - 1){
							for(int k = 0; k < cutPoint1; k++){
								if(k < cutPoint1 || k > cutPoint2){
									testData.add(Integer.valueOf(children[j].getTestData()[k]));
								}
							}
						}
						
						int position = random.nextInt(testData.size());
						if(position == 0){
							for(int k = cutPoint2; k >= cutPoint1; k--){
								testData.add(0, Integer.valueOf(children[j].getTestData()[k]));
							}
						}
						else if(position == testData.size() - 1){
							for(int k = cutPoint1; k <= cutPoint2; k++){
								testData.add(Integer.valueOf(children[j].getTestData()[k]));
							}
						}
						else{
							for(int k = cutPoint1; k <= cutPoint2; k++){
								 testData.add(position, Integer.valueOf(children[j].getTestData()[k]));
							}
						}
						
						for(int k = 0; k < guardNum; k++){
							 children[j].getTestData()[k] = testData.get(k).intValue();
						}
					}
					
				}
			}
			nextGeneration[i] = children[0];
			nextGeneration[i+1] = children[1];
		}
		
		for(int k = 0; k < N; k++){
			try{
				chromosomes[k] = (Chromosome)nextGeneration[k].clone();
			}catch(CloneNotSupportedException e){
				e.printStackTrace();
			}
		}
		
		/*for(int k = 0; k < N; k++){
			chromosomes[k].print();
		}*/
		
	}
	
	public void printOptimal(){
		System.out.println("the best fitness is " + bestFitness);
		System.out.print("the best guards are : ");
		for(int i = 0; i < guardNum; i++){
			String data = (bestGuards[i] % 13 >= 6)?"false":"true";
			System.out.print(data + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args){
		ConfigInfoXMLParser configParser = new ConfigInfoXMLParser();
		ConfigInfo configInfo = configParser.parserXml();
		String efPath = configInfo.getEFSMModelFilePath();
		EFSMXMLParser efsmParser = new EFSMXMLParser();
		EFSMModel efsm = efsmParser.parserXml(efPath);
		efsm.initStates();
		
		PathSet pathSet = new PathSet(efsm);
		List<State> path = pathSet.createRandomCyclePath();
		
		ArrayList<ArrayList<String>> guardsWithValueOnPath = efsm.getTransDataOnPath(path);
		ArrayList<String> targetValues = new ArrayList<String>();
		for(ArrayList<String> guards : guardsWithValueOnPath){
			for(String guard : guards){
				targetValues.add(guard.split(":")[1]);
			}
		}
		
		System.out.print("data for path : ");
		for(String s : targetValues){
			System.out.print(s + "\t");
		}
		System.out.println();
		
		DataGenerationByGA ga = new DataGenerationByGA(targetValues, 2, 30, 0.95, 0.50);
		ga.solve();
	}
}
