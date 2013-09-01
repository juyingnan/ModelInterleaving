package model.middleware;

public interface IContextAcquisition
{
	public Context context = null;
	
	public void GetContext();
	
	public void SetContext();
}
