package org.example.chat.server;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserThread extends Thread{
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;

    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter( socket.getOutputStream(), true);

            //printUsers();

            String userName = reader.readLine();
            server.addUserName(userName);

            String serverMessage = "New user connected: " + userName;
            server.broadcast(serverMessage, this);

            String clientMessage;

            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");

            boolean status;

            do {
                clientMessage = reader.readLine();
                status = clientMessage.equals("Disconnect");
                if(!status) {
                    serverMessage = userName + ": " + clientMessage + " (" + format.format(LocalDateTime.now()) + ")";
                    server.broadcast(serverMessage, this);
                }
            } while (!status);

            server.removeUser(userName, this);
            socket.close();

            serverMessage = userName + " has quited";
            server.broadcast(serverMessage, this);
        } catch (IOException ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    void printUsers() {
        if(server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }
}
