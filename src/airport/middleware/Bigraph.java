package model.airport.middleware;

public class Bigraph {

	private static BigraphData bigraphData;
	
//	public static void main(String[] args) {
//		System.out.println(getInfo("get in airport"));
//		System.out.println(getInfo("get current flight"));
//	}

	public static String getInfo( String info ){
		
		bigraphData = new BigraphData();
		String result = "i dont know what u say";
		
		if(info.startsWith("get in airport")){
			String[] passageInfo = bigraphData.getPassageData();
			if (passageInfo.length <= 0){
				return result;
			}
			result = passageInfo[0];
			for(int i = 1; i < passageInfo.length; i++){
				result = result + "&" + passageInfo[i];
			}
		}else if(info.startsWith("get in checkin")){
			result = "true";
		}else if (info.startsWith("get in security")){
			result = "true";
		}else if(info.startsWith("get in passageway")){
			result = "true";
		}else if(info.startsWith("get in shopping")){
			result = "true";
		}else if(info.startsWith("get in gate lounge")){
			result = "true";
		}else if(info.startsWith("get current flight")){
			String[] passageInfo = bigraphData.getPassageData();
			if (passageInfo.length <= 0){
				return result;
			}
			result = passageInfo[3];
		}else if(info.startsWith("get current light")){
			result = "30";
		}
		return result;
	}
	
	public static String putInfo(String info){
		return "true";
	}
}
