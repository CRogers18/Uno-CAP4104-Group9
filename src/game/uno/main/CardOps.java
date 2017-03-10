package game.uno.main;

import java.util.Stack;
import java.util.Random;

public class CardOps {
	
	static int[] colorCount = {25, 25, 25, 25, 8};
	static int[] valueCount = {1, 1, 1, 1, 1};
	static int pick, cardValue = 0, specialValue = 0, cardCount = 0;
	static Random rnJesus = new Random();
	
	public CardOps()
	{
		// No-argument constructor
	}
	
	public static Card[] makeNewDeck()
	{
		Card[] newDeck = new Card[108];
		
		for (int i = 0; i < 108; i++)
		{
			// Makes all the red cards
			if (i < 26)
			{
				if ((i >= 10 && i < 13) || (i >= 23 && i < 26))
				{
					newDeck[i] = new Card('r', -1, true, specialValue);
					specialValue++;
				}
				else
				{
					newDeck[i] = new Card('r', cardValue, false, 0);
					cardValue++;
				}
			}
			
			// Makes all the yellow cards
			if (i >= 26 && i < 52)
			{
				if ((i >= 36 && i < 39) || (i >= 49 && i < 52))
				{
					newDeck[i] = new Card('y', -1, true, specialValue);
					specialValue++;
				}
				else
				{
					newDeck[i] = new Card('y', cardValue, false, 0);
					cardValue++;
				}
			}
			
			// Makes all the green cards
			if (i >= 52 && i < 78)
			{
				if ((i >= 62 && i < 65) || (i >= 75 && i < 78))
				{
					newDeck[i] = new Card('g', -1, true, specialValue);
					specialValue++;
				}
				else
				{
					newDeck[i] = new Card('g', cardValue, false, 0);
					cardValue++;
				}
			}
			
			// Makes all the blue cards
			if (i >= 78 && i < 104)
			{
				if ((i >= 88 && i < 91) || (i >= 101 && i < 104))
				{
					newDeck[i] = new Card('g', -1, true, specialValue);
					specialValue++;
				}
				else
				{
					newDeck[i] = new Card('g', cardValue, false, 0);
					cardValue++;
				}
			}
			
			// Makes all the black cards
			if (i >= 104)
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
	
	// SHuffling cards returns a shuffled stack, may need a new method for different kind of shuffle
	public static Stack<Card> firstShuffle()
	{
		Stack<Card> mainDeck = new Stack<Card>(); 
		pick = rnJesus.nextInt(5);
		
		while (cardCount != 108)
		{
			pick = rnJesus.nextInt(5);
			
			// Super stupid way of forcing randomization, plz someone make it better... thx
			// valueCount can be fixed back to > 10, once special cards are implemented
			while (colorCount[pick] == 0 || valueCount[pick] > 108 && cardCount < 108)
				pick = rnJesus.nextInt(5);

			switch (pick)
			{
				case 0:
					Card newRed = new Card('r', valueCount[0], false, 0);
					mainDeck.push(newRed);
					colorCount[0]--;
					valueCount[0]++;
					break;
					
				case 1:
					Card newBlue = new Card('b', valueCount[1], false, 0);
					mainDeck.push(newBlue);
					colorCount[1]--;
					valueCount[1]++;
					break;
					
				case 2:
					Card newYellow = new Card('y', valueCount[2], false, 0);
					mainDeck.push(newYellow);
					colorCount[2]--;
					valueCount[2]++;
					break;
					
				case 3:
					Card newGreen = new Card('g', valueCount[3], false, 0);
					mainDeck.push(newGreen);
					colorCount[3]--;
					valueCount[3]++;
					break;
					
				case 4:
					Card newBlack = new Card('x', valueCount[4], false, 0);
					mainDeck.push(newBlack);
					colorCount[4]--;
					valueCount[4]++;
					break;
					
				default:
					System.out.println("[ERROR] Pick value of: " + pick + " failed to generate a new card");
					break;
			}
			
			cardCount++;
		}
		
		return mainDeck;
	}
	
	public static Card[] shuffle(Card[] deck)
	{
		// Google "Durstenfeld shuffling algorithm" and put it here with the sorted deck from initDeck()
		
		return deck;
	}

}
