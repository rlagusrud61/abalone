package abalone;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private List<Marble> marbles = new ArrayList<>();
    private int points;


    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public Team(List<Marble> marbles) {
        this.marbles = marbles;
    }

    public List<Marble> getMarbles() {
        return marbles;
    }

    public boolean teamHas(Marble marble) {
        return marbles.contains(marble);
    }
}
