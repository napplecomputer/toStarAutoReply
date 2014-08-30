package co.natsuhi.tostarautoreply.utils;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * NotificationListenerService使うときのあれこれ
 */
public class NotificationListenerServiceUtil {
    private static final String TAG = NotificationListenerServiceUtil.class.getSimpleName();
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

    /**
     * NotificationListenerServiceが有効になっているか確認する
     *
     * @param context
     * @return true:有効 false:無効
     */
    public static boolean isEnabled(Context context) {
        List<ComponentName> enabledListeners = getEnabledNotificationListeners(context);
        if (enabledListeners == null) {
            return false;
        }
        String packageName = context.getPackageName();
        for (ComponentName componentName : enabledListeners) {
            if (packageName.equals(componentName.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * NotificationListenerServiceを有効・無効にする設定画面へのIntentを作る
     *
     * @return
     */
    public static Intent createStartNotificationSettingIntent() {
        Intent intent = new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS);
        return intent;
    }

    /**
     * 有効になっているNotificationListenerの一覧を取得する
     *
     * @param context
     * @return 値が取得できなかった場合はnullが返るよ
     */
    private static List<ComponentName> getEnabledNotificationListeners(Context context) {
        String permissionName;

        try {
            Field field = Settings.Secure.class.getField("ENABLED_NOTIFICATION_LISTENERS");
            permissionName = (String) field.get(null);
        } catch (NoSuchFieldException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }

        ContentResolver contentResolver = context.getContentResolver();
        String rawListeners = Settings.Secure.getString(contentResolver,
                permissionName);
        if (rawListeners == null || "".equals(rawListeners)) {
            return null;
        }

        String[] listeners = rawListeners.split(":");

        //Stringだと中身の予想がつきにくいのでComponentNameに
        ArrayList<ComponentName> componentNames = new ArrayList<ComponentName>();
        for (String listener : listeners) {
            LogUtil.d(TAG,"enabled listener : "+listener);
            ComponentName cn = ComponentName.unflattenFromString(listener);
            if (cn != null) {
                componentNames.add(cn);
            }
        }
        return componentNames;
    }
}
