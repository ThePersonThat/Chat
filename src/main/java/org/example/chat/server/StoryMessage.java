package org.example.chat.server;

import org.example.chat.client.message.Message;

import java.util.LinkedList;

public class StoryMessage {
    LinkedList<Message> history;

    public StoryMessage() {
        history = new LinkedList<>();
    }

    public void addMessage(Message message) {
        history.add(message);
    }

    public Message getMessage(int index) {
        return history.get(index);
    }

    public int getSize() {
        return history.size();
    }
}
