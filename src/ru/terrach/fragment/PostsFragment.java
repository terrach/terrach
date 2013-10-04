package ru.terrach.fragment;

import java.util.ArrayList;

import ru.terrach.R;
import ru.terrach.activity.ThreadImagesGallery;
import ru.terrach.activity.component.adapter.ThreadGalleryAdapter;
import ru.terrach.tasks.ThreadLoadAsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PostsFragment extends Fragment {
	private String board;
	private String msg;
	private Boolean reload = false;
	private ArrayList<String> pics = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

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
		new ThreadLoadAsyncTask(getActivity(), (ListView) getView().findViewById(R.id.lvPosts), pics).execute(board, msg);
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.m_post, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.mi_post_gallery) {
			startActivity(new Intent(getActivity(), ThreadImagesGallery.class).putExtra("pics", pics));
			return true;
		} else
			return super.onOptionsItemSelected(item);
	}

}
