package ru.terrach.network;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HttpRequestHelper {
	private Context context;

	public HttpRequestHelper(Context context) {
		this.context = context;
	}

	public Bitmap getImageBitmap(String url) {
		if (url == null)
			return getNoPhotoBitmap();
		Bitmap b;
		try {
			b = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
		} catch (MalformedURLException e) {
			return null;
		} catch (FileNotFoundException e) {
			return getNoPhotoBitmap();
		} catch (IOException e) {
			return null;
		} catch (Exception e) {
			return null;
		}

		if (b != null)
			b.setDensity(Bitmap.DENSITY_NONE);
		return b == null ? getNoPhotoBitmap() : b;
	}

	private Bitmap getNoPhotoBitmap() {
		return BitmapFactory.decodeResource(context.getResources(), ru.terrach.R.drawable.no_photo);
	}

}