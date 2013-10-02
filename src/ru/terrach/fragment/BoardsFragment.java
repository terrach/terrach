package ru.terrach.fragment;

import ru.terrach.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BoardsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.f_boards, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		((ListView) getView().findViewById(R.id.lvBoards)).setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
				new String[] { "/b/", "/pr/" }));
	}

}
