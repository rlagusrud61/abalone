package abalone;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private List<Marble> members = new ArrayList<>();
    private int points;


    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public Team(List<Marble> members) {
        this.members = members;
    }

    public boolean teamHas(Marble marble) {
        return members.contains(marble);
    }
}
