package abalone;

public class Move {

    private Direction direction;
    private Group group;
    private Team team;

    public Move(Direction direction, Group group, Team team) {
        this.direction = direction;
        this.group = group;
        this.team = team;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * ENUM DIRECTION
     */
    public enum Direction {
        NE(0), E(1), SE(2), SW(3), W(4), NW(5);

        private final int val;
        private Board board;

        Direction(int val) {
            this.val = val;
        }


        Direction opposite() {
            switch (this) {
                case NE:
                    return SW;
                case E:
                    return W;
                case SE:
                    return NW;
                case SW:
                    return NE;
                case W:
                    return E;
                case NW:
                    return SE;
                default:
                    throw new IllegalStateException();
            }
        }

        boolean isParallelTo(Move.Direction direction) {
            switch (this) {
                case NE:
                case SW:
                    return direction == NE || direction == SW;
                case E:
                case W:
                    return direction == E || direction == W;
                case NW:
                case SE:
                    return direction == SE || direction == NW;

                default:
                    throw new IllegalStateException();
            }
        }
    }

    public class MoveType {

        public static final int SLIDE = 0;
        public static final int PUSH = 1;
        private Group movedMarbles;
        private Group selectedMarbles;
        private int moveType;

        public MoveType(int moveType, Group selectedMarbles, Group movedMarbles) {
            this.moveType = moveType;
            this.selectedMarbles = selectedMarbles;
            this.movedMarbles = movedMarbles;
        }

        public int getMoveType() {
            return moveType;
        }

        public Group getMovedMarbles() {
            return movedMarbles;
        }

        public Group getSelectedMarbles() {
            return selectedMarbles;
        }
    }

}
