package org.example.chat.server;

import java.util.Iterator;
import java.util.LinkedList;

public class StoryMessage {
    private LinkedList<String> history = new LinkedList<>();

    public void addMessage(String message) {
        if(history.size() > 10) {
            history.remove(0);
        }

        history.add(message);
    }

    public int getSizeHistory() {
        return history.size();
    }

    public String printHistory () {
        String stringHistory = "";

        for(int i = 0; i < getSizeHistory(); i++) {
            stringHistory = stringHistory.concat(history.get(i) + "\n");
        }

        return stringHistory;
    }
}
