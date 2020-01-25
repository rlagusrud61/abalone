package protocol;

import exceptions.ProtocolException;
import exceptions.ServerUnavailableException;

/**
 * Defines the methods that the abalone.Game Client should support.
 */
public interface ClientProtocol {


    public void handleHello(String input) throws ServerUnavailableException, ProtocolException;

    public void sendMove(String input);

    public void sendJoin(String name) throws ServerUnavailableException;

    void sendRematch(String input);

    void handleGameOver();

    void newGame();

    public void sendExit() throws ServerUnavailableException, ServerUnavailableException;

}
