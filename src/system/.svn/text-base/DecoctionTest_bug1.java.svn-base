package system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.configInfo.ConfigInfo;
import model.configInfo.parser.ConfigInfoXMLParser;
import model.service.ServiceModel;
import model.service.invokeUtil.ServiceInvokeUtil;
import model.service.parser.ServicesXMLParser;

public class DecoctionTest_bug1 {	
	public static void main(String[] args) throws IOException{
		ConfigInfoXMLParser configParser = new ConfigInfoXMLParser();
		ConfigInfo configInfo = configParser.parserXml();
		ServicesXMLParser serviceParser = new ServicesXMLParser();
		String sfPath = configInfo.getServiceModelFilePath();
		String petrifPath = configInfo.getPetrinetModelFilePath();
		ServiceModel serviceModel = serviceParser.parserXml(sfPath, petrifPath);
		
		ServiceInvokeUtil serviceUtil = new ServiceInvokeUtil();
		
		String url = "http://" + serviceModel.getIp() + serviceModel.getPath();
		String namespace = serviceModel.getNamespace();
		
		BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));

		String patientInConsultingRoom0 = "";
		String patientLinkPrescription1 = "";
		String billInChargingRoom2 = "";
		String patientInChargingRoom3 = "";
		String patientLinkBill4 = "";
		String isZhongyao5 = "";
		String materialInPharmacy6 = "";
		String patientInHospital7 = "yes";
		String isDone8 = "";
		String decoctionInPharmacy9 = "";
		String patientInPharmacy10= "";
		String patientLinkDection11 = "";
		
		
		while(!patientInConsultingRoom0.equals("yes")){
			System.out.println("Patient in Consulting Room or not: ");
			patientInConsultingRoom0 = strin.readLine();
		}
		while(!patientLinkPrescription1.equals("yes")){
			String resultPrescription = serviceUtil.invokeService(url+"Prescription", namespace, "createPrescription", "Prescription");
			System.out.println("Call_create_Prescription: " + resultPrescription);
			
			System.out.println("Prescription existed or not: ");
			patientLinkPrescription1 = strin.readLine();		
		}
		while(!billInChargingRoom2.equals("yes")){
			String resultBill = serviceUtil.invokeService(url+"Bill", namespace, "createBill", "Prescription");
			System.out.println("Call_create_Bill: " + resultBill);
			
			System.out.println("Bill existed or not: ");
			billInChargingRoom2 = strin.readLine();		
		}
		while(!patientInChargingRoom3.equals("yes")){
			System.out.println("Patient in Charging Room or not: ");
			patientInChargingRoom3 = strin.readLine();		
		}
		while(!patientLinkBill4.equals("yes")){
			String resultBillPayed = serviceUtil.invokeService(url+"Bill", namespace, "payBill", "Bill");
			System.out.println("Call_pay_Bill: " + resultBillPayed);
			
			System.out.println("Bill payed or not: ");
			patientLinkBill4 = strin.readLine();		
		}
		System.out.println("Prescription is zhongyao or not: ");
		isZhongyao5 = strin.readLine();		
		if(isZhongyao5.equals("yes")){
			serviceUtil.invokeService(url+"ProcessService", namespace, "produceDecoction", "Prescription");
			System.out.println("Call__produce_decoction");
			while(!materialInPharmacy6.equals("boilMedicine")){
				materialInPharmacy6 = serviceUtil.invokeService(url+"Check", namespace, "checkService", "status");	
			}
			System.out.println("Call__produce_decoction: " + materialInPharmacy6);
			while(patientInHospital7.equals("yes") && !decoctionInPharmacy9.equals("Finished")){
				decoctionInPharmacy9 = serviceUtil.invokeService(url+"Check", namespace, "checkService", "status");
				System.out.println("Patient in Hospital or not: ");
				patientInHospital7 = strin.readLine();	
			}
			isDone8 = "Finished";
			while(!(patientInPharmacy10.equals("yes") && isDone8.equals("Finished"))){
				System.out.println("Patient in Pharmacy or not: ");
				patientInPharmacy10 = strin.readLine();
			}			
		} else {
			while(patientInHospital7.equals("yes") && !(patientInPharmacy10.equals("yes"))){
				System.out.println("Patient in Hospital or not: ");
				patientInHospital7 = strin.readLine();
				System.out.println("Patient in Pharmacy or not: ");
				patientInPharmacy10 = strin.readLine();
			}
		}
		while(!patientLinkDection11.equals("yes")){
			System.out.println("Patient takes Decoction or not: ");
			patientLinkDection11 = strin.readLine();
		}
		System.out.println("whole process is done");
	}
	
}
