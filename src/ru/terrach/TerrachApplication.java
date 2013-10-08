package ru.terrach;

import android.app.Application;
import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

@ReportsCrashes(formKey = "", formUri = "http://terranz.ath.cx:8080/jbrss/errors/do.error.report", httpMethod = HttpSender.Method.POST, mode = ReportingInteractionMode.TOAST, resToastText = R.string.error_caught)
public class TerrachApplication extends Application {
	@Override
	public void onCreate() {
		ACRA.init(this);
		super.onCreate();
	}
}