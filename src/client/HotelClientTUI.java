package client;

import ss.week7.hotel.exceptions.ExitProgram;
import ss.week7.hotel.exceptions.ServerUnavailableException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class HotelClientTUI implements HotelClientView {

    private HotelClient client;
    private BufferedReader in;

    public HotelClientTUI(HotelClient client) {
        this.client = client;
        this.in = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void start() throws ServerUnavailableException {
        try {
            System.out.println("Input command : ");
            String input = in.readLine();
            while (true) {
                handleUserInput(input);
                System.out.println("Input command : ");
                input = in.readLine();
            }
        } catch (ExitProgram e) {
            client.sendExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void handleUserInput(String input) throws ExitProgram, ServerUnavailableException {
        try {
            String command;
            String param;
            String secparam;

            String[] split = input.split(" ");
            command = split[0];
            param = null;
            secparam = null;

            if (split.length > 1) {
                param = split[1];
                if (split.length > 2) {
                    secparam = split[2];
                }
            }

            switch (command) {
                case "h":
                    printHelpMenu();
                    break;

                case "i":
                    if (param == null) {
                        System.out.println("DO not try to break my server :( ");
                    } else {
                        client.doIn(param);
                    }
                    break;

                case "o":
                    if (param == null) {
                        System.out.println(">:( you cant go out of here");
                    }
                    client.doOut(param);
                    break;

                case "r":
                    if (param == null) {
                        System.out.println("ERROR");
                    }
                    client.doRoom(param);
                    break;

                case "a":
                    if (param == null) {
                        System.out.println("Wrong guest name");
                    }
                    client.doAct(param, secparam);
                    break;


                case "b":
                    if (param == null) {
                        System.out.println("Wrong guest name");
                    }
                    if (secparam == null) {
                        System.out.println("Wrong number of days");
                    }
                    client.doBill(param, secparam);
                    break;

                case "p":
                    client.doPrint();
                    break;

                case "x":
                    throw new ExitProgram("bai");

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

        System.out.println("Welcome to the Hotel booking system of the U Parkhotel");
        System.out.println("Commands:");
        System.out.println("i name ............... check in guest with name");
        System.out.println("o name ............... check out guest with name");
        System.out.println("r name ............... request room of guest");
        System.out.println("a name password....... activate safe, password required for PricedSafe");
        System.out.println("b name nights......... print bill for guest (name) and number of nights");
        System.out.println("h .................... help (this menu)");
        System.out.println("p .................... print state ");
        System.out.println("x .................... exit");
    }
}
