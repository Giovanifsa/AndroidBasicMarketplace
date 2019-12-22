package giovani.androidmarketplace.exceptions;

import giovani.androidmarketplace.utils.StringUtil;

public class ActivityException extends Exception {
    public ActivityException(String format, Object... parametros) {
        super(StringUtil.formatMensagem(format, parametros));
    }
}
