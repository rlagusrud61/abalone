

public class Board {

    public static void main(String[] args) {
        System.out.println("Hello");
        Board board = new Board();
        System.out.println(board.getField(11));
        System.out.println(board.getField(40));
    }

    private int blackcount;
    private int whitecount;
    private Marble[] fields;


    public Board() {

        fields = new Marble[61];
        reset2P();
    }

    private int[] size = new int[]{5, 6, 7, 8, 9, 8, 7, 6, 5};


    public int convertToInt(int[] cord) {
        int result = 0;
        for (int i = 0; i < cord[0]; i++) {
            result += size[i];
        }
        result += cord[2] + 1;
        return result;
    }


    public int[] convertToRowCol(int a) {
        int col = a;
        int row = 0;
        for (int i = 0; i < 9; i++) {
            if (col > size[i]) {
                col = col - size[i];
                row = i;
            }
        }
        int[] result = new int[]{row, col};
        return result;
    }

    public boolean isValidMoveOne(int i) {
        if (fields[i] == Marble.EMPTY) {
            return true;
        } else
            return false;
    }

    public void reset2P() {
        for (int i = 0; i < fields.length; i++) {
            fields[i] = Marble.EMPTY;
        }
        for (int i = 0; i < 12; i++) {
            fields[i] = Marble.BLACK;
        }
        for (int i = 14; i < 17; i++) {
            fields[i] = Marble.BLACK;
        }
        for (int i = 51; i < fields.length; i++) {
            fields[i] = Marble.WHITE;
        }
        for (int i = 46; i < 48; i++) {
            fields[i] = Marble.WHITE;
        }
    }

    public boolean isField(int index) {

        if (0 <= index && index < 62) {
            return true;
        }
        return false;
    }

    /**
     * Returns true of the (row,col) pair refers to a valid field on the board.
     *
     * @return true if 0 <= row < DIM && 0 <= col < DIM
     * @ensures true when both row and col are within the board's bounds
     */
    public boolean isField(int[] input) {
        int check = convertToInt(input);
        if (isField(check)) {
            return true;
        }
        return false;
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
     * @param cord the coordinates of the field
     * @return the mark on the field
     * @requires (row, col) to be a valid field
     * @ensures the result to be either EMPTY, XX or OO
     */
    public Marble getField(int cord[]) {
        if (isField(cord)) {
            return fields[convertToInt(cord)];
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
        if (getField(i) == Marble.EMPTY) {
            return true;
        }

        return false;
    }

    /**
     * Returns true if the field referred to by the (row,col) pair it empty.
     *
     * @param cord the coordinates of the field
     * @return true if the field is empty
     * @requires (row, col) to be a valid field
     * @ensures true when the Mark at (row, col) is EMPTY
     */
    public boolean isEmptyField(int[] cord) {
        if (getField(cord) == Marble.EMPTY) {
            return true;
        }

        return false;
    }


    /**
     * Returns true if the game is over. The game is over when there is a winner
     * or the whole board is full.
     *
     * @return true if the game is over
     * @ensures true if the board is full or when there is a winner
     */
    public boolean gameOver() {
        if (hasWinner()) {
            return true;
        }

        return false;
    }

    public boolean isWinner(Marble m) {
        if (m == Marble.BLACK && blackcount == 6) {
            return true;
        } else if (m == Marble.WHITE && whitecount == 6) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Returns true if the game has a winner. This is the case when one of the
     * marks controls at least one row, column or diagonal.
     *
     * @return true if the student has a winner.
     * @ensures true when either XX or OO has won
     */
    public boolean hasWinner() {
        if (isWinner(Marble.WHITE) || isWinner(Marble.BLACK)) {
            return true;

        }
        return false;
    }
    /**
     * Sets the content of field i to the mark m.
     * @requires i to be a valid field
     * @ensures field i to be set to Mark m
     * @param i the field number (see NUMBERING)
     * @param m the mark to be placed
     */
    public void setField(int i, Marble m) {

        fields[i] = m;


    }

    /**
     * Sets the content of the field represented by the (row,col) pair to the
     * mark m.
     * @requires (cord) to be a valid field
     * @ensures field (cord) to be set to Mark m
     * @param cord coordinates of the field to be set
     * @param m the mark to be placed
     */
    public void setField(int cord[], Marble m) {
        fields[convertToInt(cord)] = m;


    }
}


