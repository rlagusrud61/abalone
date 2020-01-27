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
    public int[] move(Board board) {
        int [] valid = determineMove(board);
            while ( valid == null) {
                System.out.println("ERROR: no valid choice.");
                valid = determineMove(board);
            }
            return valid;

    }

    public int[] determineMove(Board board) {
        String input = askInput();
        boolean valid = true;
        String[] commands = input.split(";");
        int marble1 = 62;
        int marble2 = 62;
        int marble3 = 62;
        int sumitoMarble = 62;
        boolean inline = false;
        boolean sumito = false;

        int count = 0;
        int[] result = new int[commands.length];
        int[] intcommands = new int[commands.length];
        if (commands.length < 2) {
            return null;
        }
        if (valid) {
            for (int i = 0; i < commands.length; i++) {
                intcommands[i] = Integer.parseInt(commands[i]);
            }
            for (int i = 1; i < intcommands.length; i++) {
                marble1 = intcommands[i];
                valid = board.isField(intcommands[1]) && (board.getField(intcommands[1]).equals(getMarble()));
               if(!valid) {
                   return null;
               }

                if (commands.length == 2) {
                    if (i > 1) {
                        marble1 = intcommands[i];
                    }

                    valid = board.isField(marble1)
                            && board.getField(marble1).equals(getMarble());
                    if(!valid) {
                        return null;
                    }
                }

                if (commands.length == 3) {
                    if (i + 1 > 2) {
                        marble2 = intcommands[(i + 1) % 2];
                    } else {
                        marble2 = intcommands[i + 1];
                    }
                    valid = board.isField(marble1)
                            && board.isField(marble2)
                            && board.getField(marble2).equals(getMarble());
                    //  && isNeighbour(intcommands[1], intcommands[2]);
                    if(!valid) {
                        return null;
                    }
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
                            && isNeighbour(intcommands[1], intcommands[2]);
                    if(!valid) {
                        return null;
                    }

                }
                if (commands.length > 4) {
                    valid = false;
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
                        if (board.isEmptyField(rowcoltest) || board.convertToInt(rowcoltest) == marble2
                                || board.convertToInt(rowcoltest) == marble3) {

                            result[i - 1] = board.convertToInt(rowcoltest);
                            if (board.convertToInt(rowcoltest) == marble2
                                    || board.convertToInt(rowcoltest) == marble3) {
                                inline = true;
                            }
                            if (!board.getField(board.convertToInt(rowcoltest)).equals(getMarble()) && !board.getField(board.convertToInt(rowcoltest)).equals(Marble.EMPTY)) {
                                count++;
                                sumitoMarble = board.convertToInt(rowcoltest);
                            }
                            if (count == 1 && inline) {
                                sumito = true;
                            }

                        } else {
                           return null;
                        }

                        break;
                    case 1:
                        rowcoltest[1] = rowcoltest[1] + 1;

                        if (board.isEmptyField(rowcoltest) || board.convertToInt(rowcoltest) == marble2
                                || board.convertToInt(rowcoltest) == marble3 || !board.getField(board.convertToInt(rowcoltest)).equals(getMarble()) && !board.getField(board.convertToInt(rowcoltest)).equals(Marble.EMPTY)) {

                            result[i - 1] = board.convertToInt(rowcoltest);
                            if (board.convertToInt(rowcoltest) == marble2
                                    || board.convertToInt(rowcoltest) == marble3) {
                                inline = true;
                            }
                            if (!board.getField(board.convertToInt(rowcoltest)).equals(getMarble()) && !board.getField(board.convertToInt(rowcoltest)).equals(Marble.EMPTY)) {
                                count++;
                                sumitoMarble = board.convertToInt(rowcoltest);
                            }
                            if (count == 1 && inline) {
                                sumito = true;
                            }
                        } else {
                            return null;
                        }
                        break;
                    case 2:
                        if (rowcol[0] < 4) {
                            rowcoltest[0] = rowcoltest[0] + 1;
                            rowcoltest[1] = rowcoltest[1] + 1;
                        } else {
                            rowcoltest[0] = rowcoltest[0] + 1;

                        }
                        if (board.isEmptyField(rowcoltest) || board.convertToInt(rowcoltest) == marble2
                                || board.convertToInt(rowcoltest) == marble3 || !board.getField(board.convertToInt(rowcoltest)).equals(getMarble()) && !board.getField(board.convertToInt(rowcoltest)).equals(Marble.EMPTY)) {

                            result[i - 1] = board.convertToInt(rowcoltest);
                            if (board.convertToInt(rowcoltest) == marble2
                                    || board.convertToInt(rowcoltest) == marble3) {
                                inline = true;
                            }
                            if (!board.getField(board.convertToInt(rowcoltest)).equals(getMarble()) && !board.getField(board.convertToInt(rowcoltest)).equals(Marble.EMPTY)) {
                                count++;
                                sumitoMarble = board.convertToInt(rowcoltest);
                            }
                            if (count == 1 && inline) {
                                sumito = true;
                            }
                        } else {
                           return null;
                        }
                        break;
                    case 3:
                        if (rowcol[0] >= 4) {
                            rowcoltest[0] = rowcoltest[0] + 1;
                            rowcoltest[1] = rowcoltest[1] - 1;
                        } else {
                            rowcoltest[0] = rowcoltest[0] + 1;

                        }
                        if (board.isEmptyField(rowcoltest) || board.convertToInt(rowcoltest) == marble2
                                || board.convertToInt(rowcoltest) == marble3 || !board.getField(board.convertToInt(rowcoltest)).equals(getMarble()) && !board.getField(board.convertToInt(rowcoltest)).equals(Marble.EMPTY)) {

                            result[i - 1] = board.convertToInt(rowcoltest);
                            if (board.convertToInt(rowcoltest) == marble2
                                    || board.convertToInt(rowcoltest) == marble3) {
                                inline = true;
                            }
                            if (!board.getField(board.convertToInt(rowcoltest)).equals(getMarble()) && !board.getField(board.convertToInt(rowcoltest)).equals(Marble.EMPTY)) {
                                count++;
                                sumitoMarble = board.convertToInt(rowcoltest);
                            }
                            if (count == 1 && inline) {
                                sumito = true;
                            }

                        } else {
                            return null;
                        }

                        break;
                    case 4:
                        rowcoltest[1] = rowcoltest[1] - 1;
                        if (board.isEmptyField(rowcoltest) || board.convertToInt(rowcoltest) == marble2
                                || board.convertToInt(rowcoltest) == marble3 || !board.getField(board.convertToInt(rowcoltest)).equals(getMarble()) && !board.getField(board.convertToInt(rowcoltest)).equals(Marble.EMPTY)) {

                            result[i - 1] = board.convertToInt(rowcoltest);
                            if (board.convertToInt(rowcoltest) == marble2
                                    || board.convertToInt(rowcoltest) == marble3) {
                                inline = true;
                            }
                            if (!board.getField(board.convertToInt(rowcoltest)).equals(getMarble()) && !board.getField(board.convertToInt(rowcoltest)).equals(Marble.EMPTY)) {
                                count++;
                                sumitoMarble = board.convertToInt(rowcoltest);
                            }
                            if (count == 1 && inline) {
                                sumito = true;
                            }
                        } else {
                            return null;
                        }

                    case 5:
                        if (rowcol[0] <= 4) {
                            rowcoltest[0] = rowcoltest[0] - 1;
                            rowcoltest[1] = rowcoltest[1] - 1;
                        } else {
                            rowcoltest[0] = rowcoltest[0] - 1;

                        }
                        if (board.isEmptyField(rowcoltest) || board.convertToInt(rowcoltest) == marble2
                                || board.convertToInt(rowcoltest) == marble3 || !board.getField(board.convertToInt(rowcoltest)).equals(getMarble()) && !board.getField(board.convertToInt(rowcoltest)).equals(Marble.EMPTY)) {

                            result[i - 1] = board.convertToInt(rowcoltest);
                            if (board.convertToInt(rowcoltest) == marble2
                                    || board.convertToInt(rowcoltest) == marble3) {

                                inline = true;
                            }
                            if (!board.getField(board.convertToInt(rowcoltest)).equals(getMarble()) && !board.getField(board.convertToInt(rowcoltest)).equals(Marble.EMPTY)) {
                                count++;
                                sumitoMarble = board.convertToInt(rowcoltest);
                            }
                            if (count == 1 && inline) {
                                sumito = true;
                            }
                        } else {
                           return null;
                        }
                        break;

                    default:
                        break;

                }
            }
        }
        if (sumito) {
            valid = sumito(board, intcommands[0], sumitoMarble, intcommands.length);
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
        }  else {
            return null;
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


    public boolean sumito(Board board, int direction, int marble, int length) {
        int secondMarble = 62;
        int[] rowcol = board.convertToRowCol(marble);
        int[] rowcoltest = rowcol;

        boolean valid = true;
        int result = 0;


        switch (direction) {
            case 0:

                if (rowcol[0] > 4) {

                    rowcoltest[0] = rowcoltest[0] - 1;
                    rowcoltest[1] = rowcoltest[1] + 1;

                } else {
                    rowcoltest[0] = rowcoltest[0] - 1;

                }
                if (board.isEmptyField(rowcoltest)) {

                    result = board.convertToInt(rowcoltest);
                }
                if (!board.getField(rowcoltest).equals(getMarble()) && !board.getField(rowcoltest).equals(Marble.EMPTY) && length > 3) {
                    result = board.convertToInt(rowcoltest);
                    secondMarble = board.convertToInt(rowcoltest);


                } else {
                    valid = false;
                }


                break;
            case 1:
                rowcoltest[1] = rowcoltest[1] + 1;

                if (board.isEmptyField(rowcoltest)) {

                    result = board.convertToInt(rowcoltest);
                } else if (!board.getField(rowcoltest).equals(getMarble()) && !board.getField(rowcoltest).equals(Marble.EMPTY) && length > 3) {
                    result = board.convertToInt(rowcoltest);
                    secondMarble = board.convertToInt(rowcoltest);


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

                    result = board.convertToInt(rowcoltest);
                }
                if (!board.getField(rowcoltest).equals(getMarble()) && !board.getField(rowcoltest).equals(Marble.EMPTY) && length > 3) {
                    result = board.convertToInt(rowcoltest);
                    secondMarble = board.convertToInt(rowcoltest);


                } else {
                    valid = false;
                }
            case 3:
                if (rowcol[0] > 4) {
                    rowcoltest[0] = rowcoltest[0] + 1;
                    rowcoltest[1] = rowcoltest[1] - 1;
                } else {
                    rowcoltest[0] = rowcoltest[0] + 1;
                }

                if (board.isEmptyField(rowcoltest)) {

                    result = board.convertToInt(rowcoltest);
                }
                if (!board.getField(rowcoltest).equals(getMarble()) && !board.getField(rowcoltest).equals(Marble.EMPTY) && length > 3) {
                    result = board.convertToInt(rowcoltest);
                    secondMarble = board.convertToInt(rowcoltest);


                } else {
                    valid = false;
                }

                break;
            case 4:
                rowcoltest[1] = rowcoltest[1] - 1;

                if (board.isEmptyField(rowcoltest)) {

                    result = board.convertToInt(rowcoltest);
                }
                if (!board.getField(rowcoltest).equals(getMarble()) && !board.getField(rowcoltest).equals(Marble.EMPTY) && length > 3) {
                    result = board.convertToInt(rowcoltest);
                    secondMarble = board.convertToInt(rowcoltest);


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

                    result = board.convertToInt(rowcoltest);
                }

                if (!board.getField(rowcoltest).equals(getMarble()) && !board.getField(rowcoltest).equals(Marble.EMPTY) && length > 3) {
                    result = board.convertToInt(rowcoltest);
                    secondMarble = board.convertToInt(rowcoltest);


                } else {
                    valid = false;
                }


                break;

            default:
                break;

        }
        if (valid) {

            if (secondMarble != 62) {
                if (sumito(board, direction, secondMarble, length)) {
                    board.setField(result, board.getField(marble));
                    return true;
                }
            } else {
                board.setField(result, board.getField(marble));
                return true;
            }

        } else {
            return false;
        }

        return false;
    }

    public String askInput() {

        String prompt = "> " + getName() + " (" + getMarble().toString() + ")"
                + ", what is your choice? ";

        System.out.println(prompt);
        String input = TextIO.getlnString();
        return input;
    }
}

