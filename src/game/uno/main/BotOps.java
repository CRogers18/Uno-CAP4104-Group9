package game.uno.main;

import java.util.Stack;

public class BotOps {
	
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
		
		for (int i = 0; i < bots[currentBot].hand.size(); i++)
		{
			
			if (bots[currentBot].hand.get(i).color == 'x')
			{
				Card tempCard = bots[currentBot].hand.remove(i);
				bots[currentBot].hand.trimToSize();
				discardDeck.push(tempCard);
				
				return ++currentBot;
			}
			
			if (bots[currentBot].hand.get(i).color == discardDeck.peek().color)
			{
				Card tempCard = bots[currentBot].hand.remove(i);
				bots[currentBot].hand.trimToSize();
				discardDeck.push(tempCard);
				
				return ++currentBot;
			}
			
			if (bots[currentBot].hand.get(i).value == discardDeck.peek().value)
			{
				Card tempCard = bots[currentBot].hand.remove(i);
				bots[currentBot].hand.trimToSize();
				discardDeck.push(tempCard);
				
				return ++currentBot;
			}
		}
		
		/* If we don't get an early return from the for-loop, then this code is 
		   reached which results in a drawn card and end of the bot's turn.   */
		Card tempCard = mainDeck.pop();
		bots[currentBot].hand.add(tempCard);
		
		return ++currentBot;
	}

}