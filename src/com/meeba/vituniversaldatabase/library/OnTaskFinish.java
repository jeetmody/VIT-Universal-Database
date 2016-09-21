package com.meeba.vituniversaldatabase.library;

public interface OnTaskFinish {
	void onTaskCompleted();

	void onTaskFailed(int errorCode);
}
