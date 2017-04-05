package game.uno.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import game.uno.main.Main.state;

public class MouseHandler implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
				
		if (e.getComponent() == GameGraphics.centerPanel && Main.gameState == state.PLAYER_TURN)
		{
			System.out.println("Draw a new card for the player...");
			GameManager.players[GameManager.currentPlayer].hand.add(GameManager.mainDeck.pop());
		}
		
		// Throws NULL Pointer after first card is "removed"
		String index = e.getComponent().getName();
		Integer cardLocationIndex = Integer.valueOf(index);
		
		if (e.getComponent() == GameGraphics.botPanel.getComponent(cardLocationIndex) && Main.gameState == state.PLAYER_TURN)
		{
			System.out.println("Try to play card selected at index " + cardLocationIndex);
			GameGraphics.playerCards.remove(e.getComponent());
			GameGraphics.playerCards.trimToSize();
			GameGraphics.botPanel.remove(e.getComponent());
			GameGraphics.updateCardCount();
			Main.gameState= state.BOT_TURN;
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