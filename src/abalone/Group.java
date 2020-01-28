package abalone;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Group {

    public Coordinate[] marbles;

    public int size;

    //Group of one marble
    public Group(Coordinate marble) {
        marbles = new Coordinate[1];
        marbles[0] = marble;
        size = 1;
    }

    //Group of two marbles
    public Group(Coordinate marble1, Coordinate marble2) {
        marbles = new Coordinate[2];
        marbles[0] = marble1;
        marbles[1] = marble2;
        size = 2;
    }

    //Group of three marbles
    public Group(Coordinate marble1, Coordinate marble2, Coordinate marble3) {
        marbles = new Coordinate[3];
        marbles[0] = marble1;
        marbles[1] = marble2;
        marbles[2] = marble3;
        size = 3;
    }

    public List<Coordinate> getMarbles() {
        return Optional.ofNullable(this.marbles).stream().flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }

    public Group step(Move.Direction d) {
        if (size == 1) {
            return new Group(getMarble1().step(d));
        } else if (size == 2) {
            return new Group(getMarble1().step(d), getMarble2().step(d));
        } else if (size == 3) {
            return new Group(getMarble1().step(d), getMarble2().step(d), getMarble3().step(d));
        }
        return null;
    }

    public Coordinate getMarble1() {
        return marbles[0];
    }

    public Coordinate getMarble2() {
        if (size < 2) return null;
        return marbles[1];
    }

    public Coordinate getMarble3() {
        if (size < 3) return null;
        return marbles[2];
    }

    public boolean isInLine() {
        return getLineDirection() != null;
    }

    public Move.Direction getLineDirection() {
        switch (size) {
            case 2:
                return getLineDirection(getMarble1(), getMarble2());
            case 3:
                return getLineDirection(getMarble1(), getMarble2(), getMarble3());
            default:
                throw new IllegalStateException("Unexpected value: " + size);
        }
    }

    private Move.Direction getLineDirection(Coordinate coord1, Coordinate coord2) {
        for (Move.Direction direction : Move.Direction.values()) {
            Coordinate pawn = new Coordinate(coord1);
            while (pawn.step(direction) != null) {
                pawn = pawn.step((direction));
                if (pawn.equals(coord2)) {
                    return direction;
                }
            }

            while (pawn.step(direction.opposite()) != null) {
                pawn = pawn.step(direction.opposite());
                if (pawn.equals(coord2)) {
                    return direction;
                }
            }
        }

        return null;
    }

    private Move.Direction getLineDirection(Coordinate coord1, Coordinate coord2, Coordinate coord3) {
        for (Move.Direction direction : Move.Direction.values()) {
            Coordinate pawn = new Coordinate(coord1);

            boolean foundMarble2 = false;
            boolean foundMarble3 = false;

            while (pawn.step(direction) != null) {
                pawn = pawn.step((direction));
                if (pawn.equals(coord2)) foundMarble2 = true;
                if (pawn.equals(coord3)) foundMarble3 = true;
            }
            while (pawn.step(direction.opposite()) != null) {
                pawn = pawn.step(direction.opposite());
                if (pawn.equals(coord2)) foundMarble2 = true;
                if (pawn.equals(coord3)) foundMarble3 = true;
            }
            if (foundMarble2 && foundMarble3) {
                return direction;
            }
        }

        return null;
    }

    public boolean isMarbleInGroup(Coordinate marble) {
        for (int i = 0; i < size; i++) {
            if (marbles[i].equals(marble)) {
                return true;
            }
        }
        return false;
    }

}
