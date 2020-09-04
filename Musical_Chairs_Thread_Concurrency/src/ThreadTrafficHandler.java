import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/******************************************************************************
 Class that acts as the thread traffic-controller to add it to each player
 and to the Emcee, so that all of them have access to the wait() and notifyAll() 
 methods through the same object (in order to be effectively synchronized).  
*******************************************************************************/
public class ThreadTrafficHandler 
{
	public ArrayList<Chair> chairs;
	private ArrayList<Player> players;

	public volatile int numPlayers;
	public int numPlayersReady;
	public String roundLoser;
	public String roundWinner;
	public int curRound;     
	public int rounds;
	public volatile int chairsTaken;

	// Constructor
	public ThreadTrafficHandler(int nPlayers)
	{		
		players = new ArrayList<Player>();
	    chairs = new ArrayList<Chair>();
	      
	    for(int i = 1; i < nPlayers; i++)
	    {
	        Chair newChair = new Chair("C"+ i);
	        chairs.add(newChair);
	    }

		numPlayers = nPlayers;
	    numPlayersReady = 0;
	    roundLoser = "";
	    roundWinner = "";
	    curRound = 1;
	    rounds = nPlayers - 1;
	    chairsTaken = 0;

	}

	
/******************************************************************************
  Helper function to access the list of players for searching or modification
******************************************************************************/
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
	
	
	
	public synchronized void waitForTheRest()
	{
		try {
//            numPlayersReady++;
            wait();
        } catch (InterruptedException e) {
        	e.printStackTrace();
//            Logger.getLogger(getName()).log(Level.SEVERE, null, e);
//            System.out.printf("Exception thrown by %s with id : %d", Thread.getName());
        }
	        
	}
	
	
/********************************************************************************
  Wakes up all player threads.
********************************************************************************/
    public synchronized void musicOff()
    {
        notifyAll();
    }	

    
/********************************************************************************
  Wakes up all player threads.
********************************************************************************/
    public synchronized void setForNextRound()
    {
        notifyAll();
    }	
    
}
