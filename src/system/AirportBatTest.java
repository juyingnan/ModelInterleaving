package system;

import interleaving.client.AirportMiddleware;

public class AirportBatTest
{
	public static void main(String[] args)
	{
		String paths[] = { "doc\\checkinSensor.smd.xml&doc\\lightSensor_1.smd.xml&doc\\lightSensor_2.smd.xml&doc\\billbroadSensor.smd.xml&doc\\lightSensor_3.smd.xml",
				"doc\\checkinSensor_error_1.smd.xml&doc\\lightSensor_1.smd.xml&doc\\lightSensor_2.smd.xml&doc\\billbroadSensor.smd.xml&doc\\lightSensor_3.smd.xml",
				"doc\\checkinSensor.smd.xml&doc\\lightSensor_1_error_1.smd.xml&doc\\lightSensor_2.smd.xml&doc\\billbroadSensor.smd.xml&doc\\lightSensor_3.smd.xml",
				"doc\\checkinSensor.smd.xml&doc\\lightSensor_1_error_2.smd.xml&doc\\lightSensor_2.smd.xml&doc\\billbroadSensor.smd.xml&doc\\lightSensor_3.smd.xml",
				"doc\\checkinSensor.smd.xml&doc\\lightSensor_1.smd.xml&doc\\lightSensor_2_error_1.smd.xml&doc\\billbroadSensor.smd.xml&doc\\lightSensor_3.smd.xml",
				"doc\\checkinSensor.smd.xml&doc\\lightSensor_1.smd.xml&doc\\lightSensor_2.smd.xml&doc\\billbroadSensor.smd.xml&doc\\lightSensor_3_error_1.smd.xml",
				"doc\\checkinSensor.smd.xml&doc\\lightSensor_1.smd.xml&doc\\lightSensor_2.smd.xml&doc\\billbroadSensor_error_1.smd.xml&doc\\lightSensor_3.smd.xml" };

		for (int i = 4; i < paths.length; i++)
		{
			BigraphServerThread bigraphServerThread = new BigraphServerThread("BS");
			ServiceServerThread serviceServerThread = new ServiceServerThread("SS");
			bigraphServerThread.start();
			serviceServerThread.start();
			// new BigraphServerThread("BS").start();
			// new ServiceServerThread("SS").start();

			int count = 0;
			while (!bigraphServerThread.isReady)
			{
				try
				{
					Thread.sleep(1000);
					count++;
					if (count % 10 == 0)
						System.out.println("wait for " + count + " seconds.");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			String efPathString = paths[i];
			String outputFileName = "D:\\output" + i + ".txt";

			System.out.println(outputFileName);

			AirportMiddleware airportMiddleware = new AirportMiddleware(4700, 4800, efPathString, outputFileName);
			airportMiddleware.EFSMModelClientsRun();
			System.out.println("*****************************");

			try
			{
				Thread.sleep(10000);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}