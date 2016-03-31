import java.util.ArrayList;
import java.util.Random;

/**
 * @author jstanley
 *
 */
public class JstanleyPokerSquaresPlayer implements PokerSquaresPlayer {

	private final int SIZE = 5; // number of rows/columns in square grid
	@SuppressWarnings("unused")
	private PokerSquaresPointSystem system; // point system
	private Card[][] grid = new Card[SIZE][SIZE]; // grid with Card objects or null (for empty positions)
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
		int suit = card.getSuit(), col; // Used for placing the card in a specific column
		
		if(isColFull(suit) && isColFull(this.OTHER_COL)) // Both the suits column and extra cards column is full
			col = this.getEmptiestCol(); // The card will placed in the least full column
		else if(isColFull(suit))
			col = this.OTHER_COL; // The card will placed in the extra column 
		else
			col = suit; // The card will placed in the corresponding column depending on its suit
		
		int row = getHighestOccurrence(card.getRank(), col); // The card will be placed in the row with the most occurrences of it's rank
		
		grid[row][col] = card; // Updates the grid to reflect the move
		int[] play = {row, col};
		return play; // Returns the chosen play
	}
	
	/**
	 * This method will return the row with the highest occurrence of the inputed value.
	 * @param val The value to be compared to.
	 * @param col The column that the card will be placed in.
	 * @return The row with the highest occurrence of the inputed value.
	 */
	private int getHighestOccurrence(int val, int col) {
		
		// Get the empty positions in that column
		ArrayList<Integer> emptyPositions = new ArrayList<Integer>();
		for(int i = 0; i < SIZE; i++) {
			if(grid[i][col] == null) {
				emptyPositions.add(i);
			}
		}
		
		int row = 0, maxRow = Integer.MIN_VALUE;
		
		for(int i : emptyPositions) { // For each empty position in col
			int res = 0; // Keeps track of the occurrences of the value in the row
			for(int j = 0; j < SIZE; j++) { // Loops through each column
				if(grid[i][j] != null && grid[i][j].getRank() == val) {
					res++;
				}
			}
			
			// Keeps track of the row with the highest occurrence
			if(res > maxRow) {
				maxRow = res;
				row = i;
			}
		}
		return row;
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
	
	/**
	 * This method returns the column with the most empty positions.
	 * @return The column with the most empty positions
	 */
	private int getEmptiestCol() {
		int col = 0, min = Integer.MIN_VALUE;
		
		for(int i = 0; i < SIZE; i++) { // Loop through each column
			int total = 0;
			for(int j = 0; j < SIZE; j++) { // Loop through each row
				if(grid[j][i] == null) {
					total++;
				}
			}
			
			if(total > min) { // Keep track of the column with the most empty positions
				min = total;
				col = i;
			}
		}
		
		return col; // Returns that column
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
		Random random = new Random();
		System.out.println("\n\nBatch game demo:");
		System.out.println(system);
		new PokerSquares(new JstanleyPokerSquaresPlayer(), system).playSequence(10000, random.nextLong(), false);
	}

}
