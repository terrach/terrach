package ru.terrach.tasks;

import java.nio.charset.Charset;

import org.acra.ACRA;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import ru.terrach.R;
import ru.terrach.constants.Constants;
import ru.terrach.core.AsyncTaskEx;
import ru.terrach.core.PostBean;
import ru.terrach.core.Utils;
import android.content.Context;
import android.text.TextUtils;

public class SendPostAsyncTask extends AsyncTaskEx<PostBean, Integer, Boolean> {

	private HttpPost httpPost;
	private String server;

	public SendPostAsyncTask(Context a) {
		super(a);
		server = a.getString(R.string.server);
	}

	@Override
	protected void onCancelled() {

	}

	@Override
	protected Boolean doInBackground(PostBean... params) {
		PostBean post = params[0];
		httpPost = new HttpPost(server + "/" + post.getBoard() + "/wakaba.pl");
		HttpClientParams.setRedirecting(httpPost.getParams(), false);
		httpPost.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2109);

		HttpResponse response = null;
		try {
			Charset utf = Charset.forName("UTF-8");
			// Заполняем параметры для отправки сообщения
			MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			multipartEntity.addPart("task", new StringBody("post", utf));
			multipartEntity.addPart("parent", new StringBody(post.getParent().toString(), utf));
			multipartEntity.addPart("shampoo", new StringBody(post.getText(), utf));

			if (!TextUtils.isEmpty(post.getCaptcha())) {
				multipartEntity.addPart("captcha", new StringBody(post.getCaptcha(), utf));
				multipartEntity.addPart("captcha_value_id_06", new StringBody(post.getCaptchaAnswer(), utf));
			}

			if (post.getSage()) {
				multipartEntity.addPart("nabiki", new StringBody("awd", utf));
			}
			// if (entity.getAttachment() != null) {
			// multipartEntity.addPart(fields.getFile(), new
			// FileBody(entity.getAttachment()));
			// }
			// if (entity.getVideo() != null) {
			// multipartEntity.addPart(fields.getVideo(), new
			// StringBody(entity.getVideo(), utf));
			// }
			if (post.getTitle() != null) {
				multipartEntity.addPart("kasumi", new StringBody(post.getTitle(), utf));
			}
			// if (!TextUtils.isEmpty(entity.getName())) {
			// multipartEntity.addPart(fields.getName(), new
			// StringBody(entity.getName(), utf));
			// }
			// Only for /po and /test
			// if (entity.getPolitics() != null) {
			// multipartEntity.addPart("anon_icon", new
			// StringBody(entity.getPolitics(), utf));
			// }

			httpPost.setEntity(multipartEntity);
			response = new DefaultHttpClient().execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 502) {
				httpPost.abort();
			}

			// // TODO: rewrite this error handling
			// if (statusCode == 301 ) {
			// uri = ExtendedHttpClient.getLocationHeader(response);
			// had301 = true;
			// i--;
			// }

			// Вернуть ссылку на тред после успешной отправки и редиректа
			if (statusCode == 302 || statusCode == 303) {
				// return ExtendedHttpClient.getLocationHeader(response);
				return true;
			}

			if (statusCode != 200) {
				// throw new SendPostException(statusCode + " - " +
				// response.getStatusLine().getReasonPhrase());
			}

			// Проверяю 200-response на наличие html-разметки с ошибкой
			String responseText = null;
			try {
				responseText = Utils.convertStreamToString(response.getEntity().getContent());
			} catch (Exception e) {
				// throw new SendPostException(e.getMessage());
				ACRA.getErrorReporter().handleSilentException(e);
			}

		} catch (Exception e) {
			// MyLog.e(TAG, e);
			// throw new
			// SendPostException(this.mResources.getString(R.string.error_send_post));
			ACRA.getErrorReporter().handleSilentException(e);
		}

		return false;
	}
}
