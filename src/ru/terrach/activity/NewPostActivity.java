package ru.terrach.activity;

import ru.terrach.R;
import ru.terrach.R.layout;
import ru.terrach.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NewPostActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_newpost);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_post, menu);
		return true;
	}

}
