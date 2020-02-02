package server;

import abalone.Game;
import abalone.Marble;
import abalone.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static protocol.ProtocolMessages.*;

public class Server {

    private List<ClientHandler> handlers;

    private List<GameRequest> twoPlayerQueue = new ArrayList<>();
    private List<GameRequest> threePlayerQueue = new ArrayList<>();
    private List<GameRequest> fourPlayerQueue = new ArrayList<>();

    private List<Game> games = new ArrayList<>();

    private boolean running = false;
    private int port = -1;

    public Server(int port) {
        this.handlers = new ArrayList<>();
        this.port = port;
    }

    public static void main(String[] args) {
        Server server = new Server(8888);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        running = true;

        System.out.println(String.format("Starting server on port %s", port));
        ServerSocket socket = new ServerSocket(port);

        while (running) {
            Socket client = socket.accept();

            System.out.println("Client connected");
            ClientHandler handler = new ClientHandler(client, this);
            handler.start();
            handlers.add(handler);
        }

        System.out.println("Shutting down...");

        // TODO: stop threads?

        socket.close();
    }

    public void sendGameMessage(Game game, String message) {
        List<String> ids = Arrays.stream(game.players).map(Player::getName).collect(Collectors.toList());

        for (ClientHandler handler : handlers) {
            if (ids.contains(handler.getHandlerId())) {
                handler.sendMessage(message);
            }
        }
    }

    public void sendGameNext(Game game, String id) {
        sendGameMessage(game, NEXT + DELIMITER + id);
    }

    public Game getGame(String id) {
        Optional<Game> game = games.stream().filter(g ->
                Arrays.stream(g.players).map(Player::getName).anyMatch(n -> n.equals(id))
        ).findFirst();
        //if some errors happen it's definitely here
        return game.get();
    }

    public Player getPlayer(String id) {
        Game game = getGame(id);
        return Arrays.stream(game.players).filter(p -> p.getName().equals(id)).findFirst().get();
    }

    public void doGameRequest(GameRequest request) {
        if (request.getSize() == 2) twoPlayerQueue.add(request);
        if (request.getSize() == 3) threePlayerQueue.add(request);
        if (request.getSize() == 4) fourPlayerQueue.add(request);

        if (twoPlayerQueue.size() == 2) {
            GameRequest request1 = twoPlayerQueue.get(0);
            GameRequest request2 = twoPlayerQueue.get(1);
            Game game = new Game(
                    new DummyPlayer(request1.getId(), Marble.WHITE),
                    new DummyPlayer(request2.getId(), Marble.BLACK)
            );
            games.add(game);
            twoPlayerQueue.clear();
            System.out.println("Started new 2p game");

            game.reset();
            sendGameMessage(game, START + DELIMITER + String.format("[%s,%s]", request1.getId(), request2.getId()));
            sendGameMessage(game, NEXT + DELIMITER + request1.getId());
        }

        if (threePlayerQueue.size() == 3) {
            GameRequest request1 = threePlayerQueue.get(0);
            GameRequest request2 = threePlayerQueue.get(1);
            GameRequest request3 = threePlayerQueue.get(2);
            Game game = new Game(
                    new DummyPlayer(request1.getId(), Marble.WHITE),
                    new DummyPlayer(request2.getId(), Marble.BLACK),
                    new DummyPlayer(request3.getId(), Marble.BLUE)
            );
            games.add(game);
            threePlayerQueue.clear();
            System.out.println("Started new 3p game");

            game.reset();
            sendGameMessage(game, START + DELIMITER + String.format("[%s,%s,%s]", request1.getId(), request2.getId(), request3.getId()));
            sendGameMessage(game, NEXT + DELIMITER + request1.getId());
        }

        if (fourPlayerQueue.size() == 4) {
            GameRequest request1 = fourPlayerQueue.get(0);
            GameRequest request2 = fourPlayerQueue.get(1);
            GameRequest request3 = fourPlayerQueue.get(2);
            GameRequest request4 = fourPlayerQueue.get(3);
            Game game = new Game(
                    new DummyPlayer(request1.getId(), Marble.WHITE),
                    new DummyPlayer(request2.getId(), Marble.BLACK),
                    new DummyPlayer(request3.getId(), Marble.BLUE),
                    new DummyPlayer(request4.getId(), Marble.RED)
            );
            games.add(game);
            fourPlayerQueue.clear();
            System.out.println("Starting new 4p game");

            game.reset();
            sendGameMessage(game, START + DELIMITER + String.format("[%s,%s,%s,%s]", request1.getId(), request2.getId(), request3.getId(), request4.getId()));
            sendGameMessage(game, NEXT + DELIMITER + request1.getId());
        }
    }

}