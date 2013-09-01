package interleaving.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import model.configInfo.ConfigInfo;
import model.configInfo.parser.ConfigInfoXMLParser;
import model.efsm.EFSMModel;
import model.efsm.State;
import model.efsm.Transition;
import model.efsm.parser.EFSMXMLParser;
import interleaving.client.Client;
import interleaving.client.util.TransitionUtil;

public class AirportMiddleware implements Runnable
{
	protected ArrayList<EFSMModel>		efsmModel	= new ArrayList();
	protected ArrayList<TransitionUtil>	transUtil	= new ArrayList();
	protected Client					bigraphClient;
	protected Client					serviceClient;
	protected boolean					transFlag;

	public AirportMiddleware()
	{
		ConfigInfoXMLParser configParser = new ConfigInfoXMLParser();
		ConfigInfo configInfo = configParser.parserXml();
		EFSMXMLParser efsmParser = new EFSMXMLParser();
		String[] efPath = new String[] { "doc\\checkinSensor.smd.xml", "doc\\lightSensor_1.smd.xml", "doc\\lightSensor_2.smd.xml", "doc\\billbroadSensor.smd.xml", "doc\\lightSensor_3.smd.xml" };
		for (String path : efPath)
		{
			EFSMModel e = efsmParser.parserXml(path);
			this.efsmModel.add(e);
		}
		for (EFSMModel efsmModel_x : this.efsmModel)
		{
			efsmModel_x.initStates();
		}
		for (EFSMModel efsmModel_x : this.efsmModel)
		{
			this.transUtil.add(new TransitionUtil(efsmModel_x.getData()));
		}
		String ip = configInfo.getServerIP();
		this.bigraphClient = new Client(ip, configInfo.getBigraphServerPort());
		this.serviceClient = new Client(ip, configInfo.getServiceServerPort());
	}

	public AirportMiddleware(int bigraphServerPort, int serviceServerPort, String outputFileName)
	{
		ConfigInfoXMLParser configParser = new ConfigInfoXMLParser();
		ConfigInfo configInfo = configParser.parserXml();
		EFSMXMLParser efsmParser = new EFSMXMLParser();
		String[] efPath = new String[] { "doc\\checkinSensor.smd.xml", "doc\\lightSensor_1_Error_2.smd.xml", "doc\\lightSensor_2.smd.xml", "doc\\billbroadSensor.smd.xml", "doc\\lightSensor_3.smd.xml" };
		for (String path : efPath)
		{
			EFSMModel e = efsmParser.parserXml(path);
			this.efsmModel.add(e);
		}
		for (EFSMModel efsmModel_x : this.efsmModel)
		{
			efsmModel_x.initStates();
		}
		for (EFSMModel efsmModel_x : this.efsmModel)
		{
			this.transUtil.add(new TransitionUtil(efsmModel_x.getData()));
		}
		String ip = configInfo.getServerIP();
		this.bigraphClient = new Client(ip, bigraphServerPort);
		this.serviceClient = new Client(ip, serviceServerPort);
	}

