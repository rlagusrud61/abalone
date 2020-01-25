
package server;

import protocol.ProtocolMessages;

import java.io.*;
import java.net.Socket;


/**
 * GameClientHandler for the Abalone application.
 * This class can handle the communication with one
 * client.
 */

public class GameClientHandler implements Runnable {


    /**
     * The socket and In- and OutputStreams
     */

    private BufferedReader in;
    private BufferedWriter out;
    private Socket sock;


    /**
     * The connected HotelServer
     */

    private GameServer srv;


    /**
     * Name of this ClientHandler
     */

    private String name;


    /**
     * Constructs a new HotelClientHandler. Opens the In- and OutputStreams.
     *
     * @param sock The client socket
     * @param srv  The connected server
     * @param name The name of this ClientHandler
     */

    public GameClientHandler(Socket sock, GameServer srv, String name) {
        try {
            in = new BufferedReader(
                    new InputStreamReader(sock.getInputStream()));
            out = new BufferedWriter(
                    new OutputStreamWriter(sock.getOutputStream()));
            this.sock = sock;
            this.srv = srv;
            this.name = name;
        } catch (IOException e) {
            shutdown();
        }
    }


    /**
     * Continuously listens to client input and forwards the input to the
     * {@link #handleCommand(String)} method.
     */

    public void run() {
        String msg;
        try {
            msg = in.readLine();
            while (msg != null) {
                System.out.println("> [" + name + "] Incoming: " + msg);
                handleCommand(msg);
                out.newLine();
                out.flush();
                msg = in.readLine();
            }
            shutdown();
        } catch (IOException e) {
            shutdown();
        }
    }


    private void handleCommand(String msg) throws IOException {


        String[] split = msg.split(ProtocolMessages.DELIMITER);
        String command = split[0];
        String first = null;
        String second = null;

        if (split.length > 1) {
            first = split[1];
            if (split.length > 2) {
                second = split[2];

            }
        }

        switch (command) {
            case "h":
                out.write(srv.getHello(first));
                break;

            case "m":
                if (first == null) {
                    out.write("Move is invalid");
                } else {
                    out.write(srv.doMove(themarbles));
                }
                break;

            case "o":
                if (first == null) {
                    System.out.println("Error Checking out");
                }
                out.write(srv.doOut(first));
                break;

            case "r":
                if (first == null) {
                    System.out.println("ERROR");
                }
                out.write(srv.doRoom(first));
                break;

            case "a":
                if (first == null) {
                    System.out.println("Wrong guest name");
                }
                out.write(srv.doAct(first, second));
                break;


            case "b":
                if (first == null) {
                    System.out.println("Wrong guest name");
                }
                if (second == null) {
                    System.out.println("Wrong number of days");
                }

                out.write(srv.doBill(first, second));
                break;

            case "p":
                out.write(srv.doPrint());
                break;

            case "x":
                shutdown();
                break;

        }
    }

    /**
     * Shut down the connection to this client by closing the socket and
     * the In- and OutputStreams.
     */

    private void shutdown() {
        System.out.println("> [" + name + "] Shutting down.");
        try {
            in.close();
            out.close();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        srv.removeClient(this);
    }
}
