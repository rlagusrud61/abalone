package tests;

import abalone.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class BoardTest {
    Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        Game game2P = new Game(new HumanPlayer("HK", Marble.WHITE),
                new HumanPlayer("Jochem", Marble.BLACK));

        Game game3P = new Game(new HumanPlayer("HK", Marble.WHITE),
                new HumanPlayer("Jochem", Marble.BLACK), new HumanPlayer("imSoDone", Marble.BLUE));

        Game game4P = new Game(new HumanPlayer("HK", Marble.WHITE),
                new HumanPlayer("Jochem", Marble.BLACK), new HumanPlayer("imSoDone", Marble.BLUE),
                new HumanPlayer("withThisProject", Marble.RED));
    }

    @Test
    void FieldTest() {
        Marble field = board.getField(new Coordinate(420, 69));
        assertNull(field);
        board.setField(new Coordinate(0, 0), Marble.BLUE);
        board.setField(new Coordinate(4, 0), Marble.EMPTY);
        assertEquals(board.getField(new Coordinate(0, 0)), Marble.BLUE);
        assertEquals(board.getField(0), Marble.BLUE);
        assertNull(board.getField(83294293));
        assertFalse(board.isField(23482749));
        assertTrue(board.isField(60));
        assertFalse(board.isField(61));

        assertTrue(board.isEmpty(new Coordinate(4, 0)));
        assertTrue(board.isEmpty(4));

    }

    @Test
    void testToString() {
        board.reset(BoardStates.getTwoPlayer());
        assertTrue(board.toString().contains(" ┃"));
    }

    @Test
    void convertToIntAndConvertToCoordinateTest() {
        int index = 10;
        Coordinate coordinate = Board.convertToCoordinate(index);
        assertEquals(Board.convertToInt(coordinate), 10);

        coordinate = new Coordinate(4, 3);
        index = Board.convertToInt(coordinate);
        assertEquals(Board.convertToCoordinate(index), coordinate);
        assertEquals(Board.convertToCoordinate(0), new Coordinate(0, 0));
        assertNull(Board.convertToCoordinate(31232198));
    }

    @Test
    void addTeamOnBoardTest() {
        List<Marble> marbleList = new ArrayList<>();
        marbleList.add(Marble.WHITE);
        marbleList.add(Marble.BLUE);
        Team team1 = new Team(marbleList);
        List<Marble> marbleList2 = new ArrayList<>();
        marbleList.add(Marble.BLACK);
        marbleList.add(Marble.RED);
        Team team2 = new Team(marbleList2);
        board.addTeamToBoard(team1);
        board.addTeamToBoard(team2);
        assertTrue(board.playingTeams.contains(team1));
        assertTrue(board.playingTeams.contains(team2));
    }

    @Test
    void constructorTest() {
        Board boardCopy = new Board(this.board);
        assertFalse(boardCopy.fields.equals(this.board.fields));
        assertTrue(boardCopy.moveCounter == this.board.moveCounter);
    }

    @Test
    void makeMoveTest() {
        board.reset(BoardStates.getTwoPlayer());
        Move.Direction direction = Move.Direction.E;
        Group group = new Group(new Coordinate(0, 1), new Coordinate(1, 1),
                new Coordinate(2, 1));
        List<Marble> marbleList = new ArrayList<>();
        marbleList.add(Marble.WHITE);
        Team team = new Team(marbleList);
        Move move = new Move(direction, group, team);
        board.makeMove(move);
        Coordinate[] coordinates = {new Coordinate(1, 1), new Coordinate(2, 1),
                new Coordinate(3, 1)};
        Arrays.stream(coordinates).forEach(field -> assertEquals(board.getField(field), Marble.WHITE));
    }


    @Test
    void makeMoveTest2() {
        Board board = new Board();
        List<Marble> marbleList = new ArrayList<>();
        marbleList.add(Marble.WHITE);
        marbleList.add(Marble.BLUE);
        Team team1 = new Team(marbleList);

        List<Marble> marbleList2 = new ArrayList<>();
        marbleList2.add(Marble.BLACK);
        marbleList2.add(Marble.RED);
        Team team2 = new Team(marbleList2);

        board.addTeamToBoard(team1);
        board.addTeamToBoard(team2);

        board.setField(Board.convertToCoordinate(15), Marble.WHITE);
        board.setField(Board.convertToCoordinate(23), Marble.WHITE);
        board.setField(Board.convertToCoordinate(32), Marble.WHITE);
        board.setField(Board.convertToCoordinate(41), Marble.BLACK);
        board.setField(Board.convertToCoordinate(49), Marble.BLUE);

        Move.Direction direction = Move.Direction.SE;
        Group group = new Group(new Coordinate(Board.convertToCoordinate(15)),
                new Coordinate(Board.convertToCoordinate(23)),
                new Coordinate(Board.convertToCoordinate(32)));
        Move move = new Move(direction, group, team1);
        board.makeMove(move);
        Coordinate[] coordinates = {new Coordinate(Board.convertToCoordinate(15)),
                new Coordinate(Board.convertToCoordinate(23)),
                new Coordinate(Board.convertToCoordinate(32))};
        Arrays.stream(coordinates).forEach(field -> assertEquals(board.getField(field), Marble.WHITE));
    }

    @Test
    void makeMovePushTest() {
        Board board = new Board();
        List<Marble> marbleList = new ArrayList<>();
        marbleList.add(Marble.WHITE);
        marbleList.add(Marble.BLUE);
        Team team1 = new Team(marbleList);

        List<Marble> marbleList2 = new ArrayList<>();
        marbleList2.add(Marble.BLACK);
        marbleList2.add(Marble.RED);
        Team team2 = new Team(marbleList2);

        board.addTeamToBoard(team1);
        board.addTeamToBoard(team2);

        board.setField(Board.convertToCoordinate(15), Marble.WHITE);
        board.setField(Board.convertToCoordinate(23), Marble.WHITE);
        board.setField(Board.convertToCoordinate(32), Marble.WHITE);
        board.setField(Board.convertToCoordinate(41), Marble.BLACK);
        board.setField(Board.convertToCoordinate(49), Marble.BLACK);

        Move.Direction direction = Move.Direction.SE;
        Group group = new Group(new Coordinate(Board.convertToCoordinate(15)),
                new Coordinate(Board.convertToCoordinate(23)),
                new Coordinate(Board.convertToCoordinate(32)));
        Move move = new Move(direction, group, team1);
        board.makeMove(move);
        Coordinate[] coordinates = {new Coordinate(Board.convertToCoordinate(23)),
                new Coordinate(Board.convertToCoordinate(32)),
                new Coordinate(Board.convertToCoordinate(41))};
        Arrays.stream(coordinates).forEach(field -> assertEquals(board.getField(field), Marble.WHITE));
        assertEquals(1, board.playingTeams.get(0).getPoints());
    }

    @Test
    void pushFriendlyTest() {
        Board board = new Board();
        List<Marble> marbleList = new ArrayList<>();
        marbleList.add(Marble.WHITE);
        marbleList.add(Marble.BLUE);
        Team team1 = new Team(marbleList);

        List<Marble> marbleList2 = new ArrayList<>();
        marbleList2.add(Marble.BLACK);
        marbleList2.add(Marble.RED);
        Team team2 = new Team(marbleList2);

        board.addTeamToBoard(team1);
        board.addTeamToBoard(team2);

        board.setField(Board.convertToCoordinate(8), Marble.WHITE);
        board.setField(Board.convertToCoordinate(15), Marble.WHITE);
        board.setField(Board.convertToCoordinate(23), Marble.WHITE);
        board.setField(Board.convertToCoordinate(32), Marble.BLUE);
        board.setField(Board.convertToCoordinate(41), Marble.BLACK);

        Move.Direction direction = Move.Direction.SE;
        Group group = new Group(new Coordinate(Board.convertToCoordinate(8)),
                new Coordinate(Board.convertToCoordinate(15)),
                new Coordinate(Board.convertToCoordinate(23)));
        Move move = new Move(direction, group, team1);
        board.makeMove(move);
        Coordinate[] coordinates = {new Coordinate(Board.convertToCoordinate(15)),
                new Coordinate(Board.convertToCoordinate(23)),
                new Coordinate(Board.convertToCoordinate(32))};
        Arrays.stream(coordinates).forEach(field -> assertEquals(board.getField(field), Marble.WHITE));
    }

    @Test
    void pushEnemyFailTest() {
        Board board = new Board();
        List<Marble> marbleList = new ArrayList<>();
        marbleList.add(Marble.WHITE);
        marbleList.add(Marble.BLUE);
        Team team1 = new Team(marbleList);

        List<Marble> marbleList2 = new ArrayList<>();
        marbleList2.add(Marble.BLACK);
        marbleList2.add(Marble.RED);
        Team team2 = new Team(marbleList2);

        board.addTeamToBoard(team1);
        board.addTeamToBoard(team2);

        board.setField(Board.convertToCoordinate(23), Marble.WHITE);
        board.setField(Board.convertToCoordinate(32), Marble.BLUE);
        board.setField(Board.convertToCoordinate(41), Marble.BLACK);
        board.setField(Board.convertToCoordinate(49), Marble.BLACK);

        Move.Direction direction = Move.Direction.SE;
        Group group = new Group(new Coordinate(Board.convertToCoordinate(23)));
        Move move = new Move(direction, group, team1);
        board.makeMove(move);
        Coordinate[] coordinates = {new Coordinate(Board.convertToCoordinate(23))};
        Arrays.stream(coordinates).forEach(field -> assertEquals(board.getField(field), Marble.WHITE));
    }


    @Test
    void makeMoveSlideTest() {
        board.reset(BoardStates.getTwoPlayer());
        Move.Direction direction = Move.Direction.SW;
        Group group = new Group(new Coordinate(2, 2), new Coordinate(3, 2),
                new Coordinate(4, 2));
        List<Marble> marbleList = new ArrayList<>();
        marbleList.add(Marble.WHITE);
        Team team = new Team(marbleList);
        Move move = new Move(direction, group, team);
        board.makeMoveSlide(move);
        Coordinate[] coordinates = {new Coordinate(2, 3), new Coordinate(3, 3),
                new Coordinate(4, 3)};
        Arrays.stream(coordinates).forEach(field -> assertEquals(board.getField(field), Marble.WHITE));
    }

    @Test
    void isNeighbourTest() {
        Coordinate c1 = new Coordinate(4, 3);
        Coordinate c2 = new Coordinate(5, 3);
        assertTrue(board.isNeighbour(c1, c2));
    }

    @Test
    void hasWinnerTest() {
        List<Marble> marbleList = new ArrayList<>();
        marbleList.add(Marble.WHITE);
        Team team = new Team(marbleList);
        board.addTeamToBoard(team);

        board.playingTeams.get(0).addPoint();
        board.playingTeams.get(0).addPoint();
        board.playingTeams.get(0).addPoint();
        assertFalse(board.hasWinner());
        board.playingTeams.get(0).addPoint();
        board.playingTeams.get(0).addPoint();
        board.playingTeams.get(0).addPoint();

        assertEquals(6, board.playingTeams.get(0).getPoints());
        assertTrue(board.hasWinner());
        assertTrue(board.gameOver());

    }

    @Test
    void isWinnerAndGameOverTest() {
        List<Marble> marbleList = new ArrayList<>();
        marbleList.add(Marble.WHITE);
        Team team = new Team(marbleList);
        board.addTeamToBoard(team);

        board.playingTeams.get(0).addPoint();
        board.playingTeams.get(0).addPoint();
        assertFalse(board.isWinner(Marble.WHITE));
        assertFalse(board.hasWinner());
        board.playingTeams.get(0).addPoint();
        board.playingTeams.get(0).addPoint();
        board.playingTeams.get(0).addPoint();
        board.playingTeams.get(0).addPoint();

        assertTrue(board.isWinner(Marble.WHITE));
        assertFalse(board.isWinner(Marble.BLACK));

        board.moveCounter = 97;

        assertTrue(board.gameOver());
    }

    @Test
    void validSelectionTest() {
        Group soloRightGroup = new Group(new Coordinate(2, 4));
        Group soloWrongGroup = new Group(new Coordinate(22, 34));
        Group rightGroup = new Group(new Coordinate(2, 2), new Coordinate(3, 2),
                new Coordinate(4, 2));

        Group wrongGroup = new Group(new Coordinate(5, 2), new Coordinate(8, 2),
                new Coordinate(4, 1));

        assertTrue(board.isValidSelection(soloRightGroup));
        assertFalse(board.isValidSelection(soloWrongGroup));
        assertTrue(board.isValidSelection(rightGroup));
        assertFalse(board.isValidSelection(wrongGroup));
    }

    @Test
    void name() {
    }
}