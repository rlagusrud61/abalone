package tests;

import abalone.Coordinate;
import abalone.Group;
import abalone.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    Group group;
    private IllegalStateException IllegalStateException;

    @BeforeEach
    void setUp() {
        Coordinate cord1 = new Coordinate(0, 0);
        Coordinate cord2 = new Coordinate(1, 0);
        Coordinate cord3 = new Coordinate(2, 0);
        group = new Group(cord1, cord2, cord3);
    }

    @Test
    void getMarblesTest() {
        Coordinate[] groupCord = group.getMarbles().toArray(new Coordinate[0]);
        Coordinate[] coordinates = {new Coordinate(0, 0), new Coordinate(1, 0),
                new Coordinate(2, 0)};

        assertArrayEquals(groupCord, coordinates);

        Coordinate marble = new Coordinate(0, 0);
        assertEquals(group.getMarble1(), marble);
        assertFalse(group.getMarble1().equals(new Coordinate(4, 1)));

        Coordinate marble2 = new Coordinate(1, 0);
        Group atom = new Group(new Coordinate(0, 0));
        assertEquals(group.getMarble2(), marble2);
        assertNull(atom.getMarble2());

        Coordinate marble3 = new Coordinate(2, 0);
        assertEquals(group.getMarble3(), marble3);
        assertNull(atom.getMarble3());



    }

    @Test
    void step() {
        Move.Direction direction = Move.Direction.SW;
        Group newGroup = group.step(direction);
        assertTrue(newGroup.getMarble1().equals(new Coordinate(0, 1)));

        Group duo = new Group(new Coordinate(0, 0), new Coordinate(1, 0));
        Group duoStep = duo.step(direction);
        assertTrue(duoStep.getMarble1().equals(new Coordinate(0, 1)));
        assertTrue(duoStep.getMarble2().equals(new Coordinate(1, 1)));
    }


    @Test
    void isInLine() {
        assertTrue(group.isInLine());
        Group duo = new Group(new Coordinate(0, 0), new Coordinate(0, 1));
        assertTrue(duo.isInLine());
        duo = new Group(new Coordinate(0, 6), new Coordinate(0, 1));
        assertFalse(duo.isInLine());
        Coordinate cord1 = new Coordinate(0, 0);
        Coordinate cord2 = new Coordinate(1, 1);
        Coordinate cord3 = new Coordinate(2, 0);
        Group newGroup = new Group(cord1, cord2, cord3);
        assertFalse(newGroup.isInLine());
    }

    @Test
    void getLineDirection() {
        Group solo = new Group(new Coordinate(0, 1));
        assertNull(solo.getLineDirection());
    }

    @Test
    void isMarbleInGroup() {
        assertTrue(group.isMarbleInGroup(new Coordinate(0, 0)));
        assertFalse(group.isMarbleInGroup(new Coordinate(4, 0)));
    }
}