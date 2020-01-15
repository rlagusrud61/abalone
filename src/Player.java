
/**
 * Abstract class for keeping a player in the Abalone Game.
 */
public abstract class Player {

    // -- Instance variables -----------------------------------------

    private String name;
    private Marble marble;

    // -- Constructors -----------------------------------------------

    /**
     * Creates a new Player object.
     *
     * @requires name is not null
     * @requires mark is either XX or OO
     * @ensures the Name of this player will be name
     * @ensures the Marble of this player will be marble
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
    public Mark getMark() {
        return mark;
    }

    /**
     * Determines the field for the next move.
     *
     * @param board the current game board
     * @return the player's choice
     * @requires board is not null and not full
     * @ensures the returned in is a valid field index and that field is empty
     */
    public abstract int determineMove(Board board);

    // -- Commands ---------------------------------------------------

    /**
     * Makes a move on the board. <br>
     *
     * @param board the current board
     * @requires board is not null and not full
     */
    public void makeMove(Board board) {
        int choice = determineMove(board);
        board.setField(choice, getMark());
    }

}
