package org.example.chat.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB implements AutoCloseable {

    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/chat_base";

    private Connection connection;

    public ConnectionDB () { }

    public void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);

            if(connection.isClosed()) {
                System.out.println("Error connect to database!");
                throw new SQLException();
            } else {
                System.out.println("Connection with database is successful");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws Exception {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
