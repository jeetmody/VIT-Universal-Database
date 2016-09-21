package com.meeba.vituniversaldatabase.library;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.meeba.vituniversaldatabase.R;

public class AlertDialogHelper {
	private Context _context = null;

	public AlertDialogHelper(Context context) {
		this._context = context;
	}

	/**
	 * Function to display error message
	 * 
	 * @param errorCode int errorCode
	 */
	public void showErrorDialog(int errorCode) {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this._context);
		
		alertBuilder = setErrorMessageFromErrorCode(errorCode, alertBuilder);
		
		alertBuilder.setCancelable(true);
		alertBuilder.setNeutralButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						
					}
				});
		
		alertBuilder.create().show();
	}
	
	public void showErrorDialog(int errorCode, boolean fin) {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this._context);
		
		if(!fin) {
			showErrorDialog(errorCode);
		} else {
			alertBuilder = setErrorMessageFromErrorCode(errorCode, alertBuilder);
			
			alertBuilder.setCancelable(true);
			alertBuilder.setNeutralButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							if(_context instanceof Activity) {
								((Activity) _context).finish();
							}
						}
					});
			
			alertBuilder.create().show();
		}
	}

	/**
	 * Function to display error dialog
	 * 
	 * @param errorMessage
	 *            String
	 */
	public void showErrorDialog(String errorMessage) {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this._context);
		
		alertBuilder.setMessage(errorMessage);
		alertBuilder.setCancelable(true);
		alertBuilder.setNeutralButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

					}
				});
		alertBuilder.create().show();
	}
	
	public AlertDialog.Builder setErrorMessageFromErrorCode(int errorCode, AlertDialog.Builder alertBuilder) {
		switch (errorCode) {
		case AppConstants.ERR_BAD_DB_EXEC:
			alertBuilder.setMessage(_context.getResources().getString(R.string.errmsg_registration_unsuccessful));
			break;
		case AppConstants.ERR_BAD_PASSWORD_HASH:
			alertBuilder.setMessage(_context.getResources().getString(R.string.errmsg_rare_error));
			break;
		case AppConstants.ERR_DUPLICATE_USER:
			alertBuilder.setMessage(_context.getResources().getString(R.string.errmsg_duplicate_user));
			break;
		case AppConstants.ERR_NO_INTERNET_CONNECTIVITY:
			alertBuilder.setMessage(_context.getResources().getString(R.string.errmsg_no_internet));
			break;
		case AppConstants.ERR_BAD_GATEWAY:
			alertBuilder.setMessage(_context.getResources().getString(R.string.errmsg_bad_gateway));
			break;
		case AppConstants.ERR_BAD_REQUEST:
			alertBuilder.setMessage(_context.getResources().getString(R.string.errmsg_bad_request));
			break;
		case AppConstants.ERR_REQUEST_TIMEOUT:
			alertBuilder.setMessage(_context.getResources().getString(R.string.errmsg_request_timeout));
			break;
		case AppConstants.ERR_NO_USER:
			alertBuilder.setMessage(_context.getResources().getString(R.string.errmsg_no_user));
			break;
		case AppConstants.ERR_BAD_PASS:
			alertBuilder.setMessage(_context.getResources().getString(R.string.errmsg_bad_pass));
			break;
		case AppConstants.ERR_EMPTY_REQUIRED_FIELD:
			alertBuilder.setMessage(_context.getResources().getString(R.string.errmsg_empty_required_fields));
			break;
		case AppConstants.ERR_HTTP_FORBIDDEN:
			alertBuilder.setMessage(_context.getResources().getString(R.string.errmsg_http_forbidden));
			break;
		case AppConstants.ERR_GATEWAY_TIMEOUT:
			alertBuilder.setMessage(_context.getResources().getString(R.string.errmsg_gateway_timeout));
			break;
		case AppConstants.ERR_GCM_FAIL:
			alertBuilder.setMessage(_context.getResources().getString(R.string.errmsg_gcm_fail));
			break;
		case AppConstants.ERR_PLAY_SERVICES:
			alertBuilder.setMessage(_context.getResources().getString(R.string.errmsg_play_services));
			break;
		default:
			alertBuilder.setMessage("Error");
		}
		
		return alertBuilder;
	}
}
