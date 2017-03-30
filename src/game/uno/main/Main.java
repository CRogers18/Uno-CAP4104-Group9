package game.uno.main;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

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
	
	public static boolean debug = true; // Set to true to print more information to console during runtime, could be moved to a constants class
	private boolean isRunning = true;	// Main game loop variable
	
	public long window;
	
	private static int playerCount = 3;	// Both variables will be moved to a different section when needed, hard-coded for now
	private String playerName = "Pipsqueak";
		
	private GLFWKeyCallback keyCallback;
		
	public static void main(String[] args)
	{
		System.out.println("[INFO] Uno: CAP 4104 Edition was built using LWJGL Version " + Version.getVersion());
		Main game = new Main();
		game.init();
		game.run();
	}
	
	public void init()
	{	
		/*   *** WINDOW INITIALIZATION ***   */
				
		// Try to initialize GLFW window management
		if(!glfwInit())
			System.err.println("[ERROR] GLFW failed to initialize!");
		
		// Prevent user modification of window and create the main window
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		window = glfwCreateWindow(1280, 720, "Uno: CAP 4104 Edition", NULL, NULL);
		
		if(window == NULL)
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
		glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		System.out.println("[INFO] OpenGL Version/Graphics Driver: " + glGetString(GL_VERSION) + "\n[INFO] OpenGL has been initialized.");
		
		/*   *** Player Initialization ***   */
		// Code from 85-102 will be moved to a createGame() method when it is made
		Player[] players = GameManager.initPlayers(playerCount, playerName);
		System.out.println("[INFO] Players initialized successfully. Game has " + playerCount + " players.\n");
		
		/*   *** CARD STACKS INITIALIZATION ***   */
		Card[] newDeck = CardOps.makeNewDeck();
		
		Stack<Card> mainDeck = CardOps.shuffle(newDeck, newDeck.length, playerCount);
		System.out.println("[INFO] Main card stack has been initialized and shuffled. Stack size = " + mainDeck.size() + "\n");
		
		if (debug == true)
		{
			System.out.println("*** Post-Shuffle ***");
			
			for (int i = 0; i < 108; i++)
				System.out.println("Card color: " + newDeck[i].color + "  Card value: " + newDeck[i].value);
		}
		
		GameManager.startGame(mainDeck, players, playerCount);
		System.out.println("Main deck size following shuffle and card to match down = " + mainDeck.size());
	}

	public void update()
	{
		// Process any pending events such as user input
		glfwPollEvents();
	}
	
	public void render()
	{
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
			
			// If a user exits the window, halt the game loop and close the program
			if(glfwWindowShouldClose(window))
					isRunning = false;
		}
	}
	
	public void GLFWKeyCallback(long window, int key, int scancode, int action, int mods) 
	{
		// Some experimentation with input handling, no functionality yet
	}

}