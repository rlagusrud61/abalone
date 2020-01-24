package client;

import exceptions.ExitProgram;
import exceptions.ProtocolException;
import exceptions.ServerUnavailableException;
import protocol.ClientProtocol;
import protocol.ProtocolMessages;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client for Networked Hotel Application.
 *
 * @author Wim Kamerman
 */
public class GameClient implements ClientProtocol {

    private Socket serverSock;
    private BufferedReader in;
    private BufferedWriter out;
    private GameClientTUI view;

    /**
     * Constructs a new HotelClient. Initialises the view.
     */
    public GameClient() {
        view = new GameClientTUI(this);
    }

    /**
     * This method starts a new HotelClient.
     *
     * @param args
     */
    public static void main(String[] args) throws ExitProgram, ProtocolException, ServerUnavailableException {
        (new GameClient()).start();
    }

    /**
     * Starts a new HotelClient by creating a connection, followed by the
     * HELLO handshake as defined in the protocol. After a successful
     * connection and handshake, the view is started. The view asks for
     * used input and handles all further calls to methods of this class.
     * <p>
     * When errors occur, or when the user terminates a server connection, the
     * user is asked whether a new connection should be made.
     */
    public void start() {
        try {
            createConnection();
            handleHello();
            view.start();
        } catch (ExitProgram e) {

        } catch (ServerUnavailableException e) {
            System.out.println("Server is unavailable at the moment!");
        } catch (ProtocolException e) {
            System.out.println("Protocol Message invalid");
        }
        view.showMessage("Do you want a new connection ? y/n");
    }

    /**
     * Creates a connection to the server. Requests the IP and port to
     * connect to at the view (TUI).
     * <p>
     * The method continues to ask for an IP and port and attempts to connect
     * until a connection is established or until the user indicates to exit
     * the program.
     *
     * @throws ExitProgram if a connection is not established and the user
     *                     indicates to want to exit the program.
     * @ensures serverSock contains a valid socket connection to a server
     */
    public void createConnection() throws ExitProgram {
        clearConnection();
        while (serverSock == null) {
            String host = "127.0.0.1";
            int port = 8888;

            // try to open a Socket to the server
            try {
                InetAddress addr = InetAddress.getByName(host);
                System.out.println("Attempting to connect to " + addr + ":"
                        + port + "...");
                serverSock = new Socket(addr, port);
                in = new BufferedReader(new InputStreamReader(
                        serverSock.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(
                        serverSock.getOutputStream()));
            } catch (IOException e) {
                System.out.println("ERROR: could not create a socket on "
                        + host + " and port " + port + ".");

                //Do you want to try again? (ask user, to be implemented)
                if (false) {
                    throw new ExitProgram("User indicated to exit.");
                }
            }
        }
    }

    /**
     * Resets the serverSocket and In- and OutputStreams to null.
     * <p>
     * Always make sure to close current connections via shutdown()
     * before calling this method!
     */
    public void clearConnection() {
        serverSock = null;
        in = null;
        out = null;
    }

    /**
     * Sends a message to the connected server, followed by a new line.
     * The stream is then flushed.
     *
     * @param msg the message to write to the OutputStream.
     * @throws ServerUnavailableException if IO errors occur.
     */
    public synchronized void sendMessage(String msg)
            throws ServerUnavailableException {

        if (out != null) {
            try {
                out.write(msg);
                out.newLine();
                out.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new ServerUnavailableException("Could not write "
                        + "to server.");
            }
        } else {
            throw new ServerUnavailableException("Could not write "
                    + "to server.");
        }
    }

    /**
     * Reads and returns one line from the server.
     *
     * @return the line sent by the server.
     * @throws ServerUnavailableException if IO errors occur.
     */
    public String readLineFromServer()
            throws ServerUnavailableException {
        if (in != null) {
            try {
                // Read and return answer from Server
                String answer = in.readLine();
                if (answer == null) {
                    throw new ServerUnavailableException("Could not read "
                            + "from server.");
                }
                return answer;
            } catch (IOException e) {
                throw new ServerUnavailableException("Could not read "
                        + "from server.");
            }
        } else {
            throw new ServerUnavailableException("Could not read "
                    + "from server.");
        }
    }

    /**
     * Reads and returns multiple lines from the server until the end of
     * the text is indicated using a line containing ProtocolMessages.EOT.
     *
     * @return the concatenated lines sent by the server.
     * @throws ServerUnavailableException if IO errors occur.
     */
    public String readMultipleLinesFromServer()
            throws ServerUnavailableException {
        if (in != null) {
            try {
                // Read and return answer from Server
                StringBuilder sb = new StringBuilder();
                for (String line = in.readLine(); line != null
                        && !line.equals(ProtocolMessages.EOT);
                     line = in.readLine()) {
                    sb.append(line + System.lineSeparator());
                }
                return sb.toString();
            } catch (IOException e) {
                throw new ServerUnavailableException("Could not read "
                        + "from server.");
            }
        } else {
            throw new ServerUnavailableException("Could not read "
                    + "from server.");
        }
    }

    /**
     * Closes the connection by closing the In- and OutputStreams, as
     * well as the serverSocket.
     */
    public void closeConnection() {
        System.out.println("Closing the connection...");
        try {
            in.close();
            out.close();
            serverSock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleHello()
            throws ServerUnavailableException, ProtocolException {
        sendMessage(String.valueOf(ProtocolMessages.HELLO));
        if (readLineFromServer().contains(String.valueOf(ProtocolMessages.HELLO))) {
            System.out.println("Welcome to the Hotel booking system of hotel! Press 'h' for help menu: ");
        } else {
            throw new ProtocolException("Can't do the handshake");
        }
    }

    @Override
    public void doMove() throws ServerUnavailableException {
        sendMessage(ProtocolMessages.IN + ProtocolMessages.DELIMITER + guestName);
        System.out.println("> " + readLineFromServer());
    }


    @Override
    public void doOut(String guestName) throws ServerUnavailableException {
        sendMessage(ProtocolMessages.OUT + ProtocolMessages.DELIMITER + guestName);
        System.out.println("> " + readLineFromServer());
    }

    @Override
    public void doRoom(String guestName) throws ServerUnavailableException {
        sendMessage(ProtocolMessages.ROOM + ProtocolMessages.DELIMITER + guestName);
        System.out.println("> " + readLineFromServer());
    }

    @Override
    public void doAct(String guestName, String password)
            throws ServerUnavailableException {
        sendMessage(ProtocolMessages.ACT + ProtocolMessages.DELIMITER + guestName + ProtocolMessages.DELIMITER + password);
        System.out.println("> " + readLineFromServer());
    }

    @Override
    public void doBill(String guestName, String nights)
            throws ServerUnavailableException {
        boolean valid = true;
        int night = 0;
        try {
            night = Integer.parseInt(nights);
        } catch (NumberFormatException e) {
            valid = false;
        }
        if (valid && night > 0) {

            sendMessage(ProtocolMessages.BILL + ProtocolMessages.DELIMITER + guestName + ProtocolMessages.DELIMITER + nights);
            System.out.println("> " + readMultipleLinesFromServer());
        } else {
            System.out.println("Night is invalid");
        }
    }

    @Override
    public void doPrint() throws ServerUnavailableException {
        sendMessage(String.valueOf(ProtocolMessages.PRINT));
        System.out.println("> " + readMultipleLinesFromServer());
    }

    @Override
    public void sendExit() throws ServerUnavailableException {
        sendMessage(String.valueOf(ProtocolMessages.EXIT));
        closeConnection();
    }

}
