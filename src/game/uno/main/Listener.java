package game.uno.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.text.BadLocationException;

import game.uno.main.Main.state;

public class Listener implements ActionListener {

	GameGraphics gg;
	
	public void actionPerformed(ActionEvent event) {
		
		if (event.getActionCommand().equals("New Game"))
		{
			Main.startNewGame = true;
			Main.loadNewGameOptions = true;
		}
		if (event.getActionCommand().equals("Options"))
			Main.loadOptions = true;
		
		if (event.getActionCommand().equals("Quit"))
			System.exit(0);
		
		if (event.getActionCommand().equals("Start"))
		{	
			// Time permitting, limit player names
			GameManager.playerName = GameGraphics.playerName.getText();
			Main.validNewGame = true;
		}
		
		if (event.getActionCommand().equals("Apply Settings"))
			GameGraphics.unLoadOptions();
		
		if (event.getActionCommand().equals("Back to Main Menu"))
			System.out.println("Need to add functionality");
	}
	
	
}