package client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public interface ClientView {
    void showMessage(String message);

    InetAddress getIp() throws UnknownHostException;

    String getString(String question);

    int getInt(String question);

    boolean getBoolean(String question);
}
