package tests;

import abalone.Board;
import abalone.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BoardTest {
    int index;
    Board board = new Board();
    Coordinate coordinate;

    @BeforeEach
    void setUp() {

        index = 10;
    }

    @Test
    void convertToIntAndConvertToCoordinate() {
        coordinate = board.convertToCoordinate(index);
        assertEquals(board.convertToInt(coordinate), 10);
    }


    @Test
    void makeMove() {
        //TODO
    }

    @Test
    void name() {
    }
}