package ru.terrach.tasks;

import java.net.URL;
import java.net.URLConnection;

import lazylist.ImageLoader;
import ru.terrach.R;
import ru.terrach.core.AsyncTaskEx;
import ru.terrach.core.Utils;
import ru.terrach.network.dto.CaptchaDTO;
import ru.terrach.service.YandexCaptchaService;
import ru.terrach.service.YandexCaptchaService.CaptchaResult;
import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

public class CaptchaLoadAsyncTask extends AsyncTaskEx<Void, Void, Boolean> {
	private static String captchaURL = "makaba/captcha.fcgi";
	private YandexCaptchaService yandexCaptchaService = new YandexCaptchaService();
	private String server;
	private CaptchaResult result;
	private ImageView captchaImageView;
	private CaptchaDTO dto;

	public CaptchaLoadAsyncTask(Context a, ImageView captchaImageView) {
		super(a);
		this.captchaImageView = captchaImageView;
		server = a.getString(R.string.server);
	}

	@Override
	protected void onCancelled() {
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		URLConnection conn;
		try {
			conn = new URL(server + captchaURL).openConnection();
			result = yandexCaptchaService.checkCaptchaMakabaResult(Utils.convertStreamToString(conn.getInputStream()));
			String captchaKey = result.captchaKey;
			dto = yandexCaptchaService.loadCaptcha(captchaKey);
		} catch (Exception e) {
			exception = e;
			return false;
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (exception != null) {
			Toast.makeText(context, "Ошибка при загрузке капчи: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
			return;
		}
		if (captchaImageView != null) {
			new ImageLoader(context).DisplayImage(dto.getUrl(), captchaImageView);
			captchaImageView.setTag(dto.getKey());
		}
	}
}
