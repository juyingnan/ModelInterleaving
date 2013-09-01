package model.middleware;

import model.efsm.State;
import model.efsm.Transition;

public class Middleware implements IContextAcquisition, IServiceInvoke
{
	protected State[]			states;
	protected Transition[]	trans;
	

	@Override
	public void InvokeService()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void GetContext()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void SetContext()
	{
		// TODO Auto-generated method stub

	}
	
	public void ContextProcess()
	{
		
	}
	
	public void Reasoning()
	{
		
	}	

	public State[] getStates()
	{
		return states;
	}

	public void setStates(State[] states)
	{
		this.states = states;
	}

	public Transition[] getTrans()
	{
		return trans;
	}

	public void setTrans(Transition[] trans)
	{
		this.trans = trans;
	}
	
	public void initStates(){
		for(int i = 0; i < this.states.length; i++){
			for(int j = 0; j < this.trans.length; j++){
				if((this.trans)[j].getInitial() == (this.states)[i].getIndex()){
					(this.states)[i].addTransition((this.trans)[j]);
				}
			}
		}	
	}
}
