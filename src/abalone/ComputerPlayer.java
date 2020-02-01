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
//    /**
//     * Takes a random (single) marble, generates a random direction and index and checks if it is valid. If it is valid,
//     * takes the move.
//     *
//     * @param board the current game board
//     * @return the chosen move
//     * @requires board != null
//     */
//
////    @Override
////    public Move makeChoice(Board board) {
////        Board boardCopy = new Board(board);// make a copy of the board
////        Move move = null;
////        Coordinate cord1 = null;
//////        Coordinate cord2;
//////        Coordinate cord3;
////        boolean isValid = false;
////        while (!isValid) {
////            int index = (int) (Math.random() * 60);
////            System.out.println("Selected index = " + index);
////            int dir = (int) (Math.random() * 6);
////            System.out.println("Selected direction = " + dir);
////            if (cord1.isValidStep(Move.Direction.intToDirection(dir)) && !boardCopy.getField(index).equals(this.getMarble())) {
////                cord1 = Board.convertToCoordinate(index);
////                Group group = new Group(cord1);
////                move = new Move(Move.Direction.intToDirection(dir), group, this.getTeam());
////                continue;
////            }
////
//////            boolean isValid2 = false;
//////            while (!isValid2) {
//////                Move.Direction direction = Move.Direction.intToDirection((int) (Math.random() * 6));
//////                cord2 = new Coordinate(cord1.step(direction));
//////                boolean validCoord2 = cord2.isValidStep(direction);
//////                if (validCoord2 && !board.getField(Board.convertToInt(cord2)).equals(this.getMarble())) {
//////                    Group duo = new Group(cord1, cord2);
//////                    move = new Move(direction, duo, this.getTeam());
//////                    continue;
//////                }
//////                boolean isValid3 = false;
//////                while (!isValid3) {
//////                    cord3 = new Coordinate(cord2.step(direction));
//////                    boolean validCoord3 = cord3.isValidStep(direction);
//////                    if (validCoord3 && !board.getField(Board.convertToInt(cord3)).equals(this.getMarble())) {
//////                        Group trio = new Group(cord1, cord2, cord3);
//////                        move = new Move(direction, trio, this.getTeam());
//////                        isValid3 = true;
//////                        continue;
//////                    }
//////                }
//////                isValid2 = true;
//////            }
//////            isValid = true;
//////        }
////        }
////            return move;
////    } is not working
//    public Move makeChoice(Board board) {
//        Board boardCopy = new Board(board);
//
//        Move move = null;
//        boolean isValid = false;
//
//        while (!isValid) {
//            int index = -1;
//            boolean rightIndex = false;
//            while (!rightIndex) {
//                index = (int) (Math.random() * 60);
//                if (boardCopy.getField(index).equals(this.getMarble())) {
//                    rightIndex = true;
//                }
//            }
//            System.out.println("Selected index = " + index);
//            Move.Direction direction = Move.Direction.intToDirection((int) (Math.random() * 6));
//            System.out.println("Selected direction = " + direction);
//
//
//            if (boardCopy.getField(index).equals(this.getTeam())) {
//                continue;
//            }
//
//            Coordinate cord1 = Board.convertToCoordinate(index);
//            Group solo = new Group(cord1);
//            move = new Move(direction, solo, this.getTeam());
//            isValid = boardCopy.makeMove(move);
//
//        }
//        return move;
//    }
//}
