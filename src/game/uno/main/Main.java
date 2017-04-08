package game.uno.main;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import game.uno.main.Main.state;

public class Main {
	
	/* NOTE: Code has been compiled and run with JRE 1.8.0_121 it has not been tested for compatibility with alternate versions */
	
	public static boolean debug = false; // Set to true to print more information to console during runtime, could be moved to a constants class
	public static boolean startNewGame = false;	// flag for checking if game UI should be drawn
	public static boolean loadNewGameOptions = false;	// flag for checking if new game menu should load
	public static boolean validNewGame = false;	// flag for checking if game settings are correct
	public static boolean loadOptions = false;	// flag for checking if the options menu should be loaded
	public static boolean cardsNeedUpdate = true;
	private static boolean audioPlaying = true;	
	private static boolean showInfo = true;
	private boolean isRunning = true;	// Main game loop variable
		
	public enum state {MAIN_MENU, GAME, PLAYER_TURN, BOT_TURN};
	public static state gameState = state.MAIN_MENU;
	
	public static int playerCount;
	public static int nextPlayer = 0;
	
	private long variableYieldTime, lastTime;
		
	private Clip clip_1, clip_2;
	private URL url_1 = this.getClass().getResource("thunder_in_your_heart.wav");
	private URL url_2 = this.getClass().getResource("whirlwind.wav");


	public static void main(String[] args)
	{
		Main game = new Main();
		game.init();
		game.run();
	}
	
