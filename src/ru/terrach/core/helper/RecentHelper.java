package ru.terrach.core.helper;

import ru.terrach.R;
import android.content.Context;
import android.content.SharedPreferences;

public class RecentHelper {
	private SharedPreferences preferences;
	private Context context;

	public RecentHelper(Context context, SharedPreferences preferences) {
		super();
		this.context = context;
		this.preferences = preferences;
	}

	public String[] getRecentBoards() {
		return preferences.getString(context.getString(R.string.recent_boards), "").split(" ");
	}

	public void addRecentBoard(String recentBoard) {
		if (!preferences.getString(context.getString(R.string.recent_boards), "").contains(recentBoard))
			updateRecent(context.getString(R.string.recent_boards), recentBoard, getRecentBoards());
	}

	public String[] getRecentThreads() {
		return preferences.getString(context.getString(R.string.recent_threads), "").split(" ");
	}

	public void addRecentThread(String recentThread) {
		if (!preferences.getString(context.getString(R.string.recent_threads), "").contains(recentThread))
			updateRecent(context.getString(R.string.recent_threads), recentThread, getRecentThreads());
	}

	private void updateRecent(String param, String recentParam, String[] recents) {
		Integer maxRecents = preferences.getInt(context.getString(R.string.max_recent),
				Integer.parseInt(context.getString(R.string.max_recent_default)));
		SharedPreferences.Editor editor = preferences.edit();
		StringBuilder sb = new StringBuilder();
		if (recents.length < maxRecents) {
			for (String r : recents)
				sb.append(r).append(" ");
			sb.append(recentParam);
		} else {
			sb.append(recentParam).append(" ");
			for (int i = 1; i < maxRecents; i++)
				sb.append(recents[i]).append(" ");
		}
		editor.putString(param, sb.toString().trim());
		editor.commit();
	}
}
