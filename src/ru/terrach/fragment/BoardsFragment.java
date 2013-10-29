package ru.terrach.fragment;

import ru.terrach.R;
import ru.terrach.activity.MainActivityInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BoardsFragment extends Fragment {

	private String[] boards = new String[] { "/b/", "/pr/" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.f_boards, null);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ListView lvBoards = ((ListView) getView().findViewById(R.id.lvBoards));
		if (lvBoards == null) {
			Toast.makeText(getActivity(), "Не наден listview", Toast.LENGTH_SHORT).show();
		} else {
			lvBoards.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, boards));
			lvBoards.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					((MainActivityInterface) getActivity()).loadBoard(boards[position]);
				}
			});
		}

	}
}
