package ru.terrach.tasks;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import ru.terrach.R;
import ru.terrach.core.AsyncTaskEx;
import ru.terrach.network.dto.BoardDTO;
import android.content.Context;
import flexjson.JSONDeserializer;

public class BoardLoadAsyncTask extends AsyncTaskEx<String, Integer, BoardDTO> {

	private String server, wakaba;

	public BoardLoadAsyncTask(Context a) {
		super(a);
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
			return new JSONDeserializer<BoardDTO>().deserialize(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
