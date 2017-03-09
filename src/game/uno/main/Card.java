package game.uno.main;

public class Card {
	
	public char color;
	public int value;
	public boolean special;
	public int specialValue;
	
	public Card(char c, int v, boolean spec, int specV)
	{
		// No-argument constructor
		color = c;
		value = v;
		special = spec;
		specialValue = specV;
		
		System.out.println("Card made with color: " + c + ", Value: " + v + ", Special: " + spec + ", Special Value: " + specV);
	}
}
