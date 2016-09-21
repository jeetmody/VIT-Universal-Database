package com.meeba.vituniversaldatabase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.meeba.vituniversaldatabase.fragments.LoginFragment;
import com.meeba.vituniversaldatabase.library.AlertDialogHelper;
import com.meeba.vituniversaldatabase.library.AppConstants;
import com.meeba.vituniversaldatabase.library.LoginHelper;
import com.meeba.vituniversaldatabase.library.OnTaskFinish;

public class LoginActivity extends FragmentActivity implements
		View.OnClickListener, OnTaskFinish {
	// auto-login flag
	private boolean auto = false;

	private String regno = null;
	private String pwdHash = null;

	private void showAlertDialog(int errorCode) {
		AlertDialogHelper helper = new AlertDialogHelper(this);
		helper.showErrorDialog(errorCode);
	}

	private void autoLoginUser() {
		LoginHelper loginHelper = null;
		loginHelper = new LoginHelper(this, this.regno, this.pwdHash, true);
		loginHelper.setOnFinishListener(this);
		loginHelper.doLogin();
	}

	private void putLoginFields() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		LoginFragment loginFrag = new LoginFragment();

		ft.replace(R.id.login_body, loginFrag);
		ft.commit();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.auto = false;

		setContentView(R.layout.activity_login);

		SharedPreferences prefs = getSharedPreferences(AppConstants.PREF_FILE,
				MODE_PRIVATE);

		if (prefs.getBoolean(AppConstants.PROPERTY_LOGGED_IN, false)) {
			Log.d("here",prefs.getString(AppConstants.PROPERTY_PASSWORD, "foobar"));
			this.auto = true;
			this.pwdHash = prefs.getString(AppConstants.PROPERTY_PASSWORD, null);
			this.regno = prefs.getString(AppConstants.PROPERTY_REGNO, null);
			autoLoginUser();
		} else {
			putLoginFields();
		}

	}

	@Override
	public void onClick(View btn) {
		switch (btn.getId()) {
		case R.id.tv_new_user:
			Intent i = new Intent(this, RegisterActivity.class);
			startActivity(i);
			finish();
		}

	}

	@Override
	public void onTaskCompleted() {
		Toast.makeText(getApplicationContext(), "Auto Login Success",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onTaskFailed(int errorCode) {
		showAlertDialog(errorCode);
	}
}
