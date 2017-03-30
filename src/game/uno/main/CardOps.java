package game.uno.main;

import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class CardOps {

	private static enum SpecialValue {SKIP, REVERSE, DRAW_TWO, WILD_CARD, WILD_CARD_DRAW_FOUR};
	static int cardValue = 0, specialValue = 0;
	
	// Makes a new deck of cards in sorted order and returns the newly created deck
	public static Card[] makeNewDeck()
	{
		// Make 108 a global variable?
		Card[] newDeck = new Card[108];
		
		// This for loop could probably be made shorter
		for (int i = 0; i < 108; i++)
		{			
			// Makes all of the red cards first, 0->25
			if (i < 25)
			{
				if ((i > 9 && i < 13) || (i > 21 && i < 25))
				{
					newDeck[i] = new Card('r', -1, true, specialValue);
					specialValue++;
				}
				
				else
				{
					if (i == 13)
					{
						cardValue = 1;
						newDeck[i] = new Card('r', cardValue, false, -1);
						cardValue++;
					}
					
					else
					{
					newDeck[i] = new Card('r', cardValue, false, -1);
					cardValue++;
					}
				}
			}
			
			// Makes all the yellow cards, 26->50
			if (i > 25 && i < 51)
			{
				if ((i > 35 && i < 39) || (i > 47 && i < 51))
				{
					newDeck[i] = new Card('y', -1, true, specialValue);
					specialValue++;
				}
				
				else
				{
					if (i == 39)
					{
						cardValue = 1;
						newDeck[i] = new Card('y', cardValue, false, -1);
						cardValue++;
					}
					
					else
					{
					newDeck[i] = new Card('y', cardValue, false, -1);
					cardValue++;
					}
				}
			}
			
			// Makes all the green cards, 52->76
			if (i > 51 && i < 77)
			{
				if ((i > 61 && i < 65) || (i > 73 && i < 77))
				{
					newDeck[i] = new Card('g', -1, true, specialValue);
					specialValue++;
				}
				
				else
				{
					if (i == 65)
					{
						cardValue = 1;
						newDeck[i] = new Card('g', cardValue, false, -1);
						cardValue++;
					}
					
					else
					{
					newDeck[i] = new Card('g', cardValue, false, -1);
					cardValue++;
					}
				}
			}
			
			// Makes all the blue cards, 78->102
			if (i > 77 && i < 103)
			{
				if ((i > 87 && i < 91) || (i > 99 && i < 103))
				{
					newDeck[i] = new Card('b', -1, true, specialValue);
					specialValue++;
				}
				
				else
				{
					if (i == 91)
					{
						cardValue = 1;
						newDeck[i] = new Card('b', cardValue, false, -1);
						cardValue++;
					}
					
					else
					{
					newDeck[i] = new Card('b', cardValue, false, -1);
					cardValue++;
					}
				}
			}
			
			// Makes a black wild card at the specified index
			if (i == 25 || i == 51 || i == 77 || i == 103)
				newDeck[i] = new Card('x', -1, true, 3);
			
			// Makes a black +4 wild card
			if (i > 103)
				newDeck[i] = new Card('x', -1, true, 4);
			
			// Assigns values to the special cards , resets at 3
			if (specialValue == 3)
				specialValue = 0;
			
			// Assigns values to the regular cards 0-9, resets at 9
			if (cardValue > 9)
				cardValue = 0;
		}
		
		return newDeck;
	}
	
	// General shuffle method, takes an array of cards, the size of the array, and # of players
	public static Stack<Card> shuffle(Card[] deck, int deckLength ,int playerCount)
	{
		// Create stack of cards
		Stack<Card> mainDeck = new Stack<Card>();
		
		// Durstenfeld shuffling algorithm provides O(n) random shuffle
		for (int i = 0; i < deckLength; i++)
		{
			int j = ThreadLocalRandom.current().nextInt(i,deckLength);
			Card temp = deck[j];
			deck[j] = deck[i];
			deck[i] = temp;
		}
		
		/* In the specific case of the first card being a wild-card, swap it out with another card before pushing to
		   the stack. This code will only execute during the first shuffle where this problem can occur. */
		
		// Needs to be re-checked, instance of the first card being a wild-card has occurred, something is wrong here
		if (deckLength == 108)
		{
			while (playerCount == 2 && deck[14].color == 'x')
			{
				int j = ThreadLocalRandom.current().nextInt(14, deckLength);
				Card temp = deck[j];
				deck[j] = deck[14];
				deck[14] = temp;
			}
			
			while (playerCount == 3 && deck[21].color == 'x')
			{
				int j = ThreadLocalRandom.current().nextInt(21, deckLength);
				Card temp = deck[j];
				deck[j] = deck[21];
				deck[21] = temp;
			}

			while (playerCount == 4 && deck[28].color == 'x')
			{
				int j = ThreadLocalRandom.current().nextInt(28, deckLength);
				Card temp = deck[j];
				deck[j] = deck[28];
				deck[28] = temp;
			}
			
			while (playerCount == 5 && deck[35].color == 'x')
			{
				int j = ThreadLocalRandom.current().nextInt(35, deckLength);
				Card temp = deck[j];
				deck[j] = deck[35];
				deck[35] = temp;
			}
		}
		
		// Once everything is good, push to the stack and return the shuffled stack of cards
		for (int i = 0; i < deckLength; i++)
			mainDeck.push(deck[i]);
		
		return mainDeck;
	}
	
	// Hands the cards out to the players, takes in the players array, player count and main deck of cards
	public static void distribute(Player[] players, int playerCount, Stack<Card> mainDeck)
	{
		// Gives each player 7 cards, 1 at a time
		for (int i = 0; i < 7; i++)
		{
			for (int j = 0; j < playerCount; j++)
				players[j].hand.add(mainDeck.pop());
		}
	}
}
