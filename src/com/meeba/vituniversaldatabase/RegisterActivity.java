package com.meeba.vituniversaldatabase;

import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.meeba.vituniversaldatabase.library.AlertDialogHelper;
import com.meeba.vituniversaldatabase.library.AppConstants;
import com.meeba.vituniversaldatabase.library.CommonUtils;
import com.meeba.vituniversaldatabase.library.Hasher;
import com.meeba.vituniversaldatabase.library.HttpPoster;
import com.meeba.vituniversaldatabase.library.HttpResponseHelper;

public class RegisterActivity extends Activity implements OnClickListener {	
	private EditText et_regno;
	private EditText et_email;
	private EditText et_password;

	private Button btn_register;

	private String regno;
	private String email;
	private String rawPassword;

	private String hashedPassword;

	private JSONObject jsonObj = null;
	private int userId;
	private SharedPreferences prefs = null;

	private class RegisterUserOnlineTask extends
			AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			String jsonData = null;
			// HTTP Post request
			HttpPoster httpPoster = null;
			for (String url : urls) {
				httpPoster = new HttpPoster(url);
				break;
			}

			// Add the post variables to the request
			httpPoster.addPair(new BasicNameValuePair("regno", regno));
			httpPoster.addPair(new BasicNameValuePair("email", email));
			httpPoster.addPair(new BasicNameValuePair("password",
					hashedPassword));

			// Execute the request
			HttpResponse response = httpPoster.execute();
			
			HttpResponseHelper myResponseHelper = new HttpResponseHelper(response);

			// check the response
			int statusCode = myResponseHelper.checkResponse();

			if (statusCode != AppConstants.OK) {
				jsonData = "{\"success\": false," + " \"code\": " + statusCode
						+ "}";
			} else {
				jsonData = myResponseHelper.toString();
			}

			return jsonData;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				jsonObj = new JSONObject(result);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			onAfterGetRegisterResponse();
		}

	}

	private void showAlertDialog(int errorCode) {
		Log.d("alertDiag", "" + errorCode);
		AlertDialogHelper helper = new AlertDialogHelper(this);
		helper.showErrorDialog(errorCode);
	}

	private void registerDetails() {
		getRegisterResponse();
	}

	private void getRegisterResponse() {
		// get EditText contents
		et_regno = (EditText) findViewById(R.id.editText_regno);
		et_email = (EditText) findViewById(R.id.editText_email);
		et_password = (EditText) findViewById(R.id.editText_password);

		// Get data from the EditTexts
		regno = et_regno.getText().toString();
		email = et_email.getText().toString();
		rawPassword = et_password.getText().toString();

		// Declare new Hasher object
		Hasher stringHasher = new Hasher();

		// Hash the raw password
		hashedPassword = stringHasher.toSHA1(rawPassword);

		// Register the user
		RegisterUserOnlineTask registerUserResponse = new RegisterUserOnlineTask();
		String[] url = { getResources().getString(R.string.REGISTER_URL) };
		registerUserResponse.execute(url);
	}

	private void onAfterGetRegisterResponse() {
		boolean success = false;
		int errorCode = AppConstants.ERR;
		
		try {
			success = jsonObj.getBoolean("success");
			
			if (!success) {
				errorCode = jsonObj.getInt("code");
			} else {
				this.userId = jsonObj.getInt("userId");
			}
		} catch (JSONException e) {
			errorCode = AppConstants.ERR;
			Log.i("jsonError", "Not Valid Json String");
		}

		if (!success) { // If some error occurred
			showAlertDialog(errorCode);
		} else {
			CommonUtils helperUtility = new CommonUtils();
			helperUtility.putBoolPreference(AppConstants.PROPERTY_FIRST_TIME, false, this.prefs);
			helperUtility.putBoolPreference(AppConstants.PROPERTY_LOGGED_IN, true, this.prefs);
			helperUtility.putStringPreference(AppConstants.PROPERTY_REGNO, regno, this.prefs);
			helperUtility.putStringPreference(AppConstants.PROPERTY_PASSWORD, hashedPassword, this.prefs);
			helperUtility.putIntPreference(AppConstants.PROPERTY_USER_ID, this.userId, this.prefs);
			helperUtility.commitPreferences();
			
			// STOPPED HERE --- gotta start next page
			Toast.makeText(getApplicationContext(), "Registration success",
					Toast.LENGTH_LONG).show();
		}
	}

	private boolean validateFields() {
		et_regno = (EditText) findViewById(R.id.editText_regno);
		et_email = (EditText) findViewById(R.id.editText_email);
		et_password = (EditText) findViewById(R.id.editText_password);

		regno = et_regno.getText().toString();
		email = et_email.getText().toString();
		rawPassword = et_password.getText().toString();

		if (regno.length() == 0 || email.length() == 0
				|| rawPassword.length() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		CommonUtils helperUtility = new CommonUtils();
		helperUtility.checkPlayServices(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CommonUtils helperUtility = new CommonUtils();
		
		setContentView(R.layout.activity_register);
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_register.setOnClickListener(this);
		
		this.prefs = getSharedPreferences(AppConstants.PREF_FILE, MODE_PRIVATE);
		
		if(!helperUtility.checkPlayServices(this)) {
			showAlertDialog(AppConstants.ERR_PLAY_SERVICES);
			finish();
		}
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
		// on clicking the "Register" button
		case R.id.btn_register:
			boolean valid = validateFields();
			if (valid) {
				registerDetails();
			} else {
				showAlertDialog(AppConstants.ERR_EMPTY_REQUIRED_FIELD);
			}
			break;
			
		case R.id.tv_login:
			Intent i = new Intent(this, LoginActivity.class);
			SharedPreferences sharedPrefs = getSharedPreferences(
					AppConstants.PREF_FILE, MODE_PRIVATE);
			Editor prefEditor = sharedPrefs.edit();
			prefEditor.putBoolean("first_time", false);
			prefEditor.commit();
			startActivity(i);
			finish();
			break;
		}
	}
}
