package interleaving.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.xml.bind.JAXB;

import interleaving.client.util.TransitionUtil;
import model.configInfo.ConfigInfo;
import model.configInfo.parser.ConfigInfoXMLParser;
import model.efsm.Middleware;
import model.efsm.State;
import model.efsm.Transition;
import model.efsm.parser.EFSMXMLParser;

public class DecoctionMiddleware extends EFSMModelClients
{
	protected Middleware	middleware;

	public DecoctionMiddleware()
	{
		ConfigInfoXMLParser configParser;
		configParser = new ConfigInfoXMLParser();
		ConfigInfo configInfo = configParser.parserXml();

		// EFSMXMLParser efsmParser = new EFSMXMLParser();
		// String efPath = configInfo.getEFSMModelFilePath();
		// this.middleware = efsmParser.parserXml(efPath, false);
		// this.middleware.initStates();
		efsmModel = null;

		String fileName = "doc\\DecoctionMiddleware.xml";
		// String fileName = "doc\\DecoctionMiddleware_error7.xml";
		// WriteToXML(fileName, middleware);
		middleware = ReadFromXML(fileName);

		this.tranUtil = new TransitionUtil(this.middleware.getData());
		String ip = configInfo.getServerIP();
		this.bigraphClient = new Client(ip, configInfo.getBigraphServerPort());
		this.serviceClient = new Client(ip, configInfo.getServiceServerPort());
	}

	public DecoctionMiddleware(String middlewareFileName)
	{
		ConfigInfoXMLParser configParser;
		configParser = new ConfigInfoXMLParser();
		ConfigInfo configInfo = configParser.parserXml();

		// EFSMXMLParser efsmParser = new EFSMXMLParser();
		// String efPath = configInfo.getEFSMModelFilePath();
		// this.middleware = efsmParser.parserXml(efPath, false);
		// this.middleware.initStates();
		efsmModel = null;

		// String fileName = "doc\\DecoctionMiddleware_error7.xml";
		// WriteToXML(fileName, middleware);
		middleware = ReadFromXML(middlewareFileName);

		this.tranUtil = new TransitionUtil(this.middleware.getData());
		String ip = configInfo.getServerIP();
		this.bigraphClient = new Client(ip, configInfo.getBigraphServerPort());
		this.serviceClient = new Client(ip, configInfo.getServiceServerPort());
	}

	public void DecoctionMiddlewareRun(String outputFileName)
	{
		File file = new File(outputFileName);
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
		{
			System.out.print(p + ":START->");
			out.print(p + ":START->");
			out.flush();
			int currentM = 0;
			State[] states = this.middleware.getStates();
			String maxC = this.bigraphClient.communicate("contextNumber");
			this.serviceClient.communicate("currentIndex:restart");
			for (int i = 0; i < 50; i++)
			{
				String preC = this.bigraphClient.communicate("currentIndex");
				String preS = this.serviceClient.communicate("currentIndex:reset");
				int preM = currentM;
				System.out.print("(C" + preC + ",M" + currentM + ",S" + preS + ")->");
				out.print("(C" + preC + ",M" + currentM + ",S" + preS + ")->");
				out.flush();
				if (preC.equals(maxC))
				{
					break;
				}
				if (currentM == (states.length - 1) && preC.equals(maxC))
				{
					break;
				}
				List<Transition> nextTs = states[currentM].getTransitions();
				boolean transFlag = false;
				String currentSubIndex;
				for (int j = 0; j < nextTs.size(); j++)
				{
					currentSubIndex = this.serviceClient.communicate("currentSubIndex");
					boolean canTransite = this.tranUtil.canTransite(nextTs.get(j), this.bigraphClient, this.serviceClient);
					if (canTransite)
					{
						transFlag = true;
						currentM = nextTs.get(j).getNext();
						this.middleware.setData(this.tranUtil.getData());
						continue;
					}
					else if ("".equals(currentSubIndex))
					{
						this.serviceClient.communicate("currentSubIndex:reset");
						this.serviceClient.communicate("currentIndex:reset");
					}
				}
				String currentC = this.bigraphClient.communicate("currentIndex");
				String currentS = this.serviceClient.communicate("currentIndex:reset");
				if (!preS.equals(currentS) && transFlag)
				{
					System.out.print("(C" + preC + ",M" + preM + ",S" + currentS + ")->");
					out.print("(C" + preC + ",M" + preM + ",S" + currentS + ")->");
					out.flush();
				}
				if (!preC.equals(currentC) && transFlag)
				{
					System.out.print("(C" + currentC + ",M" + preM + ",S" + currentS + ")->");
					out.print("(C" + currentC + ",M" + preM + ",S" + currentS + ")->");
					out.flush();
				}
				currentSubIndex = this.serviceClient.communicate("currentSubIndex");
				if (preM == currentM)
				{
					if ("".equals(currentSubIndex))
					{
						this.bigraphClient.communicate("continue");
					}
					else
					{
						String endFlagRequest = this.serviceClient.communicate("currentServiceEndFlag");
						if (!endFlagRequest.contains("no endFlag") && !endFlagRequest.equals(""))
						{
							String endResponse = this.bigraphClient.communicate(endFlagRequest);
							boolean endFlag = tranUtil.validateResponse(endResponse);
							this.serviceClient.communicate("currentEndFlag:" + endFlag);
							if (!endFlag)
							{
								this.bigraphClient.communicate("continue");
							}
						}
					}
				}
				if ("e".equals(currentSubIndex))
				{
					this.serviceClient.communicate("currentSubIndex:reset");
					this.serviceClient.communicate("currentIndex:reset");
				}
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

	public void WriteToXML(String fileName, Middleware middleware)
	{
		JAXB.marshal(middleware, fileName);
	}

	public Middleware ReadFromXML(String fileName)
	{
		return JAXB.unmarshal(fileName, (new Middleware()).getClass());
	}
}
