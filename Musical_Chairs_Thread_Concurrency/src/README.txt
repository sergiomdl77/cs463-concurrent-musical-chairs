Sergio Delgado
CS 463, Spring, 2020

1. Structure Notes:
what is the shared resource that represents the music? 
For that I simply used a Thread.sleep(1000).

And specifically, where (file/line numbers) does the emcee turn on/off the music?
Music On:   Emcee.java    (line 88)
Music Off:  Emcee.java    (line 95)

what do you use to represent a chair?
A class called Chair (with attributes:  name, and  empty) 

Declared at:  ThreadTrafficHandler.java   (line 13)
Initialized at:   ThreadTrafficHandler.java   (line 28)

2. What was your strategy for players to find empty chairs?
I used a an Iterator to traverse the ArrayList of chairs and checking on the status “empty == true”

How does this ensure all chairs are eventually found?
All chairs are initially set to “empty = true”.  The chairs that have already been found are set 
to “empty = false”, which makes them be skipped by the player when looking for empty chairs.

3. For our programming task, what were the challenges that you faced?
There was a lot of necessary learning about how to make all the player threads respond to the wait() 
and notifyAll() methods (for which an object that all thread classes have access to is necessary).  
NOTE:  The biggest challenge I encountered was the fact that even though all of the threads worked 
correctly after the first round, for some reason I was never able to fully fix Round 1. The bug is 
that, sometimes, the first round will not get any loser, because somehow the last player looking for 
a chair in the first round still gets to find one.  It is interesting the sometimes even the first
round works perfectly during the game.

Where was there competition for resources, and where was there a need for cooperation?
It was always while trying to print something on screen.  That is why the threads had to constantly put 
in waiting mode.  Also, I had to make sure to make certain functions “synchronized” so that they could 
only be executed by one player at a time.  Examples:  removeChair(),  resetChair(), lookForChair(), 
waitForTheRest(), musicOff().

4. What aspects of the task were straightforward, and which ones felt difficult or laborious?
Straight forward:  The creation of the helper methods to accomplish the small tasks by threads.  
Also, capturing the arguments from the command line.  
Laborious:  The decision making on how to split the code into different classes and deciding how to make 
the important variables be accessible to both the player and the emcee classes.

5. What kinds of bugs did you run into – deadlock?
My for-loop on the Emcee.run() had a stop for the counter that I was accidentally modifying inside the 
loop, which was resulting in an undesired number of iterations of such loop (unexpected number of rounds).  
I was also experiencing problems due to not resetting the chairs to “empty = true”.  This was causing 
to have no winners in the rounds following the first round.

Ordering issues? 
I was fortunate to not encounter ordering issues.  Once my code was debugged for other problems, it was 
properly printing everything in the expected order.  It was also behaving correctly in the sense that 
the order in which the player/thread got to look for a chair was random.


How did you attempt to inspect what was going wrong with the code? 
(Did you use any debuggers or anything?)
I utilized printouts that allowed me to see when threads were running and in what stage of their code 
they where at the time. They allowed me to figure out what round of the game they were at the mmoment
of printing out any of the mandatory printouts.

6. Any extra thoughts or comments?
This Project allowed me to understand how to control/monitor different threads while letting them do 
some important work when they are not made to “wait”.   Concurrency is a very important part of software 
development these days, and I appreciate the opportunity to practice thread maniputation.
