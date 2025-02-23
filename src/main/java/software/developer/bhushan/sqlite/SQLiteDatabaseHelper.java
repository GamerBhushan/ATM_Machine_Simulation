package software.developer.bhushan.sqlite;

import software.developer.bhushan.sqlite.tables.TransactionTable;
import software.developer.bhushan.sqlite.tables.UserTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.Paths;

public class SQLiteDatabaseHelper {

    private String dbPath = Paths.get(System.getProperty("user.dir"), "build", "resources", "main", "ATM_Database.db").toString();
    private String url = "jdbc:sqlite:" + dbPath;

    private UserTable userTable = new UserTable();
    private TransactionTable transactionTable = new TransactionTable();

    private Connection connection; // 🔹 Persistent connection

    public SQLiteDatabaseHelper() {
        init();
    }

    private void init() {
        try {
            connection = getConnection(); // Establish connection once
            if (connection != null) {
                userTable.create(connection);
                transactionTable.create(connection);
//                System.out.println("\nTable Created Successfully.\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) { // Ensure a persistent connection
                connection = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() { // Method to close connection when done
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserTable getUserTable() {
        return userTable;
    }

    public TransactionTable getTransactionTable() {
        return transactionTable;
    }
}
