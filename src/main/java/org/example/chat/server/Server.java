package org.example.chat.server;


import org.example.chat.client.Message;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private Set<ClientSocket> clients = new HashSet<>();
    private ServerSocket serverSocket;
    private StoryMessage history;
    private int port;

    public StoryMessage getHistory() {
        return history;
    }

    public Server (int port) {
        this.port = port;
    }

    public int getCountUsers() {
        return clients.size();
    }

    private void LOG (String log) {
        System.out.println(log);
    }

    public InetAddress getIpAddress (){
        return serverSocket.getInetAddress();
    }

    public String getAllUsers() {
        String users = "";

        for (ClientSocket client: clients) {
            users = users.concat("[" + client.getUserName() + "]\n");
        }

        return users;
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            this.serverSocket = serverSocket;
            history = new StoryMessage();
            LOG("Server is start on port: " + this.port);

            while (true) {
                Socket socket = serverSocket.accept();
                LOG("Connected new client");

                ClientSocket client = new ClientSocket(socket, this);
                clients.add(client);
                client.start();
            }

        } catch (IOException ex) {
            LOG("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final int PORT = 4004;
        Server server = new Server(PORT);

        server.run();
    }

    public void sendMessage(Message message, ClientSocket sender) {
        for (ClientSocket client: clients) {
            if(client != sender) {
                client.sendMessage(message);
            }
        }
    }


    public void removeUser(ClientSocket client) {
        clients.remove(client);
        LOG("Client disconnect");
    }
}