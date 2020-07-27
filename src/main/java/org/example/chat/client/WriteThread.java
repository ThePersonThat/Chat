package org.example.chat.client;

import org.example.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private Client client;

    public WriteThread (Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        ClientProgramStatus.program.setWriteTread(this);
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        Controller controller = client.getController();
        String userName = controller.inputLogin();
        writer.println(userName);


        String message;

        do {
            message = controller.waitMessage();

            if(ClientProgramStatus.program.isRunning())
                writer.println(message);
            else
                break;

        } while (true);

        writer.println("server-command: disconnect");

        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }

}