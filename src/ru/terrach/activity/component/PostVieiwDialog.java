package ru.terrach.activity.component;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

public class PostVieiwDialog {
	public static void postViewDialog(Context context, View v) {
		Dialog currentDialog = new Dialog(context);
		currentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		currentDialog.setCanceledOnTouchOutside(true);
		currentDialog.setContentView(v);
		currentDialog.show();
	}
}
