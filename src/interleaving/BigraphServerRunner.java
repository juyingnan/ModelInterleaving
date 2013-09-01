package interleaving;

import interleaving.server.BigraphServer;

public class BigraphServerRunner {
	public static void main(String args[]) {
		BigraphServer bigraphServerUtil = new BigraphServer();
		bigraphServerUtil.bigraphServerRun();
		
//		BigraphServer bigraphServerUtil = new BigraphServer(4500, "BigraphServer_1");
//		bigraphServerUtil.bigraphServerRun();
//		
//		BigraphServer bigraphServerUtil2 = new BigraphServer(4600,"BigraphServer_2");
//		bigraphServerUtil2.bigraphServerRun();
	}
}
