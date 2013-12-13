package ru.terrach.activity;

import ru.terrach.R;
import ru.terrach.core.PostBean;
import ru.terrach.network.Downloader;
import ru.terrach.tasks.CaptchaLoadAsyncTask;
import ru.terrach.tasks.SendPostAsyncTask;
import android.app.Activity;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

public class NewPostActivity extends Activity {

	private String board, thread;
	public static final String PARAM_BOARD = "board";
	public static final String PARAM_THREAD = "thread";
	private EditText edtCaptcha, edtTitle, edtText;
	private CheckBox cbSage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_newpost);
		refreshCaptcha(null);
		board = getIntent().getStringExtra(PARAM_BOARD);
		thread = getIntent().getStringExtra(PARAM_THREAD);
		edtCaptcha = (EditText) findViewById(R.id.edtCaptcha);
		edtTitle = (EditText) findViewById(R.id.edtTitle);
		edtText = (EditText) findViewById(R.id.edtPost);
		cbSage = (CheckBox) findViewById(R.id.cbSage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.new_post, menu);
		return true;
	}

	public void refreshCaptcha(View v) {
		new CaptchaLoadAsyncTask(this, (ImageView) findViewById(R.id.ivCaptchaImage)).execute();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mi_newpost_send: {
			PostBean params = new PostBean();
			params.setBoard(board);
			params.setThread(Integer.parseInt(thread));
			params.setParent(Integer.parseInt(thread));
			params.setCaptchaAnswer(edtCaptcha.getText().toString());
			params.setCaptcha((String) findViewById(R.id.ivCaptchaImage).getTag());
			params.setTitle(edtTitle.getText().toString());
			params.setText(edtText.getText().toString());
			params.setSage(cbSage.isChecked());
			new SendPostAsyncTask(this).execute(params);
		}
		}
		return true;
	}

}
