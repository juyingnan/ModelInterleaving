package interleaving.client;

public class ServiceClientTest {
	
	public static void main(String args[]){
		String host = "127.0.0.1";
		int port = 4800;
		
		Client serviceClient = new Client(host, port);
		serviceClient.openSocket();
		System.out.println("createPrescription  "+serviceClient.communicate("call:createPrescription()")+serviceClient.communicate("currentIndex"));
		System.out.println("createBill  "+serviceClient.communicate("call:createBill(prescription)")+serviceClient.communicate("currentIndex"));
		System.out.println("payBill  "+serviceClient.communicate("call:payBill(bill)")+serviceClient.communicate("currentIndex"));
		System.out.println("produceDecoction  "+serviceClient.communicate("call:produceDecoction(prescription)")+serviceClient.communicate("currentIndex"));
		System.out.println("checkService  "+serviceClient.communicate("call:checkService(r)")+serviceClient.communicate("currentIndex"));
		System.out.println("end  "+serviceClient.communicate("end"));
		serviceClient.closeSocket();
	}
}
