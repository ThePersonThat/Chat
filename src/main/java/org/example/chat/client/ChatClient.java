package org.example.chat.client;

import org.example.Controller;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    private String hostname;
    private int port;
    private String userName;
    private Controller controller;

    public ChatClient(String hostname, int port, Controller controller) {
        this.hostname = hostname;
        this.port = port;
        this.controller = controller;
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server");

            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }

    public Controller getController() {
        return controller;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public static void run(Controller controller) {
        String hostname = "localhost";
        int port = 4004;

        ChatClient client = new ChatClient(hostname, port, controller);
        client.execute();
    }
}
