package co.natsuhi.tostarautoreply;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/**
 * Created by napplecomputer on 2014/08/30.
 */
public class StarListenerService extends NotificationListenerService {
    private static final String TOSTAR_PACKAGE_NAME = "jp.co.brilliantservice.app.star";

    @Override
    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        if (statusBarNotification.getPackageName().equals(TOSTAR_PACKAGE_NAME)) {
            Notification notification = statusBarNotification.getNotification();
            Notification.Action[] actions = notification.actions;
            if (actions == null) {
                return;
            }

            for (Notification.Action action : actions) {
                PendingIntent pendingIntent = action.actionIntent;

                Intent intent = new Intent();
                try {
                    pendingIntent.send(this, 0, intent);
                    cancelNotification(statusBarNotification.getPackageName(), statusBarNotification.getTag(), statusBarNotification.getId());
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
    }
}
