package ru.terrach.core.helper;

import ru.terrach.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper {

	private NotificationManager notifier;
	private NotificationCompat.Builder builder;

	private Context context;

	public NotificationHelper(Context context) {
		this.context = context;
	}

	public void prepeareBuilder(String text) {
		notifier = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		builder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_launcher);
		builder.setContentText(text);
		builder.setContentTitle(text);
		builder.setDefaults(Notification.DEFAULT_ALL);
		builder.setTicker(text);
	}

	public void notify(int progress) {
		builder.setProgress(100, progress, false);
		notifier.notify(0, builder.build());
	}
}
