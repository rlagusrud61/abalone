package protocol;

/**
 * Protocol for Networked abalone.Abalone abalone.Game Application.
 *
 * @author Hyeon Kyeong Kim
 */
public class ProtocolMessages {
    /**
     * Delimiter used to separate arguments sent over the network.
     */
    public static final String DELIMITER = ";";
    /**
     * Used for the server-client handshake
     */
    public static final char HELLO = 'h';

    /**
     * The following chars are both used by the TUI to receive user input, and the
     * server and client to distinguish messages.
     */
    public static final char JOIN = 'j';
    public static final char INVALID = 'e';
    public static final char START = 'g';
    public static final char MOVE = 'm';
    public static final char BOARD = 'b';
    public static final char NEXT = 'n';
    public static final char EXIT = 'x';
    public static final char GAME = 'g';
    public static final char DISCONNECT = 'd';

}
