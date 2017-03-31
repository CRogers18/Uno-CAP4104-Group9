package game.uno.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class GameGraphics extends JFrame {

	private static boolean isFade = false;
	public static boolean inSubMenu = false;
	
	private static JFrame frame = new JFrame();
	private static JPanel mainPanel = new JPanel();
	private static JPanel newGamePanel = new JPanel();
	private static JPanel gameUI = new JPanel();
	public static JPanel botPanel;
	public static JLabel discardPile;
	private static JLabel mainLabel, optionsLabel1;
	private static JButton newGame, options, quit, start, back, apply;
	public static JCheckBox disableMusic, enableCBM;
	public static JSlider botCount;
	public static JTextField playerName;
		
	public static ArrayList <JLabel> playerCards = new ArrayList<JLabel>();
	private static JLabel[] cardsInHand = new JLabel[5];

	private static float r = 0.3f, g = 0f, b = 0f;
	private Color quitRed = new Color(125, 0, 0);
	
	public static void createMainMenu()
	{	
		BoxLayout mainMenu = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(mainMenu);
		
		frame.setResizable(false);
		frame.setSize(1280, 720);
		frame.setTitle("Uno: CAP 4104 Edition");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		
		mainLabel = new JLabel("Uno: CAP 4104 Edition") {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setFont(new Font("Serif", Font.BOLD, 64));
				setForeground(Color.WHITE);
			}
		};
		
		newGame = new JButton("New Game") {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setSize(200, 50);
				setMaximumSize(getSize());
				setFont(new Font("Serif", Font.BOLD, 24));
				setFocusPainted(false);
				addActionListener(new Listener());
			}
		};
		
		options = new JButton("Options") {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setSize(200, 50);
				setMaximumSize(getSize());
				setFont(new Font("Serif", Font.BOLD, 24));
				setFocusPainted(false);
				addActionListener(new Listener());
			}
		};
		
		quit = new JButton("Quit") {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setSize(200, 50);
				setMaximumSize(getSize());
				setFont(new Font("Serif", Font.BOLD, 24));
			//	setForeground(Color.WHITE);
			//	setBackground(quitRed);
				setFocusPainted(false);
				addActionListener(new Listener());
			}
		};
		
		disableMusic = new JCheckBox("Disable Background Music") {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setSize(310, 50);
				setMaximumSize(getSize());
				setFont(new Font("Serif", Font.BOLD, 24));
				setFocusPainted(false);
				addActionListener(new Listener());
			}
		};
		
		enableCBM = new JCheckBox("Enable Color-Blind Mode") {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setSize(295, 50);
				setMaximumSize(getSize());
				setFont(new Font("Serif", Font.BOLD, 24));
				setFocusPainted(false);
				addActionListener(new Listener());
			}
		};
		
		apply = new JButton("Apply Settings") {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setSize(200, 50);
				setMaximumSize(getSize());
				setFont(new Font("Serif", Font.BOLD, 24));
				setFocusPainted(false);
				addActionListener(new Listener());
			}
		};
		
		mainPanel.setBackground(new Color(0.3f, 0f, 0f));
		mainPanel.add(Box.createRigidArea(new Dimension(0, 100)));
		mainPanel.add(mainLabel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 85)));
		mainPanel.add(newGame);
		mainPanel.add(disableMusic);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 85)));
		mainPanel.add(options);
		mainPanel.add(enableCBM);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 85)));
		mainPanel.add(quit);
		mainPanel.add(apply);
		frame.getContentPane().add(mainPanel);
		
		disableMusic.setVisible(false);
		enableCBM.setVisible(false);
		apply.setVisible(false);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void menuPulse()
	{
		if (r < 0.75f && !isFade)
			r+=0.002f;
		
		if (r >= 0.75f)
			isFade = true;
		
		if (isFade)
			r-=0.002f;
		
		if (r <= 0.3f && isFade)
			isFade = false;
		
		Color mainMenuRed = new Color(r, g, b);
		
		if (!inSubMenu)
			mainPanel.setBackground(mainMenuRed);
		else
			newGamePanel.setBackground(mainMenuRed);
	}
	
	// Method for trying to handle back button functionality on sub-menu for making new game. Bugged.
	public static void loadMainMenu()
	{
		inSubMenu = false;
		
		frame.getContentPane().remove(newGamePanel);
		
		mainLabel.setVisible(true);
		
		frame.getContentPane().add(mainPanel);
		frame.getContentPane().repaint();
		frame.setVisible(true);
	}
	
	public static void loadOptionsMenu()
	{
		newGame.setVisible(false);
		options.setVisible(false);
		quit.setVisible(false);
		
		disableMusic.setVisible(true);
		enableCBM.setVisible(true);
		apply.setVisible(true);
		
		Main.loadOptions = false;
		
		frame.getContentPane().repaint();
		frame.setVisible(true);
	}
	
	public static void unLoadOptions()
	{
		disableMusic.setVisible(false);
		enableCBM.setVisible(false);
		apply.setVisible(false);
		
		newGame.setVisible(true);
		options.setVisible(true);
		quit.setVisible(true);
		
		frame.getContentPane().repaint();
		frame.setVisible(true);
	}
	
	public static void missingGraphics()
	{
		frame.getContentPane().remove(newGamePanel);
		frame.getContentPane().repaint();
		
		JLabel whoops = new JLabel("missingGraphics.png ¯\\_(ツ)_/¯ ") {
			{	
			//	setAlignmentX(Component.CENTER_ALIGNMENT);
				setFont(new Font("Serif", Font.BOLD, 64));
			}
		};
		
		frame.add(whoops);
		frame.setVisible(true);		
	}
	
	public static void newGameOptions()
	{
		inSubMenu = true;
		
		frame.getContentPane().remove(mainPanel);
		
		BoxLayout newGameMenu = new BoxLayout(newGamePanel, BoxLayout.Y_AXIS);
		newGamePanel.setLayout(newGameMenu);
		
		playerName = new JTextField("Player name", 12) {
			{
				setToolTipText("Enter player name here, max of 12 characters");
				setHorizontalAlignment(JTextField.CENTER);
				setSize(300, 50);
				setMaximumSize(getSize());
				setFont(new Font("Serif", Font.BOLD, 24));
				addFocusListener(new FocusListener()
				{
					@Override
					public void focusGained(FocusEvent event) {
							setText("");
					}

					@Override
					public void focusLost(FocusEvent event) {
						if (getText() == "")
							setText("Player name");
					}
				});
			}
		};
		
		optionsLabel1 = new JLabel("Number of Bots") {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setFont(new Font("Serif", Font.BOLD, 32));
				setForeground(Color.WHITE);
			}
		};
		
		botCount = new JSlider(JSlider.HORIZONTAL, 1, 4, 2) {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setSize(500, 65);
				setMaximumSize(getSize());
				setFont(new Font("Serif", Font.BOLD, 24));
				setMajorTickSpacing(1);
				setPaintLabels(true);
			}
		};
		
		start = new JButton("Start") {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setSize(200, 50);
				setMaximumSize(getSize());
				setFont(new Font("Serif", Font.BOLD, 24));
				setFocusPainted(false);
				addActionListener(new Listener());
			}
		};
		
		back = new JButton("Back to Main Menu") {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setSize(240, 50);
				setMaximumSize(getSize());
				setFont(new Font("Serif", Font.BOLD, 24));
				setFocusPainted(false);
				addActionListener(new Listener());
			}
		};
		
		newGamePanel.setBackground(new Color(0.3f, 0f, 0f));
		newGamePanel.add(Box.createRigidArea(new Dimension(0, 100)));
		newGamePanel.add(mainLabel);
		newGamePanel.add(Box.createRigidArea(new Dimension(0, 50)));
		newGamePanel.add(playerName);
		newGamePanel.add(Box.createRigidArea(new Dimension(0, 30)));
		newGamePanel.add(optionsLabel1);
		newGamePanel.add(Box.createRigidArea(new Dimension(0, 20)));
		newGamePanel.add(botCount);
		newGamePanel.add(Box.createRigidArea(new Dimension(0, 65)));
		newGamePanel.add(start);
		newGamePanel.add(Box.createRigidArea(new Dimension(0, 40)));
		newGamePanel.add(back);
		
		frame.getContentPane().add(newGamePanel);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		Main.loadNewGameOptions = false;
	}
	
	public static void makeGameUI()
	{
		frame.getContentPane().remove(newGamePanel);
		
		JPanel topPanel = new JPanel(new FlowLayout()) {
			{
				setPreferredSize(new Dimension(1280, 240));
				setBackground(new Color(0.35f, 0f, 0f));
			}
		};
		
		botPanel = new JPanel(new FlowLayout()) {
			{
				setPreferredSize(new Dimension(1280, 200));
				setBackground(new Color(0.35f, 0f, 0f));
			}
		};
		
		JPanel eastPanel = new JPanel(new BorderLayout()) {
			{
				setPreferredSize(new Dimension(200, 290));
				setBackground(new Color(0.35f, 0f, 0f));
			}
		};
		
		JPanel westPanel = new JPanel(new BorderLayout()) {
			{
				setPreferredSize(new Dimension(200, 290));
				setBackground(new Color(0.35f, 0f, 0f));
			}
		};
		
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)) {
			{
				setBackground(new Color(0.35f, 0f, 0f));
			}
		};
		
		JLabel deckPile = new JLabel("", JLabel.CENTER) {
			{
				setIcon(new ImageIcon(GameGraphics.class.getResource("/textures/card_back.png")));
			//	setHorizontalAlignment(SwingConstants.CENTER);
			}
		};
		
		discardPile = new JLabel("", JLabel.CENTER);
		
		JLabel[] botCards = new JLabel[5];
		
		for (int i = 0; i < Main.playerCount; i++)
		{
			botCards[i] = new JLabel();
			botCards[i].setIcon(new ImageIcon(GameGraphics.class.getResource("/textures/card_back.png")));
			botCards[i].setAlignmentY(CENTER_ALIGNMENT);
		}
		
		for (int i = 0; i < Main.playerCount; i++)
		{
			cardsInHand[i] = new JLabel();
			cardsInHand[i].setFont(new Font("Serif", Font.BOLD, 20));
			cardsInHand[i].setForeground(new Color(1f, 1f, 1f));
			cardsInHand[i].setText("Cards in Hand: " + GameManager.players[i].hand.size());
			cardsInHand[i].setAlignmentY(CENTER_ALIGNMENT);
		}
		
		switch (Main.playerCount)
		{
		
			case 2:
				topPanel.add(botCards[0]);
				topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
				topPanel.add(cardsInHand[0]);
				
		//		botPanel.add(botCards[1]);
		//		botPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		//		botPanel.add(cardsInHand[1]);
				break;
				
			case 3:
				topPanel.add(botCards[0]);
				eastPanel.add(botCards[1], BorderLayout.EAST);
				botPanel.add(botCards[2]);
				break;
				
			case 4:
				topPanel.add(botCards[0]);
				eastPanel.add(botCards[1], BorderLayout.EAST);
				westPanel.add(botCards[2], BorderLayout.CENTER);
		//		botPanel.add(botCards[3]);
				break;
				
			case 5:
				topPanel.add(botCards[0]);
				topPanel.add(Box.createRigidArea(new Dimension(200, 0)));
				topPanel.add(botCards[1]);
				eastPanel.add(botCards[2], BorderLayout.EAST);
				westPanel.add(botCards[3], BorderLayout.CENTER);
		//		botPanel.add(botCards[4]);
				break;
		
		}
		
		centerPanel.add(deckPile);
		centerPanel.add(Box.createRigidArea(new Dimension(50, 0)));
		centerPanel.add(discardPile);
		
		frame.getContentPane().add(centerPanel, "Center");
		frame.getContentPane().add(topPanel, "North");
		frame.getContentPane().add(eastPanel, "East");
		frame.getContentPane().add(botPanel, "South");
		frame.getContentPane().add(westPanel, "West");

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void updateCardCount()
	{
	//  Code for drawing the number of cards in hand, looking for a better place to put it
	//	int cardCount = GameManager.players[GameManager.currentPlayer].hand.size();
	//	cardsInHand[GameManager.currentPlayer].setText("Cards in Hand: " + GameManager.players[GameManager.currentPlayer].hand.size());
		
		int playerCardCount = GameManager.players[0].hand.size();
		char cardColor;
		int cardValue, specialValue;
		boolean special;
		
		// Preliminary code for handling players clicking on cards
		for (int i = 0; i < playerCardCount; i++)
		{
			cardColor = GameManager.players[0].hand.get(i).color;
			cardValue = GameManager.players[0].hand.get(i).value;
			special = GameManager.players[0].hand.get(i).special;
			specialValue = GameManager.players[0].hand.get(i).specialValue;
			
			JLabel card = new JLabel();
			
			if (!special)
			{
				card.setIcon(new ImageIcon(GameGraphics.class.getResource("/textures/" + cardColor + "_" + cardValue + ".png")));
				MouseListener mouseListener = new MouseHandler();
				card.addMouseListener(mouseListener);
				playerCards.add(card);
			}
			
			if (special)
			{
				switch(specialValue)
				{
					// Skip
					case 0:
						card.setIcon(new ImageIcon(GameGraphics.class.getResource("/textures/" + cardColor + "_1_" + specialValue + ".png")));
						playerCards.add(card);
						break;
					
					// Reverse
					case 1:
						card.setIcon(new ImageIcon(GameGraphics.class.getResource("/textures/" + cardColor + "_1_" + specialValue + ".png")));
						playerCards.add(card);
						break;
					
					// Draw-two
					case 2:
						card.setIcon(new ImageIcon(GameGraphics.class.getResource("/textures/" + cardColor + "_1_" + specialValue + ".png")));
						playerCards.add(card);
						break;
					
					// Wild-card
					case 3:
						card.setIcon(new ImageIcon(GameGraphics.class.getResource("/textures/" + cardColor + "_1_" + specialValue + ".png")));
						playerCards.add(card);
						break;
					
					// Wild-card +4
					case 4:
						card.setIcon(new ImageIcon(GameGraphics.class.getResource("/textures/" + cardColor + "_1_" + specialValue + ".png")));
						playerCards.add(card);
						break;
				}
			}
		}
		
		for (int j = 0; j < playerCardCount; j++)
		{
			botPanel.add(playerCards.get(j));
		}
		
		frame.getContentPane().add(botPanel, "South");
		frame.setVisible(true);
	}
	
	public static void updateDiscard()
	{
		char cardColor = GameManager.discardDeck.peek().color;
		int cardValue = GameManager.discardDeck.peek().value;
		boolean special = GameManager.discardDeck.peek().special;
		int specialValue = GameManager.discardDeck.peek().specialValue;
		
		if (!special)
		{
		//	System.out.println("Attempting to load " + cardColor + "_" + cardValue + ".png");
			discardPile.setIcon(new ImageIcon(GameGraphics.class.getResource("/textures/" + cardColor + "_" + cardValue + ".png")));
		}
		
		if (special)
		{
			switch(specialValue)
			{
				// Skip
				case 0:
					discardPile.setIcon(new ImageIcon(GameGraphics.class.getResource("/textures/" + cardColor + "_1_" + specialValue + ".png")));
					break;
				
				// Reverse
				case 1:
					discardPile.setIcon(new ImageIcon(GameGraphics.class.getResource("/textures/" + cardColor + "_1_" + specialValue + ".png")));
					break;
				
				// Draw-two
				case 2:
					discardPile.setIcon(new ImageIcon(GameGraphics.class.getResource("/textures/" + cardColor + "_1_" + specialValue + ".png")));
					break;
				
				// Wild-card
				case 3:
					discardPile.setIcon(new ImageIcon(GameGraphics.class.getResource("/textures/" + cardColor + "_1_" + specialValue + ".png")));
					break;
				
				// Wild-card +4
				case 4:
					discardPile.setIcon(new ImageIcon(GameGraphics.class.getResource("/textures/" + cardColor + "_1_" + specialValue + ".png")));
					break;
			}
		}
		
		
	}
	
	protected void paintComponent(Graphics g)
	{
	//	super.paintComponents(g);
	//	g.drawImage(mainImage, 100, 100, this);
	}	
}