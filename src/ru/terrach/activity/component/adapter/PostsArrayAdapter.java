package ru.terrach.activity.component.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lazylist.ImageLoader;
import ru.terrach.MainActivity;
import ru.terrach.R;
import ru.terrach.activity.PicViewActivity;
import ru.terrach.activity.component.PostVieiwDialog;
import ru.terrach.activity.component.PostViewHolder;
import ru.terrach.activity.component.TerrachLinkMovementMethod;
import ru.terrach.activity.component.UrlClickHandler;
import ru.terrach.core.URLUtil;
import ru.terrach.network.dto.PostDTO;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
	private String thread;

	public PostsArrayAdapter(Context context, List<List<PostDTO>> posts, String board, String thread) {
		super(context, R.layout.i_thread_item, posts);
		this.board = board;
		this.thread = thread;
		imageLoader = new ImageLoader(context);
		server = context.getString(R.string.server);
		process(posts);
	}

	private void process(List<List<PostDTO>> posts) {

	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View v = null;
		PostViewHolder vh = null;
		if (convertView == null) {
			v = LayoutInflater.from(getContext()).inflate(R.layout.i_thread_item, null);
			vh = new PostViewHolder();
			vh.date = ((TextView) v.findViewById(R.id.tvPostDate));
			vh.num = ((TextView) v.findViewById(R.id.tvPostNum));
			vh.msg = ((TextView) v.findViewById(R.id.tvPostMessage));
			vh.msg.setMovementMethod(LinkMovementMethod.getInstance());
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
		} else {
			vh.pic.setImageResource(R.drawable.no_photo);
		}
		// if (replyes.containsKey(getItem(position).get(0).num)) {
		// LinearLayout ll = (LinearLayout) v.findViewById(R.id.llReplyes);
		// for (Integer reply : replyes.get(getItem(position).get(0).num)) {
		// TextView tr = new TextView(getContext());
		// tr.setText(reply);
		// LinearLayout.LayoutParams lp = new
		// LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		// lp.setMargins(5, 5, 5, 5);
		// ll.addView(tr);
		// }
		// }
		vh.msg.setMovementMethod(TerrachLinkMovementMethod.getInstance(getContext(), new UrlClickHandler() {

			@Override
			public void handleUrlClick(String url) {
				String targetBoard = URLUtil.getBoardNameFromLink(url);
				String targetTread = URLUtil.getThreadFromLink(url);
				String targetMessage = URLUtil.getMessageFromUrl(url);
				if (targetBoard.equals(board) && targetTread.equals(thread)) {
					PostVieiwDialog.postViewDialog(getContext(), getView(getMessageIndexById(targetMessage), null, null));
				} else {
					Intent intent = new Intent(getContext(), MainActivity.class).putExtra(MainActivity.OPEN_EXTERNAL_OP, MainActivity.OPEN_THREAD)
							.putExtra(MainActivity.OPEN_EXTERNAL_URL, url);
					getContext().startActivity(intent);
				}
			}
		}));
		return v;
	}

	public void update(List<List<PostDTO>> posts) {
	}

	private int getMessageIndexById(String message) {
		for (int ret = 0; ret < getCount(); ret++) {
			if (getItem(ret).get(0).num.toString().equals(message))
				return ret;
		}
		return 0;
	}
}
