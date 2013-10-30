package ru.terrach.tasks;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.acra.ACRA;

import ru.terrach.R;
import ru.terrach.activity.component.adapter.PostsArrayAdapter;
import ru.terrach.core.AsyncTaskEx;
import ru.terrach.core.Utils;
import ru.terrach.core.WorkIsDoneListener;
import ru.terrach.network.dto.PostDTO;
import ru.terrach.network.dto.SingleThreadDTO;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import flexjson.JSONDeserializer;
import flexjson.JSONException;

public class ThreadLoadAsyncTask extends AsyncTaskEx<String, Integer, SingleThreadDTO> {
	private String server, wakaba;
	private ListView lvPosts;
	private String board;
	private String thread;
	private ArrayList<String> pics;
	private ArrayList<String> thumbs;
	private WorkIsDoneListener workIsDoneListener;

	public ThreadLoadAsyncTask(Context a, WorkIsDoneListener workIsDoneListener, ListView lvPosts, ArrayList<String> pics, ArrayList<String> thumbs) {
		super(a);
		this.workIsDoneListener = workIsDoneListener;
		this.lvPosts = lvPosts;
		this.pics = pics;
		this.thumbs = thumbs;
		server = a.getString(R.string.server);
		wakaba = a.getString(R.string.wakaba);
	}

	@Override
	protected void onCancelled() {
	}

	@Override
	protected SingleThreadDTO doInBackground(String... params) {
		board = params[0];
		thread = params[1];
		boolean success = false;
		SingleThreadDTO ret = null;
		try {
			for (int i = 0; i < 2;) {
				ret = load(board, thread);
				success = true;
				break;
			}
		} catch (MalformedURLException e) {
			exception = e;
			e.printStackTrace();
			ACRA.getErrorReporter().handleSilentException(e);
		} catch (IOException e) {
			exception = e;
			Log.e("ThreadLoadAsyncTask", "Unable to load thread : not found");
		} catch (JSONException e) {
			exception = e;
			e.printStackTrace();
			ACRA.getErrorReporter().handleSilentException(e);
		} catch (Exception e) {
			exception = e;
			e.printStackTrace();
			ACRA.getErrorReporter().handleSilentException(e);
		}

		return success ? ret : null;
	}

	private SingleThreadDTO load(String board, String thread) throws MalformedURLException, IOException, Exception {
		URLConnection conn = new URL(server + board + "/res/" + thread + ".json").openConnection();
		return new JSONDeserializer<SingleThreadDTO>().deserialize(Utils.convertStreamToString(conn.getInputStream()), SingleThreadDTO.class);
	}

	@Override
	protected void onPostExecute(SingleThreadDTO result) {
		if (exception != null) {
			Toast.makeText(context, "Тред не найден", Toast.LENGTH_SHORT).show();
			if (workIsDoneListener != null)
				workIsDoneListener.exception(exception);
		} else {
			try {
				if (result != null) {
					for (List<PostDTO> msgsInThread : result.thread) {
						if (msgsInThread.get(0).image != null) {
							pics.add(server + board + msgsInThread.get(0).image);
							thumbs.add(server + board + msgsInThread.get(0).thumbnail);
						}
					}
					PostsArrayAdapter adapter = (PostsArrayAdapter) lvPosts.getAdapter();
					if (adapter == null)
						lvPosts.setAdapter(new PostsArrayAdapter(context, result.thread, board));
					else
						adapter.update(result.thread);
				}
			} catch (Exception e) {
				Toast.makeText(context, "Ошибка при загрузке борды", Toast.LENGTH_SHORT).show();
				ACRA.getErrorReporter().handleSilentException(e);
			}
			if (workIsDoneListener != null)
				workIsDoneListener.done(null);
		}
	}

}
