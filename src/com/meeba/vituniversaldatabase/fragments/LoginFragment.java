package com.meeba.vituniversaldatabase.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.meeba.vituniversaldatabase.R;
import com.meeba.vituniversaldatabase.library.AlertDialogHelper;
import com.meeba.vituniversaldatabase.library.AppConstants;
import com.meeba.vituniversaldatabase.library.Hasher;
import com.meeba.vituniversaldatabase.library.LoginHelper;
import com.meeba.vituniversaldatabase.library.OnTaskFinish;

public class LoginFragment extends Fragment implements OnClickListener,
		OnTaskFinish {
	private String _regno = null;
	private String _pwdHash = null;
	private String _pwdRaw = null;

	private Context _context = null;
	private View _currLayout = null;

	private void doPrefChanges() {
		@SuppressWarnings("static-access")
		SharedPreferences sharedPrefs = this._context.getSharedPreferences(
				AppConstants.PREF_FILE, this._context.MODE_PRIVATE);
		Editor prefEditor = sharedPrefs.edit();
		prefEditor.putBoolean("logged_in", true);
		prefEditor.putString("regno", this._regno);
		prefEditor.putString("potty", this._pwdHash);
		prefEditor.commit();
		Log.d("prefChange", sharedPrefs.getString("potty", "potty"));
	}

	private void loginUser() {
		LoginHelper loginHelper = null;
		Hasher hasher = new Hasher();
		this._pwdHash = hasher.toSHA1(this._pwdRaw);
		
		loginHelper = new LoginHelper(this._context, this._regno, this._pwdRaw,
				false);
		loginHelper.setOnFinishListener(this);
		loginHelper.doLogin();
	}

	private void showAlertDialog(int errorCode) {
		AlertDialogHelper helper = new AlertDialogHelper(_context);
		// Toast.makeText(getApplicationContext(), ""+errorCode,
		// Toast.LENGTH_LONG).show();
		helper.showErrorDialog(errorCode);

	}

	private boolean validateFields() {
		EditText et_regno = (EditText) this._currLayout
				.findViewById(R.id.login_regno);
		EditText et_password = (EditText) this._currLayout
				.findViewById(R.id.login_password);

		this._pwdRaw = et_password.getText().toString();
		this._regno = et_regno.getText().toString();

		if (this._pwdRaw.length() == 0 || this._regno.length() == 0)
			return false;
		else
			return true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this._context = container.getContext();

		this._currLayout = inflater.inflate(R.layout.fragment_login_fields,
				container, false);

		Button btnLogin = (Button) this._currLayout
				.findViewById(R.id.btn_login);

		btnLogin.setOnClickListener(this);

		return this._currLayout;
	}

	@Override
	public void onClick(View btn) {
		switch (btn.getId()) {
		case R.id.btn_login:
			boolean ready = validateFields();
			if (ready)
				loginUser();
			else
				showAlertDialog(AppConstants.ERR_EMPTY_REQUIRED_FIELD);
			break;
		}
	}

	@Override
	public void onTaskCompleted() {
		Toast.makeText(_context, "Login Success", Toast.LENGTH_LONG).show();
		doPrefChanges();
	}

	@Override
	public void onTaskFailed(int errorCode) {
		showAlertDialog(errorCode);
	}
}
