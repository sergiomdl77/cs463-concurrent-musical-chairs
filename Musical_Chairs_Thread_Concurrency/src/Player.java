import java.util.logging.Logger;
import java.util.Iterator;
import java.util.logging.Level;

/***************************************************************************
	Class that extends the qualities of a thread and represent each of the
	players participating in the musical chairs game.
****************************************************************************/
public class Player extends Thread
{
	public String name;
	public ThreadTrafficHandler traffic;	// Common object (amongst all threads)
											// that controls all wait() and
											// notifyAll() calls (very necessary 
											// to synchronize all Thread actions)
	
	public Player(ThreadTrafficHandler t, String n)
	{
		name = n;
		setName(n);
		traffic = t;
	}
	
/******************************************************************************
 	Returns a chair object which was found as empty by a player/thread.  
 	It utilizes an Iterator to avoid common modification exceptions when 
 	accessing and modifying the chairs. 
******************************************************************************/
	public synchronized Chair lookForChair()
	{
		
		boolean found = false;
		Iterator<Chair> it = traffic.chairs.iterator();
		
		// traversing list of chairs
		while(it.hasNext() && !found) 
		{
			Chair temp = it.next();
			if (temp.isEmpty() && (traffic.numPlayersReady < traffic.numPlayers-1) )
			{   
				found = true;
				temp.setStatus(false);		// sets chair status as NOT EMPTY  (empty = false)
		   		traffic.roundWinner = this.name;  // stores thread's name as winner of round
		   		return temp;
			}
		}
		
		// if it finished the look without finding an empty chair
		traffic.roundLoser = this.name;		// Stores thread's name as loser or the round
		return null;						// and the chair returned is a null (meaning no chair found)

	}  
	
	
/*****************************************************************************************
  This is the function triggered by the start() call on this thread.  This function 
  is in charge of printing a message during the round when it found this player/thread
  found a chair to sit on.
 *****************************************************************************************/
	@Override
	public void run()
	{
		while (true)
		{
			traffic.waitForTheRest();	// this thread puts itself in waiting mode 
										// until awakened by the emcee (when it wakes up
										// it will automatically start looking for empty chair
		
			Chair foundChair = lookForChair();	// looks for empty chair

			
			if(foundChair != null)				// if empty chair found, print such message
				System.out.println(this.name + " sat in " + foundChair.getName());
				
			// if this thread lost the round, break out of loop and finish running.
			if (traffic.roundLoser.equals(this.name))
				break;	

			// if this thread is the winner of the last round, break out of loop and finish running.
			if (traffic.roundWinner.equals(this.name) && traffic.curRound == traffic.rounds)
				break;
				
			traffic.numPlayersReady++;
			traffic.waitForTheRest();					// else, put itself in waiting mode
			
		}
	}	
}



