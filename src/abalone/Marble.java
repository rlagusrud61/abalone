package abalone;

public enum Marble {
    EMPTY,
    RED,
    BLUE,
    BLACK,
    WHITE;


    public static final String RESET = "\u001B[0m";

    public String draw() {
        String marble = null;
        switch (this) {
            case RED:
                marble = "\u001B[31m" + "⚫" + RESET;
                break;
            case BLUE:
                marble = "\u001B[34m" + "⚫" + RESET;
                break;
            case BLACK:
                marble = "\u001B[30m" + "⚫" + RESET;
                break;
            case WHITE:
                marble = "\u001B[97m" + "⚫" + RESET;
                // BTW, BOLD "\033[1;30m" + BLACK  = WHITE??????????????
                break;
            default:
                marble = "⚪" + RESET;
                break;
        }

        return marble;
    }

}




