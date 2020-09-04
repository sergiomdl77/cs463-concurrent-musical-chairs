import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.Iterator;


/***************************************************************************************
 Class created to be the main thread that coordinates the rest of the threads (players)
****************************************************************************************/ 
public class Emcee extends Thread
{
	public ThreadTrafficHandler traffic;
	
	public Emcee (ThreadTrafficHandler t)
	{
	    traffic = t;
	}
	
	
/****************************************************************************************
 Adds a delay on the execution of Emcee, to emulate music being played.
****************************************************************************************/
	public void musicOn() throws InterruptedException
	{	
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}  

	
/*****************************************************************************************
  Sets the chairs' status (isEmpty) to true, to make them available for the players.
*****************************************************************************************/
	public synchronized void resetChairs()
	{   
		for(Chair chair : traffic.chairs)	// setting all chair status back to "empty"
	        chair.setStatus(true);
		traffic.chairsTaken = 0;
	}
    
/*****************************************************************************************
  Removes the last chair from the ArrayList so that it is no longer available for players.
*****************************************************************************************/
	public synchronized void removeChair()
	{
		Iterator<Chair> it = traffic.chairs.iterator();
		while(it.hasNext()) 
		  it.next();
		it.remove();
	}
	
	
/*****************************************************************************************
  This function is used to verify that all the players (still active) are done searching
  for chairs and waiting for the next round to start.
*****************************************************************************************/
	private boolean allPlayersReady()
	{
		return (traffic.numPlayersReady == traffic.numPlayers);
	}
	
	
/*****************************************************************************************
  This is the function triggered by the start() call on this thread.  This function 
  is in charge of printing the main messages of every round, besides coordinating when
  the player threads must be awaken and when to be put to wait.
*****************************************************************************************/
	@Override
	public void run()
	{
		// Beginning of the game print out
		System.out.print("BEGIN " + traffic.numPlayers + " players\n\n");
				
		for (int i=0; i<traffic.numPlayers; i++)	// We let the players start playing the game
			traffic.getPlayers().get(i).start();	// At this point, every player starts, but the first 
													// step in their code is to wait until awaken to 
													// start looking for chairs
		
		// going through rounds and printing its details
		for (traffic.curRound = 1; traffic.curRound <= traffic.rounds; traffic.curRound++)
		{
			System.out.print("Round " + traffic.curRound + "\n");
			
			// creating a delay to emulate music playing
			try {
				musicOn();				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
			
			// awake players to look for chairs
			System.out.print("Music off\n");
			traffic.musicOff();			
			
			
			// putting emcee to sleep to wait for all players to try find a chair  
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			
			// once every player is done looking for chairs
			System.out.print("");
			System.out.print(traffic.roundLoser + " lost\n\n");
				
			
			traffic.numPlayers--;				// setting up number of players expected to look for chairs
			traffic.numPlayersReady = 0;		//	

			removeChair();				//	removing a chair 
			resetChairs();				//	and setting all remaining ones to empty

			traffic.setForNextRound();	// wakes every player for the next round
										// (on the last round there will be no more thread to wake up
										// because all of them would have run through their whole function code)
		}
		
		// End of the game print outs.
		System.out.println(traffic.roundWinner + " wins!");
		System.out.println("END");

	}
	
}
