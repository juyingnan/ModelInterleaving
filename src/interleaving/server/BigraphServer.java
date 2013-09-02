package interleaving.server;

import java.util.List;

import model.bigraph.BigraphElement;
import model.bigraph.parser.BigraphBGMParser;
import model.configInfo.ConfigInfo;
import model.configInfo.parser.ConfigInfoXMLParser;

import org.bigraph.bigmc.Term;
import org.bigraph.bigmc.TermParser;

import data.bigraph.BigraphDataModel;
import data.bigraph.DataModelElement;
import data.bigraph.PropertyElement;
import data.bigraph.parser.DataModelXMLParser;

public class BigraphServer
{
	private BigraphBGMParser	bigraphParser;
	private DataModelXMLParser	dataModelParser;
	private Server				bigraphServer;
	private List<String[]>		contextModels;
	private String[]			currentContextModel;
	private BigraphDataModel	bigraphDataModel;
	private int					currentIndex;
	private int					currentContextIndex;
	private String				name;

	public BigraphServer()
	{
		ConfigInfoXMLParser configParser = new ConfigInfoXMLParser();
		ConfigInfo configInfo = configParser.parserXml();
		this.bigraphParser = new BigraphBGMParser();
		this.bigraphServer = new Server(configInfo.getBigraphServerPort());
		this.dataModelParser = new DataModelXMLParser();
		String bdmPath = configInfo.getBigraphDataModelFilePath();
		this.bigraphDataModel = this.dataModelParser.parserXml(bdmPath);
		String bfPath = configInfo.getBigraphModelFilePath();
		this.contextModels = this.bigraphParser.readContextModels(bfPath);
		this.currentIndex = 0;
		this.currentContextIndex = 0;
		this.currentContextModel = this.contextModels.get(this.currentContextIndex);
		// generateDataModels();
	}

	// added by Ju Yingnan
	// 2013-8-9
	public BigraphServer(int port, String name)
	{
		ConfigInfoXMLParser configParser = new ConfigInfoXMLParser();
		ConfigInfo configInfo = configParser.parserXml();
		this.bigraphParser = new BigraphBGMParser();
		this.bigraphServer = new Server(port);
		this.dataModelParser = new DataModelXMLParser();
		String bdmPath = configInfo.getBigraphDataModelFilePath();
		this.bigraphDataModel = this.dataModelParser.parserXml(bdmPath);
		String bfPath = configInfo.getBigraphModelFilePath();
		this.contextModels = this.bigraphParser.readContextModels(bfPath);
		this.currentIndex = 0;
		this.currentContextIndex = 0;
		this.currentContextModel = this.contextModels.get(this.currentContextIndex);
		this.name = name;
		// generateDataModels();
	}

	// private void generateDataModels(){
	// if(this.currentContextModel.length > 0){
	// Term currentContext = TermParser
	// .apply(this.currentContextModel[0]);
	// BigraphElement bigraphModel = this.bigraphParser
	// .TranslateTerm(currentContext);
	// for(DataModelElement dme : bigraphDataModel.getDataModels()){
	// String controlName = dme.getControlName();
	// List<String> names = bigraphModel.getElementNamesByControlName(controlName);
	// this.bigraphDataModel.generateDataModels(names);
	// }
	// }
	// }

