package abalone;

import utils.TextIO;

import java.util.Arrays;

public class HumanPlayer extends Player {


    public HumanPlayer(String name1, Marble marble) {
        super(name1, marble);
    }

    @Override
    public Move makeChoice(Board board) {
        boolean isValidArg = true;
            String prompt = "> " + getName() + " (" + getMarble().toString() + ")"
                    + ", what is your choice? Give direction (0-5), and a ; ,   and list the marbles separated by commas (without space).";
            System.out.println(prompt);
        do {
            String text = TextIO.getln();
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
                        Group trioGroup = new Group(firstMarbleCoordinate, secondMarbleCoordinate, thirdMarbleCoordinate);
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
