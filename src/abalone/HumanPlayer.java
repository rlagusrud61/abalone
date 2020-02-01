package abalone;

import utils.TextIO;

import java.util.Arrays;

public class HumanPlayer extends Player {
    /**
     * Creates a new Human Player object.
     *
     * @param name   Name of the human player
     * @param marble Marble color assigned to this player
     * @requires name is not null
     * @requires mark is either WHITE, BLUE, BLACK, or RED
     * @ensures the Name of this player will be name
     * @ensures the abalone.Marble of this player will be marble
     */

    public HumanPlayer(String name, Marble marble) {
        super(name, marble);
    }

    /**
     * The player must give a direction and the marbles selected in the form of
     * "direction;marble1,marble2,marble3". The input is processed and split into different strings,
     * then a new Move is made based on this information.
     *
     * @param board the current game board
     * @return selected move that is to be made and checked on the board.
     */
    @Override
    public Move makeChoice(Board board, String input) {
        boolean isValidArg = true;

        do {

            String text = input;
            int command = -1;
            String marbles;
            Integer[] marbleSplit = null;
            try {
                command = Integer.parseInt(text.split(";")[0]);
                marbles = text.split(";")[1];
                marbleSplit = Arrays.stream(marbles.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
                isValidArg = true;
            } catch (IllegalArgumentException e) {
                isValidArg = false;
                System.out.println("Not a valid argument! Write again");
            }
            if (isValidArg) {
                Move.Direction direction;
                switch (command) {
                    case 0:
                        direction = Move.Direction.NE;
                        break;
                    case 1:
                        direction = Move.Direction.E;
                        break;
                    case 2:
                        direction = Move.Direction.SE;
                        break;
                    case 3:
                        direction = Move.Direction.SW;
                        break;
                    case 4:
                        direction = Move.Direction.W;
                        break;
                    case 5:
                        direction = Move.Direction.NW;
                        break;
                    default:
                        throw new IllegalArgumentException("NOpe");
                }

                int firstMarble = marbleSplit[0];
                Coordinate firstMarbleCoordinate = Board.convertToCoordinate(firstMarble);
                Group soloGroup = new Group(firstMarbleCoordinate);
                Move choice = new Move(direction, soloGroup, this.getTeam());
                if (marbleSplit.length > 1) {
                    int secondMarble = marbleSplit[1];
                    Coordinate secondMarbleCoordinate = Board.convertToCoordinate(secondMarble);
                    Group pairGroup = new Group(firstMarbleCoordinate, secondMarbleCoordinate);
                    choice = new Move(direction, pairGroup, this.getTeam());

                    if (marbleSplit.length > 2) {
                        int thirdMarble = marbleSplit[2];
                        Coordinate thirdMarbleCoordinate = Board.convertToCoordinate(thirdMarble);
                        Group trioGroup = new Group(firstMarbleCoordinate, secondMarbleCoordinate,
                                thirdMarbleCoordinate);
                        choice = new Move(direction, trioGroup, this.getTeam());
                    }
                }
                return choice;
            }
        } while (!isValidArg);
        System.out.println("Not a valid argument!");
        return null;
    }
}
