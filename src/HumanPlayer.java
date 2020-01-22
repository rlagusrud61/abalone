import utils.TextIO;

public class HumanPlayer extends Player {

    // -- Constructors -----------------------------------------------

    /**
     * Creates a new human player object.
     *
     * @requires name is not null
     * @requires mark is either XX or OO
     * @ensures the Name of this player will be name
     * @ensures the Mark of this player will be mark
     */
    public HumanPlayer(String name, Marble marble) {
        super(name, marble);
    }

    // -- Commands ---------------------------------------------------

    /**
     * Asks the user to input the field where to place the next mark. This is
     * done using the standard input/output.
     *
     * @param board the game board
     * @return the player's chosen field
     * @requires board is not null
     * @ensures the returned in is a valid field index and that field is empty
     */
    public int[] determineMove(Board board) {
        boolean valid = true;

            String prompt = "> " + getName() + " (" + getMarble().toString() + ")"
                    + ", what is your choice? ";

            System.out.println(prompt);
            String input = TextIO.getlnString();
        while (true) {
            String[] commands = input.split(";");
            int marble1 = 0;
            int marble2 = 0;
            int marble3 = 0;
            int index = 0;
            int[] result = new int[commands.length];
            int[] intcommands = new int[commands.length];

            valid = board.isField(intcommands[1]) && (board.getField(intcommands[1]).equals(getMarble()));
            for (int i = 0; i < commands.length; i++) {
                intcommands[i] = Integer.parseInt(commands[i]);
            }
            for (int i = 1; i < intcommands.length; i++) {
                marble1 = intcommands[i];
                board.setField(marble1, Marble.EMPTY);
                if (commands.length > 2) {
                    if (i + 1 > 3) {
                        marble2 = intcommands[(i + 1) & 3];
                    } else {
                        marble2 = intcommands[i + 1];
                    }
                    board.setField(marble2, Marble.EMPTY);
                    valid = board.isField(marble1) && board.isField(marble2) && board.getField(marble1).equals(getMarble()) && board.getField(marble2).equals(getMarble());
                }
                if (commands.length > 3) {
                    if (i + 2 > 3) {
                        marble3 = intcommands[(i + 2) % 3];
                    } else {
                        marble3 = intcommands[i + 2];
                    }
                    board.setField(marble3, Marble.EMPTY);
                    valid = board.isField(marble1) && board.isField(marble2) && board.isField(marble3)&& board.getField(marble1).equals(getMarble()) && board.getField(marble2).equals(getMarble()) && board.getField(marble3).equals(getMarble());


                }

                int[] rowcol = board.convertToRowCol(marble1);
                System.out.println(rowcol[0]);
                System.out.println(rowcol[1]);
                int[] rowcoltest = rowcol;

                    switch (intcommands[0]) {
                        case 0:

                            if (rowcol[0] > 4) {
                                rowcoltest[0] = rowcoltest[0] - 1;
                                rowcoltest[1] = rowcoltest[1] + 1;
                            } else {
                                rowcoltest[0] = rowcoltest[0] - 1;

                            }
                            if (board.isEmptyField(rowcoltest) || board.convertToInt(rowcoltest) == marble2 || board.convertToInt(rowcoltest) == marble3) {

                                result[i-1] = board.convertToInt(rowcoltest);
                            } else {
                                valid = false;
                            }

                            break;
                        case 1:
                            rowcoltest[1] = rowcoltest[1] + 1;
                            if (board.isEmptyField(rowcoltest)) {
                                result[i-1] = board.convertToInt(rowcoltest);
                            } else {
                                valid = false;
                            }
                            break;
                        case 2:
                            if (rowcol[0] < 4) {
                                rowcoltest[0] = rowcoltest[0] + 1;
                                rowcoltest[1] = rowcoltest[1] + 1;
                            } else {
                                rowcoltest[0] = rowcoltest[0] + 1;

                            }
                            if (board.isEmptyField(rowcoltest)) {

                                result[i-1] = board.convertToInt(rowcoltest);
                                ;
                            } else {
                                valid = false;
                            }
                            break;
                        case 3:
                            if (rowcol[0] > 4) {
                                rowcoltest[0] = rowcoltest[0] + 1;
                                rowcoltest[1] = rowcoltest[1] - 1;
                            } else {
                                rowcoltest[0] = rowcoltest[0] + 1;

                            }
                            if (board.isEmptyField(rowcoltest)) {
                                System.out.println(board.convertToInt(rowcoltest));
                                result[i-1] = board.convertToInt(rowcoltest);

                            } else {
                                valid = false;
                            }
                            break;
                        case 4:
                            rowcoltest[1] = rowcoltest[1] - 1;
                            if (board.isEmptyField(rowcoltest)) {
                                result[i-1] = board.convertToInt(rowcoltest);
                            } else {
                                valid = false;
                            }
                            break;

                        case 5:
                            if (rowcol[0] < 4) {
                                rowcoltest[0] = rowcoltest[0] - 1;
                                rowcoltest[1] = rowcoltest[1] - 1;
                            } else {
                                rowcoltest[0] = rowcoltest[0] - 1;

                            }
                            if (board.isEmptyField(rowcoltest)) {

                                result[i-1] = board.convertToInt(rowcoltest);
                            } else {
                                valid = false;
                            }

                            break;

                    }
                    if (valid) {
                        return result;
                    } else {
                        System.out.println("ERROR: field " + input
                                + " is no valid choice.");
                        System.out.println(prompt);
                        input = TextIO.getlnString();
                    }


            }
        }

    }
}

