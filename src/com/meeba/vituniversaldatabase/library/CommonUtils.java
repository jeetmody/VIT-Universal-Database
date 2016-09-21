package com.meeba.vituniversaldatabase.library;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class CommonUtils {
	private Editor prefEditor = null;
	
	
	/**
	 * Function to get String preference
	 * 
	 * @param key	String
	 * @param pref	SharedPreference
	 * 
	 * @return 		Preference value. Empty string on failure
	 */
	public String getStringPreference(String key, SharedPreferences pref) {
		return pref.getString(key, "");
	}
	
	public String getStringPreference(String key, String defVal, SharedPreferences pref) {
		return pref.getString(key, defVal);
	}
	
	/**
	 * Function to get Integer preference
	 * 
	 * @param key	String
	 * @param int 	int
	 * @param pref	SharedPreference
	 * 
	 * @return	Integer	
	 */
	public int getIntPreference(String key, int defVal, SharedPreferences pref) {
		return pref.getInt(key, defVal);
	}
	
 	public int getIntPreference(String key, SharedPreferences pref) {
 		return pref.getInt(key, Integer.MIN_VALUE);
 	}
	
 	/**
 	 * Function to get boolean preference
 	 * 
 	 * @param key
 	 * @param pref
 	 * 
 	 * @return boolean
 	 */
	public boolean getBoolPreference(String key, boolean defVal, SharedPreferences pref) {
		return pref.getBoolean(key, defVal);
	}
	
	public boolean getBoolPreference(String key, SharedPreferences pref) {
		return pref.getBoolean(key, false);
	}
	
	public void putStringPreference(String key, String value, SharedPreferences pref) {
		if(this.prefEditor == null) {
			this.prefEditor = pref.edit();
		}
		this.prefEditor.putString(key, value);
	}
	
	public void putIntPreference(String key, int value, SharedPreferences pref) {
		if(this.prefEditor == null) {
			this.prefEditor = pref.edit();
		}
		this.prefEditor.putInt(key, value);
	}
	
	public void putBoolPreference(String key, boolean value, SharedPreferences pref) {
		if(this.prefEditor == null) {
			this.prefEditor = pref.edit();
		}
		this.prefEditor.putBoolean(key, value);
	}
	
	public boolean commitPreferences() {
		boolean result = this.prefEditor.commit();
		this.prefEditor = null;
		
		return result;
	}
	
	public boolean checkPlayServices(Activity activity) {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
		
		if(resultCode != ConnectionResult.SUCCESS) {
			if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, activity, AppConstants.PLAY_SERVICES_RESOLUTION_REQUEST);
			} else {
				Log.i("PlayServiceCheck", "This device is not supported");
			}
			return false;
		}
		return true;
	}
}
