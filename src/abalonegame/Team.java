package abalonegame;

import java.util.List;

public class Team {

    private List<Marble> members;

    public Team(List<Marble> members) {
        this.members = members;
    }

    public boolean inTeam(Marble marble) {
        return members.contains(marble);
    }
}
