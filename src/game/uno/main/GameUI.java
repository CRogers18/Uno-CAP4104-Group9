package game.uno.main;

import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JPanel;

import game.uno.main.Enums.CardColor;
import game.uno.main.Enums.CardValue;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Stack;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

public class GameUI {

	private JFrame frmAbc;
	
	private static int playerCount = 4;
	private String playerName = "Pipsqueak";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameUI window = new GameUI();					
					window.frmAbc.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAbc = new JFrame();
		frmAbc.setTitle("CAP 4714 UNO Game - Group 9");
		
		// maximize window
		Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
		frmAbc.setMaximumSize(DimMax);
		frmAbc.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		frmAbc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel southPanel = new JPanel();
		frmAbc.getContentPane().add(southPanel, BorderLayout.SOUTH);
		
		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(400, 400));
		frmAbc.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JPanel leftPanel = new JPanel();
		//leftPanel.setPreferredSize(new Dimension(600, 600));
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		frmAbc.getContentPane().add(leftPanel, BorderLayout.WEST);
		
		JPanel rightPanel = new JPanel();
		//rightPanel.setPreferredSize(new Dimension(600, 600));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		frmAbc.getContentPane().add(rightPanel, BorderLayout.EAST);
		
		JPanel centerPanel = new JPanel();
		//centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		centerPanel.setLayout(new FlowLayout());
		frmAbc.getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		
		//////////////////
		
		/*   *** Player Initialization ***   */
		Player[] players = GameManager.initPlayers(playerCount, playerName);
		System.out.println("[INFO] Players initialized successfully. Game has " + playerCount + " players.\n");
		
		/*   *** CARD STACKS INITIALIZATION ***   */
		List<Card> newDeck = CardDeck.makeNewDeck();
		Stack<Card> mainDeck = CardDeck.shuffle(newDeck, newDeck.size(), playerCount);
		System.out.println("[INFO] Main card stack has been initialized and shuffled. Stack size = " + mainDeck.size() + "\n");
		
		
		System.out.println("*** Post-Shuffle ***");
		
		for (int i = 0; i < 108; i++)
			System.out.println("Card color: " + newDeck.get(i).color + " \tCard value: " + newDeck.get(i).value);
		
		
		GameManager.startGame(mainDeck, players, playerCount);
		System.out.println("Main deck size following shuffle and card to match down = " + mainDeck.size());
		
		// ------------- display players' cards on table ----------------------
		// player 1
		JLabel[] player1 = createLabels(players[0]);
		for (int i = 0; i < player1.length; i++){
            southPanel.add(player1[i]);
        }
		
		// player 2
		JLabel[] player2 = createLabels(players[1]);
		for (int i = 0; i < player2.length; i++){
            leftPanel.add(player2[i]);
        }
		
		
		// player 3
		JLabel[] player3 = createLabels(players[2]);
		for (int i = 0; i < player3.length; i++){
            topPanel.add(player3[i]);
        }
		
		// player 4
		JLabel[] player4 = createLabels(players[3]);
		for (int i = 0; i < player4.length; i++){
            rightPanel.add(player4[i]);
        }
		
		// Deck pile and discard pile at center
		JLabel deckPile = new JLabel("");		
		deckPile.setIcon(new ImageIcon(GameUI.class.getResource("/asset/DeckPile.png")));
		deckPile.setHorizontalAlignment(SwingConstants.CENTER);
    	centerPanel.add(deckPile, BorderLayout.CENTER);
    	
    	JLabel blank = new JLabel("");		
    	blank.setIcon(new ImageIcon(GameUI.class.getResource("/asset/Blank.png")));
    	blank.setHorizontalAlignment(SwingConstants.CENTER);
    	centerPanel.add(blank, BorderLayout.CENTER);
    	
    	
    	JLabel discardPile = new JLabel("");
    	discardPile.setIcon(new ImageIcon(GameUI.class.getResource("/asset/DiscardPile.png")));
    	discardPile.setHorizontalAlignment(SwingConstants.CENTER);
    	centerPanel.add(discardPile, BorderLayout.CENTER);				
	}
	
	private JLabel[] createLabels(Player player){
        JLabel[] labels = new JLabel[7];
        for (int i = 0; i < 7; i++)
        {        	
        	CardColor color = player.hand.get(i).color;
        	CardValue value = player.hand.get(i).value;
        	
        	JLabel label = new JLabel("");
        	label.addMouseListener(new MouseAdapter() {
    			@Override
    			public void mouseClicked(MouseEvent arg0) {
    				JOptionPane.showMessageDialog(null, "clicked");
    			}
    		});
        	label.setIcon(new ImageIcon(GameUI.class.getResource("/asset/" + color + "_" + value + ".png")));
        	labels[i] = label;            
        }
        return labels;
    }

}
