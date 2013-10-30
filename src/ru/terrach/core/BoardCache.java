package ru.terrach.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.acra.ACRA;

import android.content.Context;

public class BoardCache {
	private static BoardCache instance = new BoardCache();

	private BoardCache() {
	}

	public static BoardCache getInstance() {
		return instance;
	}

	public synchronized void addToCahce(Context context, String board, String json) {
		File boardJsonFile = new FileCache(context).getFile(board + ".json");
		FileWriter fw;
		try {
			fw = new FileWriter(boardJsonFile);
			fw.write(json);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized String getFromCache(Context context, String board) {
		if (isInCache(context, board)) {
			File boardJsonFile = new FileCache(context).getFile(board + ".json");
			StringBuilder text = new StringBuilder();
			try {
				BufferedReader br = new BufferedReader(new FileReader(boardJsonFile));
				String line;
				while ((line = br.readLine()) != null) {
					text.append(line);
					text.append('\n');
				}
				br.close();
				return text.toString();
			} catch (IOException e) {
				ACRA.getErrorReporter().handleException(e);
				return null;
			}
		}
		return null;
	}

	public synchronized Boolean isInCache(Context context, String board) {
		return new FileCache(context).getFile(board + ".json").exists();
	}
}
