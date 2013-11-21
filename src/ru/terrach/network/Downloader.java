package ru.terrach.network;

import java.util.List;

import ru.terrach.R;
import ru.terrach.activity.PicViewActivity;
import ru.terrach.service.DownloadService;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

public class Downloader {
	public boolean isDownloadManagerAvailable(Context context) {
		try {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
				return false;
			}
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.setClassName("com.android.providers.downloads.ui", "com.android.providers.downloads.ui.DownloadList");
			List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
			return list.size() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void download(Context context, String url) {
		if (isDownloadManagerAvailable(context)) {
			DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
			request.setTitle(url.substring(url.lastIndexOf("/") + 1));
			// in order for this if to run, you must use the android 3.2 to
			// compile your app
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				request.allowScanningByMediaScanner();
				request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
			}
			request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
					context.getString(R.string.cache_dir) + url.substring(url.lastIndexOf("/")));

			// get download service and enqueue file
			DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
			manager.enqueue(request);
		} else {
			context.startService(new Intent(context, DownloadService.class).putExtra(PicViewActivity.PIC_URL, url));
		}
	}
}
