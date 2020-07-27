package org.example.chat.client;


import org.example.Controller;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private String hostname;
    private int port;
    private Controller controller;

    public Client(String hostname, int port, Controller controller) {
        this.hostname = hostname;
        this.port = port;
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    public static void run(Controller controller) {
        Client client = new Client("localhost", 4004, controller);

        client.execute();
    }



    private void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the server");

            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }
}