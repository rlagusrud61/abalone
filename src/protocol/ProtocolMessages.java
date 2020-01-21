package protocol;

/**
 * Protocol for Networked Abalone Game Application.
 *
 * @author Hyeon Kyeong Kim
 */
public class ProtocolMessages {

    /**
     * Delimiter used to separate arguments sent over the network.
     */
    public static final String DELIMITER = ";";

    /**
     * Sent as last line in a multi-line response to indicate the end of the text.
     */
    public static final String EOT = "--EOT--";

    /**
     * Used for the server-client handshake
     */
    public static final char HELLO = 'h';

    /**
     * The following chars are both used by the TUI to receive user input, and the
     * server and client to distinguish messages.
     */
    public static final char JOIN = 'j';
    public static final String INVALID = "iv";
    public static final String START = "start";
    public static final String MOVE = "move";
    public static final String BOARD = "board";
    public static final String NEXT = "next";
    public static final String END = "end";
    public static final char YES = 'y';
    public static final char NO = 'n';
    public static final String NOREMATCH = "norematch";
    public static final String REMATCH = "game";
    public static final String DISCONNECT = "disconnect";

}
