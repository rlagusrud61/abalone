public class Abalone {
    public static void main(String[] args) {
        Player s1;
        Player s2;
        Game game;
        System.out.println("Identify urself hooman number one");
        String name1 = TextIO.getln();
        s1 = new HumanPlayer(name1, Mark.XX);

        System.out.println("Identify urself hooman number two");
        String name2 = TextIO.getln();

        if (name2.contentEquals("-N")) {
            s2 = new ComputerPlayer(Mark.OO);
            game = new Game(s1, s2);
            game.start();
        } else if (name2.contentEquals("-S")) {
            s2 = new ComputerPlayer(Mark.OO, new SmartStrategy());
            game = new Game(s1, s2);
            game.start();
        } else if (name1.contentEquals("-S") && name2.contentEquals("-N")) {
            s1 = new ComputerPlayer(Mark.XX, new SmartStrategy());
            s2 = new ComputerPlayer(Mark.OO);
            game = new Game(s1, s2);
            game.start();
        } else if (name1.contentEquals("-N") && name2.contentEquals("-S")) {
            s1 = new ComputerPlayer(Mark.XX);
            s2 = new ComputerPlayer(Mark.OO, new SmartStrategy());
            game = new Game(s1, s2);
            game.start();
        } else {
            s2 = new HumanPlayer(name2, Mark.OO);
            game = new Game(s1, s2);
            game.start();
        }

    }
}
