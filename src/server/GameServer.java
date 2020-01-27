//package server;
//
//
//import abalone.Game;
//import abalone.Player;
//import exceptions.ExitProgram;
//import protocol.ProtocolMessages;
//import protocol.ServerProtocol;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class GameServer implements Runnable, ServerProtocol {
//
//   /**
//     * The ServerSocket of this HotelServer
//     */
//    private ServerSocket ssock;
//    /**
//     * List of GameClientHandlers, one for each connected client
//     */
//    private List<GameClientHandler> clients;
//    /**
//     * Next client number, increasing for every new connection
//     */
//    private int next_client_no;
//
//    private GameServerTUI view;
//
//    private Game game;
//    /**
//     * Constructs a new HotelServer. Initializes the clients list,
//     * the view and the next_client_no.
//     */
//    public GameServer() {
//        if(clients.size() == 2)
//        game = new Game((Player) clients[0], clients[1]);
//        clients = new ArrayList<>();
//        view = new GameServerTUI();
//        next_client_no = 1;
//
//    }
//
//    /**
//     * Start a new Game Server
//     */
//    public static void main(String[] args) {
//        GameServer server = new GameServer();
//        System.out.println("Welcome to the Abalone Game! Starting...");
//        new Thread(server).start();
//    }
//
//    /**
//     * Opens a new socket by calling {@link #setup()} and starts a new
//     * ClientServerHandler for every connecting client.
//     * If {@link #setup()} throws a ExitProgram exception, stop the program.
//     * In case of any other errors, ask the user whether the setup should be
//     * ran again to open a new socket.
//     */
//    public void run() {
//        boolean openNewSocket = true;
//        while (openNewSocket) {
//            try {
//                setup();
//                while (true) {
//                    Socket sock = ssock.accept();
//                    String name = "Client "
//                            + String.format("%02d", next_client_no++);
//                    view.showMessage("New client [" + name + "] connected!");
//                    GameClientHandler handler =
//                            new GameClientHandler(sock, this, name);
//                    new Thread(handler).start();
//                    clients.add(handler);
//                }
//
//            } catch (ExitProgram e1) {
//                // If setup() throws an ExitProgram exception,
//                // stop the program.
//                openNewSocket = false;
//            } catch (IOException e) {
//                System.out.println("A server IO error occurred: "
//                        + e.getMessage());
//
//                if (!view.getBoolean("Do you want to open a new socket?")) {
//                    openNewSocket = false;
//                }
//            }
//        }
//        view.showMessage("See you later!");
//    }
//
//
//    public void setup() throws ExitProgram {
//        // First, initialize the Hotel.
//        setupGame();
//
//        ssock = null;
//        while (ssock == null) {
//
//            int port = view.getInt("Please enter the server port.");
//
//            // try to open a new ServerSocket
//            try {
//                view.showMessage("Attempting to open a socket at 127.0.0.1 "
//                        + "on port " + port + "...");
//                ssock = new ServerSocket(port, 0,
//                        InetAddress.getByName("127.0.0.1"));
//                view.showMessage("Server started at port " + port);
//            } catch (IOException e) {
//                view.showMessage("ERROR: could not create a socket on "
//                        + "127.0.0.1" + " and port " + port + ".");
//
//                if (!view.getBoolean("Do you want to try again?")) {
//                    throw new ExitProgram("User indicated to exit the "
//                            + "program.");
//                }
//            }
//        }
//    }
//
//    private void setupGame() {
//
//    }
//
//
//    // ------------------ Server Methods --------------------------
//
//    /**
//     * Removes a clientHandler from the client list.
//     *
//     * @requires client != null
//     */
//    public void removeClient(GameClientHandler client) {
//        this.clients.remove(client);
//    }
//
//    @Override
//    public synchronized String getHello(String name, int playersAmount) {
//        return ProtocolMessages.HELLO + ProtocolMessages.DELIMITER + name;
//    }
//
//    @Override
//    public String invalid() {
//        return null;
//    }
//
//
//    @Override
//    public synchronized String doStart() {
//
//    }
//
//    @Override
//    public synchronized String doMove() {
//        player.makeMove();
//    }
//
//    @Override
//    public synchronized String nextTurn() {
//
//    }
//
//    @Override
//    public synchronized String doExit() {
//
//    }
//
//    @Override
//    public synchronized String noRematch() {
//
//    }
//    public List<GameClientHandler> getClients() {
//        return clients;
//    }
//}
