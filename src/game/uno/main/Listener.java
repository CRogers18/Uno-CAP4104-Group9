package game.uno.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.uno.main.Main.state;

public class Listener implements ActionListener {

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
			Main.validNewGame = true;
		
		if (event.getActionCommand().equals("Back"))
			GameGraphics.unLoadOptions();
	}
}