# cs463-musical-chairs-thread-concurrency

The Task: Musical Chairs
An N number of players want to sit on (N-1) chairs. An emcee (the announcer) 
turns the music on and off, and comments on who loses, who wins, and so on. Each
round, when the music turns off, all active players try to sit in the chairs,
but one will not be able to find a chair. The emcee announces that that
player has lost, and the next round begins with one fewer chairs and one
fewer players, until there is one winner.

Requirements for this project:
The number of players is the first command-line argument to the
whole program. When not present, your code must default to N = 10 .
The emcee is a separate thread, which exists for the duration of the
game. (it is okay to use the main thread for the emcee).

Each player is a separate thread. Each player has a 'name', from P1
through PN . These player threads exist for the duration of the game,
they are not recreated each round.

Each chair is a separate resource (e.g., an object). The chairs are
named C1 through C(N-1) . It must be possible for multiple players to
obtain different chairs at the same time.

If you have any sort of global lock on all chairs (meaning that
only one player at a time can access them) then you are not
fulfilling the requirements of this project. A common example
would be that the chairs are not just in an array, but that the
only way to find a chair to access is through some method call
that 'controls' the array, or if the entire array is
synchronized/locked while an individual is looking for a chair.
each round, whichever player did not manage to obtain a chair is
out. The remaining player threads get to play in the next round, and
the highest-numbered chair is removed. This means that we always
have chairs C1 through C(k-1), but not necessarily players P1
through Pk. In the last round, it's always chair C1, but it
could/should be any two of the original players.



My approach for this project:

1. Structure Notes:
what is the shared resource that represents the music? 
For that I simply used a Thread.sleep(1000).

what did I use to represent a chair?
A class called Chair (with attributes:  name, and  empty) 

Declared at:  ThreadTrafficHandler.java   (line 13)
Initialized at:   ThreadTrafficHandler.java   (line 28)

2. What was my strategy for players to find empty chairs?
I used a an Iterator to traverse the ArrayList of chairs and checking on the status “empty == true”

How does this ensure all chairs are eventually found?
All chairs are initially set to “empty = true”.  The chairs that have already been found are set 
to “empty = false”, which makes them be skipped by the player when looking for empty chairs.

3. What were the challenges that I faced?
There was a lot of necessary learning about how to make all the player threads respond to the wait() and notifyAll() methods (for which an object that all thread classes have access to is necessary).  

NOTE:  The biggest challenge I encountered was the fact that even though all of the threads worked correctly after the first round, for some reason I was never able to fully fix Round 1. The bug is that, sometimes, the first round will not get any loser, because somehow the last player looking for a chair in the first round still gets to find one.  It is interesting the sometimes even the first round works perfectly during the game.

Where was there competition for resources, and where was there a need for cooperation?
It was always while trying to print something on screen.  That is why the threads had to constantly put in waiting mode.  Also, I had to make sure to make certain functions “synchronized” so that they could only be executed by one player at a time.  Examples:  removeChair(),  resetChair(), lookForChair(), waitForTheRest(), musicOff().

4. What aspects of the task were straightforward, and which ones felt difficult or laborious?
Straight forward:  The creation of the helper methods to accomplish the small tasks by threads. Also, capturing the arguments from the command line.  
Laborious:  The decision making on how to split the code into different classes and deciding how to make the important variables be accessible to both the player and the emcee classes.

5. What kinds of bugs did I run into – deadlock?
My for-loop on the Emcee.run() had a stop for the counter that I was accidentally modifying inside the loop, which was resulting in an undesired number of iterations of such loop (unexpected number of rounds).  
I was also experiencing problems due to not resetting the chairs to “empty = true”.  This was causing to have no winners in the rounds following the first round.

Ordering issues? 
I was fortunate to not encounter ordering issues.  Once my code was debugged for other problems, it was properly printing everything in the expected order.  It was also behaving correctly in the sense that the order in which the player/thread got to look for a chair was random.


6. Any extra thoughts or comments?
This Project allowed me to understand how to control/monitor different threads while letting them do some important work when they are not made to “wait”.   Concurrency is a very important part of software development these days, and I appreciated the opportunity to practice thread sychronization.


