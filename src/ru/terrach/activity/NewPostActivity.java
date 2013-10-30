package ru.terrach.activity;

import ru.terrach.R;
import ru.terrach.tasks.CaptchaLoadAsyncTask;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class NewPostActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_newpost);
		refreshCaptcha(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.new_post, menu);
		return true;
	}

	public void refreshCaptcha(View v) {
		new CaptchaLoadAsyncTask(this, (ImageView) findViewById(R.id.ivCaptchaImage)).execute();
	}

}
