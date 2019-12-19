package giovani.androidmarketplace.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private DateUtil() {

    }

    public static String getDDMMYYYY(Date data) {
        return new SimpleDateFormat("dd-MM-yyyy").format(data);
    }

    public static Date getDDMMYYYYDate(String data) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Date getSlashedDDMMYYYYDate(String data) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getSlashedDDMMYYYY(Date data) {
        return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }
}
