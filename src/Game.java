public interface Game {

    int deadBlackCount = 0;
    int deadWhiteCount = 0;

    /**
     * The board.
     *
     * @invariant board is never null
     */
    Board board = null;

    /**
     * The 2 players of the game.
     *
     * @invariant the length of the array equals NUMBER_PLAYERS
     * @invariant all array items are never null
     */
    Player[] players = null;

    /**
     * Index of the current player.
     *
     * @invariant the index is always between 0 and NUMBER_PLAYERS
     */
    int current = 0;

    // -- Commands ---------------------------------------------------

    /**
     * Starts the Tic Tac Toe game. <br>
     * Asks after each ended game if the user want to continue. Continues until
     * the user does not want to play anymore.
     */
    void start();

    /**
     * Resets the game. <br>
     * The board is emptied and player[0] becomes the current player.
     */
    void reset();

    /**
     * Plays the Tic Tac Toe game. <br>
     * First the (still empty) board is shown. Then the game is played
     * until it is over. Players can make a move one after the other.
     * After each move, the changed game situation is printed.
     */
    void play();


    /**
     * Prints the game situation.
     */
    void update();

    /**
     * Prints the result of the last game. <br>
     *
     * @requires the game to be over
     */
    void printResult();
}