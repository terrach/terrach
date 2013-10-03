package lazylist;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Bitmap;

public class ImagesMemoryCache {

	private static final String TAG = "MemoryCache";
	private Map<String, WeakReference<Bitmap>> cache = Collections.synchronizedMap(new LinkedHashMap<String, WeakReference<Bitmap>>(10, 1.5f, true)); // ordering
	private long size = 0;// current allocated size
	private long limit = 1000000;// max memory in bytes

	public ImagesMemoryCache() {
		// use 25% of available heap size
		setLimit(Runtime.getRuntime().maxMemory() / 4);
	}

	public void setLimit(long new_limit) {
		limit = new_limit;
	}

	public Bitmap get(String id) {
		if (!cache.containsKey(id))
			return null;
		return cache.get(id).get();
	}

	public void put(String id, Bitmap bitmap) {
		try {
			if (cache.containsKey(id))
				size -= getSizeInBytes(cache.get(id).get());
			cache.put(id, new WeakReference<Bitmap>(bitmap));
			size += getSizeInBytes(bitmap);
			checkSize();
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

	private void checkSize() {
		if (size > limit) {
			Iterator<Entry<String, WeakReference<Bitmap>>> iter = cache.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, WeakReference<Bitmap>> entry = iter.next();
				size -= getSizeInBytes(entry.getValue().get());
				iter.remove();
				if (size <= limit)
					break;
			}
		}
	}

	public void clear() {
		cache.clear();
	}

	long getSizeInBytes(Bitmap bitmap) {
		if (bitmap == null)
			return 0;
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
}