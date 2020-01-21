//package server;
//
//
//import protocol.ServerProtocol;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Server TUI for Networked Hotel Application
// * <p>
// * Intended Functionality: interactively set up & monitor a new server
// *
// * @author Wim Kamerman
// */
//public class GameServer implements Runnable, ServerProtocol {
//
//    /**
//     * The name of the Hotel
//     */
//    private static final String HOTELNAME = "U Parkhotel";
//    /**
//     * The ServerSocket of this HotelServer
//     */
//    private ServerSocket ssock;
//    /**
//     * List of HotelClientHandlers, one for each connected client
//     */
//    private List<GameClientHandler> clients;
//    /**
//     * Next client number, increasing for every new connection
//     */
//    private int next_client_no;
//    /**
//     * The view of this HotelServer
//     */
//    private GameServerTUI view;
//    /**
//     * The HOTEL
//     */
//    private Hotel hotel;
//
//    /**
//     * The Password
//     */
//
//    private Password safePassword;
//
//    /**
//     * Constructs a new HotelServer. Initializes the clients list,
//     * the view and the next_client_no.
//     */
//    public GameServer() {
//        hotel = new Hotel(HOTELNAME);
//        clients = new ArrayList<>();
//        view = new GameServerTUI();
//        next_client_no = 1;
//        safePassword = new Password();
//    }
//
//    /**
//     * Start a new HotelServer
//     */
//    public static void main(String[] args) {
//        GameServer server = new GameServer();
//        System.out.println("Welcome to the Hotel Server! Starting...");
//        new Thread(server).start();
//    }
//
//    /**
//     * Returns the name of the hotel
//     *
//     * @return the name of the hotel.
//     * @requires hotel != null;
//     */
//    public String getHotelName() {
//        return HOTELNAME;
//    }
//
//    /**
//     * Opens a new socket by calling {@link #setup()} and starts a new
//     * HotelClientHandler for every connecting client.
//     * <p>
//     * If {@link #setup()} throws a ExitProgram exception, stop the program.
//     * In case of any other errors, ask the user whether the setup should be
//     * ran again to open a new socket.
//     */
//    public void run() {
//        boolean openNewSocket = true;
//        while (openNewSocket) {
//            try {
//                // Sets up the hotel application
//                setup();
//
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
//    /**
//     * Sets up a new Hotel using {@link #setupHotel()} and opens a new
//     * ServerSocket at localhost on a user-defined port.
//     * <p>
//     * The user is asked to input a port, after which a socket is attempted
//     * to be opened. If the attempt succeeds, the method ends, If the
//     * attempt fails, the user decides to try again, after which an
//     * ExitProgram exception is thrown or a new port is entered.
//     *
//     * @throws ExitProgram if a connection can not be created on the given
//     *                     port and the user decides to exit the program.
//     * @ensures a serverSocket is opened.
//     */
//    public void setup() throws ExitProgram {
//        // First, initialize the Hotel.
//        setupHotel();
//
//        ssock = null;
//        while (ssock == null) {
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
//    /**
//     * Asks the user for a hotel name and initializes
//     * a new Hotel with this name.
//     */
//    public void setupHotel() {
//        view.getString("What is the hotel name?");
//
//
//        // To be implemented.
//    }
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
//    public String getHello() {
//        return ProtocolMessages.HELLO + ProtocolMessages.DELIMITER + HOTELNAME;
//    }
//
//    @Override
//    public synchronized String doIn(String guestName) {
//        Room checkin = hotel.checkIn(guestName);
//        if (guestName == null) {
//            return "Parameter is wrong (guest name is null)";
//        } else if (checkin == null) {
//            return "Checkin unsuccessful (no room assigned)";
//        } else {
//            return "Check in successful for guest " + guestName;
//        }
//    }
//
//    @Override
//    public synchronized String doOut(String guestName) {
//        Room checkout = hotel.checkOut(guestName);
//        if (guestName == null) {
//            return "Parameter is wrong (guest name is null)";
//        } else if (checkout == null) {
//            return "Check out unsuccessful";
//        } else {
//            return "Check out successful for guest " + guestName;
//        }
//    }
//
//    @Override
//    public synchronized String doRoom(String cmd) {
//        if (cmd == null) {
//            return "Parameter is wrong (guest name null)";
//        } else {
//            return "Guest has a room " + hotel.getRoom(cmd);
//        }
//    }
//
//    @Override
//    public synchronized String doAct(String guestName, String password) {
//        Safe safe = hotel.getRoom(guestName).getSafe();
//        if (guestName == null) {
//            return "Parameter is wrong (guest name is null)";
//        }
//        if (password == null) {
//            if (safe instanceof PricedSafe) {
//                return "Parameter is wrong (password required)";
//            } else {
//                safe.activate();
//            }
//            return "Safe has been activated for room " + hotel.getRoom(guestName) + " for guest " + guestName;
//
//        } else {
//
//            if (safe instanceof PricedSafe) {
//                String passwordTest = password;
//
//                if (passwordTest.equals(safePassword.getPassword())) {
//                    PricedSafe pricedSafe = (PricedSafe) safe;
//                    pricedSafe.activate(passwordTest);
//                    return "Safe has been activated for room " + hotel.getRoom(guestName) + " for guest " + guestName;
//                } else {
//                    return "Wrong password. Enter the correct password.";
//                }
//            } else {
//                return "Please don't enter a password for priced safe.";
//            }
//        }
//    }
//
//    @Override
//    public synchronized String doBill(String guestName, String nights) {
//        try {
//            int nightNum = Integer.parseInt(nights);
//            if (this.hotel.getRoom(guestName) instanceof PricedRoom) {
//                Printer p = new StringPrinter();
//                this.hotel.getBill(guestName, nightNum, p).close();
//                view.showMessage(((StringPrinter) p).getResult() + System.lineSeparator() + ProtocolMessages.EOT);
//                return ((StringPrinter) p).getResult() + System.lineSeparator() + ProtocolMessages.EOT;
//            } else {
//                return ("not priced room" + System.lineSeparator() + ProtocolMessages.EOT);
//            }
//        } catch (NumberFormatException e) {
//            return "Second argument is not an integer";
//        }
//
//    }
//
//    // ------------------ Main --------------------------
//
//    @Override
//    public synchronized String doPrint() {
//        view.showMessage("print");
//        Room print = hotel.getFreeRoom();
//        view.showMessage("print");
//        if (print != null) {
//            return "There's a free room in  " + print + "." + System.lineSeparator() + ProtocolMessages.EOT;
//        }
//        return hotel.toString() + System.lineSeparator() + ProtocolMessages.EOT;
//    }
//
//}
