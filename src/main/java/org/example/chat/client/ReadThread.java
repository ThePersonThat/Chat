package org.example.chat.client;

import javafx.application.Platform;
import org.example.chat.client.graphics.Controller;

import java.io.*;
import java.net.Socket;

public class ReadThread extends Thread{
    private Socket socket;
    private Client client;
    private ObjectInputStream in;

    public ReadThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        ClientProgramStatus.program.setReadThread(this);
    }

    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }

        Controller controller = client.getController();

        while (true) {
            try {
                Message message = (Message) in.readObject();
                Platform.runLater(() -> controller.setMessageOther(message));

            } catch (IOException | ClassNotFoundException exception) {
                exception.printStackTrace();
                try {
                    in.close();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}