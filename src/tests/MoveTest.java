package tests;

import abalone.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {


    @Test
    void getDirection() {
        Coordinate cord1 = new Coordinate(0, 0);
        Group atom = new Group(cord1);
        Move.Direction dir = Move.Direction.SW;
        List<Marble> marbleList = new ArrayList<>();
        marbleList.add(Marble.WHITE);
        Team team = new Team(marbleList);
        Move oneMove = new Move(dir, atom, team);
        assertTrue(oneMove.getDirection().equals(dir));
    }

    @Test
    void getGroup() {
        Coordinate cord1 = new Coordinate(0, 0);
        Group atom = new Group(cord1);
        Move.Direction dir = Move.Direction.SW;
        List<Marble> marbleList = new ArrayList<>();
        marbleList.add(Marble.WHITE);
        Team team = new Team(marbleList);
        Move oneMove = new Move(dir, atom, team);
        assertEquals(oneMove.getGroup(), atom);
    }


    @Test
    void getTeam() {
        Coordinate cord1 = new Coordinate(0, 0);
        Group atom = new Group(cord1);
        Move.Direction dir = Move.Direction.SW;
        List<Marble> marbleList = new ArrayList<>();
        marbleList.add(Marble.WHITE);
        Team team = new Team(marbleList);
        Move oneMove = new Move(dir, atom, team);
        assertEquals(oneMove.getTeam(), team);

    }

    @Test
    void intToDirection() {
        Move.Direction dir = Move.Direction.intToDirection(0);
        assertEquals(dir, Move.Direction.NE);
        dir = Move.Direction.intToDirection(1);
        assertEquals(dir, Move.Direction.E);
        dir = Move.Direction.intToDirection(2);
        assertEquals(dir, Move.Direction.SE);
        dir = Move.Direction.intToDirection(3);
        assertEquals(dir, Move.Direction.SW);
        dir = Move.Direction.intToDirection(4);
        assertEquals(dir, Move.Direction.W);
        dir = Move.Direction.intToDirection(5);
        assertEquals(dir, Move.Direction.NW);
        dir = Move.Direction.intToDirection(6);
        assertNull(dir);

    }

    @Test
    void oppositeTest() {
        assertEquals(Move.Direction.SW.opposite(), Move.Direction.NE);
        assertEquals(Move.Direction.W.opposite(), Move.Direction.E);
        assertEquals(Move.Direction.NE.opposite(), Move.Direction.SW);
        assertEquals(Move.Direction.SE.opposite(), Move.Direction.NW);
        assertEquals(Move.Direction.NW.opposite(), Move.Direction.SE);
        assertEquals(Move.Direction.E.opposite(), Move.Direction.W);
        //can't test assertNull
    }

    @Test
    void isParallelToTest() {
        assertTrue(Move.Direction.SW.isParallelTo(Move.Direction.NE));
        assertTrue(Move.Direction.E.isParallelTo(Move.Direction.W));
        assertTrue(Move.Direction.SE.isParallelTo(Move.Direction.NW));
        //can't test assertNull
    }
}