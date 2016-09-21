package com.meeba.vituniversaldatabase.library;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpResponseHelper {
	private HttpResponse _response = null;
	private HttpResponseChecker _checker = null;

	public HttpResponseHelper(HttpResponse response) {
		this._response = response;
		_checker = new HttpResponseChecker(_response);
	}

	public int checkResponse() {
		return _checker.checkResponse();
	}

	public String toString() {
		String str = null;
		try {
			str = EntityUtils.toString(this._response.getEntity());
			Log.i("responseToString", str);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return str;
	}

}
