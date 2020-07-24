package org.example.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


// TODO: create date and time
// TODO: create history
// TODO: create command server

public class ChatServer {
    private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();

    public ChatServer(int port) {
        this.port = port;
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Chat Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
            }
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 4004;

        ChatServer server = new ChatServer(port);
        server.execute();
    }

    public void broadcast(String message, UserThread excludeUser) {
        for(UserThread user : userThreads) {
           // if(user != excludeUser)
                user.sendMessage(message);
        }
    }

    public void addUserName(String userName) {
        userNames.add(userName);
    }

    public void removeUser(String userName, UserThread user) {
        boolean removed = userNames.remove(userName);

        if(removed) {
            userThreads.remove(user);
            System.out.println("The user " + userName + " quited");
        }
    }

    public Set<String> getUserNames() {
        return this.userNames;
    }

    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }
}
