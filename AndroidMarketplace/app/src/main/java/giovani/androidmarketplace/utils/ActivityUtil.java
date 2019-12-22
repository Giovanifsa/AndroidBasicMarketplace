package giovani.androidmarketplace.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class ActivityUtil {
    private ActivityUtil() {

    }

    public static void iniciarActivity(Activity activityPai, Class<?> classeNovaActivity) {
        Intent intentNovaActivity = new Intent(activityPai, classeNovaActivity);

        activityPai.startActivity(intentNovaActivity);
    }

    public static void iniciarActivity(Activity activityPai, Class<?> classeNovaActivity, Bundle extras) {
        Intent intentNovaActivity = new Intent(activityPai, classeNovaActivity);
        intentNovaActivity.putExtras(extras);

        activityPai.startActivity(intentNovaActivity);
    }

    public static void iniciarActivityRaiz(Activity activityPai, Class<?> classeNovaActivity) {
        Intent intentNovaActivity = new Intent(activityPai, classeNovaActivity);
        intentNovaActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        activityPai.startActivity(intentNovaActivity);
    }

    public static void exibirDialogMensagem(Activity activityPai, int tituloResId, String mensagem, int textoBotaoResId) {
        AlertDialog alertDialog = new AlertDialog.Builder(activityPai).create();

        alertDialog.setTitle(activityPai.getString(tituloResId));
        alertDialog.setMessage(mensagem);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, activityPai.getString(textoBotaoResId),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

    public static void iniciarActivityAguardandoResultado(Activity activityPai, Class<?> classeNovaActivity, int codigoRequisicao) {
        Intent intentNovaActivity = new Intent(activityPai, classeNovaActivity);
        activityPai.startActivityForResult(intentNovaActivity, codigoRequisicao);
    }

    public static void iniciarActivityAguardandoResultado(Activity activityPai, Class<?> classeNovaActivity, int codigoRequisicao, Bundle extras) {
        Intent intentNovaActivity = new Intent(activityPai, classeNovaActivity);
        intentNovaActivity.putExtras(extras);

        activityPai.startActivityForResult(intentNovaActivity, codigoRequisicao);
    }
}
