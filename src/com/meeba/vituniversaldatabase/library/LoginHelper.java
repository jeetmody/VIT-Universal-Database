package com.meeba.vituniversaldatabase.library;

import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.meeba.vituniversaldatabase.R;

public class LoginHelper {
	private OnTaskFinish _listener;
	private Context _context = null;

	private String _regno = null;
	private String _pwdHash = null;
	private String _pwdRaw = null;
	
	private JSONObject _jsonObj = null;

	private class LoginUserOnlineTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String jsonData = null;
			// HTTP Post request
			HttpPoster httpPoster = null;
			for (String url : urls) {
				httpPoster = new HttpPoster(url);
				break;
			}

			httpPoster.addPair(new BasicNameValuePair("regno", _regno));
			httpPoster.addPair(new BasicNameValuePair("password", _pwdHash));

			HttpResponse response = httpPoster.execute();
			
			boolean loginSuccess = false;
			int errorCode = 0;

			HttpResponseHelper myResponseHelper = new HttpResponseHelper(response);

			int statusCode = myResponseHelper.checkResponse();
			// Toast.makeText(getApplicationContext(), ""+statusCode,
			// Toast.LENGTH_LONG).show();
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
				// Toast.makeText(getApplicationContext(), ""+statusCode,
				// Toast.LENGTH_LONG).show();
				_jsonObj = new JSONObject(result);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			onAfterGetLoginResponse();
		}
	}

	private void getLoginResponse() {
		if (this._pwdHash == null) {
			Hasher pwdHasher = new Hasher();
			this._pwdHash = pwdHasher.toSHA1(this._pwdRaw);
		}

		// Do the login
		LoginUserOnlineTask loginTask = new LoginUserOnlineTask();
		String[] url = { this._context.getResources().getString(
				R.string.LOGIN_URL) };
		loginTask.execute(url);

	}

	private void onAfterGetLoginResponse() {
		boolean loginSuccess = false;
		int errorCode = 0;
//
//		HttpResponseHelper myResponseHelper = new HttpResponseHelper(
//				this._response);
//
//		int statusCode = myResponseHelper.checkResponse();
//		// Toast.makeText(getApplicationContext(), ""+statusCode,
//		// Toast.LENGTH_LONG).show();
//		if (statusCode != AppConstants.OK) {
//			jsonData = "{\"success\": false," + " \"code\": " + statusCode
//					+ "}";
//		} else {
//			jsonData = myResponseHelper.toString();
//		}
//
//		try {
//			// Toast.makeText(getApplicationContext(), ""+statusCode,
//			// Toast.LENGTH_LONG).show();
//			this._jsonObj = new JSONObject(jsonData);
//			loginSuccess = this._jsonObj.getBoolean("success");
//			if (!loginSuccess) {
//				errorCode = this._jsonObj.getInt("code");
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		try {
			loginSuccess = this._jsonObj.getBoolean("success");
			if (!loginSuccess) {
				errorCode = this._jsonObj.getInt("code");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		// if some error while logging in
		if (!loginSuccess) {
			this._listener.onTaskFailed(errorCode);
		} else {
			// Toast.makeText(_context, "Success Login",
			// Toast.LENGTH_LONG).show();
			this._listener.onTaskCompleted();
		}
	}

	/**
	 * Contructor
	 * 
	 * @param rno
	 *            String registration_number
	 * @param pwd
	 *            String password
	 * @param hash
	 *            boolean password_is_hashed
	 */
	public LoginHelper(Context context, String rno, String pwd, boolean hash) {
		this._regno = rno;
		this._context = context;

		if (hash) {
			this._pwdHash = pwd;
		} else {
			this._pwdRaw = pwd;
		}
	}

	public void doLogin() {
		getLoginResponse();
	}

	public void setOnFinishListener(OnTaskFinish listener) {
		this._listener = listener;
	}
}
