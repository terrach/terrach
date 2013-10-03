package ru.terrach.tasks;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import ru.terrach.R;
import ru.terrach.activity.component.ThreadsArrayAdapter;
import ru.terrach.core.AsyncTaskEx;
import ru.terrach.network.dto.BoardDTO;
import android.content.Context;
import android.widget.ListView;
import flexjson.JSONDeserializer;

public class BoardLoadAsyncTask extends AsyncTaskEx<String, Integer, BoardDTO> {

	private String server, wakaba;
	private ListView lvThreads;

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
		try {
			URLConnection conn = new URL(server + params[0] + wakaba).openConnection();
			return new JSONDeserializer<BoardDTO>().deserialize(new InputStreamReader(conn.getInputStream(), "UTF-8"), BoardDTO.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(BoardDTO result) {
		if (result != null) {
			ThreadsArrayAdapter adapter = (ThreadsArrayAdapter) lvThreads.getAdapter();
			if (adapter == null)
				lvThreads.setAdapter(new ThreadsArrayAdapter(context, result.threads));
			else
				adapter.update(result.threads);
		}
	}

}
