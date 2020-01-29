package abalone;

public class Move {

    private Direction direction;
    private Group group;
    private Team team;

    /**
     * Constructs a Move.
     *
     * @param direction of which the group will move to
     * @param group     selected by the player
     * @param team      which the marble belongs to
     */
    public Move(Direction direction, Group group, Team team) {
        this.direction = direction;
        this.group = group;
        this.team = team;
    }

    public Direction getDirection() {
        return direction;
    }


    public Group getGroup() {
        return group;
    }

    public Team getTeam() {
        return team;
    }



    /**
     * Direction of the Coordinates.
     */
    public enum Direction {
        NE(0), E(1), SE(2), SW(3), W(4), NW(5);

        public final int val;

        /**
         * Constructs a Direction.
         * @param val of the Direction.
         */
        Direction(int val) {
            this.val = val;
        }

        /**
         * Takes an integer and converts into Enum of Direction.
         * @param integer to be converted
         * @return Direction value of the integer.
         */
        public static Direction intToDirection(int integer) {
            switch (integer) {
                case 0:
                    return NE;
                case 1:
                    return E;
                case 2:
                    return SE;
                case 3:
                    return SW;
                case 4:
                    return W;
                case 5:
                    return NW;
                default:
                    return null;
            }
        }


        /**
         * Gives the opposite direction.
         * @return opposite value of this direction
         */
        public Direction opposite() {
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

        /**
         * Checks if the given direction is parallel (in line) with a direction.
         * @param direction to be compared with
         * @return true if the direction is on the same axis as the other direction.
         */

        public boolean isParallelTo(Move.Direction direction) {
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

}
