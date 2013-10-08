package ru.terrach.activity;

import ru.terrach.R;
import ru.terrach.network.Downloader;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PicViewActivity extends Activity {

	public static final String PIC_URL = "pic_url";
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_picview);
		WebView webview = (WebView) findViewById(R.id.picView);
		webview.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				PicViewActivity.this.setTitle("Loading...");
				PicViewActivity.this.setProgress(progress * 100);
				if (progress == 100)
					PicViewActivity.this.setTitle(R.string.app_name);
			}
		});
		webview.setWebViewClient(new HelloWebViewClient());
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setUseWideViewPort(true);
		url = getIntent().getStringExtra(PIC_URL);
		webview.loadUrl(url);

		getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
	}

	private class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.m_pic_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mi_pic_save: {
			new Downloader().download(this, url);
		}
		}
		return true;
	}

}
