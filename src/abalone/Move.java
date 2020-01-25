package abalone;

public class Move {

    private Direction direction;
    private Coordinate marble1;
    private Coordinate marble2;
    private Coordinate marble3;
    private Marble[] friendlyMarbles;
    private Marble[] enemyMarbles;

    public Move(Direction direction, Coordinate marble1, Coordinate marble2, Coordinate marble3, Marble[] friendlyMarbles, Marble[] enemyMarbles) {
        this.direction = direction;
        this.marble1 = marble1;
        this.marble2 = marble2;
        this.marble3 = marble3;
        this.friendlyMarbles = friendlyMarbles;
        this.enemyMarbles = enemyMarbles;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Coordinate getMarble1() {
        return marble1;
    }

    public void setMarble1(Coordinate marble1) {
        this.marble1 = marble1;
    }

    public Coordinate getMarble2() {
        return marble2;
    }

    public void setMarble2(Coordinate marble2) {
        this.marble2 = marble2;
    }

    public Coordinate getMarble3() {
        return marble3;
    }

    public void setMarble3(Coordinate marble3) {
        this.marble3 = marble3;
    }

    public Marble[] getFriendlyMarbles() {
        return friendlyMarbles;
    }

    public void setFriendlyMarbles(Marble[] friendlyMarbles) {
        this.friendlyMarbles = friendlyMarbles;
    }

    public Marble[] getEnemyMarbles() {
        return enemyMarbles;
    }

    public void setEnemyMarbles(Marble[] enemyMarbles) {
        this.enemyMarbles = enemyMarbles;
    }

    public enum Direction {
        NE(0), E(1), SE(2), SW(3), W(4), NW(5);

        private final int val;

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

        boolean isSameAxis() {
            if (this == NE && this.opposite() == SW)
                return false;

        }

    }
}
