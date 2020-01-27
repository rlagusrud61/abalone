package abalone;

import java.util.List;

public class Group {

    public List<Coordinate> marbles;


    //Group of one marble
    public Group(Coordinate marble) {
        marbles.add(marble);
    }

    //Group of two marbles
    public Group(Coordinate marble1, Coordinate marble2) {
        marbles.add(marble1);
        marbles.add(marble2);
    }

    //Group of three marbles
    public Group(Coordinate marble1, Coordinate marble2, Coordinate marble3) {
        marbles.add(marble1);
        marbles.add(marble2);
        marbles.add(marble3);
    }

//    public List<Coordinate> getMarbles() {
//        return Optional.ofNullable(this.marbles).stream().flatMap(Arrays::stream)
//                .collect(Collectors.toList());
//    }

    public List<Coordinate> getCoordinates() {
        return marbles;
    }

    public Group step(Move.Direction d) {
        if (marbles.size() == 1) {
            return new Group(getFstCoord().step(d));
        } else if (marbles.size() == 2) {
            return new Group(getFstCoord().step(d), getSndCoord().step(d));
        } else if (marbles.size() == 3) {
            return new Group(getFstCoord().step(d), getSndCoord().step(d), getTrdCoord().step(d));
        }
        return null;
    }

    public Coordinate getFstCoord() {
        return marbles.get(0);
    }

    public Coordinate getSndCoord() {
        return marbles.get(1);
    }

    public Coordinate getTrdCoord() {
        return marbles.get(2);
    }

    public boolean isInLine() {
        return getLineDirection() != null;
    }

    public Move.Direction getLineDirection() {
        switch (marbles.size()) {
            case 2:
                return getLineDirection(getFstCoord(), getSndCoord());
            case 3:
                return getLineDirection(getFstCoord(), getSndCoord(), getTrdCoord());
            default:
                throw new IllegalStateException("Unexpected value: " + marbles.size());
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
        throw new IllegalStateException("They're not in line!");

    }

    private Move.Direction getLineDirection(Coordinate coord1, Coordinate coord2, Coordinate coord3) {
        for (Move.Direction direction : Move.Direction.values()) {
            Coordinate pawn = new Coordinate(coord1);
            int encounters = 0;
            while (pawn.step(direction) != null) {
                pawn = pawn.step((direction));
                if (pawn.equals(coord2) || pawn.equals(coord3)) {
                    encounters += 1;
                }
            }
            while (pawn.step(direction.opposite()) != null) {
                pawn = pawn.step(direction.opposite());
                if (pawn.equals(coord2) || pawn.equals(coord3)) {
                    encounters += 1;
                }
            }
            if (encounters == 2) {
                return direction;
            }
        }
        throw new IllegalStateException("They're not in line!");

    }

    public boolean isMarbleInGroup(Coordinate marble) {
        for (int i = 0; i < marbles.size(); i++) {
            if (marbles.get(i).equals(marble)) {
                return true;
            }
        }
        return false;
    }

}
