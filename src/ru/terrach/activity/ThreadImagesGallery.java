package ru.terrach.activity;

import ru.terrach.R;
import ru.terrach.activity.component.ThreadGalleryAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class ThreadImagesGallery extends Activity {

	private GridView gv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_thread_gallery);
		gv = (GridView) findViewById(R.id.gvThreadGallery);
		gv.setAdapter(new ThreadGalleryAdapter(this, getIntent().getStringArrayListExtra("pics")));
	}
}
