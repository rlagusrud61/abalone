package client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTUI implements ClientView {

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


}
