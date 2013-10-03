package ru.terrach.fragment;

import ru.terrach.R;
import ru.terrach.tasks.ThreadLoadAsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PostsFragment extends Fragment {
	private String board;
	private String msg;
	private Boolean reload = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.f_posts, null);
	}

	public void loadPosts(String board, String msg) {
		this.board = board;
		this.msg = msg;
	}

	public void reload() {
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		new ThreadLoadAsyncTask(getActivity(), (ListView) getView().findViewById(R.id.lvPosts)).execute(board, msg);
	}

}
