package org.example.chat.client;


import org.example.chat.client.graphics.Controller;
import java.net.Socket;

public class Client {
    private Controller controller;
    private Socket socket;

    public Client(Socket socket, Controller controller) {
        this.socket = socket;
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    public static void run(Controller controller, Socket socket) {
        try {
            Client client = new Client(socket, controller);

            client.execute();
        } catch (Exception ignored) {}
    }



    private void execute() {
        System.out.println("Connected to the server");

        new ReadThread(socket, this).start();
        new WriteThread(socket, this).start();
    }
}