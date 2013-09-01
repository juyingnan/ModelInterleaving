package model.efsm;

import java.io.Serializable;

public class Middleware extends EFSMModel implements Serializable
{
	/**
	 * Default Serializable ID: No.1 Evangelion 巨它件必伉左件 場瘍儂
	 */
	private static final long	serialVersionUID	= 1L;

	//JAVA BEAN
	public Middleware()
	{
	}

	public Middleware(int stateNum, int tranNum)
	{
		super(stateNum, tranNum);
	}

}
