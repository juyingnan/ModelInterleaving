package system;

import interleaving.client.DecoctionMiddleware;
import interleaving.server.BigraphServer;
import interleaving.server.ServiceServer;

public class BatTest
{
	public static void main(String[] args)
	{
		for (int i = 0; i < 13; i++)
		{
			new BigraphServerThread("BS").start();
			new ServiceServerThread("SS").start();

			try
			{
				Thread.sleep(2000);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			String middlewareFileName = "doc\\DecoctionMiddleware_Error_" + (i > 9 ? "" : "0") + i + ".xml";
			String outputFileName = "D:\\output" + i + ".txt";

			System.out.println(middlewareFileName);
			System.out.println(outputFileName);

			DecoctionMiddleware decoctionMiddleware = new DecoctionMiddleware(middlewareFileName);
			decoctionMiddleware.DecoctionMiddlewareRun(outputFileName);
			System.out.println("DecoctionMiddleware");

			try
			{
				Thread.sleep(5000);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}

}

class BigraphServerThread extends Thread
{
	public BigraphServerThread(String threadName)
	{
		super(threadName);
		isReady = false;
	}

	public boolean	isReady;

	public void run()
	{
		System.out.println(getName() + " 线程运行开始!");
		BigraphServer bigraphServerUtil = new BigraphServer();
		isReady = true;
		bigraphServerUtil.bigraphServerRun();
		System.out.println("BigraphServer");
		System.out.println(getName() + " 线程运行结束!");
	}
}

class ServiceServerThread extends Thread
{
	public ServiceServerThread(String threadName)
	{
		super(threadName);
		this.port = 4800;
		this.threadName = threadName;
	}

	public ServiceServerThread(String threadName, int port)
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
		ServiceServer serviceServerUtil = new ServiceServer(port);
		serviceServerUtil.serviceServerRun();
		System.out.println(getName() + " 线程运行结束!");
	}
}
