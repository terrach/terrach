package ru.terrach.activity;

import ru.terrach.MainActivity;
import ru.terrach.core.URLUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class UrlHandlerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Uri uri = this.getIntent().getData();
		Context context = this.getApplicationContext();
		Intent intent = new Intent(context, MainActivity.class);
		if (URLUtil.isBoardUrl(uri.getPath())) {
			intent.putExtra(MainActivity.OPEN_EXTERNAL_OP, MainActivity.OPEN_BOARD);
			intent.putExtra(MainActivity.OPEN_EXTERNAL_URL, uri.getPath());
		} else if (URLUtil.isThreadUrl(uri.getPath())) {
			intent.putExtra(MainActivity.OPEN_EXTERNAL_OP, MainActivity.OPEN_THREAD);
			intent.putExtra(MainActivity.OPEN_EXTERNAL_URL, uri.getPath());
		} else if (URLUtil.isImageUrl(uri.getPath())) {
			// intent.putExtra(MainActivity.OPEN_EXTERNAL_OP,
			// MainActivity.OPEN_IMAGE);
			// intent.putExtra(MainActivity.OPEN_EXTERNAL_URL,
			// URLUtil.parseBoard(uri.getPath()));
		} else {
		}

		intent.setData(uri);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.startActivity(intent);
		this.finish();
	}
}
