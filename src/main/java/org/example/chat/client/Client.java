package org.example.chat.client;


import org.example.Controller;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
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
        try {
            Client client = new Client("0.tcp.ngrok.io", 19971, controller);

            client.execute();
        } catch (Exception ignored) {}
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