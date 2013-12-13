package ru.terrach.core;

public class URLUtil {
	private static int secondSlash;
	private static int thirdSlash;
	private static int point;
	private static int sharp;

	// example" /pr/res/342560.html#342560
	public static void prepareUrl(String url) {
		secondSlash = url.indexOf("/", 1);
		thirdSlash = url.indexOf("/", secondSlash + 1);
		point = url.indexOf(".");
		sharp = url.indexOf("#");
	}

	public static boolean isBoardUrl(String url) {
		return false;
	}

	public static boolean isThreadUrl(String url) {
		return false;
	}

	public static boolean isImageUrl(String url) {
		return false;
	}

	public static String getBoardNameFromLink(String url) {
		String ret = url.substring(0, url.indexOf("/", 1) + 1);
		return ret;
	}

	public static String getThreadFromLink(String url) {
		prepareUrl(url);
		return url.substring(thirdSlash + 1, point);
	}

	public static String getMessageFromUrl(String url) {
		prepareUrl(url);
		return url.substring(sharp + 1, url.length());
	}
}
