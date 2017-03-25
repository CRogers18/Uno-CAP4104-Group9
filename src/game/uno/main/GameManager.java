package game.uno.main;

import java.util.Stack;

public class GameManager {
	
	private static String playerName = "Pipsqueak";
	
	public static Card[] newDeck;
	public static Stack<Card> mainDeck;
	public static Player[] players;
	public static Stack<Card> discardDeck;
	public static int currentPlayer = 0;
			
	public static Player[] initPlayers(int playerCount, String playerName)
	{
		Player[] players = new Player[playerCount];
				
		for (int i = 1; i <= playerCount; i++)
		{
			// enumerate these values for improved readability, 0 = player, 1 = bot1, etc.
			switch(i)
			{
				case 1:
					players[0] = new Player(playerName);
					break;
				
				case 2:
					players[1] = new Player("Bot 1");
					break;
					
				case 3:
					players[2] = new Player("Bot 2");
					break;
					
				case 4:
					players[3] = new Player("Bot 3");
					break;
					
				case 5:
					players[4] = new Player("Bot 4");
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
		newDeck = CardOps.makeNewDeck();
		
		mainDeck = CardOps.shuffle(newDeck, newDeck.length, playerCount);
		System.out.println("[INFO] Main card stack has been initialized and shuffled. Stack size = " + mainDeck.size());
		
		if (Main.debug == true)
		{
			System.out.println("*** Post-Shuffle ***");
			
			for (int i = 0; i < 108; i++)
				System.out.println("Card color: " + newDeck[i].color + "  Card value: " + newDeck[i].value);
		}
		
		System.out.println("[NOTE] Since game states are being worked on a new game is automatically started.");
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
	public static void attemptMove(Card cardPlayed, Stack<Card> discardDeck)
	{	
		char colorCheck = cardPlayed.color, discardCheck = discardDeck.peek().color;
		int valCheck = cardPlayed.value, discardValCheck = discardDeck.peek().value;
		
		if (colorCheck != discardCheck || valCheck != discardValCheck || colorCheck != 'x')
			System.out.println("Not a valid move.");
		
		else
		{
			discardDeck.push(cardPlayed);
			System.out.println("Valid move. Pushed card to discard stack.");
		}
	}
}