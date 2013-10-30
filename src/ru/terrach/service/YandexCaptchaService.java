package ru.terrach.service;

import ru.terrach.network.dto.CaptchaDTO;

public class YandexCaptchaService {
	public static final String YANDEX_URL = "http://captcha.yandex.net/image?key=";

	public CaptchaDTO loadCaptcha(String key) {
		CaptchaDTO captcha = new CaptchaDTO();
		captcha.setKey(key);
		captcha.setUrl(YANDEX_URL + key);
		return captcha;
	}

	private CaptchaResult emptyCaptcha() {
		return new CaptchaResult();
	}

	public CaptchaResult checkCaptchaMakabaResult(String captchaString) {
		if (captchaString == null) {
			return this.emptyCaptcha();
		}

		CaptchaResult result = new CaptchaResult();
		if (captchaString.equals("OK")) {
			result.canSkip = true;
		} else if (captchaString.equals("VIP")) {
			result.canSkip = true;
			result.successPassCode = true;
		} else if (captchaString.startsWith("CHECK")) {
			result.canSkip = false;
			result.captchaKey = captchaString.substring(captchaString.indexOf('\n') + 1);
		} else if (captchaString.equals("VIPFAIL")) {
			result.canSkip = true;
			result.failPassCode = true;
		}

		return result;
	}

	public class CaptchaResult {
		public boolean canSkip;
		public boolean successPassCode;
		public boolean failPassCode;
		public String captchaKey;
	}
}
