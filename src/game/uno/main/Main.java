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
	
	public static boolean debug = false;	// Set to true to print more information to console during runtime
	public boolean isRunning = true;
	public long window;
	
	private Thread thread;
	private GLFWKeyCallback keyCallback;
	
	private Card topCard;
		 
	/* NOTE: Code has been compiled using LWJGL Version 3.1.1 and run with JRE 1.8.0_121
	         it has not been tested for compatibility with alternate versions */
	
	public static void main(String[] args)
	{
		System.out.println("[INFO] Uno: CAP 4104 Edition was built using LWJGL Version " + Version.getVersion());
		Main game = new Main();
		game.start();
	}
	
	public void start()
	{
		// Create and run a new game thread
		thread = new Thread(this, "Uno");
		thread.start();
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
		
		// Set where the window appears and readies window to receive OpenGL method calls
		glfwSetWindowPos(window, 500, 200);
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
		/*   *** OPENGL INITIALIZATION ***   */
		GL.createCapabilities();
		
		// RGB and Alpha values to be displayed when color buffer is cleared
		glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		System.out.println("[INFO] OpenGL Version/Graphics Driver: " + glGetString(GL_VERSION) + "\n[INFO] OpenGL has been initialized.");
		
		/*   *** CARD STACK INITIALIZATION ***   */
		Stack<Card> mainDeck = CardOps.firstShuffle();
		System.out.println("[INFO] Main card stack has been created");
		Stack<Card> discardDeck = new Stack<Card>();
		
	/*	for (int i = 1; i < 109; i++)
		mainDeck.push(new Card('r', 7, false, 0));
		topCard = (Card) mainDeck.pop();
		System.out.println("Removed Card with color: " + topCard.color + ", Value: " + topCard.value + ", Special: "
		+ topCard.special + ", Special Value: " + topCard.specialValue);
		discardDeck.push(topCard);
		System.out.println("Added to discard deck, card with color: " + topCard.color + ", Value: " + topCard.value + ", Special: "
		+ topCard.special + ", Special Value: " + topCard.specialValue);
		*/
		
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

	@Override
	public void run() 
	{
		init();
		
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