package com.meeba.vituniversaldatabase;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.meeba.vituniversaldatabase.library.AlertDialogHelper;
import com.meeba.vituniversaldatabase.library.AppConstants;
import com.meeba.vituniversaldatabase.library.CommonUtils;

public class MainActivity extends Activity {
	private CommonUtils helperUtility = new CommonUtils();
	private SharedPreferences prefs = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.prefs = getSharedPreferences(AppConstants.PREF_FILE, MODE_PRIVATE);
		
		if(!helperUtility.checkPlayServices(this)) {
			AlertDialogHelper alertHelper = new AlertDialogHelper(this);
			alertHelper.showErrorDialog(AppConstants.ERR_PLAY_SERVICES, true);
			//startActivity(new Intent(this, NoPlayServiceActivity.class));
		} else if (this.prefs.getBoolean("first_time", true)){
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        } else{
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
		Log.i("MainActivity", "End of App");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
