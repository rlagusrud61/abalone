package client;

import java.net.InetAddress;

public interface ClientView {
    void showMessage(String message);

    InetAddress getIp();

    String getString(String question);

    int getInt(String question);

    boolean getBoolean(String question);
}
