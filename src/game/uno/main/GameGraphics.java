package game.uno.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class GameGraphics extends JFrame {

	private static boolean isFade = false;
	
	private static JFrame frame = new JFrame();
	private static JPanel panel = new JPanel();
	private static JLabel mainLabel, optionsLabel1;
	private static JButton newGame, options, quit, start, back;
	public static JCheckBox disableMusic, enableCBM;
	public static JSlider botCount;
	
//	private Clip clip;
//	private URL url = this.getClass().getResource("nggyu.wav");

	private static float r = 0.3f, g = 0f, b = 0f;
	private Color quitRed = new Color(125, 0, 0);
//	private static BufferedImage mainImage;
	
	public static void createMainMenu()
	{	
		
		BoxLayout mainMenu = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(mainMenu);
		
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
		
		optionsLabel1 = new JLabel("Number of Bots") {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setFont(new Font("Serif", Font.BOLD, 36));
				setForeground(Color.WHITE);
			}
		};
		
		botCount = new JSlider(JSlider.HORIZONTAL, 1, 4, 2) {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setSize(500, 75);
				setMaximumSize(getSize());
				setFont(new Font("Serif", Font.BOLD, 24));
				setMajorTickSpacing(1);
				setPaintLabels(true);
			}
		};
		
		disableMusic = new JCheckBox("Disable Rick") {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setSize(160, 50);
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
		
		back = new JButton("Back") {
			{
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setSize(200, 50);
				setMaximumSize(getSize());
				setFont(new Font("Serif", Font.BOLD, 24));
				setFocusPainted(false);
				addActionListener(new Listener());
			}
		};
		
		panel.add(Box.createRigidArea(new Dimension(0, 100)));
		panel.add(mainLabel);
		panel.add(Box.createRigidArea(new Dimension(0, 85)));
		panel.add(newGame);
		panel.add(optionsLabel1);
		panel.add(disableMusic);
		panel.add(Box.createRigidArea(new Dimension(0, 85)));
		panel.add(options);
		panel.add(botCount);
		panel.add(enableCBM);
		panel.add(Box.createRigidArea(new Dimension(0, 85)));
		panel.add(start);
		panel.add(quit);
		panel.add(back);
		frame.getContentPane().add(panel);
		
		optionsLabel1.setVisible(false);
		disableMusic.setVisible(false);
		enableCBM.setVisible(false);
		botCount.setVisible(false);
		start.setVisible(false);
		back.setVisible(false);
		
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
		panel.setBackground(mainMenuRed);
	}
	
	public static void restore()
	{
		optionsLabel1.setVisible(false);
		disableMusic.setVisible(false);
		enableCBM.setVisible(false);
		botCount.setVisible(false);
		start.setVisible(false);
		back.setVisible(false);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void loadOptionsMenu()
	{
		newGame.setVisible(false);
		options.setVisible(false);
		quit.setVisible(false);
		
		disableMusic.setVisible(true);
		enableCBM.setVisible(true);
		back.setVisible(true);
		
		Main.loadOptions = false;
		
		frame.getContentPane().repaint();
		frame.setVisible(true);
	}
	
	public static void unLoadOptions()
	{
		optionsLabel1.setVisible(false);
		botCount.setVisible(false);
		start.setVisible(false);
		
		newGame.setVisible(true);
		options.setVisible(true);
		quit.setVisible(true);
		
		frame.getContentPane().repaint();
		frame.setVisible(true);
		
		restore();
	}
	
	public static void missingGraphics()
	{
		/* Requirements never specify a menu for game options so we don't technically need one
		   between gameState MAIN_MENU and gameState GAME */
		frame.getContentPane().remove(panel);
		frame.getContentPane().repaint();
		
		JLabel whoops = new JLabel("missingGraphics.png ¯\\_(ツ)_/¯ ") {
			{	
				setAlignmentX(Component.CENTER_ALIGNMENT);
				setFont(new Font("Serif", Font.BOLD, 64));
			}
		};
		
		frame.add(whoops);
		frame.setVisible(true);		
	}
	
	public static void newGameOptions()
	{
		newGame.setVisible(false);
		options.setVisible(false);
		quit.setVisible(false);
		
		optionsLabel1.setVisible(true);
		botCount.setVisible(true);
		start.setVisible(true);
		
		Main.loadNewGameOptions = false;
	}
	
	protected void paintComponent(Graphics g)
	{
	//	super.paintComponents(g);
	//	g.drawImage(mainImage, 100, 100, this);
	}
	
}
