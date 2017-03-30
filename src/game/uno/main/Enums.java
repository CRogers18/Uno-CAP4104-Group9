package game.uno.main;

public class Enums {
	public enum CardColor
	{
		Red,
	    Yellow,
	    Green,
	    Blue,
	    Wild
	}
	
	public enum CardValue
	{
		Zero(0),
	    One(1),
	    Two(2),
	    Three(3),
	    Four(4),
	    Five(5),
	    Six(6),
	    Seven(7),
	    Eight(8),
	    Nine(9),
	    Reverse(20),
	    Skip(20),
	    DrawTwo(20),
	    DrawFour(50),
	    Wild(50);
	    
		// define card score to get card's score from card' value
	    int score;
	    CardValue(int val)
	    {
	    	score = val;
	    }
	    
	    int getScore()
	    {
	    	return score;
	    }
	}
}
