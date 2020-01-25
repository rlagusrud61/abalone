package protocol;

import exceptions.ProtocolException;
import exceptions.ServerUnavailableException;

/**
 * Defines the methods that the abalone.Game Client should support.
 *
 * @author Jochem Groen, Hyeon Kyeong Kim
 */
public interface ClientProtocol {

    /**
     * Handles the following server-client handshake: 1. Client sends
     * ProtocolMessages.HELLO to server 2. Server returns one line containing
     * ProtocolMessages.HELLO + ProtocolMessages.DELIMITER + (playerName) + ProtocolMessages.DELIMITER +
     * (amountOfPlayers)
     * <p>
     * This method sends the HELLO and checks whether the server response is valid
     * (must contain HELLO, name of the player and the amount of players). - If the response is not
     * valid, this method throws a ProtocolException. - If the response is valid, a
     * welcome message including the player is forwarded to the view.
     *
     * @throws ServerUnavailableException if IO errors occur.
     * @throws ProtocolException          if the server response is invalid.
     */
    public void handleHello(String input) throws ServerUnavailableException, ProtocolException;

    public void sendMove(String input) throws ServerUnavailableException;

    public void sendJoin(String name) throws ServerUnavailableException;

    public void sendExit() throws ServerUnavailableException, ServerUnavailableException;

}
