package model.efsm;

public class Transition
{
	private String	input;
	private String	output;
	private int		initial;
	private int		next;
	private String	guard;
	private String	action;
	private String	name;

	public Transition()
	{
	}

	public Transition(String input, String output, int initial, int next, String guard, String action, String name)
	{
		this.input = input;
		this.output = output;
		this.initial = initial;
		this.next = next;
		this.guard = guard;
		this.action = action;
		this.name = name;
	}

	public String getInput()
	{
		return input;
	}

	public void setInput(String input)
	{
		this.input = input;
	}

	public String getOutput()
	{
		return output;
	}

	public void setOutput(String output)
	{
		this.output = output;
	}

	public int getInitial()
	{
		return initial;
	}

	public void setInitial(int initial)
	{
		this.initial = initial;
	}

	public int getNext()
	{
		return next;
	}

	public void setNext(int next)
	{
		this.next = next;
	}

	public String getGuard()
	{
		return guard;
	}

	public void setGuard(String guard)
	{
		this.guard = guard;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void printTransition()
	{
		String result = "";
		result += this.name + "(input:" + this.input + "; output:" + this.output + "; initial:M" + this.initial + "; next:M" + this.next + "; guard:" + this.guard + "; action:" + this.action + ";)";
		System.out.println(result);
	}
}
