//
//package server;
//import protocol.ProtocolMessages;
//
//import java.io.*;
//import java.net.Socket;
//
//
///**
// * HotelClientHandler for the Hotel Server application.
// * This class can handle the communication with one
// * client.
// *
// * @author Wim Kamerman
// */
//
//public class GameClientHandler implements Runnable {
//
//
//    /**
//     * The socket and In- and OutputStreams
//     */
//
//    private BufferedReader in;
//    private BufferedWriter out;
//    private Socket sock;
//
//
//    /**
//     * The connected HotelServer
//     */
//
//    private GameServer srv;
//
//
//    /**
//     * Name of this ClientHandler
//     */
//
//    private String name;
//
//
//    /**
//     * Constructs a new HotelClientHandler. Opens the In- and OutputStreams.
//     *
//     * @param sock The client socket
//     * @param srv  The connected server
//     * @param name The name of this ClientHandler
//     */
//
//    public GameClientHandler(Socket sock, GameServer srv, String name) {
//        try {
//            in = new BufferedReader(
//                    new InputStreamReader(sock.getInputStream()));
//            out = new BufferedWriter(
//                    new OutputStreamWriter(sock.getOutputStream()));
//            this.sock = sock;
//            this.srv = srv;
//            this.name = name;
//        } catch (IOException e) {
//            shutdown();
//        }
//    }
//
//
//    /**
//     * Continuously listens to client input and forwards the input to the
//     * {@link #handleCommand(String)} method.
//     */
//
//    public void run() {
//        String msg;
//        try {
//            msg = in.readLine();
//            while (msg != null) {
//                System.out.println("> [" + name + "] Incoming: " + msg);
//                handleCommand(msg);
//                out.newLine();
//                out.flush();
//                msg = in.readLine();
//            }
//            shutdown();
//        } catch (IOException e) {
//            shutdown();
//        }
//    }
//
//
//    /**
//     * Handles commands received from the client by calling the according
//     * methods at the HotelServer. For example, when the message "i Name"
//     * is received, the method doIn() of HotelServer should be called
//     * and the output must be sent to the client.
//     * <p>
//     * If the received input is not valid, send an "Unknown Command"
//     * message to the server.
//     *
//     * @param msg command from client
//     * @throws IOException if an IO errors occur.
//     */
//
//    private void handleCommand(String msg) throws IOException {
//
//        String command;
//        String param;
//        String secparam;
//
//        String[] split = msg.split(ProtocolMessages.DELIMITER);
//        command = split[0];
//        param = null;
//        secparam = null;
//
//        if (split.length > 1) {
//            param = split[1];
//            if (split.length > 2) {
//                secparam = split[2];
//            }
//        }
//
//        switch (command) {
//            case "h":
//                out.write(srv.getHello());
//                break;
//
//            case "i":
//                if (param == null) {
//                    out.write("Guest name is invalid");
//                } else {
//                    out.write(srv.doIn(param));
//                }
//                break;
//
//            case "o":
//                if (param == null) {
//                    System.out.println("Error Checking out");
//                }
//                out.write(srv.doOut(param));
//                break;
//
//            case "r":
//                if (param == null) {
//                    System.out.println("ERROR");
//                }
//                out.write(srv.doRoom(param));
//                break;
//
//            case "a":
//                if (param == null) {
//                    System.out.println("Wrong guest name");
//                }
//                out.write(srv.doAct(param, secparam));
//                break;
//
//
//            case "b":
//                if (param == null) {
//                    System.out.println("Wrong guest name");
//                }
//                if (secparam == null) {
//                    System.out.println("Wrong number of days");
//                }
//
//                out.write(srv.doBill(param, secparam));
//                break;
//
//            case "p":
//                out.write(srv.doPrint());
//                break;
//
//            case "x":
//                shutdown();
//                break;
//
//        }
//    }
//
//    /**
//     * Shut down the connection to this client by closing the socket and
//     * the In- and OutputStreams.
//     */
//
//    private void shutdown() {
//        System.out.println("> [" + name + "] Shutting down.");
//        try {
//            in.close();
//            out.close();
//            sock.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        srv.removeClient(this);
//    }
//}
