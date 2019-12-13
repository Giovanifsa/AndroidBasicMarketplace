package giovani.androidmarketplace.utils;

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
}
