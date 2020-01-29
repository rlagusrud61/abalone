package abalone;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private List<Marble> marbles = new ArrayList<>();

    /**
     * Points of this team. It adds whenever the team member pushes a marble of the opponent team.
     */
    private int points;

    // Constructor

    /**
     * Constructs a team with the list of the marbles.
     *
     * @param marbles list of marbles that are added in a team.
     */
    public Team(List<Marble> marbles) {
        this.marbles = marbles;
    }

    /**
     * .
     *
     * @return the points of this team.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Adds a point to the team.
     */
    public void addPoint() {
        this.points++;
    }

    /**
     * Get the marbles that are in this team.
     * @requires marble != null
     * @return list of marbles in this team.
     */
    public List<Marble> getMarbles() {
        return marbles;
    }

    /**
     * Checks if the marble is in this team.
     * @param marble to be checked
     * @requires marble != null
     * @return true if marbles.contains(marble), else, false;
     */
    public boolean teamHas(Marble marble) {
        return marbles.contains(marble);
    }
}
