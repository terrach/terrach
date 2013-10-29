package ru.terrach.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.acra.ACRA;

import lazylist.Utils;
import ru.terrach.R;
import ru.terrach.activity.component.adapter.ThreadsArrayAdapter;
import ru.terrach.core.AsyncTaskEx;
import ru.terrach.network.dto.BoardDTO;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import flexjson.JSONDeserializer;
import flexjson.JSONException;

public class BoardLoadAsyncTask extends AsyncTaskEx<String, Integer, BoardDTO> {

	private String server, wakaba;
	private ListView lvThreads;
	private String board;

	public BoardLoadAsyncTask(Context a, ListView lvThreads) {
		super(a);
		this.lvThreads = lvThreads;
		server = a.getString(R.string.server);
		wakaba = a.getString(R.string.wakaba);
	}

	@Override
	protected void onCancelled() {
	}

	@Override
	protected BoardDTO doInBackground(String... params) {
		board = params[0];
		BoardDTO ret = null;
		boolean success = false;
		for (int i = 0; i < 2; i++) {
			try {
				ret = load(board);
				success = true;
				break;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				ACRA.getErrorReporter().handleSilentException(e);
			} catch (IOException e) {
				e.printStackTrace();
				ACRA.getErrorReporter().handleSilentException(e);
			} catch (JSONException e) {
				e.printStackTrace();
				ACRA.getErrorReporter().handleSilentException(e);
			}

		}
		return success ? ret : null;
	}

	private BoardDTO load(String board) throws MalformedURLException, IOException, JSONException {
		URLConnection conn = new URL(server + board + wakaba).openConnection();
		String json = Utils.convertStreamToString(conn.getInputStream());
		Log.i("boardjson", json);
		return new JSONDeserializer<BoardDTO>().deserialize(json, BoardDTO.class);
	}

	@Override
	protected void onPostExecute(BoardDTO result) {
		try {
			if (result != null) {
				ThreadsArrayAdapter adapter = (ThreadsArrayAdapter) lvThreads.getAdapter();
				if (adapter == null)
					lvThreads.setAdapter(new ThreadsArrayAdapter(context, result.threads, board));
				else
					adapter.update(result.threads);
			}
		} catch (Exception e) {
			Toast.makeText(context, "Ошибка при установке адаптера", Toast.LENGTH_SHORT).show();
			ACRA.getErrorReporter().handleSilentException(e);
		}
	}
}
