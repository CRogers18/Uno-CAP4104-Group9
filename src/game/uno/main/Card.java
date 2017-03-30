package game.uno.main;

import game.uno.main.Enums.*;

public class Card {
	
	public CardColor color;
	public CardValue value;
	public int score;
	
	public Card(CardColor color, CardValue value, int score)
	{
		// Initialization of card parameters
		this.color = color;
		this.value = value;
		this.score = score;				
	}
	
}