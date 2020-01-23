package abalone;

public class Board {
    private int deadBlackCount;
    private int deadWhiteCount;
    private int[] rowSizes = new int[]{5, 6, 7, 8, 9, 8, 7, 6, 5};
    private int[] redMarble4 = new int[]{1, 2, 3, 4, 7, 8, 9, 14, 15};
    private int[] blackMarble4 = new int[]{11, 17, 18, 24, 25, 26, 33, 34, 35};
    private int[] blueMarble4 = new int[]{47, 48, 53, 54, 55, 58, 59, 60, 61};
    private int[] whiteMarble4 = new int[]{27, 28, 29, 36, 37, 38, 44, 45, 51};
    private Marble[] fields;


    public Board() {

        fields = new Marble[61];
        //reset2P();
    }

    public static void main(String[] args) {
        System.out.println("Hello");
        Board board = new Board();
        board.reset4P();
        System.out.println(board.toString());
    }

    /**
     * Converts a coordinate (row, column) into a int index.
     *
     * @param cord
     * @return
     * @requires cord != null;
     */
    public int convertToInt(int[] cord) {
        int result = 0;
        for (int i = 0; i < cord[0]; i++) {
            result += rowSizes[i];
        }
        result += cord[1];
        return result;
    }


    public int[] convertToRowCol(int a) {

        int col = a;
        int row = 0;
        for (int i = 0; i < 9; i++) {
            if (col > rowSizes[i]) {
                col = col - rowSizes[i];
                row = i;
            }
        }
        row = row + 1;
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
        for (int i = 0; i < fields.length - 1; i++) {
            fields[i] = Marble.EMPTY;
        }
        for (int i = 0; i < 11; i++) {
            fields[i] = Marble.BLACK;
        }
        for (int i = 13; i < 16; i++) {
            fields[i] = Marble.BLACK;
        }
        for (int i = 50; i < fields.length; i++) {
            fields[i] = Marble.WHITE;
        }
        for (int i = 45; i < 48; i++) {
            fields[i] = Marble.WHITE;
        }
    }

    public void reset3P() {
        //Set all fields to empty
        for (int i = 0; i < fields.length; i++) {
            fields[i] = Marble.EMPTY;
        }

        //Set fields to blue
        for (int i = 50; i < fields.length; i++) {
            fields[i] = Marble.BLUE;
        }

        //Set fields to White and Black
        int index = 0;
        for (int i = 0; i < 5; i++) {
            int rowSize = rowSizes[i];

            setField(index, Marble.WHITE);
            setField(index + 1, Marble.WHITE);
            setField(index + rowSize - 2, Marble.BLACK);
            setField(index + rowSize - 1, Marble.BLACK);
            index += rowSize;
        }
        setField(35, Marble.WHITE);
        setField(42, Marble.BLACK);
    }

    public void reset4P() {
        for (int i = 0; i < fields.length; i++) {
            fields[i] = Marble.EMPTY;
        }
        for (int i : redMarble4) {
            fields[i - 1] = Marble.RED;
        }
        for (int i : blackMarble4) {
            fields[i - 1] = Marble.BLACK;
        }
        for (int i : blueMarble4) {
            fields[i - 1] = Marble.BLUE;
        }
        for (int i : whiteMarble4) {
            fields[i - 1] = Marble.WHITE;
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

        if (m == Marble.BLACK && deadBlackCount == 6) {
            return true;
        } else if (m == Marble.WHITE && deadWhiteCount == 6) {
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
     * Sets the content of the field represented by the (row,col) pair to the
     * mark m.
     *
     * @param cord coordinates of the field to be set
     * @param m    the mark to be placed
     * @requires (cord) to be a valid field
     * @ensures field (cord) to be set to Mark m
     */
    public void setField(int[] cord, Marble m) {
        fields[convertToInt(cord)] = m;


    }

    /**
     * Returns a String representation of this board. In addition to the current
     * situation, the String also shows the numbering of the fields.
     *
     * @return the game situation as String
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < rowSizes.length; i++) {
            String row = rowToString(i);
            s = s + row +"\n";
        }
        return s;
    }

    public  String rowToString(int rowindex) {
        String s = "";
        int row = rowindex;
        int col = 0;
        int[] cord = new int[] {row,col};
        for(int i = 10; i > rowSizes[row]; i--) {
            s = "   " + s;
        }
        for (int i = 0; i < rowSizes[row]; i++) {
            cord[1]= i;
            s = s + getField(cord) + " ┃   ";
        }
        return s;
    }


}


