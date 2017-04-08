package game.uno.main;

import java.util.ArrayList;

public class Player {
	
	public String name;										// Player name 
	public static int score = 0;							// Player score
	public boolean hasUno = false;							// Default Uno status of false
	public ArrayList<Card> hand = new ArrayList<Card>();	// Player cards hand
	
	public Player(String username)
	{
		// When a new player is created, set the passed string to the player name
		name = username;
	}	
}