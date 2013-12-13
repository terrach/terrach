package ru.terrach.fragment;

import java.util.ArrayList;

import ru.terrach.R;
import ru.terrach.activity.NewPostActivity;
import ru.terrach.activity.ThreadImagesGallery;
import ru.terrach.core.WorkIsDoneListener;
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

public class PostsFragment extends Fragment implements WorkIsDoneListener {
	private String board;
	private String thread;
	private Boolean reload = false;
	private ArrayList<String> pics = new ArrayList<String>();
	private ArrayList<String> thumbs = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.f_posts, null);
	}

	public void loadPosts(String board, String thread) {
		this.board = board;
		this.thread = thread;
	}

	public void reload() {
		ListView lvPosts = (ListView) getView().findViewById(R.id.lvPosts);
		pics.clear();
		thumbs.clear();
		new ThreadLoadAsyncTask(getActivity(), this, lvPosts, pics, thumbs).execute(board, thread);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ListView lvPosts = (ListView) getView().findViewById(R.id.lvPosts);
		new ThreadLoadAsyncTask(getActivity(), this, lvPosts, pics, thumbs).execute(board, thread);
		lvPosts.setFocusable(false);

	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.m_post, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mi_post_gallery: {
			startActivity(new Intent(getActivity(), ThreadImagesGallery.class).putExtra(ThreadImagesGallery.PARAM_PICS, pics).putExtra(
					ThreadImagesGallery.PARAM_THUMBS, thumbs));
			return true;
		}
		case R.id.mi_post_reload: {
			reload();
			return true;
		}
		case R.id.mi_post_send: {
			startActivityForResult(
					new Intent(getActivity(), NewPostActivity.class).putExtra(NewPostActivity.PARAM_BOARD, board).putExtra(
							NewPostActivity.PARAM_THREAD, thread), 100);
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void done(String... params) {

	}

	@Override
	public void exception(Exception e) {
		getFragmentManager().popBackStackImmediate();
	}
}
