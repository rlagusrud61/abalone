package client;

import exceptions.ExitProgram;
import exceptions.ServerUnavailableException;
import protocol.ProtocolMessages;
import server.GameServer;
import utils.TextIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClientTUI implements GameClientView {

    private GameClient client;
    private BufferedReader in;
    private PrintWriter out;
    private GameServer srv;

    public GameClientTUI(GameClient client) {
        this.client = client;
        this.in = new BufferedReader(new InputStreamReader(System.in));

    }

    @Override
    public void start() throws ServerUnavailableException {

        while (true) {


                System.out.println("wait for the game to start...");
                client.listenToServer();
                while(client.getGameStarted()) {

                System.out.println("input move:");
                String input = TextIO.getlnString();
                try {
                    handleUserInput(input);
                } catch (ExitProgram e) {
                    System.out.println("oops");
                }
            }

        }
    }


    @Override
    public void handleUserInput(String input) throws ExitProgram, ServerUnavailableException {
        try {
            String command;
            String param;
            String secparam;

            String[] split = input.split(ProtocolMessages.DELIMITER);
            command = split[0];
            param = null;
            secparam = null;

            if (split.length > 1) {
                param = split[1];
                if (split.length > 2) {
                    secparam = split[2];
                }
            }
            System.out.println(command);
            System.out.println(param);
            switch (command) {
                case "h":
                    client.start();
                    break;
                case "m":
                    System.out.println("working");
                    client.sendMove(Integer.parseInt(param), secparam);
                    break;
                case "g":
                    client.sendStart();

                case "d":
                    throw new ExitProgram("Exiting the game...");

            }
        } catch (ExitProgram e) {
            client.sendExit();
        } catch (ServerUnavailableException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public InetAddress getIp() {
        String input = getString("Put a valid IP");
        try {
            return InetAddress.getByName(input);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getString(String question) {
        showMessage(question);
        Scanner userInput = new Scanner(System.in);
        if (userInput.hasNextLine()) {
            return userInput.nextLine();
        } else {
            userInput.close();
            getString(question);
        }
        return null;
    }

    @Override
    public int getInt(String question) {
        showMessage(question);
        Scanner userInput = new Scanner(System.in);
        if (userInput.hasNextInt()) {
            return userInput.nextInt();
        } else {
            userInput.close();
            getInt(question);
        }
        return 0;
    }

    @Override
    public boolean getBoolean(String question) {
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        showMessage(question);
        if (input.contentEquals("yes")) {
            return true;
        } else if (input.contentEquals("no")) {
            return false;
        } else {
            userInput.close();
            getBoolean(question);
        }

        return false;
    }

    @Override
    public void printHelpMenu() {

        System.out.println("Welcome to Abalone");
        System.out.println("Commands:");
        System.out.println("h;name;playerAmount ............... connect with server");
        System.out.println("m;direction,marbles ............ move marbles");
        System.out.println("g;name;playerAmount ............... start new game");
        System.out.println("d ....... disconnect from server");

    }

}
