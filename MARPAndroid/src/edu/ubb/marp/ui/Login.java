package edu.ubb.marp.ui;

import java.util.Date;

import edu.ubb.marp.Constants;
import edu.ubb.marp.R;
import edu.ubb.marp.network.MyService;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * @author Rakosi Alpar, Vizer Arnold
 */
public class Login extends Activity {
	/**
	 * The requestID
	 */
	private long requestid;

	/**
	 * The loading progress dialog
	 */
	private ProgressDialog loading;

	/**
	 * Username text field
	 * */
	EditText username;
	/**
	 * Password text field
	 * */
	EditText password;
	/**
	 * Login Button
	 * */
	Button login;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		username = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
		login = (Button) findViewById(R.id.button1);

		login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				verify();
			}
		});

		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		if (pref.getBoolean("remember", false)) {
			CheckBox remember = (CheckBox) findViewById(R.id.rememberme);
			username.setText(pref.getString("username", ""));
			password.setText(pref.getString("password", ""));
			remember.setChecked(pref.getBoolean("remember", false));
			verify();
		} else {
			username.setText("");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();

		registerReceiver(broadcastReceiver, new IntentFilter(Constants.BROADCAST_ACTION));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(broadcastReceiver);
	}

	/**
	 * verifies if the password or the user name is entered
	 */
	public void verify() {
		if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
			messageBoxShow("No username or password", "Warning!");
		} else {

			loading = ProgressDialog.show(this, "Login", "Please wait...", true);

			Uri.Builder uri = new Uri.Builder();

			Intent intent = new Intent(this, MyService.class);
			intent.putExtra("ACTION", "LOGIN");
			intent.putExtra("username", username.getText().toString().toLowerCase());
			intent.putExtra("password", password.getText().toString());
			intent.setData(uri.build());

			requestid = new Date().getTime();
			intent.putExtra("requestid", requestid);

			startService(intent);
		}
	}

	/**
	 * is called when a message box with 2 buttons needs to be appeared
	 * */
	public void messageBoxShow(String message, String title) {
		AlertDialog alertDialog;

		alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setButton("Retry", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				verify();
			}
		});
		alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				username.setText("");
				password.setText("");
			}
		});
		alertDialog.show();
	}

	/**
	 * Receives the broadcasts
	 */
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.content.BroadcastReceiver#onReceive(android.content.Context,
		 * android.content.Intent)
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			if (requestid == intent.getLongExtra("originalReqeustid", 0)) {
				if (intent.getBooleanExtra("Successful", false)) {
					CheckBox remember = (CheckBox) findViewById(R.id.rememberme);

					SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					Editor editor = pref.edit();

					editor.putString("username", username.getText().toString().toLowerCase());
					editor.putString("password", password.getText().toString());
					editor.putBoolean("remember", remember.isChecked());

					editor.apply();

					loading.dismiss();
					Intent myIntent;
					if (username.getText().toString().toLowerCase().equals("manager")) {
						myIntent = new Intent(getApplicationContext(), ProjectActivity.class);
					} else {
						myIntent = new Intent(getApplicationContext(), HelloTabActivity.class);
					}
					startActivity(myIntent);
				} else {
					loading.dismiss();
					if (intent.getIntExtra("error", 10000) == 0) {
						SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
						if ((username.getText().toString().equals(pref.getString("username", "")))
								&& (password.getText().toString().equals(pref.getString("password", "")))) {
							Intent myIntent;
							if (username.getText().toString().equals("manager")) {
								myIntent = new Intent(getApplicationContext(), ProjectActivity.class);
							} else {
								myIntent = new Intent(getApplicationContext(), HelloTabActivity.class);
							}
							startActivity(myIntent);
						} else {
							messageBoxShow(Constants.getErrorMessage(intent.getIntExtra("error", 0)), "Error");
						}
					} else {
						messageBoxShow(Constants.getErrorMessage(intent.getIntExtra("error", 0)), "Error");
					}
				}
			}
		}
	};
}
