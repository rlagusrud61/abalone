package protocol;

import exceptions.ProtocolException;
import exceptions.ServerUnavailableException;

/**
 * Defines the methods that the Client should support.
 *
 * @author Jochem Groen, Hyeon Kyeong Kim
 */
public interface ClientProtocol {

    /**
     * Handles the following server-client handshake: 1. Client sends
     * ProtocolMessages.HELLO to server 2. Server returns one line containing
     * ProtocolMessages.HELLO + ProtocolMessages.DELIMITER + (hotelName)
     * <p>
     * This method sends the HELLO and checks whether the server response is valid
     * (must contain HELLO and the name of the hotel). - If the response is not
     * valid, this method throws a ProtocolException. - If the response is valid, a
     * welcome message including the hotel name is forwarded to the view.
     *
     * @throws ServerUnavailableException if IO errors occur.
     * @throws ProtocolException          if the server response is invalid.
     */
    public void handleHello() throws ServerUnavailableException, ProtocolException;

    /**
     * Sends a checkIn request to the server.
     * <p>
     * Given the name of a guest, the doIn() method sends the following message to
     * the server: ProtocolMessages.IN + ProtocolMessages.DELIMITER + guestName
     * <p>
     * The result (one line) is then retrieved and forwarded to the view.
     *
     * @param guestName Name of the guest
     * @throws ServerUnavailableException if IO errors occur.
     * @requires guestName != null
     */
    public void doIn(String guestName) throws ServerUnavailableException;

    /**
     * Sends a checkOut request to the server.
     * <p>
     * Given the name of a guest, the doOut() method sends the following message to
     * the server: ProtocolMessages.OUT + ProtocolMessages.DELIMITER + guestName
     * <p>
     * The result (one line) is then retrieved and forwarded to the view.
     *
     * @param guestName Name of the guest
     * @throws ServerUnavailableException if IO errors occur.
     * @requires guestName != null
     */
    public void doOut(String guestName) throws ServerUnavailableException;

    /**
     * Sends a room request to the server.
     * <p>
     * Given the name of a guest, the doRoom() method sends the following message to
     * the server: ProtocolMessages.ROOM + ProtocolMessages.DELIMITER + guestName
     * <p>
     * The result (one line) is then retrieved and forwarded to the view.
     *
     * @param guestName Name of the guest
     * @throws ServerUnavailableException if IO errors occur.
     * @requires guestName != null
     */
    public void doRoom(String guestName) throws ServerUnavailableException;

    /**
     * Sends a safe activation request to the server.
     * <p>
     * Given the name of a guest, the doAct() method sends the following message to
     * the server: ProtocolMessages.ACT + ProtocolMessages.DELIMITER + guestName +
     * ProtocolMessages.DELIMITER + password
     * <p>
     * The result (one line) is then retrieved and forwarded to the view.
     *
     * @param guestName Name of the guest
     * @param password  (Optional) Password in case of a protected safe
     * @throws ServerUnavailableException if IO errors occur.
     * @requires guestName != null
     */
    public void doAct(String guestName, String password) throws ServerUnavailableException;

    /**
     * Requests the bill for a guest at the server.
     * <p>
     * Given the name of a guest and the number of nights of the stay, the doBill()
     * method sends the following message to the server: ProtocolMessages.ACT +
     * ProtocolMessages.DELIMITER + guestName + ProtocolMessages.DELIMITER +
     * password
     * <p>
     * If nights is not an integer or not a positive number, a message is shown in
     * the view and no request is sent to the server.
     * <p>
     * When a request is sent to the server, the result (multiple lines, ending with
     * ProtocolMessages.EOT) is retrieved and forwarded to the view.
     *
     * @param guestName Name of the guest
     * @param nights    Number of nights of the stay
     * @throws ServerUnavailableException if IO errors occur.
     * @requires guestName != null
     * @requires nights to be integer and > 0
     */
    public void doBill(String guestName, String nights) throws ServerUnavailableException;

    /**
     * Requests the state of the hotel at the server. The state contains an overview
     * of the rooms, its guests and the state of the safes.
     * <p>
     * The doPrint() method sends the following message to the server:
     * ProtocolMessages.PRINT
     * <p>
     * The result (multiple lines, ending with ProtocolMessages.EOT) is retrieved
     * and forwarded to the view
     *
     * @throws ServerUnavailableException if IO errors occur.
     */
    public void doPrint() throws ServerUnavailableException;

    /**
     * Sends a message to the server indicating that this client will exit:
     * ProtocolMessages.EXIT;
     * <p>
     * Both the server and the client then close the connection. The client does
     * this using the {@link #closeConnection()} method.
     *
     * @throws ServerUnavailableException if IO errors occur.
     */
    public void sendExit() throws ServerUnavailableException, ServerUnavailableException;

}
