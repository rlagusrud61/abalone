//package abalone;
//
//import abalonegame.Board;
//import abalonegame.Marble;
//import abalonegame.Player;
//import utils.TextIO;
//
//public class HumanPlayer extends Player {
//    public HumanPlayer(String name1, Marble marble) {
//        super(name1, marble);
//    }
//
//    @Override
//    public int[] determineMove(Board board) {
//        String prompt = "> " + getName() + " (" + getMarble().toString() + ")"
//                + ", what is your choice? Give direction (0-5), and list the marbles separated by commas (without space).";
//
//        System.out.println(prompt);
//        String choice = TextIO.getln();
//        String command = choice.split(";")[0];
//        String marbles = choice.split(";")[1];
//        String[] marbleSplit = marbles.split(",");
//        String firstMarble = marbleSplit[0];
//        if (marbleSplit.length > 1) {
//            String secondMarble = marbleSplit[1];
//            if (marbleSplit.length > 2) {
//                String thirdMarble = marbleSplit[2];
//            }
//        }
//
//        boolean valid = false;
//        while (!valid) {
//            determineMove(board);
//            valid = board.isField(choice) && board.isEmpty(choice);
//        }
//        return choice;
//    }
//}
