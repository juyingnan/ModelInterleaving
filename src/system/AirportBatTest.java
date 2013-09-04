package system;

import interleaving.client.AirportMiddleware;

public class AirportBatTest
{
	public static void main(String[] args)
	{
		String paths[] = {
				"doc\\checkinSensor.smd.xml&doc\\lightSensor_1.smd.xml&doc\\lightSensor_2.smd.xml&doc\\billbroadSensor.smd.xml&doc\\lightSensor_3.smd.xml",
				// this one may have problem
				"doc\\checkinSensor_error_1.smd.xml&doc\\lightSensor_1.smd.xml&doc\\lightSensor_2.smd.xml&doc\\billbroadSensor.smd.xml&doc\\lightSensor_3.smd.xml",
				"doc\\checkinSensor.smd.xml&doc\\lightSensor_1_error_1.smd.xml&doc\\lightSensor_2.smd.xml&doc\\billbroadSensor.smd.xml&doc\\lightSensor_3.smd.xml",
				"doc\\checkinSensor.smd.xml&doc\\lightSensor_1_error_2.smd.xml&doc\\lightSensor_2.smd.xml&doc\\billbroadSensor.smd.xml&doc\\lightSensor_3.smd.xml",
				"doc\\checkinSensor.smd.xml&doc\\lightSensor_1.smd.xml&doc\\lightSensor_2_error_1.smd.xml&doc\\billbroadSensor.smd.xml&doc\\lightSensor_3.smd.xml",
				"doc\\checkinSensor.smd.xml&doc\\lightSensor_1.smd.xml&doc\\lightSensor_2.smd.xml&doc\\billbroadSensor.smd.xml&doc\\lightSensor_3_error_1.smd.xml",
				"doc\\checkinSensor.smd.xml&doc\\lightSensor_1.smd.xml&doc\\lightSensor_2.smd.xml&doc\\billbroadSensor_error_1.smd.xml&doc\\lightSensor_3.smd.xml" };

		for (int i = 0; i < paths.length; i++)
		{
			new BigraphServerThread("BS").start();
			new ServiceServerThread("SS").start();

			try
			{
				Thread.sleep(5000);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			String efPathString = paths[i];
			String outputFileName = "D:\\output" + i + ".txt";

			System.out.println(outputFileName);

			AirportMiddleware airportMiddleware = new AirportMiddleware(4700, 4800, efPathString, outputFileName);
			airportMiddleware.EFSMModelClientsRun();
			System.out.println("*****************************");

			try
			{
				Thread.sleep(2000);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}