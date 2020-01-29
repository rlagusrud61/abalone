package abalone;

import utils.TextIO;

import java.util.ArrayList;
import java.util.List;


/**
 * Class for maintaining the Abalone game.
 */
public class Game {

    public static final int TWO_PLAYERS = 2;
    public static final int THREE_PLAYERS = 3;
    public static final int FOUR_PLAYERS = 4;

    /**
     * The board.
     *
     * @invariant board is never null
     */
    public Board board;

    /**
     * List of the players.
     */
    public Player[] players;

    /**
     * Index of the current player.
     *
     * @invariant the index is always between 0 and NUMBER_PLAYERS
     */
    public int current;

    // -- Constructors -----------------------------------------------

    /**
     * Creates a new Game object.
     *
     * @param s0 the first player
     * @param s1 the second player
     * @requires s0 and s1 to be non-null
     */
    public Game(Player s0, Player s1) {
        board = new Board();
        players = new Player[TWO_PLAYERS];
        players[0] = s0;
        players[1] = s1;
        List<Marble> white = new ArrayList<>();
        white.add(Marble.WHITE);
        Team team1 = new Team(white); // make a new team1
        players[0].setTeam(team1); // assign team for the players
        List<Marble> black = new ArrayList<>();
        black.add(Marble.BLACK);
        Team team2 = new Team(black);
        players[1].setTeam(team2);


        board.addTeamToBoard(team1);
        board.addTeamToBoard(team2);
        current = 0;

    }

    /**
     * Creates a new Game object.
     *
     * @param s0 the first player
     * @param s1 the second player
     * @param s2 the third player
     * @requires s0 ,s1 and s2 to be non-null
     */
    public Game(Player s0, Player s1, Player s2) {
        board = new Board();
        players = new Player[THREE_PLAYERS];
        players[0] = s0;
        players[1] = s1;
        players[2] = s2;
        List<Marble> white = new ArrayList<>();
        white.add(Marble.WHITE);
        Team team1 = new Team(white);
        players[0].setTeam(team1);

        List<Marble> black = new ArrayList<>();
        black.add(Marble.BLACK);
        Team team2 = new Team(black);
        players[1].setTeam(team2);

        List<Marble> blue = new ArrayList<>();
        blue.add(Marble.BLUE);
        Team team3 = new Team(blue);
        players[2].setTeam(team3);


        board.addTeamToBoard(team1);
        board.addTeamToBoard(team2);
        board.addTeamToBoard(team3);
        current = 0;
    }

    /**
     * Creates a new Game object.
     *
     * @param s0 the first player
     * @param s1 the second player
     * @param s2 the third player
     * @param s3 the fourth player
     * @ensures s0 and s2 are in the same team
     * @ensures s1 and s3 are in the same team
     * @requires s0, s1, s2 and s3 to be non-null
     */
    public Game(Player s0, Player s1, Player s2, Player s3) {
        board = new Board();
        players = new Player[FOUR_PLAYERS];
        players[0] = s0;
        players[1] = s1;
        players[2] = s2;
        players[3] = s3;
        List<Marble> whiteBlue = new ArrayList<>();
        whiteBlue.add(Marble.WHITE);
        whiteBlue.add(Marble.BLUE);
        Team team1 = new Team(whiteBlue);
        players[0].setTeam(team1);
        players[2].setTeam(team1);

        List<Marble> blackRed = new ArrayList<>();
        blackRed.add(Marble.BLACK);
        blackRed.add(Marble.RED);
        Team team2 = new Team(blackRed);
        players[1].setTeam(team2);
        players[3].setTeam(team2);


        board.addTeamToBoard(team1);
        board.addTeamToBoard(team2);
        current = 0;
    }

    // -- Commands ---------------------------------------------------

    /**
     * Starts the Abalone game. <br>
     * Asks after each ended game if the user want to continue. Continues until
     * the user does not want to play anymore.
     */
    public void start() {
        Board.printBoard();
        boolean continueGame = true;
        while (continueGame) {
            reset();
            play();
            System.out.println("\n> Play another time? (y/n)?");
            continueGame = TextIO.getBoolean();
        }
    }

    /**
     * Resets the game. <br>
     * The board is emptied and player[0] becomes the current player.
     */
    public void reset() {
        current = 0;
        if (players.length == TWO_PLAYERS) {
            board.reset(BoardStates.getTwoPlayer());
        } else if (players.length == THREE_PLAYERS) {
            board.reset(BoardStates.getThreePlayer());
        } else if (players.length == FOUR_PLAYERS) {
            board.reset(BoardStates.getFourPlayer());
        }
    }

    /**
     * Plays the Abalone game. <br>
     * First the initial board (according to the number of players) is shown. Then the game is played
     * until it is over. Players can make a move one after the other.
     * After each move, the changed game situation is printed.
     *
     * @requires players.length > 0
     * @requires board != null
     */
    public void play() {
        while (!board.gameOver()) {
            boolean moveMade = false;
            update();

            while (!moveMade) {
                if (current >= players.length) {
                    current = 0;
                } else {
                    Move choice = players[current].makeChoice(board);
                    boolean isValidColor = false;
                    if (players[current].getMarble().equals(board.getField(choice.getGroup().getMarble1()))
                            && (choice.getGroup().getMarble2() == null || players[current].getMarble()
                            .equals(board.getField(choice.getGroup().getMarble2())))
                            && (choice.getGroup().getMarble3() == null || players[current]
                            .getMarble().equals(board.getField(choice.getGroup().getMarble3())))) {
                        isValidColor = true;
                    }
                    if (isValidColor && board.isValidSelection(choice.getGroup())
                            && board.makeMove(choice)) {
                        current++;
                        moveMade = true;
                    } else {
                        System.out.println("Selection is invalid!");
                    }
                }
            }

        }

        update();
        if (board.gameOver()) {
            printResult();
        }

    }


    /**
     * Prints the game situation.
     *
     * @requires board != null;
     */
    public void update() {
        System.out.println("\ncurrent game situation: \n\n" + board.toString()
                + "\n");
    }

    /**
     * Prints the result of the last game. <br>
     *
     * @requires the game to be over
     */
    public void printResult() {
        if (board.hasWinner()) {
            Player winner = board.isWinner(players[0].getMarble()) ? players[0]
                    : players[1];
            System.out.println("Speler " + winner.getName() + " ("
                    + winner.getMarble() + ") has won!");
        } else {
            System.out.println("Draw. There is no winner!");
        }
    }

}
