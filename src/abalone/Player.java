package abalone;

/**
 * Abstract class for keeping a player in the abalone.Abalone abalone.Game.
 */
public abstract class Player {

    // -- Instance variables -----------------------------------------

    private String name;
    private Marble marble;
    private Team team;

    // -- Constructors -----------------------------------------------

    /**
     * Creates a new abalone.Player object.
     *
     * @requires name is not null
     * @requires mark is not EMPTY
     * @requires mark is either WHITE, BLACK, BLUE, or RED
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
     * Returns the marble of the player.
     */
    public Marble getMarble() {
        return marble;
    }

    /**
     * Returns the team of the player.
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Sets a player in a team.
     */
    void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Determines the field for the next move.
     *
     * @param board the current game board
     * @return the player's choice
     * @requires board is not null
     */
    public abstract Move makeChoice(Board board , String input);


}
