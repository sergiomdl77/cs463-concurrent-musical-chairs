/****************************************************************************
	Class that saves information about a chair that is being used during 
	a round of musical chairs.  It stores name of the chair and also its
	status (either empty or not empty).
****************************************************************************/
public class Chair 
{
	private String name;
	public volatile boolean empty;
	
	public Chair(String n)
	{
		this.name = n;
		this.empty = true;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean isEmpty()
	{
		return empty;
	}
	
	public void setStatus(boolean status)
	{
		empty = status;
	}
	
	public String toString()
	{
		String s = "";
		if (empty == true)
			s = "true";
		else
			s = "false";
		
		return "( " + name + ", " + s + ")" ;
	}
}
