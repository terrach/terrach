package ru.terrach.network.dto;

public class CaptchaDTO {
	private String url;
	private String key;

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
