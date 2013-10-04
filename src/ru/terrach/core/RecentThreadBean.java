package ru.terrach.core;

public class RecentThreadBean {
	private String board, thread;

	public RecentThreadBean(String board, String thread) {
		super();
		this.board = board;
		this.thread = thread;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

}
