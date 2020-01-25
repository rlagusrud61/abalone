package server;

import java.io.PrintWriter;
import java.util.Scanner;


/**
 * Hotel Server TUI for user input and user messages
 *
 * @author Wim Kamerman
 */
public class GameServerTUI implements GameServerView {

    /**
     * The PrintWriter to write messages to
     */
    private PrintWriter console;

    /**
     * Constructs a new HotelServerTUI. Initializes the console.
     */
    public GameServerTUI() {
        console = new PrintWriter(System.out, true);
    }

    @Override
    public void showMessage(String message) {
        console.println(message);
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
        if (input.contentEquals("y")) {
            return true;
        } else if (input.contentEquals("n")) {
            return false;
        } else {
            userInput.close();
            getBoolean(question);
        }

        return false;
    }

}
