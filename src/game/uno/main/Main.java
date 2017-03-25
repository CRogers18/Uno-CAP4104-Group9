package game.uno.main;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import java.io.IOException;
import java.nio.*;
import java.util.Stack;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main implements Runnable {
	
	/* NOTE: Code has been compiled using LWJGL Version 3.1.1 and run with JRE 1.8.0_121
    		 it has not been tested for compatibility with alternate versions */
	
	public static boolean debug = false; // Set to true to print more information to console during runtime, could be moved to a constants class
	private boolean isRunning = true;	// Main game loop variable
	
	public long window;
	
	private enum state {MENU, GAME, PLAYER_TURN, BOT_TURN};
	private static state gameState = state.BOT_TURN;
	
	private float main_menu_red = 0f;
	private boolean isFade = false;
	
	private static int playerCount = 3;	// Both variables will be moved to a different section when needed, hard-coded for now
	
	private GLFWKeyCallback keyCallback;
	private long variableYieldTime, lastTime;
	
	public static int nextPlayer = 0;
	
	public static void main(String[] args)
	{
		System.out.println("[INFO] Uno: CAP 4104 Edition was built using LWJGL Version " + Version.getVersion());
		Main game = new Main();
		game.init();
		game.run();
	}
	
    /*
     * An accurate sync method that adapts automatically
     * to the system it runs on to provide reliable results.
     * 
     * @param fps The desired frame rate, in frames per second
     * @author kappa (On the LWJGL Forums)
     */
	
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
	
	public void init()
	{
		/*   *** WINDOW INITIALIZATION ***   */
		
		// Try to initialize GLFW window management
		if (!glfwInit())
			System.err.println("[ERROR] GLFW failed to initialize!");
		
		// Prevent user modification of window and create the main window
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		window = glfwCreateWindow(1280, 720, "Uno: CAP 4104 Edition", NULL, NULL);
		
		if (window == NULL)
			System.err.println("[ERROR] GLFW window creation failed!");
		
		// Gets the connected user monitor to determine possible video modes. Ex: full-screen, windowed, etc.
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		glfwSetKeyCallback(window, keyCallback);
		
		// Set where the window appears and readies window to receive OpenGL calls
		glfwSetWindowPos(window, 500, 200);
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
		/*   *** OPENGL INITIALIZATION ***   */
		GL.createCapabilities();
		
		// RGB and Alpha values to be displayed when color buffer is cleared, currently set to red
		glClearColor(0.55f, 0.0f, 0.0f, 0.0f);
		glEnable(GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		System.out.println("[INFO] OpenGL Version/Graphics Driver: " + glGetString(GL_VERSION) + "\n[INFO] OpenGL has been initialized.");
		
		/*   *** CARD STACKS INITIALIZATION ***   */
		GameManager.startGame(playerCount);
	}

	public void update()
	{
		// Process any pending events such as user input
		glfwPollEvents();
		
		switch(gameState)
		{
		
			case PLAYER_TURN:
				
				break;
				
			case BOT_TURN:
				
				// ArrayIndexOutOfBoundsException is thrown when these checks are made after the nextPlayer assignment (bug?)
				Player currentPlayer = GameManager.players[nextPlayer];
				
				if (currentPlayer.hand.size() == 1)
				{
					currentPlayer.hasUno = true;
					System.out.println(nextPlayer + " has Uno!");
				}
				
				if (currentPlayer.hand.size() == 0)
				{
					System.out.println(nextPlayer + " has won!");
					gameState = state.MENU;
				}
				
				System.out.println("Number of cards remaining: " + GameManager.mainDeck.size());
				System.out.println("Player 0: " + GameManager.players[0].hand.size() + "\n" + "Player 1: " + GameManager.players[1].hand.size() + "\n" + "Player 2: " + GameManager.players[2].hand.size() + "\n");
				
				nextPlayer = BotOps.playMove(GameManager.players, GameManager.currentPlayer, GameManager.discardDeck, GameManager.mainDeck);
				System.out.println("");
				
				if (nextPlayer == 3)
				{
					System.out.println("Reseting players");
					nextPlayer = 0;
				}
				GameManager.currentPlayer = nextPlayer;
				break;
		}
	}
	
	public void render()
	{
		
		switch(gameState)
		{
			case MENU:
				
				glBegin(GL_QUADS);
					glColor4f(main_menu_red, 0, 0, 0);
					glVertex2f(-1.0f, 1.0f);
					glVertex2f(1.0f, 1.0f);
					glVertex2f(1.0f, -1.0f);
					glVertex2f(-1.0f, -1.0f);
				glEnd();
				
				if (isFade == false)
				{
					main_menu_red+=0.005f;
					
					if (main_menu_red == 0.75f)
						isFade = true;
				}
				
//				System.out.println("isFade = " + isFade);
				
				break;
			
			case GAME:
				
				glBegin(GL_QUADS);
					glColor4f(main_menu_red, 0, 0, 0);
					glVertex2f(-1.0f, 1.0f);
					glVertex2f(1.0f, 1.0f);
					glVertex2f(1.0f, -1.0f);
					glVertex2f(-1.0f, -1.0f);
				glEnd();
				
			break;
			
			case BOT_TURN:
								
				glBegin(GL_QUADS);
				
				switch(GameManager.discardDeck.peek().color)
				{
					case 'r':
						glColor4f(0.8f, 0, 0, 0);
						break;
						
					case 'y':
						glColor4f(0.8f, 0.8f, 0, 0);
						break;
						
					case 'g':
						glColor4f(0, 0.8f, 0, 0);
						break;
						
					case 'b':
						glColor4f(0, 0, 0.8f, 0);
						break;
						
					case 'x':
						glColor4f(0, 0, 0, 0);
						break;
				}
				
					glVertex2f(-0.25f, 0.5f);
					
					glVertex2f(-0.25f, -0.5f);
					
					glVertex2f(0.25f, -0.5f);
					
					glVertex2f(0.25f, 0.5f);

				glEnd();
				
				break;
		}
		
		// Swap front and back window buffers 
		glfwSwapBuffers(window);
		
		// Clear color buffers on render call
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
	}

	public void run() 
	{		
		// Main game loop for processing events and rendering changes
		while(isRunning)
		{
			update();
			render();
			// Should cap the game at 60 fps
			sync(60);
			
			// If a user exits the window, halt the game loop and close the program
			if (glfwWindowShouldClose(window))
					isRunning = false;
		}
	}
	
	public void GLFWKeyCallback(long window, int key, int scancode, int action, int mods) 
	{
		// Some experimentation with input handling, no functionality yet
	}

}