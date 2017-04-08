package game.uno.main;

import java.util.Stack;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import game.uno.main.Main.state;

public class GameManager {
	
	public static String playerName = "Pipsqueak";
	
	public static Card[] newDeck;
	public static Stack<Card> mainDeck;
	public static Player[] players;
	public static Stack<Card> discardDeck;
	public static int activePlayer = 0;
	
	private static int playerEffected;
			
	public static Player[] initPlayers(int playerCount, String playerName)
	{
		Player[] players = new Player[playerCount];
		String[] names = {"Tristana", "Shaco", "Akali", "Jinx", "Caitlyn", "Darius", "Diana", "Garen", "Jax", "Rengar"};
		
		// shuffle the array and then take the first names, guarantee unique names
		Collections.shuffle(Arrays.asList(names));
				
		for (int i = 1; i <= playerCount; i++)
		{
			// enumerate these values for improved readability, 0 = player, 1 = bot1, etc.
			switch (i)
			{
				case 1:
					players[0] = new Player(playerName);
					break;
				
				case 2:
					players[1] = new Player(names[0]);
					break;
					
				case 3:
					players[2] = new Player(names[1]);
					break;
					
				case 4:
			    	players[3] = new Player(names[2]);	

					break;
					
				case 5:
					players[4] = new Player(names[3]);
					break;
					
				default:
					System.out.println("[ERROR] Player creation encountered an error!");
					break;
			}
		}
		
		return players;
	}
	
	public static void startGame(int playerCount)
	{
		Main.gameState = state.GAME;
		newDeck = CardOps.makeNewDeck();
		
		mainDeck = CardOps.shuffle(newDeck, newDeck.length, playerCount);
		System.out.println("[INFO] Main card stack has been initialized and shuffled. Stack size = " + mainDeck.size());
		
		if (Main.debug == true)
		{
			System.out.println("*** Post-Shuffle ***");
			
			for (int i = 0; i < 108; i++)
				System.out.println("Card color: " + newDeck[i].color + "  Card value: " + newDeck[i].value);
		}
		
		players = GameManager.initPlayers(playerCount, playerName);
		
		System.out.println("[INFO] Players initialized successfully. Game has " + playerCount + " players.");
		
		// Hand out cards to each player
		CardOps.distribute(players, playerCount, mainDeck);
		
		// Create the discard deck stack
		discardDeck = new Stack<Card>();
		
		// Remove the first card from the main deck and push it to the discard stack
		Card tempCard = mainDeck.pop();
		discardDeck.push(tempCard);
		
		for(int i = 0; i < playerCount; i++)
		{
			System.out.println("\nPlayer " + i + " Cards:");
			for(int j = 0; j < 7; j++)
			{
				System.out.print(players[i].hand.get(j).color + "" + players[i].hand.get(j).value);
				// Cause it needs to look perfect ;_;
				if (j != 6)
					System.out.print(", ");
				else
					System.out.print(" ");
			}
			System.out.println("");
		}
		
		System.out.println("\n[INFO] Main deck size following shuffle and card to match = " + mainDeck.size());
		System.out.println("Card to match is: " + tempCard.color + " " + tempCard.value + ", Special: " + tempCard.special + " , SpecialValue: " + tempCard.specialValue + "\n");		
	}
	
	// Method to check if user has a valid move, code will likely be moved when a more appropriate place is found
	public static boolean attemptMove(Card cardPlayed)
	{	
		char colorCheck = cardPlayed.color, discardCheck = discardDeck.peek().color;
		int valCheck = cardPlayed.value, discardValCheck = discardDeck.peek().value;
		
		if (colorCheck != discardCheck || valCheck != discardValCheck || colorCheck != 'x')
			return false;
			
		else
		{
			discardDeck.push(cardPlayed);
			System.out.println("Valid move. Pushed card to discard stack.");
			return true;
		}
	}
	
