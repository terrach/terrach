package ru.terrach.activity.component.adapter;

import java.util.List;

import lazylist.ImageLoader;
import ru.terrach.R;
import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ThreadGalleryAdapter extends ArrayAdapter<String> {

	private ImageLoader imageLoader;

	public ThreadGalleryAdapter(Context context, List<String> objects) {
		super(context, R.layout.i_gallery_image, objects);
		imageLoader = new ImageLoader(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.i_gallery_image, null);
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		int picWidth = 0;
		if (display.getHeight() > display.getWidth()) {
			// portrait
			picWidth = display.getWidth() / 3;
		} else {
			picWidth = display.getWidth() / 3;
		}
		GridView.LayoutParams lp = new GridView.LayoutParams(picWidth, picWidth);		
		iv.setLayoutParams(lp);
		imageLoader.DisplayImage(getItem(position), iv);
		return iv;
	}
}
