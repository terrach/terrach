package ru.terrach.core;

import java.util.Timer;
import java.util.TimerTask;

import ru.terrach.constants.Constants;
import android.content.Context;
import android.os.AsyncTask;

public abstract class AsyncTaskEx<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	private TimerTask tt;
	protected Context context;
	protected Exception exception;

	public AsyncTaskEx(Context a) {
		this(Constants.MAX_WORK_TIME, a);
	}

	public AsyncTaskEx(Long delay, Context a) {
		this.context = a;
		tt = new TimerTask() {
			@Override
			public void run() {
				AsyncTaskEx.this.cancel(true);
			}
		};
		Timer t = new Timer();
		t.schedule(tt, delay);
	}

	@Override
	protected abstract void onCancelled();

}
