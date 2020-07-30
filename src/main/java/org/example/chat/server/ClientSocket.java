package org.example.chat.server;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


// TODO: if you will have time correct the bug when the user write server-command: disconnect

enum Command {
    DISCONNECT, PRINT_USERS, HELP, IS_NOT_COMMAND, COUNT, GET_IP;

    public static boolean isServerCommand(String msg) {
        return msg.startsWith("server-command:");
    }
}

public class ClientSocket extends Thread {
    private Socket socket;
    private Server server;
    private PrintWriter writer;
    private BufferedReader reader;
    private String userName;

    public ClientSocket(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }



    private Command executeCommand(String command) {
        switch (command) {
            case "disconnect":
                return Command.DISCONNECT;
            case "help":
                sendMessage("Server: commands:" +
                        "\ndisconnect - disconnect from server (Don't use it if you don't want to this chat to break);" +
                        "\nhelp - commands;" +
                        "\ncount - count of users on server" +
                        "\nusers - print users on server");
                return Command.HELP;
            case "count":
                sendMessage("Server: Count of user: " + server.getCountUsers());
                return Command.COUNT;
            case "users":
                sendMessage("Server: " + server.getAllUsers());
                return Command.PRINT_USERS;
            case "ip":
                sendMessage(server.getIpAddress().toString());
                return Command.GET_IP;
            default:
                sendMessage("Server: Error! Such a command doesn't exist. Check the syntax");
                return Command.IS_NOT_COMMAND;
        }
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            userName = reader.readLine();
            server.sendMessage("New user connected: " + userName);

            if(server.getHistory().getSizeHistory() != 0) {
                sendMessage(server.getHistory().printHistory());
            }

            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
            String message;

            do {
                message = reader.readLine();

                if(Command.isServerCommand(message)) {
                    if(executeCommand(message.substring(15).trim()) == Command.DISCONNECT)
                        break;

                } else {
                    message = userName + ": " + message.trim() + " (" + format.format(LocalDateTime.now()) + ")";
                    server.getHistory().addMessage(message);
                    server.sendMessage(message);
                }
            } while (true);

            server.removeUser(this);
            socket.close();

            message = userName + " has quited";
            server.sendMessage(message);
        } catch (IOException ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        writer.println(msg);
    }
}
