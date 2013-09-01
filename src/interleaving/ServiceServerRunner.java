package interleaving;

import interleaving.server.ServiceServer;

public class ServiceServerRunner {
	public static void main(String args[]) {
		ServiceServer serviceServerUtil = new ServiceServer();
		serviceServerUtil.serviceServerRun();
	}
}
