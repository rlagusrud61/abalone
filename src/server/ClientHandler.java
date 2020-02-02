package server;

import abalone.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static protocol.ProtocolMessages.DELIMITER;

public class ClientHandler extends Thread {

    private Server server;
    private String id = "UNKNOWN";

    private boolean running = false;

    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket, Server server) {
        this.server = server;

        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        running = true;

        while (running) {
            String message = null;

            try {
                System.out.println("Listening to the client...");
                message = in.readLine();
            } catch (IOException e) {
                System.err.println("Something went wrong while listening to client " + id);
                running = false;
                break;
            }

            // Print raw message
            System.out.println(String.format("[%s] RAW: %s", this.id, message));

            // Handle command
            String[] parts = message.split(DELIMITER);
            switch (parts[0]) {
                case "m":
                    Game game = server.getGame(id);

                    List<Coordinate> coords = new ArrayList<>();
                    for (String item : parts[2].substring(1, parts[2].length() - 1).split(",")) {
                        coords.add(Board.convertToCoordinate(Integer.parseInt(item)));
                    }

                    Group group = null;
                    if (coords.size() == 1) group = new Group(coords.get(0));
                    if (coords.size() == 2) group = new Group(coords.get(0), coords.get(1));
                    if (coords.size() == 3) group = new Group(coords.get(0), coords.get(1), coords.get(2));

                    Move move = new Move(Move.Direction.intToDirection(Integer.parseInt(parts[1])), group,
                            server.getPlayer(id).getTeam());

                    boolean isValid = game.doMove(move);

                    if (isValid) {
                        server.sendGameMessage(game, message);
                    }

                    // Give new turn
                    String nextPlayer = game.players[game.current].getName();
                    server.sendGameNext(game, nextPlayer);

                    break;
                case "h":
                    this.id = parts[1];
                    System.out.println(String.format("[%s] GAME_REQUEST %s", this.id, parts[2]));

                    server.doGameRequest(new GameRequest(this.id, Integer.parseInt(parts[2])));
                    break;
                default:
                    System.err.println("Invalid command \"" + message + "\"");
                    break;
            }
        }

        close();
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String getHandlerId() {
        return id;
    }

    private void close() {
        try {
            this.in.close();
            this.out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
