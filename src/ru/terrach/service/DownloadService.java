package ru.terrach.service;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import ru.terrach.R;
import ru.terrach.activity.PicViewActivity;
import ru.terrach.core.helper.NotificationHelper;
import android.app.IntentService;
import android.content.Intent;

public class DownloadService extends IntentService {

	private NotificationHelper notificationHelper;

	public DownloadService() {
		super("Download service");
		notificationHelper = new NotificationHelper(getApplicationContext());
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String surl = intent.getStringExtra(PicViewActivity.PIC_URL);
		notificationHelper.prepeareBuilder(surl.substring(surl.lastIndexOf("/")));
		try {
			URL url = new URL(surl);
			URLConnection connection = url.openConnection();
			connection.connect();
			// this will be useful so that you can show a typical 0-100%
			// progress bar
			int fileLength = connection.getContentLength();

			// download the file
			InputStream input = new BufferedInputStream(url.openStream());
			OutputStream output = new FileOutputStream(android.os.Environment.DIRECTORY_DOWNLOADS + getString(R.string.cache_dir));

			byte data[] = new byte[1024];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				notificationHelper.notify((int) (total * 100 / fileLength));
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
