package server;


import abalone.*;
import exceptions.ExitProgram;
import protocol.ProtocolMessages;
import protocol.ServerProtocol;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class GameServer implements Runnable, ServerProtocol {

    /**
     * The ServerSocket of this HotelServer
     */
    private ServerSocket ssock;
    /**
     * List of GameClientHandlers, one for each connected client
     */
    private List<GameClientHandler> clients;
    private List<Game> games = new ArrayList<>();

    private List<GameClientHandler> twoPlayerQueue = new ArrayList<>();
    private List<GameClientHandler> threePlayerQueue = new ArrayList<>();
    private List<GameClientHandler> fourPlayerQueue = new ArrayList<>();


    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    /**
     * Next client number, increasing for every new connection
     */
    private int nextClientNo;

    private GameServerTUI view;


    private Board board;

    private GameServer srv;

    /**
     * Constructs a new HotelServer. Initializes the clients list,
     * the view and the next_client_no.
     */
    public GameServer() {
        srv = this;
        clients = new ArrayList<>();
        view = new GameServerTUI();
        nextClientNo = 1;

    }

    /**
     * Start a new Game Server
     */
    public static void main(String[] args) {
        GameServer server = new GameServer();
        System.out.println("Welcome to the Abalone Game! Starting...");
        new Thread(server).start();
    }

    /**
     * Opens a new socket by calling {@link #setup()} and starts a new
     * ClientServerHandler for every connecting client.
     * If {@link #setup()} throws a ExitProgram exception, stop the program.
     * In case of any other errors, ask the user whether the setup should be
     * ran again to open a new socket.
     */
    public void run() {
        boolean openNewSocket = true;
        while (openNewSocket) {
            try {
                setup();
                while (true) {
                    Socket sock = ssock.accept();
                    String name = "[Client " + nextClientNo + " ]";
                    view.showMessage("New client [" + name + "] connected!");
                    GameClientHandler handler =
                            new GameClientHandler(sock, this, name);

                    clients.add(handler);
                    new Thread(handler).start();
                    System.out.println("do the do join");
                    doJoin(name);
                }

            } catch (ExitProgram e1) {
                // If setup() throws an ExitProgram exception,
                // stop the program.
                openNewSocket = false;
            } catch (IOException e) {
                System.out.println("A server IO error occurred: "
                        + e.getMessage());

                if (!view.getBoolean("Do you want to open a new socket?")) {
                    openNewSocket = false;
                }
            }
        }
        view.showMessage("See you later!");
    }


    public void setup() throws ExitProgram {
        // First, initialize the Hotel.
        setupGame();

        ssock = null;
        while (ssock == null) {

            int port = view.getInt("Please enter the server port.");

            // try to open a new ServerSocket
            try {
                view.showMessage("Attempting to open a socket at 127.0.0.1 "
                        + "on port " + port + "...");
                ssock = new ServerSocket(port, 0,
                        InetAddress.getByName("127.0.0.1"));
                view.showMessage("Server started at port " + port);
            } catch (IOException e) {
                view.showMessage("ERROR: could not create a socket on "
                        + "127.0.0.1" + " and port " + port + ".");

                if (!view.getBoolean("Do you want to try again?")) {
                    throw new ExitProgram("User indicated to exit the "
                            + "program.");
                }
            }
        }
    }

    private void setupGame() {

    }


    // ------------------ Server Methods --------------------------

    /**
     * Removes a clientHandler from the client list.
     *
     * @requires client != null
     */
    public void removeClient(GameClientHandler client) {
        this.clients.remove(client);
    }

    @Override
    public synchronized String getHello(String name, int playersAmount) {

        GameClientHandler newClient = null;
        for (GameClientHandler client : clients) {
            if (client.getName().equals(name)) {
                newClient = client;
            }
        }
        System.out.println(name);

        if (playersAmount == 2) {


            twoPlayerQueue.add(newClient);

        } else if (playersAmount == 3) {


            threePlayerQueue.add(newClient);

        } else if (playersAmount == 4) {
            fourPlayerQueue.add(newClient);
        }

        doJoin(name);

        return ProtocolMessages.HELLO + ProtocolMessages.DELIMITER + name + ProtocolMessages.DELIMITER + playersAmount;
    }

    @Override
    public String invalid() {
        return null;
    }


    @Override
    public synchronized String doStart() {

        if (twoPlayerQueue.size() >= 2) {
            player1 = new HumanPlayer(twoPlayerQueue.get(0).getName(), Marble.WHITE);
            player2 = new HumanPlayer(twoPlayerQueue.get(1).getName(), Marble.BLACK);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Game game = new Game(srv, player1, player2);
                    twoPlayerQueue.get(0).setGame(game);
                    twoPlayerQueue.get(1).setGame(game);
                    twoPlayerQueue.get(0).setPlayerIndex(0);
                    twoPlayerQueue.get(1).setPlayerIndex(1);
                    games.add(game);
                    game.start();
                }
            });
            System.out.println("started");
            thread.start();
            try {
                for (GameClientHandler client : clients) {
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getSock().getOutputStream()));
                    out.write(ProtocolMessages.START + ProtocolMessages.DELIMITER + player1.getName() + ProtocolMessages.DELIMITER + player2.getName());
                    out.newLine();
                    out.flush();
                }
            } catch (IOException e) {
            }
            return ProtocolMessages.START + ProtocolMessages.DELIMITER + player1.getName() + ProtocolMessages.DELIMITER + player2.getName();

        }
        if (threePlayerQueue.size() >= 3) {
            player1 = new HumanPlayer(threePlayerQueue.get(0).getName(), Marble.WHITE);
            player2 = new HumanPlayer(threePlayerQueue.get(1).getName(), Marble.BLACK);
            player3 = new HumanPlayer(threePlayerQueue.get(2).getName(), Marble.BLUE);


            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Game game = new Game(srv, player1, player2, player3);
                    games.add(game);
                    threePlayerQueue.get(0).setGame(game);
                    threePlayerQueue.get(1).setGame(game);
                    threePlayerQueue.get(2).setGame(game);
                    threePlayerQueue.get(0).setPlayerIndex(0);
                    threePlayerQueue.get(1).setPlayerIndex(1);
                    threePlayerQueue.get(2).setPlayerIndex(2);

                    game.start();
                }
            });
            thread.start();
            try {
                for (GameClientHandler client : clients) {
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getSock().getOutputStream()));
                    out.write(ProtocolMessages.START + ProtocolMessages.DELIMITER + player1.getName() + ProtocolMessages.DELIMITER + player2.getName() + ProtocolMessages.DELIMITER + player3.getName());
                    out.newLine();
                    out.flush();
                }
            } catch (IOException e) {
            }
            return ProtocolMessages.START + ProtocolMessages.DELIMITER + player1.getName() + ProtocolMessages.DELIMITER + player2.getName() + ProtocolMessages.DELIMITER + player3.getName();

        }
        if (fourPlayerQueue.size() >= 4) {
            player1 = new HumanPlayer(fourPlayerQueue.get(0).getName(), Marble.WHITE);
            player2 = new HumanPlayer(fourPlayerQueue.get(1).getName(), Marble.BLACK);
            player3 = new HumanPlayer(fourPlayerQueue.get(2).getName(), Marble.BLUE);
            player4 = new HumanPlayer(fourPlayerQueue.get(3).getName(), Marble.RED);


            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Game game = new Game(srv, player1, player2, player3, player4);
                    fourPlayerQueue.get(0).setGame(game);
                    fourPlayerQueue.get(1).setGame(game);
                    fourPlayerQueue.get(2).setGame(game);
                    fourPlayerQueue.get(3).setGame(game);
                    fourPlayerQueue.get(0).setPlayerIndex(0);
                    fourPlayerQueue.get(1).setPlayerIndex(1);
                    fourPlayerQueue.get(2).setPlayerIndex(2);
                    fourPlayerQueue.get(3).setPlayerIndex(3);
                    games.add(game);
                    game.start();
                }
            });
            thread.start();
            try {
                for (GameClientHandler client : clients) {
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getSock().getOutputStream()));
                    out.write(ProtocolMessages.START + ProtocolMessages.DELIMITER + player1.getName() + ProtocolMessages.DELIMITER + player2.getName() + ProtocolMessages.DELIMITER + player3.getName() + ProtocolMessages.DELIMITER + player4.getName());
                    out.newLine();
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
            }
            return ProtocolMessages.START + ProtocolMessages.DELIMITER + player1.getName() + ProtocolMessages.DELIMITER + player2.getName() + ProtocolMessages.DELIMITER + player3.getName() + ProtocolMessages.DELIMITER + player4.getName();

        }
        return "waiting for the game to start";
    }

    @Override
    public synchronized String doMove(String name, int direction, String marbles) {
        String input = direction + ";" + marbles;
    for(GameClientHandler client : clients) {
    if(client.getName().equals(name)) {

          client.getGame().validMove(client.getGame().players[client.getPlayerIndex()].makeChoice(client.getGame().getBoard(),input));
      }



    }


        System.out.println("move is working tho");
        return "doMove function is working";
    }

    @Override
    public synchronized boolean sendTurn(int current) {
        System.out.println("turn sent");
        for(GameClientHandler client : clients) {

                try {
                    System.out.println("turn is alright");
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getSock().getOutputStream()));
                    if(client.getPlayerIndex() == current) {
                        out.write(ProtocolMessages.NEXT);
                        out.newLine();
                        out.flush();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        return true;
    }

    @Override
    public synchronized String doExit() {
        return null;
    }

    @Override
    public synchronized String sendBoard() {
        for(GameClientHandler client : clients) {
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getSock().getOutputStream()));
               out.write(client.getGame().clientUpdate());
               out.newLine();
               out.flush();
                System.out.println("out write not working again");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "sending board";
    }

    @Override
    public synchronized void doJoin(String name) {

        try {
            System.out.println(name);
            if (twoPlayerQueue.contains(name)) {
                for (GameClientHandler client : clients) {
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getSock().getOutputStream()));
                    System.out.println(client);
                    System.out.println("joined");
                    out.write(ProtocolMessages.JOIN + ProtocolMessages.DELIMITER + name);
                    out.newLine();
                    out.flush();

                }

            } else if (threePlayerQueue.contains(name)) {
                for (GameClientHandler client : clients) {
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getSock().getOutputStream()));

                    out.write(ProtocolMessages.JOIN + ProtocolMessages.DELIMITER + name);
                    out.newLine();
                    out.flush();
                }

            } else if (fourPlayerQueue.contains(name)) {
                for (GameClientHandler client : clients) {
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getSock().getOutputStream()));

                    out.write(ProtocolMessages.JOIN + ProtocolMessages.DELIMITER + name);
                    out.newLine();
                    out.flush();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<GameClientHandler> getClients() {
        return clients;
    }
}
