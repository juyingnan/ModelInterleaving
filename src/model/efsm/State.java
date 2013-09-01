package model.efsm;

import java.util.ArrayList;
import java.util.List;

public class State
{
	private int					index;
	private List<Transition>	trans	= new ArrayList<Transition>();
	private boolean				isVisited;

	public State()
	{
	}

	public State(int index)
	{
		this.index = index;
		this.isVisited = false;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public List<Transition> getTransitions()
	{
		return trans;
	}

	public void setTransitions(List<Transition> transitions)
	{
		this.trans = transitions;
	}

	public void addTransition(Transition t)
	{
		this.trans.add(t);
	}

	public boolean isVisited()
	{
		return isVisited;
	}

	public void setVisited(boolean isVisited)
	{
		this.isVisited = isVisited;
	}

	public void printState()
	{
		System.out.println("State:" + this.index);
	}
}
