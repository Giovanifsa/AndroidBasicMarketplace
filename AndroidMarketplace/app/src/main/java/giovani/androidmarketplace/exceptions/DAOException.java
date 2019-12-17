package giovani.androidmarketplace.exceptions;

import giovani.androidmarketplace.utils.StringUtil;

public class DAOException extends Exception {
    public DAOException(String format, Object... parametros) {
        super(StringUtil.formatMensagem(format, parametros));
    }

    public DAOException(EnumExceptionsFixas enumExceptionsFixas, Object... parametros) {
        super(StringUtil.formatMensagem(enumExceptionsFixas.getFormat(), parametros));
    }
}
