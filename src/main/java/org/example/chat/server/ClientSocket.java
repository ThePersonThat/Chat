package org.example.chat.server;

import org.example.chat.client.Message;

import java.io.*;
import java.net.Socket;


// TODO: if you will have time correct the bug when the user write server-command: disconnect


public class ClientSocket extends Thread {
    private Socket socket;
    private Server server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String userName;

    public ClientSocket(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            Message message;

            while (true) {
                message = (Message) in.readObject();
                server.sendMessage(message, this);
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


}
