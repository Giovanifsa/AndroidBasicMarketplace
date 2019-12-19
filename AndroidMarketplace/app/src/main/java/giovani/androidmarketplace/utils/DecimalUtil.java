package giovani.androidmarketplace.utils;

import java.math.BigDecimal;

public class DecimalUtil {
    private DecimalUtil() {

    }

    public static String formatarDuasCasasDecimais(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
    }
}
