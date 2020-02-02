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

    private ServerTUI view = new ServerTUI();

    private List<ClientHandler> handlers;

    private List<GameRequest> twoPlayerQueue = new ArrayList<>();
    private List<GameRequest> threePlayerQueue = new ArrayList<>();
    private List<GameRequest> fourPlayerQueue = new ArrayList<>();

    private List<Game> games = new ArrayList<>();

    private boolean running = false;
    private int port = -1;

    public Server() {
        this.handlers = new ArrayList<>();
    }

    public synchronized static void main(String[] args) {
        Server server = new Server();
        server.start();
    }


    public void start() {
        running = true;

        ServerSocket socket = null;

        while (socket == null) {
            port = view.getInt("Put a valid port number.");
            System.out.println(String.format("Starting server on port %s", port));

            try {
                socket = new ServerSocket(port);
            } catch (IOException e) {
                System.err.println(String.format("Could not bind to port %s", port));
            } catch (IllegalArgumentException e) {
                System.err.println(String.format("Invalid port %s", port));
            }
        }

        while (running) {
            Socket client = null;
            try {
                client = socket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("A new client connected!");
            ClientHandler handler = new ClientHandler(client, this);
            handler.start();
            handlers.add(handler);
        }

        System.out.println("Shutting down...");
        // TODO: stop threads?
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendGameMessage(Game game, String message) {
        List<String> ids = Arrays.stream(game.players).map(Player::getName).collect(Collectors.toList());

        for (ClientHandler handler : handlers) {
            if (ids.contains(handler.getHandlerId())) {
                handler.sendMessage(message);
            }
        }
    }

    public synchronized void sendGameNext(Game game, String id) {
        sendGameMessage(game, NEXT + DELIMITER + id);
    }

    public synchronized Game getGame(String id) {
        Optional<Game> game = games.stream().filter(g ->
                Arrays.stream(g.players).map(Player::getName).anyMatch(n -> n.equals(id))
        ).findFirst();
        return game.get();
    }

    public synchronized Player getPlayer(String id) {
        Game game = getGame(id);
        return Arrays.stream(game.players).filter(p -> p.getName().equals(id)).findFirst().get();
    }

    public synchronized void doGameRequest(GameRequest request) {
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

    public synchronized void removeClient(ClientHandler client) {
        this.handlers.remove(client);
    }

}

