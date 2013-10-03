package ru.terrach.activity.component;

import java.util.List;

import lazylist.ImageLoader;
import ru.terrach.R;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
		iv.setLayoutParams(new GridView.LayoutParams(90,90));
		imageLoader.DisplayImage(getItem(position), iv);
		return iv;
	}

}
