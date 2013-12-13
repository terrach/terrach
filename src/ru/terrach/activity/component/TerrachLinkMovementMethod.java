package ru.terrach.activity.component;

import ru.terrach.core.URLUtil;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class TerrachLinkMovementMethod extends LinkMovementMethod {

	private static Context movementContext;

	private static TerrachLinkMovementMethod linkMovementMethod = new TerrachLinkMovementMethod();

	private static UrlClickHandler clickHandler;

	public boolean onTouchEvent(android.widget.TextView widget, android.text.Spannable buffer, android.view.MotionEvent event) {
		int action = event.getAction();

		if (action == MotionEvent.ACTION_UP) {
			int x = (int) event.getX();
			int y = (int) event.getY();

			x -= widget.getTotalPaddingLeft();
			y -= widget.getTotalPaddingTop();

			x += widget.getScrollX();
			y += widget.getScrollY();

			Layout layout = widget.getLayout();
			int line = layout.getLineForVertical(y);
			int off = layout.getOffsetForHorizontal(line, x);

			URLSpan[] link = buffer.getSpans(off, off, URLSpan.class);
			if (link.length != 0) {
				String url = link[0].getURL();
				if (clickHandler != null)
					clickHandler.handleUrlClick(url);
//				Log.d("Link", url);
//				Log.d("Link", URLUtil.getBoardNameFromLink(url));
//				Log.d("Link", URLUtil.getThreadFromLink(url));
//				Log.d("Link", URLUtil.getMessageFromUrl(url));
				return true;
			}
		}

		return super.onTouchEvent(widget, buffer, event);
	}

	public static android.text.method.MovementMethod getInstance(Context c, UrlClickHandler clickHandler) {
		movementContext = c;
		TerrachLinkMovementMethod.clickHandler = clickHandler;
		return linkMovementMethod;
	}
}