	public static void checkEffects()
	{
		int specialCardValue = discardDeck.peek().specialValue;
		boolean isSpecial = discardDeck.peek().special;
		
		if (isSpecial)
		{
			switch (specialCardValue)
			{
				case 0:
					System.out.println("Special card is a skip card");
					
					activePlayer += 2;
					
					if (activePlayer > Main.playerCount)
						activePlayer = 1;
					
					if (activePlayer == Main.playerCount)
						activePlayer = 0;
					
					System.out.println("Skipping player " + (activePlayer - 1) + "'s turn");
					
					/* Add 2 to current player, if it goes over playerCount apply skip to player0. Otherwise
					   move to (current player + 2) */
					break;
				
				case 1:
					System.out.println("Special card is a reverse card");
					
					if (!GameGraphics.reversedFlow)
					{
						GameGraphics.direction.setIcon(new ImageIcon(GameGraphics.class.getResource("/textures/cw_direction.png")));
						GameGraphics.reversedFlow = true;
						break;
					}
					
					else
					{
						GameGraphics.direction.setIcon(new ImageIcon(GameGraphics.class.getResource("/textures/ccw_direction.png")));
						GameGraphics.reversedFlow = false;
					}
					
					/* Switch some boolean value in botOps and Main to have it return --currentPlayer rather than ++ */
					break;
					
				case 2:
					System.out.println("Special card is a draw two card");
					
					playerEffected = activePlayer + 1;
					
					if (playerEffected == Main.playerCount)
						playerEffected = 0;
					
					for (int i = 0; i < 2; i++)
					{
						Card draw2 = mainDeck.pop();
						players[playerEffected].hand.add(draw2);
						players[playerEffected].hand.trimToSize();
						
						if (playerEffected == 0)
							GameGraphics.updatePlayerHandLabels();
					}
					
					System.out.println("Player " + playerEffected + " has drawn 2 cards");
					
					break;
					
				case 3:
					System.out.println("Special card is a wild card");
					// Nothing needs to be done as wild card color picking is already handled by botOps
					break;
					
				case 4:
					System.out.println("Special card is a wild card draw four");
					// Just need to add 1 to current player and have that player draw 4 cards, color picking is handled by botOps
					playerEffected = activePlayer + 1;
					
					if (playerEffected == Main.playerCount)
						playerEffected = 0;
					
					for (int i = 0; i < 4; i++)
					{
						Card draw4 = mainDeck.pop();
						players[playerEffected].hand.add(draw4);
						players[playerEffected].hand.trimToSize();
						
						if (playerEffected == 0)
							GameGraphics.updatePlayerHandLabels();
					}
					
					System.out.println("Player " + playerEffected + " has drawn 4 cards");
					
					break;
					
				default:
					System.out.println("[ERROR] Special card has an improperly defined special value!");
					// If we get here something went wrong
					break;
			}
		}
					
	}

	public static void calculateScore(Player winner) 
	{	
		int score = winner.score;
		
		for (int i = 0; i < Main.playerCount; i++)
		{
			for (int j = 0; j < players[i].hand.size(); j++)
			{
				// If the card is a special card, get what type it is
				if (players[i].hand.get(j).special)
				{
					// Assign points according to special card value
					switch (players[i].hand.get(j).specialValue)
					{
						// Skip
						case 0:
							score += 20;
							break;
						
						// Draw-two
						case 1:
							score += 20;
							break;
						
						// Reverse
						case 2:
							score += 20;
							break;
						
						// Wild-card
						case 3:
							score += 50;
							break;
						
						// Wild-card + draw-four
						case 4:
							score += 50;
							break;
							
						default:
							System.out.println("[ERROR] While trying to calculate special card score!");
							break;
					}
				}
				
				// Otherwise add card values together to get score
				score += players[i].hand.get(j).value;
			}
		}
		
		winner.score = score;
		
	}

	public static void nextRound() {
		
		Card[] newRoundDeck = new Card[108];
		int index = 0;
		int playerCount = Main.playerCount;
		
		// First collect all the cards from players hands and stick them in a new array
		for (int i = 0; i < playerCount; i++)
		{
			for (int j = 0; j < players[i].hand.size(); j++)
			{
				Card card = players[i].hand.remove(j);
				newRoundDeck[index] = card;
				index++;
			}
			
			players[i].hand.trimToSize();
			System.out.println("Player " + i + " cards remaining in hand: " + players[i].hand.size());
		}
		
		// Next empty both the mainDeck and discardDeck stacks of their cards
		while (!mainDeck.empty())
		{
			Card card = mainDeck.pop();
			newRoundDeck[index] = card;
			index++;
		}
		
		while (!discardDeck.empty())
		{
			Card card = discardDeck.pop();
			newRoundDeck[index] = card;
			index++;
		}
		
		// Now that all of the cards have been collected, pass them to the shuffle method
		mainDeck = CardOps.shuffle(newRoundDeck, 108, playerCount);
		
		// Distribute our newly shuffled deck of cards to the players
		CardOps.distribute(players, playerCount, mainDeck);
		
		Card firstCard = mainDeck.pop();
		discardDeck.push(firstCard);
		
		// Print new hands for verification that shuffle was successful
		for (int i = 0; i < playerCount; i++)
		{
			System.out.println("\nPlayer " + i + " Cards:");
			for(int j = 0; j < 7; j++)
			{
				System.out.print(players[i].hand.get(j).color + "" + players[i].hand.get(j).value);
				// Cause it needs to look perfect ;_;
				if (j != 6)
					System.out.print(", ");
				else
					System.out.print(" ");
			}
			System.out.println("");
		}
		
		System.out.println("\n[INFO] Main deck size following shuffle and card to match = " + mainDeck.size());
		System.out.println("Card to match is: " + firstCard.color + " " + firstCard.value + ", Special: " + firstCard.special + " , SpecialValue: " + firstCard.specialValue + "\n");
	}
	
}