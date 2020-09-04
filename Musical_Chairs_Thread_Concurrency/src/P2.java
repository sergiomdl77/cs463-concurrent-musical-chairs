import java.util.ArrayList;

public class P2
{
	public static void main(String[] args)
	{
		// obtaining the number of players from the command line arguments
		int numPlayers;
		if (args.length > 0)
			numPlayers = Integer.parseInt(args[0]);
		else
			numPlayers = 10;
		
		// creating the thread traffic controller to add it to each player
		// and to the Emcee, so that all of them have access to the wait()
		// and notifyAll() methods through the same object (in order to be 
		// effectively synchronized)
		ThreadTrafficHandler traffic = new ThreadTrafficHandler(numPlayers);

		// creating all players
		for (int i=1 ; i <= numPlayers; i++)  
	    { 
	        Player player = new Player(traffic, "P"+ i);
	        traffic.getPlayers().add(player);
	    }		
		
		// creating the emcee
		Emcee emcee = new Emcee(traffic);

		// staring the main thread, which will start all player threads.
		emcee.start();
		
		
	}
}
