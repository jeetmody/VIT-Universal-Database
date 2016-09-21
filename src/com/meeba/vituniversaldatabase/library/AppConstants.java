package com.meeba.vituniversaldatabase.library;

public class AppConstants {
	public static final String PREF_FILE = "VITUDPreferences";

	public static final int ERR = 200;
	public static final int OK = 201;
	public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	public static final String GCM_SENDER_ID = "129610856183";

	// Errors same as server errors
	public static final int ERR_DUPLICATE_USER = 100;
	public static final int ERR_BAD_DB_EXEC = 101;
	public static final int ERR_BAD_PASSWORD_HASH = 102;
	public static final int ERR_NO_USER = 103;
	public static final int ERR_BAD_PASS = 104;
	public static final int ERR_GCM_FAIL = 105;

	// Application errors
	public static final int ERR_NO_INTERNET_CONNECTIVITY = 501;
	public static final int ERR_BAD_GATEWAY = 502;
	public static final int ERR_BAD_REQUEST = 503;
	public static final int ERR_REQUEST_TIMEOUT = 504;
	public static final int ERR_EMPTY_REQUIRED_FIELD = 505;
	public static final int ERR_HTTP_FORBIDDEN = 506;
	public static final int ERR_GATEWAY_TIMEOUT = 507;
	public static final int ERR_PLAY_SERVICES = 508;
	
	// Preference properties
	public static final String PROPERTY_FIRST_TIME = "first_time";
	public static final String PROPERTY_LOGGED_IN = "logged_in";
	public static final String PROPERTY_REGNO = "regno";
	public static final String PROPERTY_PASSWORD = "foobar";
	public static final String PROPERTY_USER_ID = "userId";
	public static final String PROPERTY_GCM_REG_ID = "gcm_reg_id";
	public static final String PROPERTY_APP_VERSION = "app_version";
}
