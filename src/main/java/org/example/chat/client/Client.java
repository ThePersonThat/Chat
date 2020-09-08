package org.example.chat.client;


import org.example.chat.client.graphics.controllers.ControllerChat;
import java.net.Socket;

public class Client {
    private ControllerChat controller;
    private Socket socket;

    public Client(Socket socket, ControllerChat controller) {
        this.socket = socket;
        this.controller = controller;
    }

    public ControllerChat getController() {
        return controller;
    }

    public static void run(ControllerChat controller, Socket socket) {
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