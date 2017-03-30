package game.uno.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

import game.uno.main.Enums.*;

public class CardDeck
{
    
    public CardDeck()
    {
        
    }
    
    public static List<Card> makeNewDeck()
    {
    	List<Card> Cards = new ArrayList<Card>();

        for (CardColor c : CardColor.values())
        {            
        	if(c != CardColor.Wild)	// not for Wild cards since Wild cards have no c        	
            {
                for (CardValue val : CardValue.values())
                {
                    switch (val)
                    {
                    	// only 1 zero card for each color
                    	case Zero:    
                    		Card zeroCard = new Card(c, val, val.getScore());
                        	Cards.add(zeroCard);
                        	break;
                        	
                        // 2 cards for each color except wild cards
                    	case One:	
                        case Two:
                        case Three:
                        case Four:
                        case Five:
                        case Six:
                        case Seven:
                        case Eight:
                        case Nine:
                        case Skip:
                        case Reverse:
                        case DrawTwo:
                            Card card = new Card(c, val, val.getScore());
                        	Cards.add(card);
                        	Card duplCard = new Card(c, val, val.getScore());
                        	Cards.add(duplCard);                        	                           
                            break;                                                                              
                    }
                }
            }
            else	// handle for Wild cards
            {
                for (int i = 1; i <= 4; i++)	// 4 wild cards
                {
                	CardValue val = CardValue.Wild;
                	Card card = new Card(c, val, val.getScore());
                	Cards.add(card);
                }
                for (int i = 1; i <= 4; i++)	// 4 draw four wild cards
                {
                	CardValue val = CardValue.DrawFour;
                	Card card = new Card(c, val, val.getScore());
                	Cards.add(card);
                }
            }
        }
        
        return Cards;
    }

    // General shuffle method
 	public static Stack<Card> shuffle(List<Card> deck, int deckSize, int playerCount)
 	{
 		// Create
 		Stack<Card> mainDeck = new Stack<Card>();
 		
 		// "Durstenfeld shuffling algorithm"
 		for (int i = 0; i < deckSize; i++)
 		{
 			int j = ThreadLocalRandom.current().nextInt(i,deckSize);
 			Card temp = deck.get(j);
 			deck.set(j, deck.get(i));
 			deck.set(i, temp);
 		} 		 	
 		
 		// shuffle deck
 		//Collections.shuffle(deck);
 		
 		// Once everything is good, push to the stack and return the shuffled stack of cards
 		for (int i = 0; i < deckSize; i++)
 			mainDeck.push(deck.get(i));
 		
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
