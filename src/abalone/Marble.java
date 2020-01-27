package abalone;

public enum Marble {
    EMPTY,
    WHITE,
    BLACK,
    BLUE,
    RED;

//
//    private List<Marble> friendlyMarbles;
//    private List<Marble> enemyMarbles;

    public static final String RESET = "\u001B[0m";


    public String draw() {
        String marble;
        switch (this) {
            case RED:
                marble = "\u001B[31m" + "⚫" + RESET;
                break;
            case BLUE:
                marble = "\u001B[34m" + "⚫" + RESET;
                break;
            case BLACK:
                marble = "\u001B[97m" + "⚫" + RESET;
                break;
            case WHITE:
                marble = "\u001B[30m" + "⚫" + RESET;
                // BTW, BOLD "\033[1;30m" + BLACK  = WHITE??????????????
                break;
            default:
                marble = "⚪" + RESET;
                break;
        }

        return marble;
    }

}




