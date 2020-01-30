package protocol;

/**
 * Defines the methods that the Hotel Server should support. The results
 * should be returned to the client.
 *
 * @author Wim Kamerman
 */
public interface ServerProtocol {

    /**
     * Returns a String to be sent as a response to a Client HELLO request,
     * including the name of the hotel: ProtocolMessages.HELLO +
     * ProtocolMessages.DELIMITER + (playerName);
     *
     * @return String to be sent to client as a handshake response.
     */
    public String getHello(String name, int playersAmount);

    String invalid();

    String doStart();

    void doJoin(String name);

    String doMove(int direction, String marbles);

    String nextTurn();

    String doExit();

    String noRematch();
}
