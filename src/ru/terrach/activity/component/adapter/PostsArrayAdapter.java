package ru.terrach.activity.component.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lazylist.ImageLoader;
import ru.terrach.R;
import ru.terrach.activity.PicViewActivity;
import ru.terrach.activity.component.PostViewHolder;
import ru.terrach.network.dto.PostDTO;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class PostsArrayAdapter extends ArrayAdapter<List<PostDTO>> {
	private ImageLoader imageLoader;
	private String board;
	private String server;
	private Map<Integer, List<Integer>> replyes = new HashMap<Integer, List<Integer>>();

	public PostsArrayAdapter(Context context, List<List<PostDTO>> posts, String board) {
		super(context, R.layout.i_thread_item, posts);
		this.board = board;
		imageLoader = new ImageLoader(context);
		server = context.getString(R.string.server);
		process(posts);
	}

	private void process(List<List<PostDTO>> posts) {

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = null;
		PostViewHolder vh = null;
		if (convertView == null) {
			v = LayoutInflater.from(getContext()).inflate(R.layout.i_thread_item, null);
			vh = new PostViewHolder();
			vh.date = ((TextView) v.findViewById(R.id.tvPostDate));
			vh.num = ((TextView) v.findViewById(R.id.tvPostNum));
			vh.msg = ((TextView) v.findViewById(R.id.tvPostMessage));
			vh.pic = ((ImageView) v.findViewById(R.id.ivPostImage));
			v.setTag(vh);
		} else {
			v = convertView;
			vh = (PostViewHolder) v.getTag();
		}

		vh.date.setText(getItem(position).get(0).date);
		vh.num.setText(getItem(position).get(0).num.toString());
		vh.msg.setText(Html.fromHtml(getItem(position).get(0).comment));
		if (getItem(position).get(0).image != null) {
			imageLoader.DisplayImage(server + board + getItem(position).get(0).thumbnail, vh.pic);
			vh.pic.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					getContext().startActivity(
							new Intent(getContext(), PicViewActivity.class).putExtra(PicViewActivity.PIC_URL,
									server + board + getItem(position).get(0).image));
				}
			});
		}
		if (replyes.containsKey(getItem(position).get(0).num)) {
			LinearLayout ll = (LinearLayout) v.findViewById(R.id.llReplyes);
			for (Integer reply : replyes.get(getItem(position).get(0).num)) {
				TextView tr = new TextView(getContext());
				tr.setText(reply);
				LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				lp.setMargins(5, 5, 5, 5);
				ll.addView(tr);
			}
		}
		return v;
	}

	public void update(List<List<PostDTO>> posts) {
	}

}
