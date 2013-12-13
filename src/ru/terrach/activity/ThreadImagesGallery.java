package ru.terrach.activity;

import java.util.ArrayList;

import ru.terrach.R;
import ru.terrach.activity.component.adapter.ThreadGalleryAdapter;
import ru.terrach.network.Downloader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ThreadImagesGallery extends Activity {

	private GridView gv;
	private ArrayList<String> pics = new ArrayList<String>();
	public static final String PARAM_THUMBS = "thumbs";
	public static final String PARAM_PICS = "pics";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_thread_gallery);
		gv = (GridView) findViewById(R.id.gvThreadGallery);
		pics = getIntent().getStringArrayListExtra(PARAM_PICS);
		gv.setAdapter(new ThreadGalleryAdapter(this, getIntent().getStringArrayListExtra(PARAM_THUMBS), pics));
		setTitle("Галерея");
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startActivity(new Intent(ThreadImagesGallery.this, PicViewActivity.class).putExtra(PicViewActivity.PIC_URL, ((String) view.getTag())));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.m_gal_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mi_gal_saveall: {
			for (String url : pics)
				new Downloader().download(this, url);
		}
		}
		return true;
	}
}
