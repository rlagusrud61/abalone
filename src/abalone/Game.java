//package abalone;
//
//import utils.TextIO;
//
//import java.util.List;
//
//public class Game {
//
//    /**
//     * The board.
//     *
//     * @invariant board is never null
//     */
//    private Board board;
//
//    /**
//     * The players of the game.
//     *
//     * @invariant all array items are never null
//     */
//    private List<Player> players;
//
//    /**
//     * Index of the current player.
//     */
//    private int current;
//
//    // -- Constructors -----------------------------------------------
//
//    /**
//     * Creates a new Game object.
//     *
//     * @param s0 the first player
//     * @param s1 the second player
//     * @requires s0 and s1 to be non-null
//     */
//    public Game(Player s0, Player s1) {
//        board = new Board();
//        players.add(s0);
//        players.add(s1);
//        current = 0;
//    }
//
//    public Game(Player s0, Player s1, Player s2) {
//        board = new Board();
//        players.add(s0);
//        players.add(s1);
//        players.add(s2);
//        current = 0;
//    }
//
//    public Game(Player s0, Player s1, Player s2, Player s3) {
//        board = new Board();
//        players.add(s0);
//        players.add(s1);
//        players.add(s2);
//        players.add(s3);
//        current = 0;
//    }
//    // -- Commands ---------------------------------------------------
//
//    /**
//     * Starts the Tic Tac Toe game. <br>
//     * Asks after each ended game if the user want to continue. Continues until
//     * the user does not want to play anymore.
//     */
//    public void start() {
//        System.out.println(board.toString());
//        boolean continueGame = true;
//        while (continueGame) {
//            reset();
//            play();
//            System.out.println("\n> Play another time? (y/n)?");
//            continueGame = TextIO.getBoolean();
//        }
//    }
//
//    /**
//     * Resets the game. <br>
//     * The board is emptied and player[0] becomes the current player.
//     */
//    private void reset() {
//        current = 0;
//        BoardStates boardStates = new BoardStates();
//
//    }
//
//    /**
//     * Plays the Tic Tac Toe game. <br>
//     * First the (still empty) board is shown. Then the game is played
//     * until it is over. Players can make a move one after the other.
//     * After each move, the changed game situation is printed.
//     */
//    private void play() {
//        while (!board.gameOver() && !board.hasWinner()) {
//            board.setField(players.get(current).determineMove(board), players.get(current).getMarble());
//            current++;
//            update();
//        }
//    }
//
//    /**
//     * Prints the game situation.
//     */
//    private void update() {
//        System.out.println("\ncurrent game situation: \n\n" + board.toString()
//                + "\n");
//    }
//
//    /**
//     * Prints the result of the last game. <br>
//     *
//     * @requires the game to be over
//     */
//    private void printResult() {
//        if (board.hasWinner()) {
//            Player winner = null;
//            if (board.isWinner(players.get(0).getMarble())) {
//                winner = players.get(0);
//            } else if (board.isWinner(players.get(1).getMarble())) {
//                winner = players.get(1);
//            } else if (board.isWinner(players.get(2).getMarble())) {
//                winner = players.get(2);
//            } else if (board.isWinner(players.get(3).getMarble())) {
//                winner = players.get(3);
//            }
//            System.out.println("Player  " + winner.getName() + " ("
//                    + winner.getMarble() + ") has won!");
//        } else {
//            System.out.println("Draw. There is no winner!");
//        }
//    }
//}
