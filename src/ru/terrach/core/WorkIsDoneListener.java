package ru.terrach.core;

public interface WorkIsDoneListener {
	public void done(String... params);
	public void exception(Exception e);
}
