package ru.terrach.tasks;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import ru.terrach.R;
import ru.terrach.core.AsyncTaskEx;
import ru.terrach.network.dto.SingleThreadDTO;
import android.content.Context;
import flexjson.JSONDeserializer;

public class ThreadLoadAsyncTask extends AsyncTaskEx<String, Integer, SingleThreadDTO> {
	private String server, wakaba;

	public ThreadLoadAsyncTask(Context a) {
		super(a);
		server = a.getString(R.string.server);
		wakaba = a.getString(R.string.wakaba);
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub

	}

	@Override
	protected SingleThreadDTO doInBackground(String... params) {
		try {
			URLConnection conn = new URL(server + params[0] + "/res/" + params[1] + ".json").openConnection();
			return new JSONDeserializer<SingleThreadDTO>().deserialize(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
