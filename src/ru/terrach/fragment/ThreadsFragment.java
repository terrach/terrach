package ru.terrach.fragment;

import ru.terrach.R;
import ru.terrach.activity.MainActivityInterface;
import ru.terrach.activity.component.PostViewHolder;
import ru.terrach.tasks.BoardLoadAsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
		new BoardLoadAsyncTask(getActivity(), lvThreads).execute(board);
		lvThreads.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String num = ((PostViewHolder) view.getTag()).num.getText().toString();
				((MainActivityInterface) getActivity()).loadThread(board, num);
			}
		});
	}

}
