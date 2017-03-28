package game.uno.main;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main {
	
	/* NOTE: Code has been compiled and run with JRE 1.8.0_121 it has not been tested for compatibility with alternate versions */
	
	public static boolean debug = false; // Set to true to print more information to console during runtime, could be moved to a constants class
	public static boolean startNewGame = false;
	public static boolean loadNewGameOptions = false;
	public static boolean validNewGame = false;
	public static boolean loadOptions = false;
	private boolean isRunning = true;	// Main game loop variable
		
	public enum state {MAIN_MENU, GAME, PLAYER_TURN, BOT_TURN};
	public static state gameState = state.MAIN_MENU;
	
	private static int playerCount;
	public static int nextPlayer = 0;
	
	private long variableYieldTime, lastTime;
		
	private Clip clip;
	private URL url = this.getClass().getResource("nggyu.wav");

	public static void main(String[] args)
	{
		Main game = new Main();
		game.init();
		game.run();
	}
	
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
		
		GameGraphics.createMainMenu();
		
		// *Palpatine voice* DO IT
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
		switch(gameState)
		{
			case MAIN_MENU:
				
				if (loadOptions)
					GameGraphics.loadOptionsMenu();
				
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
				
				break;
				
			case GAME:
				
				break;
			
			case PLAYER_TURN:
				
					break;
				
			case BOT_TURN:
	
				// This is confusing... players[nextPlayer] = currentPlayer? Terrible variable name that need to be re-named.
				Player currentPlayer = GameManager.players[nextPlayer];
				
				System.out.println("Number of cards remaining: " + GameManager.mainDeck.size());
				System.out.println("Player 0: " + GameManager.players[0].hand.size() + "\n" + "Player 1: " + GameManager.players[1].hand.size() + "\n" + "Player 2: " + GameManager.players[2].hand.size() + "\n");
				
				nextPlayer = BotOps.playMove(GameManager.players, GameManager.currentPlayer, GameManager.discardDeck, GameManager.mainDeck);
				
				if (nextPlayer == 3)
				{
					System.out.println("Reseting players");
					nextPlayer = 0;
				}
				
				if (currentPlayer.hand.size() == 1)
				{
					currentPlayer.hasUno = true;
					System.out.println(currentPlayer.name + " has Uno!");
				}
				
				if (currentPlayer.hand.size() == 0)
				{
					System.out.println(currentPlayer.name + " has won!");
					gameState = state.MAIN_MENU;
				}
				
				System.out.println("");
				GameManager.currentPlayer = nextPlayer;
				break;
		}
	}
	
	private void render()
	{
		switch(gameState)
		{				
			case MAIN_MENU:
				
				GameGraphics.menuPulse();
				
				break;
			
			case GAME:
				
				if (startNewGame)
					GameGraphics.missingGraphics();
				
				startNewGame = false;
				
				if (!GameGraphics.disableMusic.isSelected())
					audioPlay();
				
		    	break;
			
			case BOT_TURN:
				break;
				
			case PLAYER_TURN:
				break;
		}
	}
	
	private void audioPrep() throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		AudioInputStream is = AudioSystem.getAudioInputStream(url);
		clip = AudioSystem.getClip();
		clip.open(is);
		
		/*
		while(true)
		{
			clip.start();
			clip.stop();
			clip.setFramePosition(0);
		}
		*/
	}
	
	private void audioPlay()
	{
		clip.start();
	}

	private void run() 
	{		
		// Main game loop for processing events and rendering changes
		while(isRunning)
		{
			update();
			render();
			// Should cap the game at 60 fps
			sync(60);
		}
	}

}