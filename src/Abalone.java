import utils.TextIO;
public class Abalone {
    public static void main(String[] args) {
        Player s1;
        Player s2;
        Game2P game;
        System.out.println("Identify urself hooman number one");
        String name1 = TextIO.getln();
        s1 = new HumanPlayer(name1, Marble.BLACK);

        System.out.println("Identify urself hooman number two");
        String name2 = TextIO.getln();

            s2 = new HumanPlayer(name2, Marble.WHITE);
            game = new Game2P(s1, s2);

            game.start();
        }

    }

