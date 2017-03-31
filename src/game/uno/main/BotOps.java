package game.uno.main;

import java.util.Stack;

public class BotOps {
	
	public static boolean override = false;
	public static char overrideColor;
	
	private static char currentCardColor;
	private static int currentCardValue;
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
		
		/* Work on handling special cards still needed, bots will still incorrectly play a 
		   special card when the move should be invalid */
		
		for (int i = 0; i < bots[currentBot].hand.size(); i++)
		{
			currentCardColor = bots[currentBot].hand.get(i).color;
			currentCardValue = bots[currentBot].hand.get(i).value;
			currentCard = bots[currentBot].hand.get(i);
			
			if (currentCardColor == 'x')
			{
				if (Main.debug)
					System.out.println("Current color to match is " + discardDeck.peek().color);
				
				override = true;
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
							overrideColor = bots[currentBot].hand.get(j).color;
							break;
						}
						
						j++;
					}
					
					// This can be made random at a later time
					if (j == bots[currentBot].hand.size())
						overrideColor = 'r';
				}
				
				if (Main.debug)
					System.out.println("After wild-card new color to match is " + overrideColor);
				
				return ++currentBot;
			}
			
			if (currentCardColor == discardDeck.peek().color && override == false)
			{
				if (currentCard.special)
				{
					Card tempCard = bots[currentBot].hand.get(i);
					bots[currentBot].hand.remove(i);
					bots[currentBot].hand.trimToSize();
					discardDeck.push(tempCard);
					
					return ++currentBot;
				}
				
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
			
			if (currentCardValue == discardDeck.peek().value && override == false)
			{
				if (currentCard.specialValue == discardDeck.peek().specialValue)
				{
					Card tempCard = bots[currentBot].hand.get(i);
					bots[currentBot].hand.remove(i);
					bots[currentBot].hand.trimToSize();
					discardDeck.push(tempCard);
					
					return ++currentBot;
				}
				
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