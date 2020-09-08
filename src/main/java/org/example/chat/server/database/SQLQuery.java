package org.example.chat.server.database;

import org.example.chat.client.message.*;
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
    private static final String ADD_FILE = "INSERT INTO table_file (name, extension, data) VALUES(?, ?, ?)";
    private static final String GET_LAST_FILE_INDEX = "SELECT last_insert_id() FROM table_file";
    private static final String GET_FILE_BY_ID = "SELECT * FROM table_file WHERE id_file = ?";

    public SQLQuery(Connection connection) {
        this.connection = connection;
    }


    private int addFileAndGetId(AbstractMessageFile message) throws SQLException {
        preaparedStatement = connection.prepareStatement(ADD_FILE);

        preaparedStatement.setString(1, message.getFilename());
        preaparedStatement.setString(2, message.getExtension());
        preaparedStatement.setBinaryStream(3, new ByteArrayInputStream(message.getContentInBytes()));

        preaparedStatement.execute();

        return getLastId();
    }

    private int getLastId() throws SQLException {
        preaparedStatement = connection.prepareStatement(GET_LAST_FILE_INDEX);
        ResultSet set = preaparedStatement.executeQuery();

        set.next();
        return set.getInt(1);
    }

    public void addMessage(Message message) {

        try {
            String type = message.getTypeMessage();

            if(type.equals("text")) {
                preaparedStatement = connection.prepareStatement(ADD_MESSAGE);

                preaparedStatement.setString(2,"text");
                preaparedStatement.setBinaryStream(3, new ByteArrayInputStream(message.getContentInBytes()));
                preaparedStatement.setNull(4, Types.BLOB);
            } else {
                int index = addFileAndGetId((AbstractMessageFile)message);

                preaparedStatement = connection.prepareStatement(ADD_MESSAGE);
                preaparedStatement.setString(2, type);
                preaparedStatement.setInt(4, index);
                preaparedStatement.setNull(3, Types.BLOB);
            }

            preaparedStatement.setString(1, message.getTime());

            preaparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void setMessage(Message message, ResultSet set, String type) throws SQLException {
        message.setTime(set.getString("time"));

        if(type.equals(TEXT)) {
            message.setContent(set.getBytes(TEXT));
        } else {
            int index = set.getInt(FILE);

            preaparedStatement =  connection.prepareStatement(GET_FILE_BY_ID);
            preaparedStatement.setInt(1, index);
            set = preaparedStatement.executeQuery();
            set.next();

            ((AbstractMessageFile) message).setFilename(set.getString("name") + set.getString("extension"));
            message.setContent(set.getBytes("data"));
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
