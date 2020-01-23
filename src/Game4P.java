import utils.TextIO;

public class Game4P implements Game {

    public static final int NUMBER_PLAYERS = 4;
    /**
     * The board.
     *
     * @invariant board is never null
     */
    private Board board;

    /**
     * The 2 players of the game.
     *
     * @invariant the length of the array equals NUMBER_PLAYERS
     * @invariant all array items are never null
     */
    private Player[] players;

    /**
     * Index of the current player.
     *
     * @invariant the index is always between 0 and NUMBER_PLAYERS
     */
    private int current;

    // -- Constructors -----------------------------------------------

    /**
     * Creates a new Game object.
     *
     * @param s0 the first player
     * @param s1 the second player
     * @requires s0 and s1 to be non-null
     */
    public Game4P(Player s0, Player s1, Player s2, Player s3) {
        board = new Board();
        players = new Player[NUMBER_PLAYERS];
        players[0] = s0;
        players[1] = s1;
        players[2] = s2;
        players[3] = s3;
        current = 0;
    }

    // -- Commands ---------------------------------------------------

    /**
     * Starts the Abalone game. <br>
     * Asks after each ended game if the user want to continue. Continues until
     * the user does not want to play anymore.
     */
    @Override
    public void start() {
        boolean continueGame = true;
        while (continueGame) {
            reset();
            play();
            System.out.println("\n> Play another time? (y/n)?");
            continueGame = TextIO.getBoolean();
        }
    }

    /**
     * Resets the game. <br>
     * The board is emptied and player[0] becomes the current player.
     */
    @Override
    public void reset() {
        current = 0;
        board.reset4P();
    }

    /**
     * Plays the Abalone game. <br>
     * First the (still empty) board is shown. Then the game is played
     * until it is over. Players can make a move one after the other.
     * After each move, the changed game situation is printed.
     */
    @Override
    public void play() {
        while (!board.gameOver()) {
            System.out.println(board);
            if (current == 0) {
                players[0].makeMove(board);
                current++;
            }
            update();
            if (current == 1 && !board.gameOver()) {
                players[1].makeMove(board);
                current++;

            }
            if (current == 2 && !board.gameOver()) {
                players[2].makeMove(board);
                current++;
            }
            if (current == 3 && !board.gameOver()) {
                players[3].makeMove(board);
                current++;
            }

            if (current > 3) {
                current = 0;

            }
        }
        update();
        printResult();
    }

    /**
     * Prints the game situation.
     */
    @Override
    public void update() {
        System.out.println("\ncurrent game situation: \n\n" + board.toString()
                + "\n");
    }

    /**
     * Prints the result of the last game. <br>
     *
     * @requires the game to be over
     */
    @Override
    public void printResult() {
        if (board.hasWinner()) {
            for (int i = 0; i < players.length; i++) {
                if (board.isWinner(players[i].getMarble())) {
                    Player winner = players[i];
                    System.out.println("Speler " + winner.getName() + " ("
                            + winner.getMarble().toString() + ") has won!");
                }
            }
        } else {
            System.out.println("Draw. There is no winner!");
        }
    }
}