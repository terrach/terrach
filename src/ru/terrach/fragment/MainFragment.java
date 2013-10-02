package ru.terrach.fragment;

import ru.terrach.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class MainFragment extends Fragment {

	private GridView gvLast;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.f_main, null);
	}

	private void load() {
		gvLast = (GridView) getView().findViewById(R.id.gvMainRecent);
		gvLast.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[] { "recent item1", "recent item2",
				"recent item3" }));
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		load();
	}

}
