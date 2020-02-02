package protocol;

import client.Client;

public interface ClientProtocol extends Runnable {
    static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    void run();

    void start();
}
