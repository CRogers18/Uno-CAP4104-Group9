package game.uno.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private static boolean colorBlindMode = false;	// color blind mode flag
	private static String assetFolder = "/textures/";	// asset folder: default is textures
	
	private static JFrame frame = new JFrame();
	private static JPanel mainPanel = new JPanel();
	private static JPanel newGamePanel = new JPanel();
	private static JPanel gameUI = new JPanel();
	public static JPanel botPanel, centerPanel;
	public static JLabel discardPile;
	private static JLabel mainLabel, optionsLabel1, deckPile;
	private static JButton newGame, options, quit, start, back, apply;
	public static JCheckBox disableMusic, enableCBM;
	public static JSlider botCount;
	public static JTextField playerName;	
		
	public static ArrayList <JLabel> playerCards = new ArrayList<JLabel>();
	private static JLabel[] cardsInHand = new JLabel[5];
	private static JLabel cardsRemain;

	private static float r = 0.3f, g = 0f, b = 0f;
	private Color quitRed = new Color(125, 0, 0);
	
	public static void createMainMenu()
	{	
		BoxLayout mainMenu = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(mainMenu);
		
		frame.setResizable(false);
		frame.setSize(1408, 792);
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
				addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						colorBlindMode = !colorBlindMode;	
						if(colorBlindMode)	
							assetFolder = "/textures_blind/";
						else
							assetFolder = "/textures/";
					}
				});				
			}};
		
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
	
	public void addActionListener(ActionListener l) {
		apply.addActionListener(l);
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
	
	public static void newGameOptions()
	{
		inSubMenu = true;
		
		frame.getContentPane().remove(mainPanel);
		
		BoxLayout newGameMenu = new BoxLayout(newGamePanel, BoxLayout.Y_AXIS);
		newGamePanel.setLayout(newGameMenu);
		
		playerName = new JTextField("Enter Player Name", 12) {
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
							setText("Enter Player Name");
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
	
	public static void setBotPosition(JPanel p, JLabel playerName, JLabel botCard, JLabel cardsInHand)
	{
		p.add(playerName);
		p.add(botCard);				
		p.add(cardsInHand);
	}
	
	// create singleBotPanel panel contains: 
	// Bot name label, back card image label and Remaining cards label
	public static JPanel createSingleBotpanel(JLabel playerName, JLabel botCard, JLabel cardsInHand)
	{		
		JPanel singleBotPanel = new JPanel();
		singleBotPanel.setLayout(new BoxLayout(singleBotPanel, BoxLayout.Y_AXIS));
		singleBotPanel.setBackground(new Color(0.35f, 0f, 0f));
		
		singleBotPanel.add(playerName);
		singleBotPanel.add(botCard);				
		singleBotPanel.add(cardsInHand);
		
		return singleBotPanel;
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
		
		// create eastPanel
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setBackground(new Color(0.35f, 0f, 0f));
		
		//create westPanel
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		westPanel.setBackground(new Color(0.35f, 0f, 0f));
		
		// create drawPilePanel panel contains: Draw label and Remaining cards label
		JPanel drawPilePanel = new JPanel();
		drawPilePanel.setLayout(new BoxLayout(drawPilePanel, BoxLayout.Y_AXIS));
		drawPilePanel.setBackground(new Color(0.35f, 0f, 0f));
		
		centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)) {
			{
				setBackground(new Color(0.35f, 0f, 0f));
			}
		};
						
		JLabel[] botCards = new JLabel[5];
		
		for (int i = 0; i < Main.playerCount; i++)
		{
			botCards[i] = new JLabel();
			botCards[i].setIcon(new ImageIcon(GameGraphics.class.getResource(assetFolder + "card_back.png")));
			botCards[i].setAlignmentY(CENTER_ALIGNMENT);
		}
		
		for (int i = 0; i < Main.playerCount - 1; i++)	// player 0 is human, so the first bot is player 1
		{
			cardsInHand[i] = new JLabel();
			cardsInHand[i].setFont(new Font("Serif", Font.BOLD, 20));
			cardsInHand[i].setForeground(new Color(1f, 1f, 1f));
			cardsInHand[i].setText("Cards in hand: " + GameManager.players[i+1].hand.size());
			cardsInHand[i].setAlignmentY(CENTER_ALIGNMENT);
		}
		
		JLabel[] playerNames = new JLabel[5];
		for (int i = 0; i < Main.playerCount; i++)
		{
			playerNames[i] = new JLabel();
			playerNames[i].setFont(new Font("Serif", Font.BOLD, 20));
			playerNames[i].setForeground(new Color(1f, 1f, 1f));
			playerNames[i].setText("Name: " + GameManager.players[i].name);
			//playerNames[i].setAlignmentX(Component.CENTER_ALIGNMENT);
		}
		
		switch (Main.playerCount)
		{
			
			case 2:				
				setBotPosition(westPanel, playerNames[1], botCards[0], cardsInHand[0]);
				break;
				
			case 3:
				setBotPosition(westPanel, playerNames[1], botCards[0], cardsInHand[0]);
				JPanel singleBotPanel_31 = createSingleBotpanel(playerNames[2], botCards[1], cardsInHand[1]);
				topPanel.add(singleBotPanel_31);
				break;
				
			case 4:
				setBotPosition(westPanel, playerNames[1], botCards[0], cardsInHand[0]);
				JPanel singleBotPanel_41 = createSingleBotpanel(playerNames[2], botCards[1], cardsInHand[1]);
				topPanel.add(singleBotPanel_41);
				topPanel.add(Box.createRigidArea(new Dimension(200, 0)));	
				JPanel singleBotPanel_42 = createSingleBotpanel(playerNames[3], botCards[2], cardsInHand[2]);
				topPanel.add(singleBotPanel_42);
				break;
				
			case 5:
				setBotPosition(westPanel, playerNames[1], botCards[0], cardsInHand[0]);
				JPanel singleBotPanel_51 = createSingleBotpanel(playerNames[2], botCards[1], cardsInHand[1]);
				topPanel.add(singleBotPanel_51);
				topPanel.add(Box.createRigidArea(new Dimension(200, 0)));	
				JPanel singleBotPanel_52 = createSingleBotpanel(playerNames[3], botCards[2], cardsInHand[2]);
				topPanel.add(singleBotPanel_52);	
				setBotPosition(eastPanel, playerNames[4], botCards[3], cardsInHand[3]);
				break;		
		}
		
		// create center components (skip, direction, uno, deckPile, and discardPile)							
		JLabel skip = new JLabel("", JLabel.CENTER);
		skip.setIcon(new ImageIcon(GameGraphics.class.getResource(assetFolder + "skip.png")));
		JLabel direction = new JLabel("", JLabel.CENTER);
		direction.setIcon(new ImageIcon(GameGraphics.class.getResource(assetFolder + "cw_direction.png")));		
		JLabel uno = new JLabel("", JLabel.CENTER);
		uno.setIcon(new ImageIcon(GameGraphics.class.getResource(assetFolder + "uno.png")));
		deckPile = new JLabel("", JLabel.CENTER) {
			{
				addMouseListener(new MouseHandler());
			}
		};
		deckPile.setIcon(new ImageIcon(GameGraphics.class.getResource(assetFolder + "draw.png")));	
		cardsRemain = new JLabel("", JLabel.CENTER);
		//cardsRemain.setAlignmentX(Component.CENTER_ALIGNMENT);
		cardsRemain.setFont(new Font("Serif", Font.BOLD, 20));
		cardsRemain.setForeground(new Color(1f, 1f, 1f));		
		drawPilePanel.add(deckPile);
		drawPilePanel.add(cardsRemain);
		
		discardPile = new JLabel("", JLabel.CENTER);
		
		centerPanel.add(skip);
		centerPanel.add(direction);
		centerPanel.add(uno);
		centerPanel.add(Box.createRigidArea(new Dimension(50, 0)));
		centerPanel.add(drawPilePanel);
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
				String cardName = "" + i;
				card.setIcon(new ImageIcon(GameGraphics.class.getResource(assetFolder + "" + cardColor + "_" + cardValue + ".png")));
				MouseListener mouseListener = new MouseHandler();
				card.addMouseListener(mouseListener);
				card.setName(cardName);
				playerCards.add(card);
			}
			
			if (special)
			{
				card.setIcon(new ImageIcon(GameGraphics.class.getResource(assetFolder + "" + cardColor + "_1_" + specialValue + ".png")));
				MouseListener mouseListener = new MouseHandler();
				card.addMouseListener(mouseListener);
				playerCards.add(card);
			}
		}
		
		for (int j = 0; j < playerCardCount; j++)
		{
			botPanel.add(playerCards.get(j));
		}
		
		// update Cards In Hand for bots	
		for (int i = 0; i < Main.playerCount - 1; i++)	// player 0 is human, so the first bot is player 1
		{
			cardsInHand[i].setText("Cards in hand: " + GameManager.players[i+1].hand.size());
		}
		
		// refresh remaining cards display
		cardsRemain.setText("Remaining: " + GameManager.mainDeck.size());
		
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
			discardPile.setIcon(new ImageIcon(GameGraphics.class.getResource(assetFolder + "" + cardColor + "_" + cardValue + ".png")));
		
		if (special)
			discardPile.setIcon(new ImageIcon(GameGraphics.class.getResource(assetFolder + "" + cardColor + "_1_" + specialValue + ".png")));
	}
	
	protected void paintComponent(Graphics g)
	{
	//	super.paintComponents(g);
	//	g.drawImage(mainImage, 100, 100, this);
	}	
}