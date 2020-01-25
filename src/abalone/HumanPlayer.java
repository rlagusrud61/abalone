package abalone;

import utils.TextIO;

public class HumanPlayer extends Player {
    public HumanPlayer(String name1, MarbleColor marble) {
        super(name1, marble);
    }

    @Override
    public int[] determineMove(Board board) {
        String prompt = "> " + getName() + " (" + getMarble().toString() + ")"
                + ", what is your choice? ";

        System.out.println(prompt);
        int choice = TextIO.getln();


        boolean valid; //TODO check if it's empty
        while (!valid) {
            System.out.println("ERROR: field " + choice
                    + " is no valid choice.");
            System.out.println(prompt);
            choice = TextIO.getInt();
            valid = board.isField(choice) && board.isEmptyField(choice);
        }
        return choice;
    }
}
