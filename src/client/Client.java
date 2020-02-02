package client;

import abalone.*;
import protocol.ClientProtocol;
import server.DummyPlayer;
import utils.TextIO;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static protocol.ProtocolMessages.*;

public class Client implements Runnable, ClientProtocol {

    // Game info
    private String id;
    private Game game;

    // Client utils
    private ClientTUI view;
    private boolean running = false;

    // Server connection
    private Socket socket = null;
    private BufferedReader in;
    private PrintWriter out;

    // Server info
    private InetAddress ip;
    private int port = -1;

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public Client() {
        view = new ClientTUI();
    }

    @Override
    public void run() {
        while (running) {
            // User input - connection
            if (ip == null) {
                try {
                    ip = view.getIp();
                } catch (UnknownHostException e) {
                    System.err.println("Invalid ip address");
                    continue;
                }
            }

            if (port == -1) {
                port = view.getInt("Enter a valid port number.");
            }

            if (socket == null || socket.isClosed()) {
                try {
                    socket = new Socket(ip, port);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                } catch (IOException e) {
                    System.err.println(String.format("Couldn't connect to server at %s:%s", ip, port));
                    ip = null;
                    port = -1;
                    continue;
                } catch (IllegalArgumentException e) {
                    System.err.println(String.format("Invalid port %s", port));
                    ip = null;
                    port = -1;
                    continue;
                }
            }

            takeCommands();
        }
    }


    public void takeCommands() {
        // Check if in game
        if (game == null) {
            // Make game request
            id = view.getString("What is your name?");
            int size = view.getInt("How many players should be in your game?");
            out.println(HELLO + DELIMITER + id + DELIMITER + size);
            view.showMessage("Please wait until the game starts...");
        }

        String message = null;
        try {
            message = in.readLine();

            if (message == null) {
                System.err.println("Server closed connection");
                close();
                return;
            }
        } catch (IOException e) {
            System.err.println("Lost connection to server");
            socket = null;
            return;
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
                    boolean isValidArg = true;
                    Player player = null;
                    for (Player p : game.players) {
                        if (p.getName().equals(id)) {
                            player = p;
                        }
                    }

                    String prompt = "> " + player.getName() + " (" + player.getMarble().toString() + ")"
                            + ", what is your choice? Give direction (0-5), and a ; , "
                            + "  and list the marbles separated by commas (without space).";
                    System.out.println(prompt);
                    do {
                        String text = TextIO.getln();
                        int command = -1;
                        String marbles;
                        Integer[] marbleSplit = null;
                        try {
                            command = Integer.parseInt(text.split(";")[0]);
                            marbles = text.split(";")[1];
                            marbleSplit = Arrays.stream(marbles.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
                            isValidArg = true;
                        } catch (IllegalArgumentException e) {
                            isValidArg = false;
                            System.out.println("Not a valid argument! Write again");
                        }
                        if (isValidArg) {
                            Move.Direction direction;
                            switch (command) {
                                case 0:
                                    direction = Move.Direction.NE;
                                    break;
                                case 1:
                                    direction = Move.Direction.E;
                                    break;
                                case 2:
                                    direction = Move.Direction.SE;
                                    break;
                                case 3:
                                    direction = Move.Direction.SW;
                                    break;
                                case 4:
                                    direction = Move.Direction.W;
                                    break;
                                case 5:
                                    direction = Move.Direction.NW;
                                    break;
                                default:
                                    throw new IllegalArgumentException("NOpe");
                            }

                            int firstMarble = marbleSplit[0];
                            Coordinate firstMarbleCoordinate = Board.convertToCoordinate(firstMarble);
                            Group soloGroup = new Group(firstMarbleCoordinate);
                            Move choice = new Move(direction, soloGroup, player.getTeam());
                            if (marbleSplit.length > 1) {
                                int secondMarble = marbleSplit[1];
                                Coordinate secondMarbleCoordinate = Board.convertToCoordinate(secondMarble);
                                Group pairGroup = new Group(firstMarbleCoordinate, secondMarbleCoordinate);
                                choice = new Move(direction, pairGroup, player.getTeam());

                                if (marbleSplit.length > 2) {
                                    int thirdMarble = marbleSplit[2];
                                    Coordinate thirdMarbleCoordinate = Board.convertToCoordinate(thirdMarble);
                                    Group trioGroup = new Group(firstMarbleCoordinate, secondMarbleCoordinate,
                                            thirdMarbleCoordinate);
                                    choice = new Move(direction, trioGroup, player.getTeam());
                                }
                            }

                            out.println(MOVE + DELIMITER + choice.getDirection().val + DELIMITER +
                                    String.format("[%s]", choice.getGroup().getMarbles().stream().map(c -> String.valueOf(Board.convertToInt(c))).collect(Collectors.joining(","))));
                        }
                    } while (!isValidArg);
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
                break;
            case "d":
                System.err.println("A player disconnected from your game!");

                try {
                    close();
                } catch (IOException e) {
                    System.err.println("Connection lost with server, couldn't close connection");
                }

                return;
            case "x":
                System.out.println("Game is Over!");
                try {
                    close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.err.println("Invalid command \"" + message + "\"");
        }
    }

    @Override
    public void start() {
        running = true;
        run();
    }

    public void close() throws IOException {
        game = null;

        in.close();
        out.close();
        socket.close();
    }

}
