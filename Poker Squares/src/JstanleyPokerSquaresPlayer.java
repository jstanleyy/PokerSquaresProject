import java.util.ArrayList;
import java.util.Random;

/**
 * @author jstanley
 *
 */
public class JstanleyPokerSquaresPlayer implements PokerSquaresPlayer {

	private final int SIZE = 5; // number of rows/columns in square grid
	private final int NUM_POS = SIZE * SIZE; // number of positions in square grid
	private PokerSquaresPointSystem system; // point system
	private Card[][] grid = new Card[SIZE][SIZE]; // grid with Card objects or null (for empty positions)
	private Random random = new Random(); // A random number generator for breaking minimax score ties
	private final int OTHER_COL = 4;
	
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
	}

	/* (non-Javadoc)
	 * @see PokerSquaresPlayer#getPlay(Card, long)
	 */
	@Override
	public int[] getPlay(Card card, long millisRemaining) {
		int[] play; // The play to be returned
		int suit = card.getSuit(); // Used for placing the card in a specific column
		
		// If the column for that suit is full, place in the extra cards column
		if(isColFull(suit)) {
			// If the extra cards column is full, place in the first open column
			if(isColFull(OTHER_COL)) {
				// Loops to find the first column with a free space
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
				play = makePlay(card, col); // Places the card in that column
			}
			else {
				play = makePlay(card, OTHER_COL); // Places the card in the first open slot in the extra column
			}
		}
		else {
			play = makePlay(card, suit); // Places the card in the first open slot in that suits column
		}
		
		return play; // Returns the chosen play
	}

	/**
	 * This method finds the first open space in the given column, and updates the grid to reflect that move.
	 * @param card The card to be place.
	 * @param col The column that the card will be placed in.
	 * @return An array containing the chosen row and column.
	 */
	private int[] makePlay(Card card, int col) {
		boolean done = false;
		int row = 0;
		while(!done) {
			if(grid[row][col] == null) {
				grid[row][col] = card;
				done = true;
			}
			else {
				row++;
			}
		}
		int[] play = {row, col};
		return play;
	}
	
	/**
	 * Checks if the given column has a free space in it.
	 * @param col The column to be checked.
	 * @return Whether or not the given column has free space.
	 */
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
		new PokerSquares(new JstanleyPokerSquaresPlayer(), system).playSequence(10, 0, false);
	}

}
