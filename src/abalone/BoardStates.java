package abalone;

import java.util.Arrays;

public class BoardStates {

    public static void main(String[] args) {

    }

    public static Marble[] getTwoPlayer() {
        Marble[] fields = new Marble[61];

        Arrays.fill(fields, Marble.EMPTY);

        for (int i = 0; i < 11; i++) {
            fields[i] = Marble.WHITE;
        }
        for (int i = 13; i < 16; i++) {
            fields[i] = Marble.WHITE;
        }
        for (int i = 50; i < fields.length; i++) {
            fields[i] = Marble.BLACK;
        }
        for (int i = 45; i < 48; i++) {
            fields[i] = Marble.BLACK;
        }

        return fields;
    }

    public static Marble[] getThreePlayer() {
        Marble[] fields = new Marble[61];

        //Set all fields to empty
        Arrays.fill(fields, Marble.EMPTY);

        //Set fields to blue
        for (int i = 50; i < fields.length; i++) {
            fields[i] = Marble.BLUE;
        }

        //Set fields to White and Black
        int index = 0;
        for (int i = 0; i < 5; i++) {
            int rowSize = Board.ROW_SIZES[i];

            fields[index] = Marble.BLACK;
            fields[index + 1] = Marble.BLACK;
            fields[index + rowSize - 2] = Marble.WHITE;
            fields[index + rowSize - 1] = Marble.WHITE;
            index += rowSize;
        }

        fields[35] = Marble.BLACK;
        fields[42] = Marble.WHITE;

        return fields;
    }

    public static Marble[] getFourPlayer() {
        Marble[] fields = new Marble[61];

        Arrays.fill(fields, Marble.EMPTY);

        int[] whiteMarble4 = new int[]{1, 2, 3, 4, 7, 8, 9, 14, 15};
        int[] blackMarble4 = new int[]{11, 17, 18, 24, 25, 26, 33, 34, 35};
        int[] blueMarble4 = new int[]{47, 48, 53, 54, 55, 58, 59, 60, 61};
        int[] redMarble4 = new int[]{27, 28, 29, 36, 37, 38, 44, 45, 51};

        for (int i : whiteMarble4) {
            fields[i - 1] = Marble.WHITE;
        }
        for (int i : blackMarble4) {
            fields[i - 1] = Marble.BLACK;
        }
        for (int i : blueMarble4) {
            fields[i - 1] = Marble.BLUE;
        }
        for (int i : redMarble4) {
            fields[i - 1] = Marble.RED;
        }
        return fields;

    }

}
