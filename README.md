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


