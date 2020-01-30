
package server;


import abalone.Game;
import protocol.ProtocolMessages;
import java.util.List;
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
    private Game game;


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
                System.out.println(msg);
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

        System.out.println(msg);
        String[] split = msg.split(ProtocolMessages.DELIMITER);
        String command = split[0];
        String first = null;
        String second = null;
        String third = null;

        if (split.length > 1) {
            first = split[1];
            if (split.length > 2) {
                second = split[2];

            }

        }

        switch (command) {
            case "h":
                if (isValidName(first) && isValidAmount(second)) {
                    out.write(srv.getHello(first, Integer.parseInt(second)));
                    out.write(srv.doStart());
                    this.name = first;

                }  else {

                    out.write("invalid name or invalid amount of players (must be between 2-4");
                }
                break;

            case "m":
                out.write(first);
                if (first == null || second == null) {
                    out.write("Move is invalid");
                } else {
                    out.write(srv.doMove(Integer.parseInt(first), second));
                }
                break;

            case "g":
                out.write(srv.doStart());

                break;

            case "d":
                out.write(srv.doExit());
                shutdown();
                break;

        }
    }

    private boolean isValidAmount(String playerAmount) {
        int check = Integer.parseInt(playerAmount);
        return check > 1 && check < 5;
    }

    private boolean isValidName(String name) {
        if(!(srv.getClients() == null)) {
            List<GameClientHandler> clients = srv.getClients();
            for (GameClientHandler client : clients) {
                if (client.getName().equals(name)) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
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

    public String getName() {
        return name;
    }
    public Socket getSock() {
        return sock;
    }

    public BufferedReader getInput() {
        return in;
    }
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
       this.game = game;
    }
}