	private void getRequestProcess(String line, BigraphElement bigraphModel)
	{
		String[] agents = line.split(";");
		if (agents.length == 4)
		{
			if (agents[2].equals("of"))
			{
				BigraphElement be = bigraphParser.checkA(bigraphModel, agents[3]);
				if (be == null)
				{
					bigraphServer.printLine(agents[3] + " is not in current context C" + this.getCurrentIndex());
				}
				else
				{
					DataModelElement dme = dataModelParser.checkDataModelA(bigraphDataModel, agents[3]);
					if (dme == null)
					{
						bigraphServer.printLine(agents[3] + " does not have a Data Model");
					}
					else
					{
						PropertyElement pe = dataModelParser.checkPropertyA(dme, agents[1]);
						if (pe == null)
						{
							bigraphServer.printLine(agents[3] + " does not have a Property " + agents[1]);
						}
						else
						{
							bigraphServer.printLine(pe.getValue());
						}
					}
				}
			}
			else
			{
				BigraphElement a = bigraphParser.checkA(bigraphModel, agents[1]);
				BigraphElement b = bigraphParser.checkA(bigraphModel, agents[3]);
				if (a == null)
				{
					bigraphServer.printLine(agents[1] + " is not in current context C" + this.getCurrentIndex());
				}
				else if (b == null)
				{
					bigraphServer.printLine(agents[3] + " is not in current context C" + this.getCurrentIndex());
				}
				else if (agents[2].equals("in"))
				{
					if (bigraphParser.checkAinB(a, b))
					{
						bigraphServer.printLine(a.getName() + " is in " + b.getName() + " in C" + this.getCurrentIndex());
					}
					else
					{
						bigraphServer.printLine(a.getName() + " is not in " + b.getName() + " in C" + this.getCurrentIndex());
					}
				}
				else if (agents[2].equals("link"))
				{
					if (bigraphParser.checkAlinkB(a, b))
					{
						bigraphServer.printLine(a.getName() + " is linked to " + b.getName() + " in C" + this.getCurrentIndex());
					}
					else
					{
						bigraphServer.printLine(a.getName() + " is not linked to " + b.getName() + " in C" + this.getCurrentIndex());
					}
				}
				else
				{
					bigraphServer.printLine("Request format wrong!!!");
				}
			}
		}
		else
		{
			bigraphServer.printLine("Request format wrong!!!");
		}
	}

	private void putRequestProcess(String line, BigraphElement bigraphModel)
	{
		if (!line.contains(";"))
		{
			if (this.getCurrentIndex() >= this.currentContextModel.length - 1)
			{
				this.bigraphServer.printLine("interleaving finished!!!");
			}
			else
			{
				this.CTXOneStep();
				this.bigraphServer.printLine("put is finished from C" + (this.getCurrentIndex() - 1) + " to C" + this.getCurrentIndex());
			}
		}
		else
		{
			String[] agents = line.split(";");
			if (agents.length == 4)
			{
				BigraphElement be = bigraphParser.checkA(bigraphModel, agents[1]);
				if (be == null)
				{
					bigraphServer.printLine(agents[1] + " is not in current context C" + this.getCurrentIndex());
				}
				else
				{
					DataModelElement dme = dataModelParser.checkDataModelA(bigraphDataModel, agents[1]);
					if (dme == null)
					{
						bigraphServer.printLine(agents[1] + " does not have a Data Model");
					}
					else
					{
						PropertyElement pe = dataModelParser.checkPropertyA(dme, agents[2]);
						if (pe == null)
						{
							bigraphServer.printLine(agents[1] + " does not have a Property " + agents[2]);
						}
						else
						{
							pe.setValue(agents[3]);
							bigraphServer.printLine("Value of Property " + agents[2] + " has changed to " + pe.getValue() + "successfully");
						}
					}
				}
			}
			else
			{
				bigraphServer.printLine("Request format wrong!!!");
			}
		}
	}

	private void continueRequestProcess(String line)
	{
		if (this.getCurrentIndex() >= this.currentContextModel.length - 1)
		{
			this.bigraphServer.printLine("interleaving finished!!!");
		}
		else
		{
			this.CTXOneStep();
			this.bigraphServer.printLine("continue is finished from C" + (this.getCurrentIndex() - 1) + " to C" + this.getCurrentIndex());
		}
	}

	private void nextRequestProcess(String line)
	{
		if (this.getCurrentContextIndex() >= this.contextModels.size() - 1)
		{
			this.bigraphServer.printLine("interleaving finished!!!");
		}
		else
		{
			this.CTXModelOneStep();
			this.currentContextModel = this.contextModels.get(currentContextIndex);
			this.currentIndex = 0;
			this.bigraphServer.printLine("next is finished from Model" + (this.getCurrentContextIndex() - 1) + " to Model" + this.getCurrentContextIndex());
		}
	}

	private void currentIndexRequestProcess()
	{
		this.bigraphServer.printLine(this.getCurrentIndex() + "");
	}

	private void currentContextIndexRequestProcess()
	{
		this.bigraphServer.printLine(this.getCurrentContextIndex() + "");
	}

