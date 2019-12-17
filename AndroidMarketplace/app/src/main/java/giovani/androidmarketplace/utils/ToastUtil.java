package giovani.androidmarketplace.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private ToastUtil() {

    }

    public static void printShort(Context contexto, CharSequence texto) {
        Toast.makeText(contexto, texto, Toast.LENGTH_SHORT).show();
    }

    public static void printLong(Context contexto, CharSequence texto) {
        Toast.makeText(contexto, texto, Toast.LENGTH_LONG).show();
    }

    public static void printShort(Activity contexto, int resId) {
        Toast.makeText(contexto, contexto.getString(resId), Toast.LENGTH_SHORT).show();
    }

    public static void printLong(Activity contexto, int resId) {
        Toast.makeText(contexto, contexto.getString(resId), Toast.LENGTH_LONG).show();
    }
}
