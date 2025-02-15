package org.Project.CinemaSeatBooking.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

}
