package data.efsm;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class Chromosome implements Cloneable{
	
	private ArrayList<String> targetValues;
	private int guardNum;
	private int[] testData;
	private double fitness;
	
	public Chromosome(ArrayList<String> targetValues){
		this.guardNum = targetValues.size();
		this.targetValues = targetValues;
		testData = new int[guardNum];
	}

	public ArrayList<String> getTargetValues() {
		return targetValues;
	}

	public void setTargetValues(ArrayList<String> targetValues) {
		this.targetValues = targetValues;
	}

	public int getGuardNum() {
		return guardNum;
	}

	public void setGuardNum(int guardNum) {
		this.guardNum = guardNum;
	}

	public int[] getTestData() {
		return testData;
	}

	public void setTestData(int[] testData) {
		this.testData = testData;
	}

	public double getFitness() {
		this.fitness = caculateFitness();
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public void randomGeneration(){
		Vector<Integer> allowedGuard = new Vector<Integer>();
		for(int i = 0; i < guardNum; i++){
			allowedGuard.add(Integer.valueOf(i));
		}
		
		Random r = new Random(System.currentTimeMillis());
		for(int i = 0; i < guardNum; i++){
			int index = r.nextInt(allowedGuard.size());
			int selected = allowedGuard.get(index);
			
			testData[i] = selected;
			allowedGuard.remove(index);
		}
	}
	
	private double caculateFitness(){
		double fitness = 0.0;
		int guardNum = targetValues.size();
		double valid = 0.0;
		for(int i = 0; i < guardNum; i++){
			String value = (testData[i] % 13 >= 6)?"false":"true";
			valid += (value.equals(targetValues.get(i))) ? 1.0 : 0.0;
		}
		fitness = valid/guardNum;
		return fitness;	
	}
	
	public void print(){
		System.out.print("test data is : ");
		for(int i = 0; i < guardNum; i++){
			String data = (testData[i] % 13 >= 6)?"false":"true";
			System.out.print(data + "\t");
		}
		System.out.println();
		System.out.println("Its fitness measure is :¡¡" +  getFitness());
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Chromosome chromosome = (Chromosome)super.clone();
		chromosome.guardNum = this.guardNum;
		chromosome.testData = this.testData;
		chromosome.fitness = this.fitness;
		return chromosome;
	}
	
}
