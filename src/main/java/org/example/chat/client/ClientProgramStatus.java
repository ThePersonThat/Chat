package org.example.chat.client;

public class ClientProgramStatus {
    public static ClientProgramStatus program = new ClientProgramStatus();
    private Thread readThread;
    private Thread writeTread;
    private boolean isRunning = true;

    public Thread getReadThread() {
        return readThread;
    }

    public void setReadThread(Thread readThread) {
        this.readThread = readThread;
    }

    public Thread getWriteTread() {
        return writeTread;
    }

    public void setWriteTread(Thread writeTread) {
        this.writeTread = writeTread;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}