	private void contextNumRequestProcess()
	{
		int num = this.currentContextModel.length - 1;
		this.bigraphServer.printLine(num + "");
	}

	private void contextModelNumRequestProcess()
	{
		int num = this.contextModels.size() - 1;
		this.bigraphServer.printLine(num + "");
	}

	private void wrongFormatResponse()
	{
		this.bigraphServer.printLine("BigraphServer Request format wrong!!!");
	}

	public void bigraphServerRun()
	{
		this.bigraphServer.openServerSocket();
		String line = this.bigraphServer.readLine();
		if (line.contains("get") || line.contains("put"))
		{
			System.out.println("Client:" + line);
		}

		while (!line.equals("end"))
		{
			//TermParser termParser = new TermParser();
			int maxIndex = this.currentContextModel.length - 1;
			// System.out.println(this.getCurrentIndex());
			// System.out.println(this.currentContextModel[this.getCurrentIndex()]);
			
			Term currentContext = TermParser.apply(this.currentContextModel[this.getCurrentIndex()]);
			BigraphElement bigraphModel = this.bigraphParser.TranslateTerm(currentContext);
			Term nextContext = null;
			BigraphElement nextBigraphModel = null;
			if (this.getCurrentIndex() < maxIndex - 1)
			{
				// System.out.println(this.getCurrentIndex() + 1);
				// System.out.println(maxIndex - 1);
				// System.out.println(this.currentContextModel[this.getCurrentIndex() + 1]);
				
				nextContext = TermParser.apply(this.currentContextModel[this.getCurrentIndex() + 1]);
				nextBigraphModel = this.bigraphParser.TranslateTerm(nextContext);
			}
			if (line.contains("nextContext:") && line.contains("get") && line.contains(";") && nextBigraphModel != null)
			{
				this.getRequestProcess(line.substring(12), nextBigraphModel);
			}
			else if (line.contains("get") && line.contains(";"))
			{
				this.getRequestProcess(line, bigraphModel);
			}
			else if (line.contains("put"))
			{
				this.putRequestProcess(line, bigraphModel);
			}
			else if (line.contains("continue"))
			{
				this.continueRequestProcess(line);
			}
			else if (line.contains("next"))
			{
				this.nextRequestProcess(line);
			}
			else if (line.contains("currentIndex"))
			{
				this.currentIndexRequestProcess();
			}
			else if (line.contains("currentContextIndex"))
			{
				this.currentContextIndexRequestProcess();
			}
			else if (line.contains("contextNumber"))
			{
				this.contextNumRequestProcess();
			}
			else if (line.contains("contextModelNumber"))
			{
				this.contextModelNumRequestProcess();
			}
			else
			{
				this.wrongFormatResponse();
			}
			this.bigraphServer.flush();
			line = this.bigraphServer.readLine();
			if (line.contains("get") || line.contains("put"))
			{
				System.out.println("Client:" + line);
			}
		}
		this.bigraphServer.closeServerSocket();
	}

	public List<String[]> getContextModels()
	{
		return contextModels;
	}

	public void setContextModels(List<String[]> contextModels)
	{
		this.contextModels = contextModels;
	}

	public String[] getCurrentContextModel()
	{
		return currentContextModel;
	}

	public void setCurrentContextModel(String[] currentContextModel)
	{
		this.currentContextModel = currentContextModel;
	}

	public BigraphBGMParser getBigraphParser()
	{
		return bigraphParser;
	}

	public void setBigraphParser(BigraphBGMParser bigraphParser)
	{
		this.bigraphParser = bigraphParser;
	}

	public Server getBigraphServer()
	{
		return bigraphServer;
	}

	public void setBigraphServer(Server bigraphServer)
	{
		this.bigraphServer = bigraphServer;
	}

	public int getCurrentIndex()
	{
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex)
	{
		this.currentIndex = currentIndex;
	}

	public void CTXOneStep()
	{
		currentIndex++;
	}

	public int getCurrentContextIndex()
	{
		return currentContextIndex;
	}

	public void setCurrentContextIndex(int currentContextIndex)
	{
		this.currentContextIndex = currentContextIndex;
	}

	public void CTXModelOneStep()
	{
		currentContextIndex++;
	}
}
