package ru.terrach.tasks;

import android.content.Context;
import ru.terrach.core.AsyncTaskEx;
import ru.terrach.core.PostBean;

public class SendPostAsyncTask extends AsyncTaskEx<PostBean, Integer, Boolean>  {

	public SendPostAsyncTask(Context a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Boolean doInBackground(PostBean... params) {
		// TODO Auto-generated method stub
		return null;
	}

}