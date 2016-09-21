package com.meeba.vituniversaldatabase.library;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class InternetConnectivityHelper {
	private ConnectivityManager connManager = null;

	public InternetConnectivityHelper(Object object) {
		connManager = (ConnectivityManager) object;
	}

	public boolean isConnectedToInternet() {
		Log.d("connHelper", "checking connectivity");
		if (connManager != null) {
			NetworkInfo[] info = connManager.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}

		return false;
	}

}
