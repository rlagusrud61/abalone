package abalone;

public enum Marble {
    EMPTY,
    WHITE,
    BLACK,
    BLUE,
    RED;

    public static final String RESET = "\u001B[0m";

    /**
     * Prints the marble according to its color.
     * This is to facilitate the view and the interaction of the users with the game.
     *
     * @return colored marbles to be printed on the console
     */
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
                break;
            default:
                marble = "⚪" + RESET;
                break;
        }

        return marble;
    }

}




