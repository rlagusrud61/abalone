package abalone;

public class ComputerPlayer extends Player {
    /**
     * Creates a new Computer Player object.
     *
     * @param name   Name of the computer player
     * @param marble Marble color assigned to this player
     * @requires name is not null
     * @requires mark is either WHITE, BLUE, BLACK, or RED
     * @ensures the Name of this player will be name
     * @ensures the abalone.Marble of this player will be marble
     */
    public ComputerPlayer(String name, Marble marble) {
        super(name, marble);
    }

    @Override
    public Move makeChoice(Board board) {
        return null;
    }
}
