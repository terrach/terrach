package ru.terrach.fragment;

import java.util.ArrayList;
import java.util.List;

import ru.terrach.R;
import ru.terrach.activity.MainActivityInterface;
import ru.terrach.activity.component.adapter.RecentPostsAdapter;
import ru.terrach.core.RecentThreadBean;
import ru.terrach.core.helper.RecentHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainFragment extends Fragment {

	private GridView gvLast;
	private RecentHelper recentHelper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.f_main, null);
	}

	private void load() {
		gvLast = (GridView) getView().findViewById(R.id.gvMainRecent);
		List<RecentThreadBean> recents = new ArrayList<RecentThreadBean>();
		String[] rb = recentHelper.getRecentBoards();
		String[] rt = recentHelper.getRecentThreads();
		for (int i = 0; i < rb.length; i++) {
			recents.add(new RecentThreadBean(rb[i], rt[i]));
		}
		gvLast.setAdapter(new RecentPostsAdapter(getActivity(), recents));
		gvLast.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				RecentThreadBean recentThreadBean = (RecentThreadBean) view.getTag();
				((MainActivityInterface) getActivity()).loadThread(recentThreadBean.getBoard(), recentThreadBean.getThread());
			}
		});
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		recentHelper = new RecentHelper(getActivity(), getActivity().getPreferences(0));
		load();
	}

}
