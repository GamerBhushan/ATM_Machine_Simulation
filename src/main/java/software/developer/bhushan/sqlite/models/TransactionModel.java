package software.developer.bhushan.sqlite.models;

public class TransactionModel {
    private String Transaction_ID,Transaction_From,Transaction_To,Transaction_Amount,Transaction_Type,Transaction_Date,Transaction_Time;

    public static  String TYPE_BANK_TRANSFER = "Bank Transfer";
    public static String TYPE_WITHDRAWAL = "Withdrawal";
    public static String TYPE_DEPOSIT = "Deposit";

    public String getTransaction_ID() {
        return Transaction_ID;
    }

    public void setTransaction_ID(String transaction_ID) {
        Transaction_ID = transaction_ID;
    }

    public String getTransaction_From() {
        return Transaction_From;
    }

    public void setTransaction_From(String transaction_From) {
        Transaction_From = transaction_From;
    }

    public String getTransaction_To() {
        return Transaction_To;
    }

    public void setTransaction_To(String transaction_To) {
        Transaction_To = transaction_To;
    }

    public String getTransaction_Amount() {
        return Transaction_Amount;
    }

    public void setTransaction_Amount(String transaction_Amount) {
        Transaction_Amount = transaction_Amount;
    }

    public String getTransaction_Type() {
        return Transaction_Type;
    }

    public void setTransaction_Type(String transaction_Type) {
        Transaction_Type = transaction_Type;
    }

    public String getTransaction_Date() {
        return Transaction_Date;
    }

    public void setTransaction_Date(String transaction_Date) {
        Transaction_Date = transaction_Date;
    }

    public String getTransaction_Time() {
        return Transaction_Time;
    }

    public void setTransaction_Time(String transaction_Time) {
        Transaction_Time = transaction_Time;
    }
}
