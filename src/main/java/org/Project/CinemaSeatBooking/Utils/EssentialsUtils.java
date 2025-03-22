package org.Project.CinemaSeatBooking.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EssentialsUtils {

    public static String formatDate(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-M-d");
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

            Date date = inputFormat.parse(inputDate);

            return outputFormat.format(date);
        } catch (ParseException e) {
            return "Invalid date format";
        }
    }

    public static String formatDateTime(LocalDateTime dateTime, boolean is12HourFormat) {
        String pattern = is12HourFormat ? "MMMM d, yyyy (hh:mm:ss a)" : "MMMM d, yyyy (HH:mm:ss)";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);

        return dateTime.format(formatter);
    }

    public static boolean isValidDateTime(String dateTime, boolean isOnlyDate) {
        String dateTimePattern;
        if (isOnlyDate) dateTimePattern = "\\d{4}-\\d{2}-\\d{2}";
        else dateTimePattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";

        // Check format using regex
        if (!dateTime.matches(dateTimePattern)) {
            return false;
        }

        // Validate actual date using SimpleDateFormat
        SimpleDateFormat dateFormat;
        if (isOnlyDate) dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        else dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false); // Strict validation

        try {
            dateFormat.parse(dateTime); // Attempt parsing
            return true;
        } catch (ParseException e) {
            return false; // Invalid date/time values
        }
    }

    public static String addHours(String dateTime, int hours) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false); // Strict format checking

        try {
            Date date = dateFormat.parse(dateTime); // แปลง String เป็น Date
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR_OF_DAY, hours); // เพิ่มเวลา

            return dateFormat.format(cal.getTime()); // คืนค่าเป็น String
        } catch (ParseException e) {
            return "Invalid Date/Time format!";
        }
    }

}
