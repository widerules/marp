package edu.ubb.marp.ui;

import java.util.Date;

import edu.ubb.marp.Constants;
import edu.ubb.marp.R;
import edu.ubb.marp.database.DatabaseContract;
import edu.ubb.marp.database.DatabaseContract.TABLE_USERS;
import edu.ubb.marp.network.MyService;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class NewProjectActivity extends Activity {
	private static final String tag = "NewProjectActivity";
	private ProgressDialog loading;
	private long requestid;
	
	private int myresourceid;
	private int resourceID;
	private int projectID;
	private int startWeek, endWeek, deadLine;
	private String projectName;
	private int[] booking;
	private int minWeek, maxWeek;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(tag, "onCreate");

		setContentView(R.layout.newproject);
		
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

		Uri.Builder uri = new Uri.Builder();
		uri = new Uri.Builder();
		uri.authority(DatabaseContract.PROVIDER_NAME);
		uri.path(DatabaseContract.TABLE_USERS);
		uri.scheme("content");

		ContentResolver cr = getContentResolver();

		String projection[] = { TABLE_USERS.USERID };

		Cursor c = cr.query(uri.build(), projection, TABLE_USERS.USERNAME + " = '" + pref.getString("username", "") + "'", null,
				null);

		c.moveToFirst();

		resourceID = c.getInt(c.getColumnIndex(TABLE_USERS.USERID));

		Button addButton = (Button) findViewById(R.id.addProjectButton);
		addButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//sendRequest();
				EditText projectName = (EditText) findViewById(R.id.projectName);
				CheckBox openedStatus = (CheckBox) findViewById(R.id.openedStatus);
				EditText startweek = (EditText) findViewById(R.id.startweek);
				EditText deadline = (EditText) findViewById(R.id.deadline);
				EditText nextrelease = (EditText) findViewById(R.id.nextrelease);
				Spinner status = (Spinner) findViewById(R.id.statusSpinner);
				
				if ((projectName.getText().toString().isEmpty())
						|| (startweek.getText().toString().isEmpty())
						|| (deadline.getText().toString().isEmpty())
						|| (nextrelease.getText().toString().isEmpty()))
					messageBoxShow("Please fill in all fields", "Error!");
				else {
				
				Intent myIntent = new Intent(getApplicationContext(), StripeActivity.class);
				Bundle bundle = new Bundle();
				
				int startWeek=Integer.parseInt(startweek.getText().toString());
				int endWeek;
				int deadLine=Integer.parseInt(deadline.getText().toString());
				if(deadLine-startWeek>24)
					endWeek=startWeek+24;
				else
					endWeek=deadLine;
				
				bundle.putString("ACTION", "NEWPROJECT");
				bundle.putString("projectname", projectName.getText().toString());
				bundle.putBoolean("openedstatus", openedStatus.isChecked());
				bundle.putString("startweek", startweek.getText().toString());
				bundle.putString("endweek", Integer.toString(endWeek));
				bundle.putString("deadline", deadline.getText().toString());
				bundle.putString("nextrelease", nextrelease.getText().toString());
				bundle.putString("statusname", status.getSelectedItem().toString());

				myIntent.putExtras(bundle);
				startActivity(myIntent);
				}
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();

		registerReceiver(broadcastReceiver, new IntentFilter(
				Constants.BROADCAST_ACTION));
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(broadcastReceiver);
	}
	
	private void sendRequest() {
		loading = ProgressDialog.show(this, "Loading", "Please wait...");

		Uri.Builder uriSending = new Uri.Builder();
		uriSending.authority(DatabaseContract.PROVIDER_NAME);
		uriSending.path(Integer.toString(Constants.QUERYAVAILABLERESOURCESCODE));
		uriSending.scheme("content");

		EditText startWeekText = (EditText) findViewById(R.id.startweek);
		EditText deadLineText = (EditText) findViewById(R.id.deadline);

		startWeek = Integer.parseInt(startWeekText.getText().toString());
		deadLine = Integer.parseInt(deadLineText.getText().toString());

		if (deadLine - startWeek > 24)
			endWeek = startWeek + 24;
		else
			endWeek = deadLine;

		Intent intent = new Intent(this, MyService.class);
		intent.putExtra("ACTION", "QUERYWITHOUTSTORING");
		intent.setData(uriSending.build());
		intent.putExtra("startweek", startWeek);
		intent.putExtra("endweek", endWeek);
		intent.putExtra("action", "insert");
		intent.putExtra("projectname", projectName);
		intent.putExtra("resourceid", resourceID);

		requestid = new Date().getTime();
		intent.putExtra("requestid", requestid);
		startService(intent);
	}

	/*public void sendRequest() {
		EditText projectName = (EditText) findViewById(R.id.projectName);
		CheckBox openedStatus = (CheckBox) findViewById(R.id.openedStatus);
		EditText startweek = (EditText) findViewById(R.id.startweek);
		EditText deadline = (EditText) findViewById(R.id.deadline);
		EditText nextrelease = (EditText) findViewById(R.id.nextrelease);
		Spinner status = (Spinner) findViewById(R.id.statusSpinner);

		if ((projectName.getText().toString().isEmpty())
				|| (startweek.getText().toString().isEmpty())
				|| (deadline.getText().toString().isEmpty())
				|| (nextrelease.getText().toString().isEmpty()))
			messageBoxShow("Please fill in all fields", "Error!");
		else {
			loading = ProgressDialog.show(this, "Login",
					"Please wait...", true);

			Uri.Builder uri = new Uri.Builder();

			Intent intent = new Intent(this, MyService.class);
			intent.putExtra("ACTION", "NEWPROJECT");
			intent.putExtra("projectname", projectName.getText().toString());
			intent.putExtra("openedstatus", openedStatus.isChecked());
			intent.putExtra("startweek", startweek.getText().toString());
			intent.putExtra("deadline", deadline.getText().toString());
			intent.putExtra("nextrelease", nextrelease.getText().toString());
			intent.putExtra("statusname", status.getSelectedItem().toString());
			intent.setData(uri.build());

			requestid = new Date().getTime();
			intent.putExtra("requestid", requestid);

			startService(intent);
		}
	}*/

	/** this method is called when a messagebox needs to be appered */
	public void messageBoxShow(String message, String title) {
		AlertDialog alertDialog;

		alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setButton("Retry", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				sendRequest();
			}
		});
		alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		alertDialog.show();
	}

	/*private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(tag, "BroadcastReceiver");
			if (requestid == intent.getLongExtra("originalReqeustid", 0)) {
				loading.dismiss();
				if (intent.getBooleanExtra("Successful", false)) {
					//finish();
					Intent myIntent = new Intent(getApplicationContext(),
							StripeActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("projectid", ids[position]);
					myIntent.putExtras(bundle);
					startActivity(myIntent);
				} else {
					messageBoxShow(Constants.getErrorMessage(intent
							.getIntExtra("error", 0)), "Error");
				}
			}
		}
	};*/
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(tag, "BroadcastReceiver");
			if (requestid == intent.getLongExtra("originalReqeustid", 0)) {
				loading.dismiss();

				if (intent.getBooleanExtra("Successful", false)) {
					// finish();
					int[] results = intent.getIntArrayExtra("results");
					
					CheckBox openedStatus = (CheckBox) findViewById(R.id.openedStatus);
					EditText nextrelease = (EditText) findViewById(R.id.nextrelease);
					Spinner status = (Spinner) findViewById(R.id.statusSpinner);
					
					Intent myIntent = new Intent(getApplicationContext(), StripeActivity.class);
					Bundle bundle = new Bundle();

					bundle.putString("ACTION", "newproject");
					bundle.putString("projectname", projectName);
					bundle.putInt("resourceid", resourceID);
					intent.putExtra("openedstatus", openedStatus.isChecked());
					bundle.putInt("startweek", startWeek);
					bundle.putInt("endweek", endWeek);
					bundle.putInt("deadline", deadLine);
					intent.putExtra("nextrelease", nextrelease.getText().toString());
					intent.putExtra("statusname", status.getSelectedItem().toString());
					
					bundle.putIntArray("results", results);

					myIntent.putExtras(bundle);
					startActivity(myIntent);
				} else {
					messageBoxShow(Constants.getErrorMessage(intent.getIntExtra("error", 0)), "Error");
				}
			}
		}
	};
}
