import java.util.ArrayList;
import java.util.Random;

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
	private int[] plays = new int[NUM_POS];
	private Random random = new Random(); // A random number generator for breaking minimax score ties
	private final int OTHER_COL = 4;
	
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
		
		// (re)initialize list of play positions (row-major ordering)
		for(int i = 0; i < NUM_POS; i++) {
			plays[i] = i;
		}
	}

	/* (non-Javadoc)
	 * @see PokerSquaresPlayer#getPlay(Card, long)
	 */
	@Override
	public int[] getPlay(Card card, long millisRemaining) {
		int[] play;
		int suit = card.getSuit();
		
		if(isColFull(suit)) {
			if(isColFull(OTHER_COL)) {
				boolean done = false;
				int col = 0;
				while(!done) {
					if(isColFull(col)) {
						col++;
					}
					else {
						done = true;
					}
				}
				play = makePlay(card, col);
			}
			else {
				play = makePlay(card, OTHER_COL);
			}
		}
		else {
			play = makePlay(card, suit);
		}
		
		return play;
	}

	private int[] makePlay(Card card, int col) {
		boolean done = false;
		int row = 0;
		while(!done) {
			if(grid[row][col] == null) {
				grid[row][col] = card;
				numPlays++;
				done = true;
			}
			else {
				row++;
			}
		}
		int[] play = {row, col};
		return play;
	}
	
	private boolean isColFull(int col) {
		boolean res = true;
		for(int i = 0; i < grid.length; i++) {
			if(grid[i][col] == null) {
				res = false;
			}
		}
		return res;	
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
		//System.out.println(system);
		//new PokerSquares(new JstanleyPokerSquaresPlayer(), system ).play();
		
		System.out.println("\n\nBatch game demo:");
		System.out.println(system);
		new PokerSquares(new JstanleyPokerSquaresPlayer(), system).playSequence(10, 10000, false);
	}

}
