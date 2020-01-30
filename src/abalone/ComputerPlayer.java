//package abalone;
//
//public class ComputerPlayer extends Player {
//    public static int NAIVE = 1;
//    public static int SMART = 2;
//
//    /**
//     * Creates a new Computer Player object.
//     *
//     * @param name   Name of the computer player
//     * @param marble Marble color assigned to this player
//     * @requires name is not null
//     * @requires mark is either WHITE, BLUE, BLACK, or RED
//     * @ensures the Name of this player will be name
//     * @ensures the abalone.Marble of this player will be marble
//     */
//    public ComputerPlayer(String name, Marble marble) {
//        super(name, marble);
//    }
//
//    @Override
//    public Move makeChoice(Board board) {
//        //direction, group, team and
//        int numberOfMarbles = (int) (Math.random() * 3);
//        int direction = (int) (Math.random() * 6);
//        if (numberOfMarbles < 2) {
//            if (this.getMarble()) {
//                Group soloGroup = new Group();
//        Board boardCopy = new Board(board);
//
//        Move move = null;
//        boolean isValid = false;
//
//        while (!isValid) {
//            int index = (int) (Math.random() * 60);
//            System.out.println("Selected index = " + index);
//            int dir = (int) (Math.random() * 6);
//            System.out.println("Selected direction = " + dir);
//            Move.Direction direction = Move.Direction.intToDirection(dir);
//
//            if (!board.getField(index).equals(this.getMarble())) {
//                continue;
//            }
//
//            Coordinate cord1 = Board.convertToCoordinate(index);
//            Group solo = new Group(cord1);
//            move = new Move(direction, solo, this.getTeam());
//            isValid = boardCopy.makeMove(move);
//        }
//        Group group = new Group();
//        Move choice = new Move(, this.getTeam());
//        return null;
//
//        return move;
//    }
//}
