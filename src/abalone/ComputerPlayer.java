package abalone;

public class ComputerPlayer extends Player {
    public static int NAIVE = 1;
    public static int SMART = 2;
    /**
     * Creates a new Computer Player object.
     *
     * @param name Name of the computer player
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
        //direction, group, team and
        int numberOfMarbles = (int) (Math.random() * 3);
        int direction = (int) (Math.random() * 6);
        if (numberOfMarbles < 2) {
            if (this.getMarble()) {
                Group soloGroup = new Group()

            }
        }
        Group group = new Group()
        Move choice = new Move(, this.getTeam())
        return null;
    }
}
