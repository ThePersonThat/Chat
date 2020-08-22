package org.example.chat.server;


import org.example.chat.client.message.Message;
import org.example.chat.server.database.ConnectionDB;
import org.example.chat.server.database.SQLQuery;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private Set<ClientSocket> clients = new HashSet<>();
    private SQLQuery query;

    private int port;

    public Server (int port) {
        this.port = port;
    }

    private void LOG (String log) {
        System.out.println(log);
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(port);
             ConnectionDB db = setupDB()) {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SQLQuery getQuery() {
        return query;
    }

    private ConnectionDB setupDB() {
        // set global time_zone = '-3:00';
        ConnectionDB db = new ConnectionDB();
        db.connect();

        query = new SQLQuery(db.getConnection());
        return db;
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
}