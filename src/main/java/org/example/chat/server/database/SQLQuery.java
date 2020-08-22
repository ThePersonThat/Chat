package org.example.chat.server.database;

import org.example.chat.client.message.Message;
import org.example.chat.client.message.MessageFile;
import org.example.chat.client.message.MessageImage;
import org.example.chat.client.message.MessageText;
import org.example.chat.server.StoryMessage;

import java.io.ByteArrayInputStream;
import java.sql.*;

public class SQLQuery {

    private Connection connection;
    private PreparedStatement preaparedStatement = null;

    private static final String FILE = "messageFile";
    private static final String TEXT = "messageText";
    private static final String ADD_MESSAGE = "INSERT INTO messages (time, type, messageText, messageFile) VALUES(?, ?, ?, ?)";
    private static final String GET_MESSAGES = "SELECT * FROM messages";

    public SQLQuery(Connection connection) {
        this.connection = connection;
    }

    public void addMessage(Message message) {

        try {
            preaparedStatement = connection.prepareStatement(ADD_MESSAGE);

            preaparedStatement.setString(1, message.getTime());
            String type = message.getTypeMessage();

            if(type.equals("text")) {
                preaparedStatement.setString(2,"text");
                preaparedStatement.setBinaryStream(3, new ByteArrayInputStream(message.getContentInBytes()));
                preaparedStatement.setNull(4, Types.BLOB);
            } else {
                preaparedStatement.setString(2, type);
                preaparedStatement.setBinaryStream(4, new ByteArrayInputStream(message.getContentInBytes()));
                preaparedStatement.setNull(3, Types.BLOB);
            }

            preaparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void setMessage(Message message, ResultSet set, String type) {
        try {
            message.setTime(set.getString("time"));
            message.setContent(set.getBytes(type));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void setHistory(StoryMessage history) {
        try {
            preaparedStatement = connection.prepareStatement(GET_MESSAGES);
            ResultSet set = preaparedStatement.executeQuery();

            while (set.next()) {
                Message message;
                switch (set.getString("type")) {
                    case "text":
                        message = new MessageText();
                        setMessage(message, set, TEXT);
                        break;
                    case "image":
                        message = new MessageImage();
                        setMessage(message, set, FILE);
                        break;
                    case "file":
                        message = new MessageFile();
                        setMessage(message, set, FILE);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + set.getString("type"));
                }

                history.addMessage(message);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
