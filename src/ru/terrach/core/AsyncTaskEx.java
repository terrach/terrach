package ru.terrach.core;

import java.util.Timer;
import java.util.TimerTask;

import org.acra.ACRA;

import ru.terrach.constants.Constants;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public abstract class AsyncTaskEx<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	private TimerTask tt;
	protected Context context;
	protected Exception exception;
	protected ProgressDialog dlg;

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

	protected void startProgress(String title, String message) {
		dlg = ProgressDialog.show(context, title, message);
	}

	protected void stopProgess() {
		try {
			if (dlg != null && dlg.isShowing())
				dlg.dismiss();
		} catch (Exception e) {
			ACRA.getErrorReporter().handleException(e, false);
		}
	}

	@Override
	protected Result doInBackground(Params... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onPostExecute(Result result) {
		stopProgess();
	}

}
