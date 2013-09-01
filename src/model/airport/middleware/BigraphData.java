package model.airport.middleware;

public class BigraphData {
	public static final String[] passageData = new String[]{
		"405-40-1340", "Marie C. Sherer", "606-471-5451", "CA984", "9A", "female", "20", "4"
	};
	
	public String[] getPassageData(){
		return passageData;
	}
	
	public String getPassagePhone(){
		return passageData[2];
	}
}
