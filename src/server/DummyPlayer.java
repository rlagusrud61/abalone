package server;

import abalone.Board;
import abalone.Marble;
import abalone.Move;
import abalone.Player;

public class DummyPlayer extends Player {

    /**
     * Creates a new abalone.Player object.
     *
     * @param name
     * @param marble
     * @requires name is not null
     * @requires mark is not EMPTY
     * @requires mark is either WHITE, BLACK, BLUE, or RED
     * @ensures the Name of this player will be name
     * @ensures the abalone.Marble of this player will be marble
     */
    public DummyPlayer(String name, Marble marble) {
        super(name, marble);
    }

    @Override
    public Move makeChoice(Board board) {
        return null;
    }

}
