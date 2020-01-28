package Tests;

import static  org.junit.jupiter.api.Assertions.*;

import abalone.Board;
import abalone.Coordinate;
import org.junit.jupiter.api.*;



class BoardTest {
    int index;
    Board board= new Board();
    Coordinate coordindex;
    @BeforeEach
    void setUp() {
     index = 10;
    }

    @Test
    void convertToIntAndConvertToCoordinate() {
      coordindex =  board.convertToCoordinate(index)   ;
      assertEquals(board.convertToInt(coordindex), 10);
    }


    @Test
    void makeMove() {
        //TODO
    }
}