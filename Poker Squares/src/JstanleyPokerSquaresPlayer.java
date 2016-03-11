
/**
 * @author jstanley
 *
 */
public class JstanleyPokerSquaresPlayer implements PokerSquaresPlayer {

	private final int SIZE = 5; // number of rows/columns in square grid
	private final int NUM_POS = SIZE * SIZE; // number of positions in square grid
	private final int NUM_CARDS = Card.NUM_CARDS; // number of cards in deck
	private PokerSquaresPointSystem system; // point system
	private Card[][] grid = new Card[SIZE][SIZE]; // grid with Card objects or null (for empty positions)
	private int numPlays = 0; // number of Cards played into the grid so far
	
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
		// TODO stuff
		// clear grid
		for(int row = 0; row < SIZE; row++) {
			for(int col = 0; col < SIZE; col++) {
				grid[row][col] = null;
			}
		}

	}

	/* (non-Javadoc)
	 * @see PokerSquaresPlayer#getPlay(Card, long)
	 */
	@Override
	public int[] getPlay(Card card, long millisRemaining) {
		// TODO Auto-generated method stub
		return null;
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
