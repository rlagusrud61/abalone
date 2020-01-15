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
    public int determineMove(Board board) {
        String prompt = "> " + getName() + " (" + getMarble().toString() + ")"
                + ", what is your choice? ";

        System.out.println(prompt);
        String input = TextIO.getlnString();
        String[] commands = input.split(";");
        int[] intcommands = new int[commands.length];
        for (int i = 0; i < commands.length; i++) {
            intcommands[i] = Integer.parseInt(commands[i]);
        }
        int[] rowcol = board.convertToRowCol(intcommands[1]);
        int[] rowcoltest = rowcol;
        switch (intcommands[0]) {
            case 0:

                if (rowcol[0] > 4) {
                    rowcoltest[0] = rowcoltest[0] - 1;
                    rowcoltest[1] = rowcoltest[1] + 1;
                } else {
                    rowcoltest[0] = rowcoltest[0] - 1;

                }
                if (board.isEmptyField(rowcoltest)) {

                    return board.convertToInt(rowcoltest);
                }

                break;
            case 1:
                rowcoltest[1] = rowcoltest[1] + 1
                        if (board.isEmptyField(rowcoltest)) {
                            return board.convertToInt(rowcoltest);
                        }
        else{
                System.out.println("ERROR: field " + choice
                        + " is no valid choice.");
                System.out.println(prompt);
                choice = TextIO.getInt();
                valid = board.isField(choice) && board.isEmptyField(choice);
            }
            return choice;
        }

    }