	public void EFSMModelClientsRun()
	{
		File file = new File("D:\\ouputText.txt");
		if (file.exists())
		{
			file.delete();
		}
		try
		{
			file.createNewFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(file);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		PrintWriter out = new PrintWriter(fos);
		out.flush();

		this.bigraphClient.openSocket();
		this.serviceClient.openSocket();
		String maxCM = this.bigraphClient.communicate("contextModelNumber");
		for (int p = 0; p < Integer.parseInt(maxCM); p++)
		{// 每一个情境模型
			System.out.print(p + ":START->");
			out.print(p + ":START->");
			out.flush();
			int[] currentM = new int[this.efsmModel.size()];
			// State[] states = this.efsmModel.getStates();
			String maxC = this.bigraphClient.communicate("contextNumber");
			this.serviceClient.communicate("currentIndex:restart");
			for (int i = 0; i < 50; i++)
			{
				String preC = this.bigraphClient.communicate("currentIndex");
				String preS = this.serviceClient.communicate("currentIndex:reset");

				// 打印当前状态
				printStatus(Integer.parseInt(preC), currentM, out);
				// 如果到了情境最后一个点就结束了

				this.transFlag = false;

				// 多个sensor
				for (int j = 0; j < currentM.length; j++)
				{
					currentM[j] = interleaving(this.efsmModel.get(j), this.transUtil.get(j), currentM[j]);
					printStatus(Integer.parseInt(preC), currentM, out);
				}
				// currentM[0] = interleaving(efsmModel_shop, tranUtil_shop,
				// currentM_shop);
				// printStatus(Integer.parseInt(preC), currentM, out);
				//
				// currentM[1] = interleaving(efsmModel_1, tranUtil_1, currentM_1);
				// printStatus(Integer.parseInt(preC), currentM, out);
				//
				// currentM[2] = interleaving(efsmModel_2, tranUtil_2, currentM_2);
				// printStatus(Integer.parseInt(preC), currentM, out);
				//
				// currentM[3] = interleaving(efsmModel_3, tranUtil_3, currentM_3);
				// printStatus(Integer.parseInt(preC), currentM, out);

//				System.out.println();
//				out.println();
//				out.flush();

				if (preC.equals(maxC))
				{
					break;
				}

				// 最后一起put
				boolean needPut = false;
				for (TransitionUtil t : this.transUtil)
				{
					if (!t.getPushString().equals(""))
					{
						needPut = true;
						break;
					}
				}
				if (needPut)
				{
					this.transUtil.get(0).putToBigraph(bigraphClient);
					for (TransitionUtil t : this.transUtil)
					{
						t.cleanPushString();
					}
				}
				else
				{
					this.bigraphClient.communicate("continue");
				}

				String currentC = this.bigraphClient.communicate("currentIndex");
				String currentS = this.serviceClient.communicate("currentIndex:reset");
			}
			System.out.println("END");
			out.println("END");
			out.flush();
			this.bigraphClient.communicate("next");
		}
		out.close();
		this.bigraphClient.communicate("end");
		this.serviceClient.communicate("end");
		this.bigraphClient.closeSocket();
		this.serviceClient.closeSocket();
	}

	public void printStatus(int c, int[] m, PrintWriter out)
	{// 打印状态
		System.out.print("(C" + c);
		out.print("(C" + c);
		for (int i = 0; i < m.length; i++)
		{
			System.out.print(",M" + i + m[i]);
			out.print(",M" + i + m[i]);
		}
		System.out.print(")->");
		out.print(")->");
		out.flush();
	}

	public int interleaving(EFSMModel efsmModel, TransitionUtil tranUtil, int currentM)
	{
		State[] states = efsmModel.getStates();
		List<Transition> nextTs = states[currentM].getTransitions();
		String currentSubIndex;
		for (int j = 0; j < nextTs.size(); j++)
		{
			currentSubIndex = this.serviceClient.communicate("currentSubIndex");
			boolean canTransite = tranUtil.canTransite(nextTs.get(j), this.bigraphClient, this.serviceClient);
			if (canTransite)
			{
				this.transFlag = this.transFlag && true;
				currentM = nextTs.get(j).getNext();
				efsmModel.setData(tranUtil.getData());
				continue;
			}
			else if ("".equals(currentSubIndex))
			{
				this.serviceClient.communicate("currentSubIndex:reset");
				this.serviceClient.communicate("currentIndex:reset");
			}
		}
		return currentM;
	}

	// public EFSMModel getEfsmModel() {
	// return efsmModel_1;
	// }
	//
	// public void setEfsmModel(EFSMModel efsmModel) {
	// this.efsmModel_1 = efsmModel;
	// }
	//
	// public TransitionUtil getTranUtil() {
	// return tranUtil_1;
	// }
	//
	// public void setTranUtil(TransitionUtil tranUtil) {
	// this.tranUtil_1 = tranUtil;
	// }

	public Client getBigraphClient()
	{
		return bigraphClient;
	}

	public void setBigraphClient(Client bigraphClient)
	{
		this.bigraphClient = bigraphClient;
	}

	public Client getServiceClient()
	{
		return serviceClient;
	}

	public void setServiceClient(Client serviceClient)
	{
		this.serviceClient = serviceClient;
	}

	@Override
	public void run()
	{
		EFSMModelClientsRun();
	}
}
