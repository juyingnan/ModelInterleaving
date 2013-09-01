package interleaving.client;

public class BigraphClientTest {
	
	public static void main(String args[]){
		String host = "127.0.0.1";
		int port = 4700;

		Client efsmClient = new Client(host, port);
		efsmClient.openSocket();
		System.out.println("get;Illum;of;Light  "+efsmClient.communicate("get;Illum;of;Light"));
		System.out.println("get;Patient;in;Hospital  "+efsmClient.communicate("get;Patient;in;Hospital"));
		System.out.println("get;Patient;link;Hospital  "+efsmClient.communicate("get;Patient;link;Hospital"));
		System.out.println("put  "+efsmClient.communicate("put"));
		System.out.println("get;Patient;in;Hospital  "+efsmClient.communicate("get;Patient;in;Hospital"));
		System.out.println("get;Patient;link;Hospital  "+efsmClient.communicate("get;Patient;link;Hospital"));
		System.out.println("end  "+efsmClient.communicate("end"));
		efsmClient.closeSocket();
	}
}
