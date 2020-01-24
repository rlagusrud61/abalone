package abalone;

public class Board {

    public static final int[] ROW_SIZES = new int[]{5, 6, 7, 8, 9, 8, 7, 6, 5};

    private Marble[] fields;

    public Board() {
        fields = new Marble[61];
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.reset(BoardStates.getTwoPlayer());

        boolean test = board.isInLine(new Coordinate(3, 3), new Coordinate(4, 5), new Coordinate(4, 5));
        System.out.println(test);

        System.out.println(board);
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

    private Coordinate convertToRowCol(int index) {
        int counter = 0;

        for (int row : ROW_SIZES) {
            for (int col = 0; col < ROW_SIZES[row]; col++) {
                if (counter == index) {
                    return new Coordinate(col, row);
                }

                counter += 1;
            }
        }

        throw new IndexOutOfBoundsException();
    }

    private boolean isFieldEmpty(int i) {
        return fields[i] == Marble.EMPTY;
    }

    private boolean isField(int index) {
        return 0 <= index && index < 62;
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
    public boolean isEmptyField(int i) {
        return getField(i) == Marble.EMPTY;
    }

    /**
     * Sets the content of field i to the mark m.
     *
     * @param i the field number (see NUMBERING)
     * @param m the mark to be placed
     * @requires i to be a valid field
     * @ensures field i to be set to Mark m
     */
    public void setField(int i, Marble m) {
        fields[i] = m;
    }

    /**
     * Returns true if the field referred to by the (row,col) pair it empty.
     *
     * @param coord the coordinates of the field
     * @return true if the field is empty
     * @requires (row, col) to be a valid field
     * @ensures true when the Mark at (row, col) is EMPTY
     */
    public boolean isEmptyField(Coordinate coord) {
        return getField(coord) == Marble.EMPTY;
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
        int row = rowindex;
        int col = 0;

        for (int i = 10; i > ROW_SIZES[row]; i--) {
            s.insert(0, "   ");
        }
        for (int i = 0; i < ROW_SIZES[row]; i++) {
            Coordinate coord = new Coordinate(col, row);
            s.append(getField(coord).draw()).append(" ┃   ");
        }
        return s.toString();
    }

    public void reset(Marble[] state) {
        this.fields = state;
    }

    public void makeMove(Move move) {
        // Check if neighbours
        if (move.getMarble3() != null) {

        }
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

    private boolean isInLine(Coordinate coord1, Coordinate coord2, Coordinate coord3) {
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
                return true;
            }
        }

        return false;
    }

}


