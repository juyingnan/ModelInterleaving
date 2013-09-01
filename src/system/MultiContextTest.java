package system;

import interleaving.client.AirportMiddleware;
import interleaving.client.MultiEFSMModelClients;
import interleaving.server.BigraphServer;
import system.ServiceServerThread;

public class MultiContextTest
{

	public static void main(String[] args)
	{
		// bigraph
		new BigraphServerMultiThread("Bigraph_1", 4700).start();
		//new BigraphServerMultiThread("Bigraph_2", 4701).start();

		// Services
		new ServiceServerThread("SS1", 4800).start();
		//new ServiceServerThread("SS2", 4801).start();

		try
		{
			Thread.sleep(2000);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		// Middleware
		// MultiEFSMModelClients client1 = new MultiEFSMModelClients(4700, 4800, "client1out");
		// MultiEFSMModelClients client2 = new MultiEFSMModelClients(4701, 4801, "client2out");
		AirportMiddleware client1 = new AirportMiddleware(4700, 4800, "client1out");
		//AirportMiddleware client2 = new AirportMiddleware(4701, 4801, "client1out");
		Thread thread1 = new Thread(client1);
		//Thread thread2 = new Thread(client2);
		thread1.start();
		//thread2.start();

	}

}

class BigraphServerMultiThread extends Thread
{
	public BigraphServerMultiThread(String threadName, int port)
	{
		super(threadName);
		this.port = port;
		this.threadName = threadName;
	}

	int		port;
	String	threadName;

	public void run()
	{
		System.out.println(getName() + " 线程运行开始!");
		BigraphServer bigraphServerUtil = new BigraphServer(port, threadName);
		bigraphServerUtil.bigraphServerRun();
		System.out.println(getName() + " 线程运行结束!");
	}
}
