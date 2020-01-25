package abalone;

import java.util.Arrays;

public class BoardStates {

    public static MarbleColor[] getTwoPlayer() {
        MarbleColor[] fields = new MarbleColor[61];

        for (int i = 0; i < fields.length - 1; i++) {
            fields[i] = MarbleColor.EMPTY;
        }
        for (int i = 0; i < 11; i++) {
            fields[i] = MarbleColor.BLACK;
        }
        for (int i = 13; i < 16; i++) {
            fields[i] = MarbleColor.BLACK;
        }
        for (int i = 50; i < fields.length; i++) {
            fields[i] = MarbleColor.WHITE;
        }
        for (int i = 45; i < 48; i++) {
            fields[i] = MarbleColor.WHITE;
        }

        return fields;
    }

    public static MarbleColor[] getThreePlayer() {
        MarbleColor[] fields = new MarbleColor[61];

        //Set all fields to empty
        Arrays.fill(fields, MarbleColor.EMPTY);

        //Set fields to blue
        for (int i = 50; i < fields.length; i++) {
            fields[i] = MarbleColor.BLUE;
        }

        //Set fields to White and Black
        int index = 0;
        for (int i = 0; i < 5; i++) {
            int rowSize = Board.ROW_SIZES[i];

            fields[index] = MarbleColor.WHITE;
            fields[index + 1] = MarbleColor.WHITE;
            fields[index + rowSize - 2] = MarbleColor.BLACK;
            fields[index + rowSize - 1] = MarbleColor.BLACK;
            index += rowSize;
        }

        fields[35] = MarbleColor.WHITE;
        fields[42] = MarbleColor.BLACK;

        return fields;
    }

    public static MarbleColor[] getFourPlayer() {
        MarbleColor[] fields = new MarbleColor[61];

        Arrays.fill(fields, MarbleColor.EMPTY);

        int[] redMarble4 = new int[]{1, 2, 3, 4, 7, 8, 9, 14, 15};
        int[] blackMarble4 = new int[]{11, 17, 18, 24, 25, 26, 33, 34, 35};
        int[] blueMarble4 = new int[]{47, 48, 53, 54, 55, 58, 59, 60, 61};
        int[] whiteMarble4 = new int[]{27, 28, 29, 36, 37, 38, 44, 45, 51};

        for (int i : redMarble4) {
            fields[i - 1] = MarbleColor.RED;
        }
        for (int i : blackMarble4) {
            fields[i - 1] = MarbleColor.BLACK;
        }
        for (int i : blueMarble4) {
            fields[i - 1] = MarbleColor.BLUE;
        }
        for (int i : whiteMarble4) {
            fields[i - 1] = MarbleColor.WHITE;
        }
        return fields;

    }

}
