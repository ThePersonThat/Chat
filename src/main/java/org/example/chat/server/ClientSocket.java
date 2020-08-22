package org.example.chat.server;

import org.example.chat.client.message.Message;
import org.example.chat.server.database.SQLQuery;

import java.io.*;
import java.net.Socket;


public class ClientSocket extends Thread {
    private Socket socket;
    private Server server;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientSocket(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            SQLQuery query = server.getQuery();
            StoryMessage history = new StoryMessage();
            query.setHistory(history);
            Message message;

            for(int i = 0; i < history.getSize(); i++) {
                sendMessage(history.getMessage(i));
            }

            while (true) {
                message = (Message) in.readObject();
                query.addMessage(message);
                server.sendMessage(message, this);
            }

        } catch (IOException | ClassNotFoundException ex) {

            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

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
