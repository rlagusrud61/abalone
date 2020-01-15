

public class Board {

    public static void main(String[] args) {
        System.out.println("Hello");
    }

    private Marble[] fields;


    public Board() {

        fields = new Marble[61];
        reset();
    }

    private int[] size = new int[]{5, 6, 7, 8, 9, 8, 7, 6, 5};


    public int convertToInt(int[] cord) {
        int result = 0;
        for (int i = 0; i < cord[0]; i++) {
            result += size[i];
        }
        result += cord[2] + 1;
        return result;
    }


    public int[] convertToRowCol(int a) {
        int col = a;
        int row = 0;
        for (int i = 0; i < 9; i++) {
            if (col > size[i]) {
                col = col - size[i];
                row = i;
            }
        }
        int[] result = new int[]{row, col};
        return result;
    }

    public boolean isValidMoveOne(int i) {
        if (fields[i] == Marble.EMPTY) {
            return true;
        } else
            return false;
    }

    public void reset() {
        for (int i = 0; i < fields.length; i++) {
            fields[i] = Marble.EMPTY;
        }
        for (int i = 0; i < 12; i++) {
            fields[i] = Marble.BlACK;
        }
        for (int i = 14; i < 17; i++) {
            fields[i] = Marble.BlACK;
        }
        for (int i = 51; i < fields.length; i++) {
            fields[i] = Marble.WHITE;
        }
        for (int i = 46; i < 48; i++) {
            fields[i] = Marble.WHITE;
        }
    }

}