package game.uno.main;

import java.util.Stack;
import java.util.Random;

public class CardOps {
	
	static int[] colorCount = {25, 25, 25, 25, 8};
	static int[] valueCount = {1, 1, 1, 1, 1};
	static int pick, cardCount = 0;
	static Random rnJesus = new Random();
	
	public CardOps()
	{
		// No-argument constructor
	}
	
	// Make a sorted deck of cards :)
	public static Card[] initDeck()
	{
		Card[] deck = new Card[108];
		
		for (int i = 0; i < 5; i++)
		{
			for (int j = 1; j < 10; j++)
			{
				// IDK
			}
		}
		
		return deck;
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