	// Method for capping game at 60 fps
    private void sync(int fps) {
    	
        if (fps <= 0) return;
          
        long sleepTime = 1000000000 / fps; // nanoseconds to sleep this frame
        long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000*1000));
        long overSleep = 0; // time the sync goes over by
          
        try {
            while (true) {
                long t = System.nanoTime() - lastTime;
                  
                if (t < sleepTime - yieldTime) {
                    Thread.sleep(1);
                } else if (t < sleepTime) {
                    // burn the last few CPU cycles to ensure accuracy
                    Thread.yield();
                } else {
                    overSleep = t - sleepTime;
                    break; // exit while loop
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);
             
            // auto tune the time sync should yield
            if (overSleep > variableYieldTime) {
                // increase by 200 microseconds (1/5 a ms)
                variableYieldTime = Math.min(variableYieldTime + 200*1000, sleepTime);
            }
            else if (overSleep < variableYieldTime - 200*1000) {
                // decrease by 2 microseconds
                variableYieldTime = Math.max(variableYieldTime - 2*1000, 0);
            }
        }
    }
	
	private void init()
	{
		// Create main menu graphics
		GameGraphics.createMainMenu();
		
		// Prepare audio tracks for play-back
		try {
			audioPrep();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void update()
	{
		switch (gameState)
		{
			case MAIN_MENU:
				
				// Run message once on game load
				if (showInfo)
				{
					JOptionPane.showConfirmDialog(null, "Just a heads up... \n1) \"Back to Main Menu\" has been removed.\n"
												+ "2) Player interaction with hand is pretty much done. \n3) New rounds currently"
												+ " throw a NullPointerException error and fail to start. \n4) Card effects are"
												+ " temporarily disabled while player hand iteraction is debugged. ");
					showInfo = false;
				}
				
				// If options menu is requested, load it
				if (loadOptions)
					GameGraphics.loadOptionsMenu();
				
				// If new game menu is requested, load menu and if menu is properly configured, start new game
				if (startNewGame)
				{
					if (loadNewGameOptions)
						GameGraphics.newGameOptions();
					
					if (validNewGame)
					{
						playerCount = 1 + GameGraphics.botCount.getValue();
						GameManager.startGame(playerCount);
					}
				}
				
				// If user disables background music, disable the music
				if (!GameGraphics.disableMusic.isSelected())
					audioPlaying = true;
				
				if (!GameGraphics.disableMusic.isSelected() && audioPlaying)
					audioPlay();
				
				if (GameGraphics.disableMusic.isSelected() && audioPlaying)
					audioStop();
					
				break;
				
			case GAME:
					break;
			
			// Framework for player interaction with cards, this method call probably shouldn't be here
			case PLAYER_TURN:
					GameGraphics.updateCardCount();
					
					Player realPlayer = GameManager.players[0];
					
					if (realPlayer.hand.size() == 1)
						realPlayer.hasUno = true;
					
					else
						realPlayer.hasUno = false;
					
					break;
				
			case BOT_TURN:
					
				// Retrieve current player from the players array at the index specified by activePlayer in gameManager
				Player currentPlayer = GameManager.players[GameManager.activePlayer];
				
				if (GameManager.activePlayer == 0)
				{
					gameState = state.PLAYER_TURN;
					break;
				}
					
				System.out.println("Player " + GameManager.activePlayer+ "'s turn");
				
		//		System.out.println("Number of cards remaining: " + GameManager.mainDeck.size());
				
				// Get next player after current player makes their move
				nextPlayer = BotOps.playMove(GameManager.players, GameManager.activePlayer, GameManager.discardDeck, GameManager.mainDeck);
				GameGraphics.updateDiscard();
				
				// If card at top of discard is a special card, apply effects of the card
		//		if (GameManager.discardDeck.peek().special)
		//			GameManager.checkEffects();
				
				// If we've reached the end of the player count, then reset back to real player and change game state to lock it
				if (nextPlayer == playerCount)
				{
					nextPlayer = 0;
					gameState = state.PLAYER_TURN;
					break;
				}
				
				// After a move has been made, set the activePlayer to the next person to play
				GameManager.activePlayer = nextPlayer;
				
				// Check if player has Uno, if so update their player data
				if (currentPlayer.hand.size() == 1)
				{
					currentPlayer.hasUno = true;
					// Button light-up code goes here
					System.out.println(currentPlayer.name + " has Uno!");
				}
				
				else 
					currentPlayer.hasUno = false;
				
				// Once someone wins, display panel with winner, need to add option to start over again
				if (currentPlayer.hand.size() == 0)
				{
				//	GameGraphics.updateCardCount();
					System.out.println(currentPlayer.name + " has won!");
					JOptionPane.showConfirmDialog(null, currentPlayer.name + " has won the round!");
					
					GameManager.calculateScore(currentPlayer);
					
					if (currentPlayer.score >= 500)
						JOptionPane.showConfirmDialog(null, currentPlayer.name + " has won the game!");
					
					System.out.println("Winner's score = " + currentPlayer.score);
					GameManager.nextRound();
					gameState = state.PLAYER_TURN;
					break;
				}
				
				System.out.println("");
				
				break;
		}
	}
	
	private void render()
	{
		switch (gameState)
		{				
			case MAIN_MENU:
				
				// If on the main menu, draw the pulsing background
				GameGraphics.menuPulse();
				
				break;
			
			case GAME:
				
				// If a new game is being started, then generate the game UI and check audio settings
				if (startNewGame)
					GameGraphics.makeGameUI();
				
				startNewGame = false;
				GameGraphics.updateDiscard();
				
				if (!GameGraphics.disableMusic.isSelected())
					audioPlay();
				
				GameGraphics.generateNewGameHand();
	//			GameManager.checkEffects();
				
				gameState = state.PLAYER_TURN;
				
		    	break;
			
		    // Basic framework for playing the game, shouldn't call updateCardCount, but it does for now
			case BOT_TURN:
				GameGraphics.updateCardCount();
				GameGraphics.updatePlayerHandLabels();
				break;
				
			case PLAYER_TURN:
				break;
		}
	}
	
	// Prepares audio for play-back
	private void audioPrep() throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		AudioInputStream is_1 = AudioSystem.getAudioInputStream(url_1);
		AudioInputStream is_2 = AudioSystem.getAudioInputStream(url_2);
		clip_1 = AudioSystem.getClip();
		clip_1.open(is_1);
		clip_2 = AudioSystem.getClip();
		clip_2.open(is_2);
		clip_2.loop(clip_2.LOOP_CONTINUOUSLY);
	}
	
	// Plays audio clips
	private void audioPlay()
	{		
		if (gameState == state.GAME)
		{
			clip_2.stop();
			clip_1.start();
		}
		
		if (gameState == state.MAIN_MENU && audioPlaying)
			clip_2.start();
	}
	
	// Stops audio clips
	private void audioStop()
	{
		audioPlaying = false;
		clip_2.stop();
	}
	
	// Main game loop for processing events and rendering changes
	private void run() 
	{		
		while(isRunning)
		{
			update();
			render();
			// Should cap the game at 60 fps
			sync(60);
		}
	}

}