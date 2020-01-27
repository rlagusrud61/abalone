package abalone;

/**
 * Abstract class for keeping a player in the abalone.Abalone abalone.Game.
 */
public abstract class Player {

    // -- Instance variables -----------------------------------------

    private String name;
    private Marble marble;

    // -- Constructors -----------------------------------------------

    /**
     * Creates a new abalone.Player object.
     *
     * @requires name is not null
     * @requires mark is either XX or OO
     * @ensures the Name of this player will be name
     * @ensures the abalone.Marble of this player will be marble
     */
    public Player(String name, Marble marble) {
        this.name = name;
        this.marble = marble;
    }

    // -- Queries ----------------------------------------------------

    /**
     * Returns the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the mark of the player.
     */
    public Marble getMarble() {
        return marble;
    }

    /**
     * Determines the field for the next move.
     *
     * @param board the current game board
     * @return the player's choice
     * @requires board is not null and not full
     * @ensures the returned in is a valid field index and that field is empty
     */
    public abstract int[] determineMove(Board board);

    // -- Commands ---------------------------------------------------
//
//    /**
//     * Makes a move on the board. <br>
//     *
//     * @param board the current board
//     * @requires board is not null and not full
//     */
//    public void makeMove(Board board) {
//        int[] choice = determineMove(board);
//        for (int i = 0; i < choice.length - 1; i++) {
//            System.out.println(choice[i]);
//            board.setField(choice[i], getMarble());
//        }
//    }
//

}
