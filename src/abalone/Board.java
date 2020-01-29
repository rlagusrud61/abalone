package abalone;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public static final int[] ROW_SIZES = new int[]{5, 6, 7, 8, 9, 8, 7, 6, 5};

    private Marble[] fields;
    public List<Team> playingTeams = new ArrayList<>(); // The teams that are currently playing in this board
    private int moveCounter;


    public Board() {
        fields = new Marble[61];
    }

    /**
     * Constructor of the board. Makes a copy of a given board.
     *
     * @param board board to be copied.
     */
    public Board(Board board) {
        this.fields = new Marble[board.fields.length];
        System.arraycopy(board.fields, 0, this.fields, 0, board.fields.length);
        this.moveCounter = board.moveCounter;
        this.playingTeams = board.playingTeams;
    }

    private static final String[] NUMBERING = {
            "        // ┃ 00 ┃ 01 ┃ 02 ┃ 03 ┃ 04 ┃ \\\\",
            "      // ┃ 05 ┃ 06 ┃ 07 ┃ 08 ┃ 09 ┃ 10 ┃ \\\\",
            "   // ┃ 11 ┃ 12 ┃ 13 ┃ 14 ┃ 15 ┃ 16 ┃ 17 ┃ \\\\",
            " // ┃ 18 ┃ 19 ┃ 20 ┃ 21 ┃ 22 ┃ 23 ┃ 24 ┃ 25 ┃ \\\\",
            "┃┃  26 ┃ 27 ┃ 28 ┃ 29 ┃ 30 ┃ 31 ┃ 32 ┃ 33 ┃ 34  ┃┃",
            "\\\\  ┃ 35 ┃ 36 ┃ 37 ┃ 38 ┃ 39 ┃ 40 ┃ 41 ┃ 42 ┃ //",
            "   \\\\  ┃ 43 ┃ 44 ┃ 45 ┃ 46 ┃ 47 ┃ 48 ┃ 49 ┃ //",
            "     \\\\  ┃ 50 ┃ 51 ┃ 52 ┃ 53 ┃ 54 ┃ 55 ┃ //",
            "       \\\\  ┃ 56 ┃ 57 ┃ 58 ┃ 59 ┃ 60 ┃ //"
    };

    /**
     * Prints the indices of the board on the console.
     *
     * @invariant NUMBERING.length == 9
     */
    public static void printBoard() {
        for (int i = 0; i < 9; i++) {
            System.out.println(NUMBERING[i]);
        }
    }

    /**
     * Converts a coordinate (row, column) into a int index.
     *
     * @param coord Coordinate to be converted to index
     * @return index form of the coordinate
     * @requires cord != null;
     */

    public static int convertToInt(Coordinate coord) {
        int result = 0;
        for (int i = 0; i < coord.yyCord; i++) {
            result += ROW_SIZES[i];
        }
        result += coord.xxCord;
        return result;
    }

    /**
     * Converts a given index to Coordinate form (x,y).
     *
     * @param index index to be converted to Coordinate
     * @return Coordinate form of the index (x,y)
     * @throws IndexOutOfBoundsException when index is out of bounds.
     * @requires index >= 0 && index < 61
     */
    public static Coordinate convertToCoordinate(int index) {
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

    public void addTeamToBoard(Team team) {
        playingTeams.add(team);
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

    /**
     * Returns the content of the field referred to by the (row,col) pair.
     *
     * @param coord the coordinates of the field
     * @return the mark on the field
     * @requires (row, col) to be a valid field
     * @ensures the result to be either EMPTY, WHITE, BLACK, or RED
     */
    public Marble getField(Coordinate coord) {
        if (isField(coord)) {
            return fields[convertToInt(coord)];
        }
        return null;
    }

    public boolean isField(int index) {
        return 0 <= index && index < 62;
    }

    private boolean isField(Coordinate coord) {
        return coord.yyCord >= 0 && coord.yyCord < ROW_SIZES.length
                && coord.xxCord < ROW_SIZES[coord.yyCord] && coord.xxCord >= 0;
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

    /**
     * Prints every row of the board.
     *
     * @param rowindex index of the row
     * @return String value of every row of the board.
     * @invariant ROW_SIZES.length == 9
     * @requires rowindex > 0
     */
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

    /**
     * First checks if the move in the argument is valid, and if it is, makes the move and returns true.
     * If the movement is invalid, it tries to make a sliding move (side-step) move and returns false fo
     *
     * @param move The move that the user wants to make
     * @return true if the move was successful , false if the move was unsuccessful
     */
    public boolean makeMove(Move move) {
        // Check if the marbles are in line ( if group size is 1, then it's always true)
        if (move.getGroup().size == 1 || move.getGroup().getLineDirection().isParallelTo(move.getDirection())) {
            Coordinate pawn = move.getGroup().getMarble1();

            // Make a pawn and walk to the Coordinate closest in the move direction , within the group
            Coordinate step = pawn.step(move.getDirection());
            if (step == null) {
                return false;
            } else {
                while (move.getGroup().isMarbleInGroup(pawn.step(move.getDirection()))) {
                    pawn = pawn.step(move.getDirection());
                }
            }

            // Calculate friendlies' push strength (own marbles + friendly marbles)
            List<Coordinate> friendlies = new ArrayList<>();
            while (pawn.isValidStep(move.getDirection()) && move.getTeam()
                    .teamHas(getField(pawn.step(move.getDirection())))) {
                pawn = pawn.step(move.getDirection());
                friendlies.add(pawn);

                if (getField(pawn).equals(getField(move.getGroup().getMarble1()))) {
                    return false;
                }
            }

            // Calculate enemy strength
            List<Coordinate> enemies = new ArrayList<>();
            while (pawn.isValidStep(move.getDirection()) && !isEmpty(pawn.step(move.getDirection()))
                    && !move.getTeam().teamHas(getField(pawn.step(move.getDirection())))) {
                pawn = pawn.step(move.getDirection());
                enemies.add(pawn);
            }


            boolean lastCoordEmpty = false;
            boolean lastCoordOutOfBounds = false;
            //if the last coordinate the pawn moves to is empty, then return lastCoordEmpty to true
            if (pawn.isValidStep(move.getDirection()) && getField(pawn.step(move.getDirection())) == Marble.EMPTY) {
                lastCoordEmpty = true;
            }
            //if the last coordinate the pawn moves to is null (out of bounds),
            // then return lastCoordOutOfBounds to true.
            if (pawn.step(move.getDirection()) == null) {
                lastCoordOutOfBounds = true;
            }

            // If friendly size > enemy size
            if (enemies.size() > 0) {
                if (move.getGroup().getMarbles().size() + friendlies.size() > enemies.size()
                        && (lastCoordEmpty || lastCoordOutOfBounds)) {
                    // Successful push

                    // Move enemy marbles first
                    for (Coordinate enemy : enemies) {
                        Marble color = getField(enemy);
                        if (enemy.step(move.getDirection()) == null) {
                            move.getTeam().addPoint();
                        } else {
                            setField(enemy.step(move.getDirection()), getField(enemy));

                        }
                    }

                    // Move friendly marbles second
                    for (Coordinate friendly : friendlies) {
                        setField(friendly.step(move.getDirection()), getField(friendly));
                    }

                    // And lastly move marbles of the player
                    Marble color = getField(move.getGroup().getMarble1());
                    Group moveGroupDestination = move.getGroup().step(move.getDirection());

                    for (Coordinate coord : move.getGroup().getMarbles()) {
                        setField(coord, Marble.EMPTY);
                    }
                    for (Coordinate coord : moveGroupDestination.getMarbles()) {
                        setField(coord, color);
                    }
                    moveCounter++;

                } else {
                    return false;
                }
            } else {
                // Case: No enemies
                if (friendlies.size() > 0) {
//                    if (friendlies.stream().allMatch(f -> getField(f)
//                    .equals(getField(move.getGroup().getMarble1())))){
//                    return false;


//                  Case: Pushing friendlies
                    if (lastCoordOutOfBounds) {
                        return false;
                    } else if (lastCoordEmpty) {
                        // Move friendly marbles
                        for (Coordinate friendly : friendlies) {
                            setField(friendly.step(move.getDirection()), getField(friendly));
                        }

                        // Move player marbles
                        Group moveGroupDestination = move.getGroup().step(move.getDirection());
                        for (Coordinate coord : move.getGroup().getMarbles()) {
                            setField(coord, Marble.EMPTY);
                        }
                        for (Coordinate coord : moveGroupDestination.getMarbles()) {
                            setField(coord, getField(move.getGroup().getMarble1()));
                        }
                        moveCounter++;
                    } else {
                        throw new IllegalStateException("wa da fuq????");
                    }
                } else {
                    // Case: Normal move
                    Marble color = getField(move.getGroup().getMarble1());

                    for (Coordinate originalMarble : move.getGroup().getMarbles()) {
                        setField(originalMarble, Marble.EMPTY);
                    }

                    for (Coordinate destination : move.getGroup().step(move.getDirection()).getMarbles()) {
                        setField(destination, color);

                    }
                    moveCounter++;
                }
            }
        } else {
            // Marbles cannot push in this direction, hence side-step move
            return makeMoveSlide(move);
        }

        return true;
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
        if (Math.abs(coord1.xxCord - coord2.xxCord) == 1) {
            int diffY = Math.abs(coord1.yyCord - coord2.yyCord);
            return diffY == 0 || diffY == 1;
        }

        if (Math.abs(coord1.xxCord - coord2.xxCord) == 0) {
            return Math.abs(coord1.yyCord - coord2.yyCord) == 1;
        }

        return false;
    }

    /**
     * Checks if the board has a winner.
     *
     * @return true if there is a team with 6 points.
     * @requires playingTeams != null;
     */
    public boolean hasWinner() {
        for (Team team : playingTeams) {
            if (team.getPoints() > 5) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given marble color is the winner of the board.
     *
     * @param marble color of the marble
     * @return true if there is a winner in the board, and if
     * @requires marble != null
     */
    public boolean isWinner(Marble marble) {
        if (hasWinner()) {
            for (Team team : playingTeams) {
                if (team.getPoints() > 5) {
                    return team.teamHas(marble);
                }
            }
        }
        return false;
    }

    /**
     * Checks if the game is over in the board.
     *
     * @return true if there is a winner in the board or when the number of moves exceed 96.
     */
    public boolean gameOver() {
        return hasWinner() || (moveCounter > 97);
    }

    /**
     * Checks if the group of marbles that the user chose are valid.
     *
     * @param group the group of marbles that the user chose.
     * @return true if (group.getMarbles.size() == 1 || (group.size() == 2 && marble1 and marble2 are neighbouring) ||
    (group.size() == 3 && marbles 1,2,3 are in a line)) else, return false.
     * @ensures group.getMarbles().size >= 1
     * @requires group != null && group.getMarbles().size > 1
     */
    public boolean isValidSelection(Group group) {
        if (group.getMarbles().size() == 1) {
            return true;
        } else if (group.getMarbles().size() == 2) {
            return isNeighbour(group.getMarble1(), group.getMarble2());
        } else if (group.getMarbles().size() == 3) {
            int neighbours = 0;

            if (isNeighbour(group.getMarble1(), group.getMarble2())) {
                neighbours++;
            }
            if (isNeighbour(group.getMarble1(), group.getMarble3())) {
                neighbours++;
            }
            if (isNeighbour(group.getMarble2(), group.getMarble3())) {
                neighbours++;
            }

            return neighbours == 2 && group.isInLine();
        }
        return false;
    }
}


