# Dodge!

### Summary

This is a game about dodging red squares! The more squares you dodge, the better you score. It includes a leaderboard, 
so you can see how much worse all your friends are than you, and it is saved between sessions! The main file creates a 
new GameDisplay, which then runs all the code.

### Instructions

Use the mouse to interact with buttons. After starting the game, use wasd, arrow keys, or the numpad (limited to 
orthogonal directions for fairness) to move around. Yellow squares will become red squares. Touching a red square will
damage you. Touching green squares gives you bonus points, and can sometimes reduce the difficulty or give you life!
You get points for surviving as long as possible.

The size controls are pretty self-explanatory, but size 4 is special. It changes the game to not include the randomly 
designed attacks, as they will otherwise take up the entire screen. The size can be changed from 4 to 15, inclusive, and
each size has its own leaderboard.

### File descriptions

* .gitignore
  * Makes sure that the leaderboard is not saved to git.
* EndPanel
  * The JPanel that asks you for your name after you lose the game.
* GameDisplay
  * The class which holds the JFrame and instances of every JPanel. It also handles logic for switching between JPanels.
* GameManager
  * The class that manages the actual status of the game. It does not interact with swing directly.
* GamePanel
  * The JPanel that draws the game using a Graphics object. It also manages the game loop.
* LeaderboardPanel
  * The JPanel that displays the leaderboard after each game. It also has controls to prune or clear the leaderboards.
* Main
  * Creates a new GameDisplay.
* MenuPanel
  * The JPanel shown after the application is run. Allows you to navigate to play the game or view the leaderboard.
* Score
  * A simple data class representing one score in the leaderboard. Implements comparable.
* Tile
  * A state machine managing the status of each tile in the game.

### Sources:
* https://jvm-gaming.org/t/game-loops/36689
  * Referenced while writing game loop.
* https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it
  * Referenced while writing code for leaderboard file saving
* https://stackoverflow.com/questions/16111496/java-how-can-i-write-my-arraylist-to-a-file-and-read-load-that-file-to-the
  * Referenced while writing code for leaderboard file saving