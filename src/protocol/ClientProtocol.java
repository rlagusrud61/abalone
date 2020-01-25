package protocol;

import exceptions.ProtocolException;
import exceptions.ServerUnavailableException;

/**
 * Defines the methods that the abalone.Game Client should support.
 */
public interface ClientProtocol {


    public void handleHello(String input) throws ServerUnavailableException, ProtocolException;

    public void sendMove(String name, int playerAmount) throws ServerUnavailableException;

    public void sendJoin(String name, int playerAmount) throws ServerUnavailableException;

    void sendRematch(String input);

    void handleGameOver();

    void newGame();

    public void sendExit() throws ServerUnavailableException, ServerUnavailableException;

}
