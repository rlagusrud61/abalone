package abalone;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Group {

    public Coordinate[] marbles;

    public int size;

    /**
     * Constructs a group of one marble.
     *
     * @param marble Coordinate of the marble
     * @invariant marbles.length == 1
     * @invariant size == 1
     */
    //Group of one marble
    public Group(Coordinate marble) {
        marbles = new Coordinate[1];
        marbles[0] = marble;
        size = 1;
    }

    /**Constructs a group of two marbles.
     * @invariant marbles.length == 2
     * @invariant size == 2
     * @param marble1 Coordinate of the first marble
     * @param marble2 Coordinate of the second marble
     */
    //Group of two marbles
    public Group(Coordinate marble1, Coordinate marble2) {
        marbles = new Coordinate[2];
        marbles[0] = marble1;
        marbles[1] = marble2;
        size = 2;
    }

    /**Constructs a group of three marbles.
     * @invariant marbles.length == 3
     * @invariant size == 3
     * @param marble1 Coordinate of the first marble
     * @param marble2 Coordinate of the second marble
     * @param marble3 Coordinate of the third marble
     */
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

    /**Moves (steps) the group to the given direction.
     * @requires d != null
     * @param d Direction
     * @return a new group of coordinates that steps each marbles in the group to the given direction.
     */
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

    /**
     * Returns the first marble.
     *
     * @return the first marble.
     * @requires marbles[0] != null;
     */
    public Coordinate getMarble1() {
        return marbles[0];
    }

    /**Returns the second marble.
     * @ensures size > 1
     * @returns the second marble.
     */
    public Coordinate getMarble2() {
        if (size < 2) {
            return null;
        }
        return marbles[1];
    }

    /**Returns the third marble.
     * @ensures size > 2
     * @returns the third marble.
     */


    public Coordinate getMarble3() {
        if (size < 3) {
            return null;
        }
        return marbles[2];
    }

    /**Checks if the group of coordinates are in line.
     * @return true if this.getLineDirection() != null, false if it this.getLineDirection() == null;
     */
    public boolean isInLine() {
        return getLineDirection() != null;
    }

    /**Gives the direction in which the group of coordinates are aligned.
     * @requires size > 1
     * @return direction of the line
     */
    public Move.Direction getLineDirection() {
        switch (size) {
            case 2:
                return getLineDirection(getMarble1(), getMarble2());
            case 3:
                return getLineDirection(getMarble1(), getMarble2(), getMarble3());
            default:
                return null;
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
                if (pawn.equals(coord2)) {
                    foundMarble2 = true;
                }
                if (pawn.equals(coord3)) {
                    foundMarble3 = true;
                }
            }
            while (pawn.step(direction.opposite()) != null) {
                pawn = pawn.step(direction.opposite());
                if (pawn.equals(coord2)) {
                    foundMarble2 = true;
                }
                if (pawn.equals(coord3)) {
                    foundMarble3 = true;
                }
            }
            if (foundMarble2 && foundMarble3) {
                return direction;
            }
        }

        return null;
    }

    /**
     * Checks if the given coordinate is in the selected group.
     * @param marble the coordinate of the marble
     * @return true if the marble belongs to the group, else false.
     */
    public boolean isMarbleInGroup(Coordinate marble) {
        for (int i = 0; i < size; i++) {
            if (marbles[i].equals(marble)) {
                return true;
            }
        }
        return false;
    }

}
