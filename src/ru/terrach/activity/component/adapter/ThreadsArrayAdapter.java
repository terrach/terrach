package ru.terrach.activity.component.adapter;

import java.util.List;

import lazylist.ImageLoader;
import ru.terrach.R;
import ru.terrach.activity.PicViewActivity;
import ru.terrach.activity.component.PostViewHolder;
import ru.terrach.network.dto.PostDTO;
import ru.terrach.network.dto.ThreadDTO;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ThreadsArrayAdapter extends ArrayAdapter<ThreadDTO> {

	private ImageLoader imageLoader;
	private String board;
	private String server;

	public ThreadsArrayAdapter(Context context, List<ThreadDTO> threads, String board) {
		super(context, R.layout.i_thread_item, threads);
		this.board = board;
		imageLoader = new ImageLoader(context);
		server = context.getString(R.string.server);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;
		if (convertView == null)
			v = LayoutInflater.from(getContext()).inflate(R.layout.i_thread_item, null);
		else
			v = convertView;

		PostViewHolder vh = null;
		if (v.getTag() == null) {
			vh = new PostViewHolder();
			vh.date = ((TextView) v.findViewById(R.id.tvPostDate));
			vh.num = ((TextView) v.findViewById(R.id.tvPostNum));
			vh.msg = ((TextView) v.findViewById(R.id.tvPostMessage));
			vh.pic = ((ImageView) v.findViewById(R.id.ivPostImage));
			vh.pic.setClickable(true);
			vh.posts = ((TextView) v.findViewById(R.id.tvThreadPosts));
			vh.pics = ((TextView) v.findViewById(R.id.tvThreadPics));
			v.setTag(vh);
		} else
			vh = (PostViewHolder) v.getTag();
		final PostDTO firstPost = getItem(position).posts.get(0).get(0);
		vh.date.setText(firstPost.date);
		vh.num.setText(firstPost.num.toString());
		vh.msg.setText(Html.fromHtml(firstPost.comment));
		vh.pics.setText(getItem(position).image_count.toString() + " пикчей");
		vh.posts.setText(getItem(position).reply_count.toString() + " ответов");
		if (firstPost.image != null) {
			imageLoader.DisplayImage(server + board + firstPost.thumbnail, vh.pic);
			vh.pic.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					getContext().startActivity(
							new Intent(getContext(), PicViewActivity.class).putExtra(PicViewActivity.PIC_URL, server + board + firstPost.image));
				}
			});
		} else {
			vh.pic.setImageResource(R.drawable.no_photo);
		}

		return v;
	}

	public void update(List<ThreadDTO> threads) {
		notifyDataSetChanged();
	}
}
