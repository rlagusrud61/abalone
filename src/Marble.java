
public enum Marble {
    EMPTY,
    RED,
    BLUE,
    BLACK,
    WHITE;


    public static void main(String[] args) {
        System.out.println(Marble.EMPTY);
        System.out.println(Marble.RED);
        System.out.println(Marble.BLUE);
        System.out.println(Marble.WHITE);
        System.out.println(Marble.BLACK);

    }

    @Override
    public String toString() {
        String marble = null;
        String RESET = "\u001B[0m";
        switch (this) {
            case EMPTY:
                marble = "⚪" + RESET;
                break;
            case RED:
                marble = "\u001B[31m" + "⚫" + RESET;
                break;
            case BLUE:
                marble = "\u001B[34m" + "⚫" + RESET;
                break;
            case WHITE:
                marble = "\u001B[30m" + "⚫" + RESET;
                break;
            case BLACK:
                marble = "\u001B[97m" + "⚫" + RESET;
                // BTW, BOLD "\033[1;30m" + BLACK  = WHITE??????????????
                break;
        }

        return marble;
    }
}




