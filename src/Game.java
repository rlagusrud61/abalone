public interface Game {

    /**
     * The board.
     *
     * @invariant board is never null
     */

    Board board = new Board();
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
    int current;

    // -- Constructors -----------------------------------------------

    /**
     * Creates a new Game object.
     *
     * @param s0 the first player
     * @param s1 the second player
     * @requires s0 and s1 to be non-null
     */
    public Game(Player s0, Player s1);

    // -- Commands ---------------------------------------------------

    /**
     * Starts the Tic Tac Toe game. <br>
     * Asks after each ended game if the user want to continue. Continues until
     * the user does not want to play anymore.
     */
    public void start();

    /**
     * Resets the game. <br>
     * The board is emptied and player[0] becomes the current player.
     */
    private void reset() {
        current = 0;
        board.reset();
    }

    /**
     * Plays the Tic Tac Toe game. <br>
     * First the (still empty) board is shown. Then the game is played
     * until it is over. Players can make a move one after the other.
     * After each move, the changed game situation is printed.
     */
    private void play() {
        while (!board.gameOver() && !board.hasWinner()) {
            board.setField(players[current].determineMove(board), players[current].getMark());
            current = (current == 0) ? 1 : 0;
            update();
        }
    }

    /**
     * Prints the game situation.
     */
    private void update() {
        System.out.println("\nCurrent game situation: \n\n" + board.toString()
                + "\n");
    }

    /**
     * Prints the result of the last game. <br>
     *
     * @requires the game to be over
     */
    private void printResult() {
        if (board.hasWinner()) {
            Player winner = board.isWinner(players[0].getMark()) ? players[0]
                    : players[1];
            System.out.println("Player " + winner.getName() + " ("
                    + winner.getMark().toString() + ") has won!");
        } else {
            System.out.println("Draw. There is no winner!");
        }
    }
}
