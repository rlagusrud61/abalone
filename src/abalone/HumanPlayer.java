package abalone;

import utils.TextIO;

public class HumanPlayer extends Player {


    Board board = new Board();
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
            int marble1 = 62;
            int marble2 = 62;
            int marble3 = 62;
            int index = 0;
            int[] result = new int[commands.length];
            int[] intcommands = new int[commands.length];

            valid = board.isField(intcommands[1]) && (board.getField(intcommands[1]).equals(getMarble()));
            for (int i = 0; i < commands.length; i++) {
                intcommands[i] = Integer.parseInt(commands[i]);
            }
            for (int i = 1; i < intcommands.length; i++) {
                marble1 = intcommands[i];

                if (commands.length < 3) {
                    if (i > 1) {
                        marble1 = intcommands[i];
                    }

                    valid = board.isField(marble1)
                            && board.getField(marble1).equals(getMarble());

                }

                if (commands.length == 3) {
                    if (i + 1 > 2) {
                        marble2 = intcommands[(i + 1) % 2];
                    } else {
                        marble2 = intcommands[i + 1];
                    }
                    valid = board.isField(marble1)
                            && board.isField(marble2)
                            && board.getField(marble2).equals(getMarble())
                            && isNeighbour(intcommands[1], intcommands[2]);

                }

                if (commands.length > 3) {
                    if (i + 2 > 3) {
                        marble3 = intcommands[(i + 2) % 3];
                    } else {
                        marble3 = intcommands[i + 2];
                    }
                    if (i + 1 > 3) {
                        marble2 = intcommands[(i + 1) % 3];
                    } else {
                        marble2 = intcommands[i + 1];
                    }

                    valid = board.isField(marble1)
                            && board.isField(marble2)
                            && board.isField(marble3)
                            && board.getField(marble3).equals(getMarble())
                            && isNeighbour(intcommands[1], intcommands[2])
                            && isNeighbour(intcommands[2], intcommands[3]);
                    System.out.println(isInLine(marble1, marble2, marble3));


                }


                int[] rowcol = board.convertToRowCol(marble1);
                int[] rowcoltest = rowcol;

                switch (intcommands[0]) {
                    case 0:

                        if (rowcol[0] > 4) {
                            rowcoltest[0] = rowcoltest[0] - 1;
                            rowcoltest[1] = rowcoltest[1] + 1;
                        } else {
                            rowcoltest[0] = rowcoltest[0] - 1;

                        }
                        if (board.isEmptyField(rowcoltest)
                                || board.convertToInt(rowcoltest) == marble2
                                || board.convertToInt(rowcoltest) == marble3) {

                            result[i - 1] = board.convertToInt(rowcoltest);
                        } else {
                            valid = false;
                        }

                        break;
                    case 1:
                        rowcoltest[1] = rowcoltest[1] + 1;
                        if (board.isEmptyField(rowcoltest)) {
                            result[i - 1] = board.convertToInt(rowcoltest);
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

                            result[i - 1] = board.convertToInt(rowcoltest);
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

                            result[i - 1] = board.convertToInt(rowcoltest);

                        } else {
                            valid = false;
                        }
                        break;
                    case 4:
                        rowcoltest[1] = rowcoltest[1] - 1;
                        if (board.isEmptyField(rowcoltest)) {
                            result[i - 1] = board.convertToInt(rowcoltest);
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

                            result[i - 1] = board.convertToInt(rowcoltest);
                        } else {
                            valid = false;
                        }

                        break;

                    default:
                        break;

                }
            }
            if (valid) {

                if (commands.length == 2) {
                    board.setField(marble1, Marble.EMPTY);
                }

                if (commands.length == 3) {
                    board.setField(marble1, Marble.EMPTY);
                    board.setField(marble2, Marble.EMPTY);
                }
                if (commands.length == 4) {
                    System.out.println(marble1);
                    System.out.println(marble2);
                    System.out.println(marble3);

                    board.setField(marble1, Marble.EMPTY);
                    board.setField(marble2, Marble.EMPTY);
                    board.setField(marble3, Marble.EMPTY);
                }
                return result;
            } else {
                System.out.println("ERROR: field " + input
                        + " is no valid choice.");
                System.out.println(prompt);
                input = TextIO.getlnString();
            }


        }

    }

    /**
     * Checks if two marbles are neighbours to each other.
     *
     * @param marble1 first marble
     * @param marble2 second marble
     * @return true if the two marbles are together, false if they are apart.
     */
    public boolean isNeighbour(int marble1, int marble2) {
        int[] rowcolA = board.convertToRowCol(marble1);
        int[] rowcolB = board.convertToRowCol(marble2);
        int[] rowcoltest = rowcolA;
        if (Math.abs(marble1 - marble2) == 1) {
            return true;
        } else {
            rowcoltest[0] = rowcolA[0] - 1;
            if (board.convertToInt(rowcoltest) == board.convertToInt(rowcolB)) {
                return true;
            }
            rowcoltest[0] = rowcolA[0] + 1;
            if (board.convertToInt(rowcoltest) == board.convertToInt(rowcolB)) {
                return true;
            }
            if (rowcolA[0] < 4) {
                rowcoltest[0] = rowcolA[0] - 1;
                rowcoltest[1] = rowcolA[1] - 1;
                if (board.convertToInt(rowcoltest) == board.convertToInt(rowcolB)) {
                    return true;
                }
                rowcoltest[0] = rowcolA[0] + 1;
                rowcoltest[1] = rowcolA[1] + 1;
                if (board.convertToInt(rowcoltest) == board.convertToInt(rowcolB)) {
                    return true;
                }
            } else if (rowcolA[0] > 4) {
                rowcoltest[0] = rowcolA[0] - 1;
                rowcoltest[1] = rowcolA[1] + 1;
                if (board.convertToInt(rowcoltest) == board.convertToInt(rowcolB)) {
                    return true;
                }

                rowcoltest[0] = rowcolA[0] + 1;
                rowcoltest[1] = rowcolA[1] - 1;
                if (board.convertToInt(rowcoltest) == board.convertToInt(rowcolB)) {
                    return true;
                }
            } else {
                rowcoltest[0] = rowcolA[0] + 1;
                rowcoltest[1] = rowcolA[1] - 1;
                if (board.convertToInt(rowcoltest) == board.convertToInt(rowcolB)) {
                    return true;
                }
                rowcoltest[0] = rowcolA[0] - 1;
                rowcoltest[1] = rowcolA[1] - 1;
                if (board.convertToInt(rowcoltest) == board.convertToInt(rowcolB)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if marbles 1,2,3 are in a straight line.
     *
     * @param marble1 first marble
     * @param marble2 second marble
     * @param marble3 third marble
     * @return true if marbles 1,2,3 are in line, false if they are not.
     */

    public boolean isInLine(int marble1, int marble2, int marble3) {
        int difference12 = Math.abs(marble1 - marble2);
        int difference23 = Math.abs(marble2 - marble3);
        if (difference12 == 5 && difference23 == 6) {
            return true;
        } else if (difference12 == 6 && difference23 == 7) {
            return true;
        } else if (difference12 == 7 && difference23 == 8) {
            return true;
        } else if (difference12 == 8 && difference23 == 9) {
            return true;
        }
        return false;
    }
}

