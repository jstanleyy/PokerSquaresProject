
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
	private Card[] simDeck = Card.getAllCards(); // A list of all cards
	private int[] plays = new int[NUM_POS];
	private int[][] legalPlays = new int[NUM_POS][NUM_POS];
	
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
		
		// Match simDeck to actual play event. So, from index numPlays on, is a list of undealt cards
		int cardIndex = numPlays;
		while(card.equals(simDeck[cardIndex])) {
			cardIndex++;
		}
		simDeck[cardIndex] = simDeck[numPlays];
		simDeck[numPlays] = card;
		
		if (numPlays < 24) { // Not the last play
			int remainingPlays = NUM_POS - numPlays; // Number of plays remaining
			// Copies the play positions that are empty
			System.arraycopy(plays, numPlays, legalPlays[numPlays], 0, remainingPlays);
			
		}
		
		// the position that the card will ultimately be placed
		int[] playPos = { plays[numPlays] / SIZE, plays[numPlays] % SIZE };
		makePlay(card, playPos[0], playPos[1]);
		return playPos; // return the chose play
	}
	
	/**
	 * This method updates the global variables to reflect the chosen play.
	 * @param card The card to be played.
	 * @param row The row in which the card is to be placed.
	 * @param col The column in which the card is to be placed
	 */
	private void makePlay(Card card, int row, int col) {
		
		// Match simDeck to event
		int cardIndex = numPlays;
		while(!card.equals(simDeck[cardIndex])) {
			cardIndex++;
		}
		simDeck[cardIndex] = simDeck[numPlays];
		simDeck[numPlays] = card;
		
		// Update grid and plays to reflect the chosen play
		grid[row][col] = card;
		int play = row * SIZE + col;
		int j = 0;
		while(plays[j] != play) {
			j++;
		}
		plays[j] = plays[numPlays];
		plays[numPlays] = play;
		
		numPlays++; // Increment numPlays by 1
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
