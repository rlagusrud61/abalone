package abalone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    public static final int[] ROW_SIZES = new int[]{5, 6, 7, 8, 9, 8, 7, 6, 5};

    private Marble[] fields;
    private List<Team> teams;
    private int moveCounter;


    public Board() {
        fields = new Marble[61];
    }

    private static final String[] NUMBERING = {
            "        // ┃ 00 ┃ 01 ┃ 02 ┃ 03 ┃ 04 ┃ \\\\",
            "      // ┃ 05 ┃ 06 ┃ 07 ┃ 08 ┃ 09 ┃ 10 ┃ \\\\",
            "   // ┃ 11 ┃ 12 ┃ 13 ┃ 14 ┃ 15 ┃ 16 ┃ 17 ┃ \\\\",
            " // ┃ 18 ┃ 19 ┃ 20 ┃ 21 ┃ 22 ┃ 23 ┃ 24 ┃ 25 ┃ \\\\",
            "// ┃ 26 ┃ 27 ┃ 28 ┃ 29 ┃ 30 ┃ 31 ┃ 32 ┃ 33 ┃ 34 ┃ \\\\",
            "\\\\ ┃ 35 ┃ 36 ┃ 37 ┃ 38 ┃ 39 ┃ 40 ┃ 41 ┃ 42 ┃ //",
            "   \\\\ ┃ 43 ┃ 44 ┃ 45 ┃ 46 ┃ 47 ┃ 48 ┃ 49 ┃ //",
            "     \\\\ ┃ 50 ┃ 51 ┃ 52 ┃ 53 ┃ 54 ┃ 55 ┃ //",
            "       \\\\ ┃ 56 ┃ 57 ┃ 58 ┃ 59 ┃ 60 ┃ //"
    };

    public static void main(String[] args) {
        Board board = new Board();
        board.reset(BoardStates.getFourPlayer());
        printBoard();

        // Game start
        System.out.println(board.toString());

        // Turn 1
        board.makeMove(new Move(Move.Direction.NW,
                new Group(
                        board.convertToCoordinate(32),
                        board.convertToCoordinate(23)),
                new Team(Arrays.asList(Marble.BLACK, Marble.RED))
        ));
        System.out.println(board.toString());

        // Turn 2
        board.makeMove(new Move(Move.Direction.W,
                new Group(
                        board.convertToCoordinate(17),
                        board.convertToCoordinate(15),
                        board.convertToCoordinate(16)),
                new Team(Arrays.asList(Marble.BLACK, Marble.RED))
        ));
        System.out.println(board.toString());

        // Turn 3
        board.makeMove(new Move(Move.Direction.NW,
                new Group(
                        board.convertToCoordinate(28),
                        board.convertToCoordinate(37)),
                new Team(Arrays.asList(Marble.BLACK, Marble.RED))
        ));
        System.out.println(board.toString());

        // Turn 4
        board.makeMove(new Move(Move.Direction.NW,
                new Group(
                        board.convertToCoordinate(28),
                        board.convertToCoordinate(19)),
                new Team(Arrays.asList(Marble.BLACK, Marble.RED))
        ));
        System.out.println(board.toString());

        // Turn 5
        System.out.println(board.makeMove(new Move(Move.Direction.W,
                new Group(
                        board.convertToCoordinate(14),
                        board.convertToCoordinate(15),
                        board.convertToCoordinate(16)),
                new Team(Arrays.asList(Marble.BLACK, Marble.WHITE))
        )));
        System.out.println(board.toString());
    }


    public static void printBoard() {
        for (int i = 0; i < 9; i++) {
            System.out.println(NUMBERING[i] + '\n');
        }
    }

    /**
     * Converts a coordinate (row, column) into a int index.
     *
     * @param coord
     * @return
     * @requires cord != null;
     */

    public int convertToInt(Coordinate coord) {
        int result = 0;
        for (int i = 0; i < coord.y; i++) {
            result += ROW_SIZES[i];
        }
        result += coord.x;
        return result;
    }

    private Coordinate convertToCoordinate(int index) {
        int counter = 0;

        for (int y = 0; y < ROW_SIZES.length; y++) {
            for (int x = 0; x < ROW_SIZES[y]; x++) {
                if (counter == index) {
                    return new Coordinate(x, y);
                }

                counter += 1;
            }
        }

        throw new IndexOutOfBoundsException();
    }

    /**
     * Returns the content of the field i.
     *
     * @param i the number of the field (see NUMBERING)
     * @return the mark on the field
     * @requires i to be a valid field
     * @ensures the result to be either EMPTY, XX or OO
     */
    public Marble getField(int i) {
        if (isField(i)) {
            return fields[i];
        }

        return null;
    }

    public boolean isField(int index) {
        return 0 <= index && index < 62;
    }

    private boolean isField(Coordinate coord) {
        return coord.y >= 0 && coord.y < ROW_SIZES.length
                && coord.x < ROW_SIZES[coord.y] && coord.x >= 0;
    }

    /**
     * Returns the content of the field referred to by the (row,col) pair.
     *
     * @param coord the coordinates of the field
     * @return the mark on the field
     * @requires (row, col) to be a valid field
     * @ensures the result to be either EMPTY, XX or OO
     */
    public Marble getField(Coordinate coord) {
        if (isField(coord)) {
            return fields[convertToInt(coord)];
        }
        return null;
    }

    /**
     * Returns true if the field i is empty.
     *
     * @param i the index of the field (see NUMBERING)
     * @return true if the field is empty
     * @requires i to be a valid field index
     * @ensures true when the Mark at index i is EMPTY
     */
    public boolean isEmpty(int i) {
        return getField(i) == Marble.EMPTY;
    }

    /**
     * Returns true if the field referred to by the (row,col) pair it empty.
     *
     * @param coord the coordinates of the field
     * @return true if the field is empty
     * @requires (row, col) to be a valid field
     * @ensures true when the Mark at (row, col) is EMPTY
     */
    public boolean isEmpty(Coordinate coord) {
        return getField(coord) == Marble.EMPTY;
    }

    /**
     * Sets the content of field i to the mark m.
     *
     * @param m the mark to be placed
     * @requires i to be a valid field
     * @ensures field i to be set to Mark m
     */
    public void setField(Coordinate coordinate, Marble m) {
        fields[convertToInt(coordinate)] = m;
    }


    /**
     * Returns a String representation of this board. In addition to the current
     * situation, the String also shows the numbering of the fields.
     *
     * @return the game situation as String
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < ROW_SIZES.length; i++) {
            String row = rowToString(i);
            s.append(row).append("\n");
        }
        return s.toString();
    }

    public String rowToString(int rowindex) {
        StringBuilder s = new StringBuilder();

        for (int i = 10; i > ROW_SIZES[rowindex]; i--) {
            s.insert(0, "   ");
        }
        for (int i = 0; i < ROW_SIZES[rowindex]; i++) {
            Coordinate coord = new Coordinate(i, rowindex);
            s.append(getField(coord).draw()).append(" ┃   ");
        }
        return s.toString();
    }

    public void reset(Marble[] marbles) {
        this.fields = marbles;
    }

    public boolean makeMove(Move move) {
        // Check if neighbours
        if (move.getGroup().size == 1 || move.getGroup().getLineDirection().isParallelTo(move.getDirection())) {
            Coordinate pawn = move.getGroup().getMarble1();

            // Walk to Coordinate closest in move direction

//            while (pawn.step(move.getDirection()).equals(move.getGroup().getMarble1())
//                    || move.getGroup().size >= 2 && pawn.step(move.getDirection()).equals(move.getGroup().getMarble2())
//                    || move.getGroup().size >= 3 && pawn.step(move.getDirection()).equals(move.getGroup().getMarble3())) {
            while (move.getGroup().isMarbleInGroup(pawn.step(move.getDirection()))) {
                pawn = pawn.step(move.getDirection());
            }

            // Calculate our push strength (own marbles + friendly marbles)
            List<Coordinate> friendlies = new ArrayList<Coordinate>();
            while (move.getTeam().teamHas(getField(pawn.step(move.getDirection())))) {
                pawn = pawn.step(move.getDirection());
                friendlies.add(pawn);
            }

            // Calculate enemy strength
            List<Coordinate> enemies = new ArrayList<>();
            while (pawn.isValidStep(move.getDirection()) && !isEmpty(pawn.step(move.getDirection()))
                    && !move.getTeam().teamHas(getField(pawn.step(move.getDirection())))) {
                pawn = pawn.step(move.getDirection());
                enemies.add(pawn);
            }

            boolean last_empty = false;
            boolean last_out_of_bounds = false;
            if (pawn.isValidStep(move.getDirection()) && getField(pawn.step(move.getDirection())) == Marble.EMPTY) {
                last_empty = true;
            }
            if (pawn.step(move.getDirection()) == null) {
                last_out_of_bounds = true;
            }

            if (enemies.size() > 0) {
                if (move.getGroup().getMarbles().size() + friendlies.size() > enemies.size()
                        && (last_empty || last_out_of_bounds)) {
                    // Successful push

                    // Move enemy marbles
                    for (Coordinate enemy : enemies) {
                        Marble color = getField(enemy);
                        if (enemy.step(move.getDirection()) == null) {
                            move.getTeam().addPoints(1);
                        } else {
                            setField(enemy.step(move.getDirection()), getField(enemy));

                        }
                    }

                    // Move friendly marbles
                    for (Coordinate friendly : friendlies) {
                        setField(friendly.step(move.getDirection()), getField(friendly));
                    }

                    // Move player marbles
                    Marble color = getField(move.getGroup().getMarble1());
                    Group move_group_dest = move.getGroup().step(move.getDirection());

                    for (Coordinate coord : move.getGroup().getMarbles()) {
                        setField(coord, Marble.EMPTY);
                    }
                    for (Coordinate coord : move_group_dest.getMarbles()) {
                        setField(coord, color);
                    }
                    moveCounter++;

                } else {
                    return false;
                }
            } else {
                // Case: No enemies
                if (friendlies.size() > 0) {
                    // Case: Pushing friendlies

                    if (last_out_of_bounds) {
                        return false;
                    } else if (last_empty) {
                        // Move friendly marbles
                        for (Coordinate friendly : friendlies) {
                            setField(friendly.step(move.getDirection()), getField(friendly));
                        }

                        // Move player marbles
                        Group move_group_dest = move.getGroup().step(move.getDirection());
                        for (Coordinate coord : move.getGroup().getMarbles()) {
                            setField(coord, Marble.EMPTY);
                        }
                        for (Coordinate coord : move_group_dest.getMarbles()) {
                            setField(coord, getField(move.getGroup().getMarble1()));
                        }
                        moveCounter++;
                    } else {
                        throw new IllegalStateException("wa da fuq????");
                    }
                } else {
                    // Case: Normal move
                    Marble color = getField(move.getGroup().getMarble1());

                    for (Coordinate original_marble : move.getGroup().getMarbles()) {
                        setField(original_marble, Marble.EMPTY);
                    }

                    for (Coordinate destination : move.getGroup().step(move.getDirection()).getMarbles()) {
                        setField(destination, color);

                        moveCounter++;
                    }
                }
            }
        } else {
            // Marbles cannot push in this direction
            return makeMoveSlide(move);
        }

        return false;
    }

    private boolean makeMoveSlide(Move move) {
        Group marbles = move.getGroup();
        Group destination = marbles.step(move.getDirection());

        // Check if destinations are empty (if valid move)
        for (Coordinate dest : destination.getMarbles()) {
            if (!isEmpty(dest)) {
                return false;
            }
        }

        // Set the destination fields
        for (Coordinate dest : destination.getMarbles()) {
            setField(dest, getField(marbles.getMarble1()));
        }

        // Reset the original fields
        for (Coordinate orig : marbles.getMarbles()) {
            setField(orig, Marble.EMPTY);
        }
        moveCounter++;

        return true;
    }

    private boolean isNeighbour(Coordinate coord1, Coordinate coord2) {
        if (Math.abs(coord1.x - coord2.x) == 1) {
            int yDiff = Math.abs(coord1.y - coord2.y);
            return yDiff == 0 || yDiff == 1;
        }

        if (Math.abs(coord1.x - coord2.x) == 0) {
            return Math.abs(coord1.y - coord2.y) == 1;
        }

        return false;
    }

    public boolean hasWinner() {
        for (Team team : teams) {
            if (team.getPoints() < 7) {
                return true;
            }
        }
        return false;
    }

    public boolean isWinner(Marble marble) {
        if (hasWinner()) {
            for (Team team : teams) {
                return team.teamHas(marble);
            }
        }
        return false;
    }
    public boolean gameOver() {
        if
    }

    public boolean gameOver() {
        return hasWinner() || (moveCounter < 97);
    }
}


