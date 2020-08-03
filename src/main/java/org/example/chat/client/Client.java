package org.example.chat.client;


import org.example.Controller;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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