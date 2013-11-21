package ru.terrach.fragment;

import ru.terrach.R;
import ru.terrach.activity.MainActivityInterface;
import ru.terrach.activity.component.PostViewHolder;
import ru.terrach.tasks.BoardLoadAsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ThreadsFragment extends Fragment {

	private ListView lvThreads;
	private String board;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.f_threads, null);
	}

	public void loadThreads(String board) {
		this.board = board;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ListView lvThreads = (ListView) getView().findViewById(R.id.lvThreads);
		new BoardLoadAsyncTask(getActivity(), lvThreads, true).execute(board);
		lvThreads.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String num = ((PostViewHolder) view.getTag()).num.getText().toString();
				((MainActivityInterface) getActivity()).loadThread(board, num);
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.m_board, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.mi_refresh) {
			new BoardLoadAsyncTask(getActivity(), lvThreads, true).execute(board);
			return true;
		} else
			return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putString("board", board);
		super.onSaveInstanceState(outState);

	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if (savedInstanceState != null)
			board = savedInstanceState.getString("board");
	}

}
