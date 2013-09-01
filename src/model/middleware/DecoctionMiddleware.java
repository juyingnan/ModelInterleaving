package model.middleware;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DecoctionMiddleware extends Middleware implements Serializable 
{
	/**
	 * Default Serializable ID: No.1
	 * Evangelion 巨它件必伉左件 場瘍儂
	 */
	private static final long	serialVersionUID	= 1L;

	public DecoctionMiddleware()
	{
		
	}
	
	public void WriteToXML(String fileName, model.efsm.Middleware middleware)
	{
		File file = new File(fileName);

        ObjectOutputStream oout;
		try
		{
			oout = new ObjectOutputStream(new FileOutputStream(file));
			oout.writeObject(middleware);
	        oout.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}        
	}
}
