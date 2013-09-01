package interleaving.client.util;

import java.util.HashMap;

import model.efsm.Transition;
import interleaving.client.Client;

public class TransitionUtil
{
	private HashMap<String, String>	data;
	private String					putString	= "";

	public TransitionUtil(HashMap<String, String> data)
	{
		this.data = data;
	}

	public boolean canTransite(Transition tran, Client bigraphClient, Client serviceClient)
	{
		boolean result = false;
		this.input(tran.getInput(), bigraphClient);
		result = this.guard(tran.getGuard(), bigraphClient);
		if (result)
		{
			this.action(tran.getAction(), bigraphClient, serviceClient);
		}
		return result;
	}

	private boolean guard(String guard, Client client)
	{
		if (guard == null)
		{
			return true;
		}
		else if (guard.contains(" && "))
		{
			String[] guards = guard.split(" && ");
			boolean result = true;
			for (int i = 0; i < guards.length; i++)
			{
				result = result && subGuard(guards[i], client);
			}
			return result;
		}
		else
		{
			return subGuard(guard, client);
		}
	}

	private boolean subGuard(String guard, Client client)
	{
		String[] operators = new String[] { " == ", " != ", " > ", " < ", " >= ", " <= ", "no change", "change" };

		if (guard.endsWith(" no change"))
		{
			String vName = guard.split(" no change")[0];
			String changeFlag = this.getData().get(vName);
			if (changeFlag.equals("false"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (guard.endsWith(" change"))
		{
			String vName = guard.split(" change")[0];
			String changeFlag = this.getData().get(vName);
			if (changeFlag != null)
			{
				if (changeFlag.equals("false"))
				{
					return false;
				}
				else
				{
					return true;
				}
			}
			else
				return false;
		}
		else
		{
			for (int i = 0; i < 6; i++)
			{
				if (guard.contains(operators[i]))
				{
					String lPart, rPart;
					Double lNum, rNum;
					String[] guardParts = guard.split(operators[i]);
					if (guardParts.length == 2)
					{
						if (i == 0 || i == 1)
						{
							if (this.getData().containsKey(guardParts[0]))
							{
								lPart = this.getData().get(guardParts[0]);
							}
							else
							{
								lPart = guardParts[0];
							}
							if (this.getData().containsKey(guardParts[1]))
							{
								rPart = this.getData().get(guardParts[1]);
							}
							else
							{
								rPart = guardParts[1];
							}
							if (i == 0)
							{
								return lPart.equals(rPart);
							}
							else
							{
								return !lPart.equals(rPart);
							}
						}
						else
						{
							if (this.getData().containsKey(guardParts[0]))
							{
								lNum = Double.parseDouble(this.getData().get(guardParts[0]));
							}
							else
							{
								lNum = Double.parseDouble(guardParts[0]);
							}
							if (this.getData().containsKey(guardParts[1]))
							{
								rNum = Double.parseDouble(this.getData().get(guardParts[1]));
							}
							else
							{
								rNum = Double.parseDouble(guardParts[1]);
							}
							if (i == 2)
							{
								return lNum > rNum;
							}
							else if (i == 3)
							{
								return lNum < rNum;
							}
							else if (i == 4)
							{
								return lNum >= rNum;
							}
							else
							{
								return lNum <= rNum;
							}
						}
					}
					else
					{
						System.out.println("Wrong guard format!!!");
						return false;
					}
				}
			}
		}
		return false;
	}

	private void input(String input, Client client)
	{
		if (input == null)
		{
			return;
		}
		else if (input.contains(" && "))
		{
			String[] inputs = input.split(" && ");
			for (int i = 0; i < inputs.length; i++)
			{
				subInput(inputs[i], client);
			}
		}
		else
		{
			subInput(input, client);
		}
	}

	private void subInput(String input, Client client)
	{
		if (input.startsWith("get:"))
		{
			String subInput = input.substring(4);
			if (subInput.contains(" "))
			{
				String[] inputs = subInput.split(" ");
				String request = "get";
				for (int i = 0; i < inputs.length; i++)
				{
					request += ";" + inputs[i];
				}
				if (client != null)
				{
					String response = client.communicate(request);
					if (validateResponse(response))
					{
						this.addData(subInput, "true");
					}
					else
					{
						this.addData(subInput, "false");
					}
				}
			}
			else
			{
				System.out.println("Wrong input format!!!");
			}
		}
		else
		{
			System.out.println("Wrong input format!!!");
		}
	}

	public boolean validateResponse(String response)
	{
		boolean result = false;
		if (response == null)
		{
			return result;
		}
		else if (response.contains("Request format wrong"))
		{
			System.out.println(response);
			return result;
		}
		else if (response.contains(" in ") && !response.contains(" not in ") && !response.contains("current context") && !response.contains(" not linked "))
		{
			result = true;
		}
		else if (response.contains(" link ") && !response.contains(" not linked "))
		{
			result = true;
		}
		else if (response.equals("true"))
		{
			result = true;
		}
		return result;
	}

	private void action(String action, Client bigraphClient, Client serviceClient)
	{
		if (action == null)
		{
			return;
		}
		else if (action.contains(" && "))
		{
			String[] actions = action.split(" && ");
			for (int i = 0; i < actions.length; i++)
			{
				subAction(actions[i], bigraphClient, serviceClient);
			}
		}
		else
		{
			subAction(action, bigraphClient, serviceClient);
		}

		// if (!this.putString.equals("")) {
		// putToBigraph(bigraphClient);
		// //System.out.println("i send a put");
		// }
	}

	private void subAction(String action, Client bigraphClient, Client serviceClient)
	{
		if (action.contains(" = "))
		{
			String[] actionParts = action.split(" = ");
			if (actionParts.length == 2)
			{
				if (actionParts[1].contains("call_"))
				{
					String serviceName = actionParts[1].substring(5);
					String response = null;
					if (serviceClient != null)
					{
						response = serviceClient.communicate("call:" + serviceName);
						// System.out.println("i call a service " + serviceName);
					}
					if (!actionParts[1].contains("checkService"))
					{
						this.addData(actionParts[0], response);
					}
					else
					{
						if (serviceClient != null)
						{
							serviceClient.communicate("currentSubIndex:" + response);
							String currentSubIndex = serviceClient.communicate("currentSubIndex");
							if ("e".equals(currentSubIndex))
							{
								this.addData(actionParts[0], "true");
							}
							else
							{
								this.addData(actionParts[0], "false");
							}
						}
					}
				}
				else
				{
					this.addData(actionParts[0], actionParts[1]);
				}
			}
			else
			{
				System.out.println("Wrong action format!!!");
			}
		}
		else if (action.contains("put:"))
		{
			if (bigraphClient != null)
			{
				addPutString(action);
				// bigraphClient.communicate("put");
			}
		}
		else
		{
			System.out.println("Wrong action format!!!");
		}
	}

	public void addPutString(String put)
	{
		this.putString = this.putString + "&" + put;
	}

	public String getPushString()
	{
		return this.putString;
	}

	public void cleanPushString()
	{
		this.putString = "";
	}

	public void putToBigraph(Client bigraphClient)
	{
		if (bigraphClient != null)
		{
			bigraphClient.communicate("put");
			this.putString = "";
		}
	}

	public HashMap<String, String> getData()
	{
		return data;
	}

	public void setData(HashMap<String, String> data)
	{
		this.data = data;
	}

	public void addData(String key, String value)
	{
		this.data.put(key, value);
	}
}
