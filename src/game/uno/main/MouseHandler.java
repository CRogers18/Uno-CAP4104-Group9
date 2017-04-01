package game.uno.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import game.uno.main.Main.state;

public class MouseHandler implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		GameGraphics.playerCards.remove(e.getComponent());
		GameGraphics.playerCards.trimToSize();
		GameGraphics.botPanel.remove(e.getComponent());
		GameGraphics.updateCardCount();
		Main.gameState= state.BOT_TURN;
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