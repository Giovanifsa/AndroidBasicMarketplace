package giovani.androidmarketplace.utils;

import java.math.BigDecimal;

public class DecimalUtil {
    private DecimalUtil() {

    }

    public static String formatarDuasCasasDecimais(BigDecimal bigDecimal) {
        try {
            return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
        } catch (NumberFormatException ex) {
            return "0";
        }
    }

    public static BigDecimal formatarDuasCasasDecimais(String valor) {
        try {
            return new BigDecimal(valor).setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (NumberFormatException ex) {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal formatarBDDuasCasasDecimais(BigDecimal bigDecimal) {
        try {
            return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (NumberFormatException ex) {
            return BigDecimal.ZERO;
        }
    }
}
