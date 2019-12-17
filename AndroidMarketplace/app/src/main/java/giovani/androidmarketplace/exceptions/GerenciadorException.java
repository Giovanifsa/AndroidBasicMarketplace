package giovani.androidmarketplace.exceptions;

import giovani.androidmarketplace.utils.StringUtil;

public class GerenciadorException extends Exception {
    public GerenciadorException(String format, Object... parametros) {
        super(StringUtil.formatMensagem(format, parametros));
    }
}
