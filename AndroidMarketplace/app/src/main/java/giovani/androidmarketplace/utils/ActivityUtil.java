package giovani.androidmarketplace.utils;

import android.app.Activity;
import android.content.Intent;

public class ActivityUtil {
    private ActivityUtil() {

    }

    public static void iniciarActivity(Activity activityPai, Class<?> classeNovaActivity) {
        Intent intentNovaActivity = new Intent(activityPai, classeNovaActivity);

        activityPai.startActivity(intentNovaActivity);
    }

    public static void iniciarActivityRaiz(Activity activityPai, Class<?> classeNovaActivity) {
        Intent intentNovaActivity = new Intent(activityPai, classeNovaActivity);
        intentNovaActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        activityPai.startActivity(intentNovaActivity);
    }
}
