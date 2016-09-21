package com.meeba.vituniversaldatabase.library;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import android.util.Log;

public class HttpPoster {
	private HttpClient client = null;
	private HttpPost post = null;
	private HttpParams params = null;

	private int connectionTimeout = 60000;
	private int socketTimeout = 60000;

	private List<NameValuePair> pairs = null;

	private HttpResponse response;

	public HttpPoster(String url) {
		params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
		HttpConnectionParams.setSoTimeout(params, socketTimeout);

		client = new DefaultHttpClient(params);
		post = new HttpPost(url);

		pairs = new ArrayList<NameValuePair>();
	}

	public void addPair(NameValuePair pair) {
		pairs.add(pair);
	}

	public HttpResponse execute() {
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			//StringEntity se = new StringEntity("application/x-www-form-urlencoded", HTTP.UTF_8);
			//se.setContentType("application/x-www-form-urlencoded");
			//post.setEntity(se);
			//post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			Log.d("postDet", post.getHeaders("Content-Type").toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;
	}
}
