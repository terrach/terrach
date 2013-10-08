package ru.terrach.activity.component.adapter;

import java.util.List;

import ru.terrach.R;
import ru.terrach.core.RecentThreadBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RecentPostsAdapter extends ArrayAdapter<RecentThreadBean> {
	public RecentPostsAdapter(Context context, List<RecentThreadBean> recent) {
		super(context, R.layout.i_recent_item, recent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null)
			v = LayoutInflater.from(getContext()).inflate(R.layout.i_recent_item, null);
		((TextView) v.findViewById(R.id.tvRecentBoard)).setText(getItem(position).getBoard());
		((TextView) v.findViewById(R.id.tvRecentThread)).setText(getItem(position).getThread());
		v.setTag(getItem(position));
		return v;
	}

}
