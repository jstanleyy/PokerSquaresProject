
/**
 * @author jstanley
 *
 */
public class JstanleyPokerSquaresPlayer implements PokerSquaresPlayer {

	private final int SIZE = 5; // number of rows/columns in square grid
	private final int NUM_POS = SIZE * SIZE; // number of positions in square grid
	private final int NUM_CARDS = Card.NUM_CARDS; // number of cards in deck
	private int depthLimit = 2; // default depth limit
	private PokerSquaresPointSystem system; // point system
	private Card[][] grid = new Card[SIZE][SIZE]; // grid with Card objects or null (for empty positions)
	private int numPlays = 0; // number of Cards played into the grid so far
	
	/**
	 * Creates a Jstanley player with the default depth limit of 2.
	 */
	public JstanleyPokerSquaresPlayer() {
	}
	
	/**
	 * Creates a Jstanley player with a specified depth limit.
	 * @param depthIn The desired depth limit of the player.
	 */
	public JstanleyPokerSquaresPlayer(int depthIn) {
		this.depthLimit = depthIn;
	}
	
	/* (non-Javadoc)
	 * @see PokerSquaresPlayer#setPointSystem(PokerSquaresPointSystem, long)
	 */
	@Override
	public void setPointSystem(PokerSquaresPointSystem system, long millis) {
		this.system = system;
	}

	/* (non-Javadoc)
	 * @see PokerSquaresPlayer#init()
	 */
	@Override
	public void init() {
		// clear grid
		for(int row = 0; row < SIZE; row++) {
			for(int col = 0; col < SIZE; col++) {
				grid[row][col] = null;
			}
		}
		
		numPlays = 0; // reset numPlays
	}

	/* (non-Javadoc)
	 * @see PokerSquaresPlayer#getPlay(Card, long)
	 */
	@Override
	public int[] getPlay(Card card, long millisRemaining) {
		int[] play = new int[2]; // the position that the card will ultimately be placed
		
		if (numPlays < 24) { // Not the last play
			int remainingPlays = NUM_POS - numPlays;
			
		}
		else { // Must be placed in the last open spot
			
		}
		return play; // return the chose play
	}

	/* (non-Javadoc)
	 * @see PokerSquaresPlayer#getName()
	 */
	@Override
	public String getName() {
		return "JstanleyPokerSquaresPlayer";
	}
	
	/**
	 * Demonstrate JstanleyPokerSquaresPlayer with the American point system.
	 * @param args (not used)
	 */
	public static void main(String[] args) {
		PokerSquaresPointSystem system = PokerSquaresPointSystem.getAmericanPointSystem();
		System.out.println(system);
		new PokerSquares(new JstanleyPokerSquaresPlayer(), system ).play();
	}

}
