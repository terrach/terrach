package ru.terrach.activity.component;

public class PostHolder {
	public String board, post;

	public PostHolder(String board, String post) {
		super();
		this.board = board;
		this.post = post;
	}

	@Override
	public String toString() {
		return post;
	}

}
