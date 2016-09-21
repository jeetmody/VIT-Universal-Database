package com.meeba.vituniversaldatabase.library;


import java.util.concurrent.atomic.AtomicInteger;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;

//Class to generate unique view IDs
public class ViewIdGenerator {
	private static final AtomicInteger idGenerated = new AtomicInteger(1);

	@SuppressLint("NewApi")
	public static int generateViewId() {
		if (Build.VERSION.SDK_INT < 17) {
			// If API version is lower than 17
			for (;;) {
				final int res = idGenerated.get();

				int newValue = res + 1;
				if (newValue > 0x00FFFFFF) {
					newValue = 1;
				}
				if (idGenerated.compareAndSet(res, newValue)) {
					return res;
				}
			}
		} else {
			// If API version is newer than 17
			return View.generateViewId();
		}
	}
}
