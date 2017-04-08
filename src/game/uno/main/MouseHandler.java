package game.uno.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import game.uno.main.Main.state;

public class MouseHandler implements MouseListener {
	
	public static int choice = -1;
	
	@Override
	public void mouseClicked(MouseEvent e) {
						
		if (e.getComponent() == GameGraphics.centerPanel && Main.gameState == state.PLAYER_TURN)
		{
			System.out.println("Draw a new card for the player...");
			GameManager.players[GameManager.activePlayer].hand.add(GameManager.mainDeck.pop());
		}
		
		JLabel test = (JLabel) e.getSource();
		int location = (int) test.getClientProperty("index");
		
		
		if (e.getComponent() == GameGraphics.botPanel.getComponent(location) && Main.gameState == state.PLAYER_TURN)
		{
			System.out.println("Try to play card selected at index " + location);
			
			// Remove the card from the player card array
			Card playCard = GameManager.players[0].hand.get(location);
			GameManager.players[0].hand.remove(location);
			GameManager.players[0].hand.trimToSize();
			GameManager.discardDeck.push(playCard);
			
			// Update the discard deck with the newly played card and repaint the bottom panel with the updated cards
			GameGraphics.updateDiscard();
			GameGraphics.botPanel.repaint();
			
			// Remove from the JLabel array and the JPanel on the bottom
			GameGraphics.playerCards.remove(location);
			GameGraphics.playerCards.trimToSize();
			GameGraphics.botPanel.remove(GameGraphics.botPanel.getComponent(location));
			GameGraphics.updatePlayerHandLabels();
			GameGraphics.updateCardCount();
			
			if (GameManager.players[0].hand.size() == 0)
			{
				System.out.println(GameManager.players[0].name + " has won!");
				JOptionPane.showConfirmDialog(null, GameManager.players[0].name + " has won the round!");
				
				GameManager.calculateScore(GameManager.players[0]);
				JOptionPane.showConfirmDialog(null, GameManager.players[0].name + " score is now " + GameManager.players[0].score);
				
				if (GameManager.players[0].score >= 500)
					JOptionPane.showConfirmDialog(null, GameManager.players[0].name + " has won the game!");
				
				GameManager.nextRound();
				GameGraphics.generateNewGameHand();
				GameGraphics.updateCardCount();
				GameGraphics.updateDiscard();
				Main.gameState = state.PLAYER_TURN;
			}
			
			else 
			{
			// Assuming no reverse-card is in play
			Main.nextPlayer = 1;
			
			if (GameManager.discardDeck.peek().special)
				GameManager.checkEffects();
			
			if (GameManager.discardDeck.peek().specialValue == 3 || GameManager.discardDeck.peek().specialValue == 4)
			{
				Object[] colorOptions = {"Red", "Yellow", "Green", "Blue"};
				choice = JOptionPane.showOptionDialog(GameGraphics.frame, "Please select a new color to match.", "Wild-Card Selection", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, colorOptions, null);
				System.out.println("Player Selected" + choice);
			}
			
			if (GameManager.discardDeck.peek().specialValue != 0)
				GameManager.activePlayer = 1;
			
			Main.gameState= state.BOT_TURN;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}