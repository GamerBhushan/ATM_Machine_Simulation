package software.developer.bhushan.utils;

import software.developer.bhushan.ui.ATM;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utils {

    public static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return currentDate.format(formatter);
    }
    public static String getCurrentTime() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return currentTime.format(formatter);
    }

    public static String generateRandomNumber(int digits) {
        if (digits <= 0) {
            throw new IllegalArgumentException("Number of digits must be greater than 0.");
        }

        long min = (long) Math.pow(10, digits - 1);  // Smallest number with 'digits' digits
        long max = (long) Math.pow(10, digits) - 1;  // Largest number with 'digits' digits

        Random random = new Random();
        long number = min + (long) (random.nextDouble() * (max - min));

        return String.valueOf(number);
    }

    public static String generateUniqueAccountNumber(){
        int cnt = 1;
        String acNo = "None";
        while (cnt != 0){
            acNo = generateRandomNumber(5);
            cnt = ATM.databaseHelper.getUserTable().countUsersByACNo(ATM.databaseHelper.getConnection(),acNo);
        }
        return acNo;
    }
}
