package game.uno.main;

import java.util.Stack;

public class BotOps {
	
	public static boolean override = false;
	public static char overrideColor;
	
	private static char currentCardColor;
	private static int currentCardValue, currentSpecialValue;
	private static Card currentCard;
	
	public static void evilPlot(Player[] bots, int currentBot, Stack <Card> discardDeck, Stack <Card> mainDeck)
	{
		// To-do: Make the bot's not stupid
	}
	
	public static int playMove(Player[] bots, int currentBot, Stack <Card> discardDeck, Stack <Card> mainDeck)
	{
		// Need to not get into the habit of putting the thread to sleep to make a delay...
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Bot logic is a lot better but still prone to bugs
		
		for (int i = 0; i < bots[currentBot].hand.size(); i++)
		{
			currentCardColor = bots[currentBot].hand.get(i).color;
			currentCardValue = bots[currentBot].hand.get(i).value;
			currentSpecialValue = bots[currentBot].hand.get(i).specialValue;
			currentCard = bots[currentBot].hand.get(i);
			
			// If current card is a wild-card, play it, as a wild-card is a valid move at any time
			if (currentCardColor == 'x')
			{
				if (Main.debug)
					System.out.println("Current color to match is " + discardDeck.peek().color);
				
				Card tempCard = bots[currentBot].hand.get(i);
				bots[currentBot].hand.remove(i);
				bots[currentBot].hand.trimToSize();
				discardDeck.push(tempCard);
				
				if (bots[currentBot].hand.size() != 0)
				{
					int j = 0;
					
					while (j < bots[currentBot].hand.size())
					{
						if (bots[currentBot].hand.get(j).color != 'x')
						{
							override = true;
							overrideColor = bots[currentBot].hand.get(j).color;
							break;
						}
						
						j++;
					}
					
					// This can be made random at a later time, just defaults to red when the bot has no cards left in hand that aren't wild-cards
					if (!override)
						overrideColor = 'r';
				}
				
				if (Main.debug)
					System.out.println("After wild-card new color to match is " + overrideColor);
				
				return ++currentBot;
			}
			
			// If card color matches card to play color and a wild-card was not just played, play it
			if (currentCardColor == discardDeck.peek().color && override == false)
			{	
				if (Main.debug)
					System.out.println("Current card to match is " + discardDeck.peek().color  + " " + discardDeck.peek().value);
				
				Card tempCard = bots[currentBot].hand.get(i);
				bots[currentBot].hand.remove(i);
				bots[currentBot].hand.trimToSize();
				discardDeck.push(tempCard);

				if (Main.debug)
					System.out.println("Card played is " + tempCard.color + " " + tempCard.value);
				
				return ++currentBot;
			}
			
			// If card values match and a wild-card was not just played and the card isn't a special card, then play it
			if (currentCardValue == discardDeck.peek().value && override == false && !currentCard.special)
			{
				if (Main.debug)
					System.out.println("Current card to match is " + discardDeck.peek().color + " " + discardDeck.peek().value);
				 
				Card tempCard = bots[currentBot].hand.get(i);
				bots[currentBot].hand.remove(i);
				bots[currentBot].hand.trimToSize();
				discardDeck.push(tempCard);

				if (Main.debug)
					System.out.println("Card played is " + tempCard.color + " " + tempCard.value);
				
				return ++currentBot;
			}
			
			// If current card is special and special values match, play it
			if (currentCard.special && currentSpecialValue == discardDeck.peek().specialValue)
			{
				if (Main.debug)
					System.out.println("Current card to match is " + discardDeck.peek().color + " " + discardDeck.peek().specialValue);
				 
				Card tempCard = bots[currentBot].hand.get(i);
				bots[currentBot].hand.remove(i);
				bots[currentBot].hand.trimToSize();
				discardDeck.push(tempCard);
				
				if (Main.debug)
					System.out.println("Card played is " + tempCard.color + " " + tempCard.specialValue);
				
				return ++currentBot;
			}
			
			// If wild-card was just played and the current card color matches the color that was set by the override, play it
			if (override == true && (currentCardColor == overrideColor))
			{
				if (Main.debug)
					System.out.println("Current color to match after wild-card is " + overrideColor);
				
				override = false;
				Card tempCard = bots[currentBot].hand.get(i);
				bots[currentBot].hand.remove(i);
				bots[currentBot].hand.trimToSize();
				discardDeck.push(tempCard);
				
				if (Main.debug)
					System.out.println("Card played is " + tempCard.color + " " + tempCard.value);
				
				return ++currentBot;
			}
		}
		
		/* If we don't get an early return from the for-loop, then this code is 
		   reached which results in a drawn card and end of the bot's turn.   */
		Card tempCard = mainDeck.pop();
		bots[currentBot].hand.add(tempCard);
		
		if (Main.debug)
			System.out.println("No match found, drawing card from main deck");
		
		return ++currentBot;
	}

}