package software.developer.bhushan.sqlite.models;

public class UserModel {
    private String User_Account_Number,User_Pin,User_Name;
    private double User_Balance;
    private String User_Email,User_Mobile_Number;

    public String getUser_Pin() {
        return User_Pin;
    }

    public void setUser_Pin(String user_Pin) {
        User_Pin = user_Pin;
    }

    public String getUser_Account_Number() {
        return User_Account_Number;
    }

    public void setUser_Account_Number(String user_Account_Number) {
        User_Account_Number = user_Account_Number;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public double getUser_Balance() {
        return User_Balance;
    }

    public void setUser_Balance(double user_Balance) {
        User_Balance = user_Balance;
    }

    public String getUser_Email() {
        return User_Email;
    }

    public void setUser_Email(String user_Email) {
        User_Email = user_Email;
    }

    public String getUser_Mobile_Number() {
        return User_Mobile_Number;
    }

    public void setUser_Mobile_Number(String user_Mobile_Number) {
        User_Mobile_Number = user_Mobile_Number;
    }
}
