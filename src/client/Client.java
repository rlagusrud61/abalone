package client;

import abalone.*;
import server.DummyPlayer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static protocol.ProtocolMessages.DELIMITER;
import static protocol.ProtocolMessages.HELLO;

public class Client implements Runnable {

    private Game game;
    private String id;
    private GameClientTUI tui;
    private boolean running = false;

    private BufferedReader in;
    private PrintWriter out;

    /**
     * initialises the view of the client
     */
    public Client() {
        tui = new GameClientTUI();
    }
/**
 * starts a new client.
 */
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void run() {
        // User input - connection
        InetAddress ip = null; // tui.getIp();
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int port = 8888; // tui.getInt("Port?");

        Socket socket = null;
        try {
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (running) {
            // Check if in game
            if (game == null) {
                // Make game request
                id = tui.getString("What is your name?");
                int size = tui.getInt("How many players should be in your game?");
                out.println(HELLO + DELIMITER + id + DELIMITER + size);
            }

            String message = null;
            try {
                message = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Print raw message
            System.out.println(String.format("[%s] RAW: %s", "SERVER", message));

            String[] parts = message.split(DELIMITER);
            switch (parts[0]) {
                case "s":
                    List<String> ids = new ArrayList<>();
                    for (String id : parts[1].substring(1, parts[1].length() - 1).split(",")) {
                        ids.add(id);
                    }

                    if (ids.size() == 2) {
                        game = new Game(
                                new DummyPlayer(ids.get(0), Marble.WHITE),
                                new DummyPlayer(ids.get(1), Marble.BLACK)
                        );
                    }

                    if (ids.size() == 3) {
                        game = new Game(
                                new DummyPlayer(ids.get(0), Marble.WHITE),
                                new DummyPlayer(ids.get(1), Marble.BLACK),
                                new DummyPlayer(ids.get(2), Marble.BLUE)
                        );
                    }

                    if (ids.size() == 4) {
                        game = new Game(
                                new DummyPlayer(ids.get(0), Marble.WHITE),
                                new DummyPlayer(ids.get(1), Marble.BLACK),
                                new DummyPlayer(ids.get(2), Marble.BLUE),
                                new DummyPlayer(ids.get(3), Marble.RED)
                        );
                    }

                    game.reset();
                case "n":
                    System.out.println(String.format("[SERVER] It is %s's turn", parts[1]));
                    if (id.equals(parts[1])) {
                        String input = tui.getString("Move?");
                        // TODO validate
                        out.println(input);
                    }

                    break;
                case "m":
                    List<Coordinate> coords = new ArrayList<>();
                    for (String item : parts[2].substring(1, parts[2].length() - 1).split(",")) {
                        coords.add(Board.convertToCoordinate(Integer.parseInt(item)));
                    }

                    Group group = null;
                    if (coords.size() == 1) group = new Group(coords.get(0));
                    if (coords.size() == 2) group = new Group(coords.get(0), coords.get(1));
                    if (coords.size() == 3) group = new Group(coords.get(0), coords.get(1), coords.get(2));

                    Move move = new Move(Move.Direction.intToDirection(Integer.parseInt(parts[1])), group,
                            Arrays.stream(game.players).filter(p -> p.getName().equals(id)).findFirst().get().getTeam());

                    game.doMove(move);

                    // Print board
                    game.update();
                default:
                    System.err.println("Invalid command \"" + message + "\"");
            }
        }
    }

    private void start() {
        running = true;
        run();
    }

}
