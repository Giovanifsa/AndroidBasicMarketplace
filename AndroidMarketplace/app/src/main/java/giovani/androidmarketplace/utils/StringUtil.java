package giovani.androidmarketplace.utils;

import java.text.MessageFormat;

public class StringUtil {
    private StringUtil() {

    }

    public static String formatMensagem(String format, Object... parametros) {
        return new MessageFormat(format).format(parametros);
    }

    public static boolean isBlank(String string) {
        return (string.isEmpty() || string.trim().equals(""));
    }
}
