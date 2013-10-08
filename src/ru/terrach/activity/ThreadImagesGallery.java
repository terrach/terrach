package ru.terrach.activity;

import ru.terrach.R;
import ru.terrach.activity.component.adapter.ThreadGalleryAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ThreadImagesGallery extends Activity {

	private GridView gv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_thread_gallery);
		gv = (GridView) findViewById(R.id.gvThreadGallery);
		gv.setAdapter(new ThreadGalleryAdapter(this, getIntent().getStringArrayListExtra("thumbs"), getIntent().getStringArrayListExtra("pics")));
		setTitle("Галерея");
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startActivity(new Intent(ThreadImagesGallery.this, PicViewActivity.class).putExtra(PicViewActivity.PIC_URL, ((String) view.getTag())));
			}
		});
	}
}
