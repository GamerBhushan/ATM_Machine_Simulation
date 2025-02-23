package software.developer.bhushan.sqlite.tables;

import software.developer.bhushan.sqlite.models.UserModel;

import java.sql.*;

public class UserTable {
    private final String TABLE_NAME = "Users";
    private final String COL_1 = "User_Account_Number";
    private final String COL_2 = "User_Pin";
    private final String COL_3 = "User_Name";
    private final String COL_4 = "User_Balance";
    private final String COL_5 = "User_Email";
    private final String COL_6 = "User_Mobile_Number";

    private final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COL_1 + " TEXT PRIMARY KEY, " +
                    COL_2 + " TEXT NOT NULL, " +
                    COL_3 + " TEXT, " +
                    COL_4 + " FLOAT, " +
                    COL_5 + " TEXT, " +
                    COL_6 + " TEXT );";

    public void create(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(CREATE_TABLE_QUERY);
//            System.out.println("Table '" + TABLE_NAME + "' created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public void insert(Connection connection, UserModel userModel) {
        String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY)) {
            pstmt.setString(1, userModel.getUser_Account_Number());
            pstmt.setString(2, userModel.getUser_Pin());  // PIN Column Added
            pstmt.setString(3, userModel.getUser_Name());
            pstmt.setDouble(4, userModel.getUser_Balance());
            pstmt.setString(5, userModel.getUser_Email());
            pstmt.setString(6, userModel.getUser_Mobile_Number());

            pstmt.executeUpdate();
            System.out.println("User inserted successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting user: " + e.getMessage());
        }
    }

    public void updateByAccountNumber(Connection connection, UserModel userModel) {
        String UPDATE_QUERY = "UPDATE " + TABLE_NAME + " SET " +
                COL_2 + " = ?, " + // Update PIN as well
                COL_3 + " = ?, " +
                COL_4 + " = ?, " +
                COL_5 + " = ?, " +
                COL_6 + " = ? WHERE " + COL_1 + " = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY)) {
            pstmt.setString(1, userModel.getUser_Pin());
            pstmt.setString(2, userModel.getUser_Name());
            pstmt.setDouble(3, userModel.getUser_Balance());
            pstmt.setString(4, userModel.getUser_Email());
            pstmt.setString(5, userModel.getUser_Mobile_Number());
            pstmt.setString(6, userModel.getUser_Account_Number());

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
    }

    public void deleteByAccountNumber(Connection connection, String userAccountNumber) {
        String DELETE_QUERY = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_1 + " = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY)) {
            pstmt.setString(1, userAccountNumber);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }

    public UserModel findUserByACNo(Connection connection, String acno) {
        String QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_1 + " = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(QUERY)) {
            pstmt.setString(1, acno);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                UserModel user = new UserModel();
                user.setUser_Account_Number(rs.getString(COL_1));
                user.setUser_Pin(rs.getString(COL_2));  // Fetch PIN
                user.setUser_Name(rs.getString(COL_3));
                user.setUser_Balance(rs.getDouble(COL_4));
                user.setUser_Email(rs.getString(COL_5));
                user.setUser_Mobile_Number(rs.getString(COL_6));
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error finding user: " + e.getMessage());
        }
        return null; // Return null if user not found
    }

    public int countUsersByACNo(Connection connection, String accountNumber) {
        String COUNT_QUERY = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + COL_1 + " = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(COUNT_QUERY)) {
            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1); // Get the count from the result set
            }
        } catch (SQLException e) {
            System.err.println("Error counting users: " + e.getMessage());
        }
        return 0; // Return 0 if no users found or in case of an error
    }
}
