package org.example.chat.client;
import org.example.Controller;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;

    public WriteThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;

        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        Controller controller = client.getController();
        String userName = controller.inputLogin();
        client.setUserName(userName);
        writer.println(userName);

        String text;

        do {
            text = controller.waitMessage();
            writer.println(text);

        } while (!text.equals("Disconnect"));

        try {
            socket.close();
            controller.exit();
        } catch (IOException ex) {
            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
}
