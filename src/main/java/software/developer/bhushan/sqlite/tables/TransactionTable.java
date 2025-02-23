package software.developer.bhushan.sqlite.tables;

import software.developer.bhushan.sqlite.models.TransactionModel;

import java.sql.*;

public class TransactionTable {
    private final String TABLE_NAME = "Transactions";

    private final String COL_1 = "Transaction_ID";
    private final String COL_2 = "Transaction_From";
    private final String COL_3 = "Transaction_To";
    private final String COL_4 = "Transaction_Amount";
    private final String COL_5 = "Transaction_Type";
    private final String COL_6 = "Transaction_Date";
    private final String COL_7 = "Transaction_Time";

    private final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COL_1 + " TEXT PRIMARY KEY, " +
                    COL_2 + " TEXT, " +
                    COL_3 + " TEXT, " +
                    COL_4 + " FLOAT, " +
                    COL_5 + " TEXT, " +
                    COL_6 + " TEXT, " +
                    COL_7 + " TEXT, " +
                    "FOREIGN KEY (" + COL_2 + ") REFERENCES Users(User_Account_Number), " +
                    "FOREIGN KEY (" + COL_3 + ") REFERENCES Users(User_Account_Number) " +
                    ");";

    public void create(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(CREATE_TABLE_QUERY);
//            System.out.println("Table '" + TABLE_NAME + "' created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public void insert(Connection connection, TransactionModel transactionModel) {
        String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY)) {
            pstmt.setString(1, transactionModel.getTransaction_ID());
            pstmt.setString(2, transactionModel.getTransaction_From());
            pstmt.setString(3, transactionModel.getTransaction_To());
            pstmt.setDouble(4, Double.parseDouble(transactionModel.getTransaction_Amount()));
            pstmt.setString(5, transactionModel.getTransaction_Type());
            pstmt.setString(6, transactionModel.getTransaction_Date());
            pstmt.setString(7, transactionModel.getTransaction_Time());

            pstmt.executeUpdate();
            System.out.println("Transaction inserted successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting transaction: " + e.getMessage());
        }
    }

    public void deleteByTransactionFrom(Connection connection, String transactionFrom) {
        String DELETE_QUERY = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_2 + " = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY)) {
            pstmt.setString(1, transactionFrom);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Transactions from user deleted successfully.");
            } else {
                System.out.println("No transactions found for the given user.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting transactions: " + e.getMessage());
        }
    }
}
