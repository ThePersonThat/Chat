package org.example.chat.client;

import org.example.chat.client.graphics.Controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WriteThread extends Thread {
    private ObjectOutputStream out;
    private Socket socket;
    private Client client;

    public WriteThread (Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
        ClientProgramStatus.program.setWriteTread(this);

        try {
            out = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {
        Controller controller = client.getController();
        Message message;

        try {
            while (ClientProgramStatus.program.isRunning()) {
                message = controller.waitMessage();

                out.writeObject(message);
            }

            //out.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}