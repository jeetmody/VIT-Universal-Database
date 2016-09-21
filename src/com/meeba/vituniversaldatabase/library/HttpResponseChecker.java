package com.meeba.vituniversaldatabase.library;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import android.util.Log;

public class HttpResponseChecker {
	private HttpResponse _response = null;
	
	public HttpResponseChecker(HttpResponse response) {
		this._response = response;
	}
	
	public int checkResponse() {
		int resCode = 0;
		
		if(this._response == null) {
			return 501;
		}
		int statusCode = this._response.getStatusLine().getStatusCode();
		Log.d("CheckResponse", ""+statusCode);
		
		switch(statusCode) {
		case HttpStatus.SC_OK:
			resCode = AppConstants.OK;
			break;
		case HttpStatus.SC_BAD_GATEWAY:
			resCode = AppConstants.ERR_BAD_GATEWAY;
			break;
		case HttpStatus.SC_BAD_REQUEST:
			resCode = AppConstants.ERR_BAD_REQUEST;
			break;			
		case HttpStatus.SC_REQUEST_TIMEOUT:
			resCode = AppConstants.ERR_REQUEST_TIMEOUT;
			break;
		case HttpStatus.SC_FORBIDDEN:
			resCode = AppConstants.ERR_HTTP_FORBIDDEN;
			break;
		case HttpStatus.SC_GATEWAY_TIMEOUT:
			resCode = AppConstants.ERR_GATEWAY_TIMEOUT;
			break;
		default:
			resCode = AppConstants.ERR;
		}
		
		return resCode;
	}
}
