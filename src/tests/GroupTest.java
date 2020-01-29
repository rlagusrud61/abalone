package tests;

import abalone.Coordinate;
import abalone.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class GroupTest {

    Group group;

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
    }

    @Test
    void step() {
    }

    @Test
    void getMarble1() {

    }

    @Test
    void getMarble2() {
    }

    @Test
    void getMarble3() {
    }

    @Test
    void isInLine() {
    }

    @Test
    void getLineDirection() {
    }

    @Test
    void isMarbleInGroup() {
    }
}