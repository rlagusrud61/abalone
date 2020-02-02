package client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClientTUI {

    public void showMessage(String message) {
        System.out.println(message);
    }

    public InetAddress getIp() {
        String input = getString("Put a valid IP");
        try {
            return InetAddress.getByName(input);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

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

    public void printHelpMenu() {

        System.out.println("Welcome to Abalone");
        System.out.println("Commands:");
        System.out.println("h;name;playerAmount ............... connect with server");
        System.out.println("m;direction,marbles ............ move marbles");
        System.out.println("g;name;playerAmount ............... start new game");
        System.out.println("d ....... disconnect from server");

    }

}
