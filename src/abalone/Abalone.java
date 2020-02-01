package abalone;

import utils.TextIO;

import java.util.ArrayList;
import java.util.List;

/**
 * Application to run the Abalone game.
 */

public class Abalone {
    /**
     * Main method of Abalone.
     * The users input their names,
     *
     * @param args .
     */
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        Game game;

        System.out.println("Player 1 name?");
        String name1 = TextIO.getln();
        players.add(new HumanPlayer(name1, Marble.WHITE));


        System.out.println("Player 2 name?");
        String name2 = TextIO.getln();
        players.add(new HumanPlayer(name2, Marble.BLACK));

        System.out.println("Player 3? If not playing, type 'n'");
        String name3 = TextIO.getln();
        if (!name3.equals("n")) {
            players.add(new HumanPlayer(name3, Marble.BLUE));
        }

        System.out.println("Player 4? If player 3 doesn't exist, you can't play. Type 'n'. ");
        String name4 = TextIO.getln();
        if (!name4.equals("n")) {
            players.add(new HumanPlayer(name4, Marble.RED));
        }

        if (players.size() == 2) {
            game = new Game(players.get(0), players.get(1));
            game.start();
        } else if (players.size() == 3) {
            game = new Game(players.get(0), players.get(1), players.get(2));
            game.start();
        } else if (players.size() == 4) {
            game = new Game(players.get(0), players.get(1), players.get(2), players.get(3));
            game.start();
        }


    }

}

