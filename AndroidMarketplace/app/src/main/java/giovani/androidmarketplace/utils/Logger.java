package giovani.androidmarketplace.utils;

import android.util.Log;

public class Logger {
    private Logger() {

    }

    public static void info(Object requisitante, String format, Object... parametros) {
        Log.i("AM", montarTexto(requisitante, format, parametros));
    }

    public static void error(Object requisitante, String format, Object... parametros) {
        Log.e("AM", montarTexto(requisitante, format, parametros));
    }

    private static String montarTexto(Object requisitante, String format, Object... parametros) {
        String nomeRequisitante = requisitante.getClass().getSimpleName();
        long momentoAtual = System.currentTimeMillis();

        return "[" + nomeRequisitante + " - " + momentoAtual + " ] ~ " + StringUtil.formatMensagem(format, parametros);
    }
}
