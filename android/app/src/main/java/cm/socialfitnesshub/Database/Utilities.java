package cm.socialfitnesshub.Database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Utilities {

    public static Date stringToDate(String dateString, String format) {

        if (format == null || format.isEmpty()) {
            format = "dd/MM/yyyy";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateToString(Date date, String format) {
        if (format == null || format.isEmpty()) {
            format = "dd/MM/yyyy";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
