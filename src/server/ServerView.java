package server;

public interface ServerView {
    void showMessage(String message);

    String getString(String question);

    int getInt(String question);

    boolean getBoolean(String question);
}
