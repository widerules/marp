package edu.ubb.marp.ui;

import java.util.ArrayList;
import java.util.Date;

import edu.ubb.marp.Constants;
import edu.ubb.marp.R;
import edu.ubb.marp.database.DatabaseContract;
import edu.ubb.marp.database.DatabaseContract.*;
import edu.ubb.marp.network.MyService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MyAccountActivity extends Activity {
	private static final String tag = "MyAccountActivity";
	
	private Intent sentIntent;
	private long requestid;
	private ProgressDialog loading;
	ArrayList<ListRecord> users = new ArrayList<ListRecord>();
    
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ChangePassword c= new ChangePassword(this);
		setContentView(R.layout.myaccount);
        //setContentView(c.returnView());
		//setArrayList("Vizer Arnold", "Arni00", "0742764458", "vizer_arnold@yahoo.com");
		
		sendRequest();
	}
	
	private void sendRequest(){
		loading=ProgressDialog.show(this, "Loading", "Please wait...");
		
        Uri.Builder uri = new Uri.Builder();
		uri.authority(DatabaseContract.PROVIDER_NAME);
		uri.path(Integer.toString(Constants.QUERYUSER));
		//uri.appendPath("Projects");
		uri.scheme("content");

		Intent intent = new Intent(this,
				MyService.class);
		intent.putExtra("ACTION", "QUERY");
		intent.putExtra("self", true);
		intent.setData(uri.build());
		//sentIntent=intent;
		requestid=new Date().getTime();
		intent.putExtra("requestid", requestid);
		startService(intent);
	}
	  
	  @Override
	  protected void onStart(){
		  super.onStart();
		  
		  registerReceiver(broadcastReceiver, new IntentFilter(
					Constants.BROADCAST_ACTION));
	  }
	  
	  @Override
	  protected void onStop(){
		  super.onStop();
		  unregisterReceiver(broadcastReceiver);
	  }
	
	public void setArrayList(String name, String username, String telephone, String email){
		ListRecord user = new ListRecord("Name", name);
        users.add(user);
        user = new ListRecord("Username", username);
        users.add(user);
        user = new ListRecord("Telephone", telephone);
        users.add(user);
        user = new ListRecord("E-mail", email);
        users.add(user);
        user = new ListRecord("Change Password","");
        users.add(user);
	}
	
	public void messageBoxShow(String message, String title){
    	AlertDialog alertDialog;
    	
      	alertDialog = new AlertDialog.Builder(this).create();
    	alertDialog.setTitle(title);
    	alertDialog.setMessage(message);
    	alertDialog.setButton("Retry",
    		    new DialogInterface.OnClickListener() {
    		        public void onClick(DialogInterface dialog, int which) {
    					sendRequest();
    		        }
    		    });
    	alertDialog.setButton2("Cancel",
    		    new DialogInterface.OnClickListener() {
    		        public void onClick(DialogInterface dialog, int which) {
    		        }
    		    });
    	alertDialog.show();
    }
	
	public void editDialog(String title,String editableText, int position){
		
		AlertDialog alertDialog;
		EditText editDialog = new EditText(this);
      	
		editDialog.setText(editableText);
		alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(title);
		alertDialog.setView(editDialog);
		final int myPosition=position;
		final String newText = editDialog.getText().toString();
		alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch(myPosition){
				case 0: //Name
					break;
				case 1: //Username
					break;
				case 2: //Telephone
					/*Intent intent = new Intent(this, MyService.class);
					intent.putExtra("ACTION", "QUERYWITHOUTSTORING");
					intent.putExtra("password", newText);
					intent.setData(uri.build());
					// sentIntent =intent;
					requestid = new Date().getTime();
					intent.putExtra("requestid", requestid);

					startService(intent);*/
					break;
				case 3: //E-mail
					break;
				}
			}
		});
		alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		alertDialog.show();
	}
	
	public void editPasswordDialog(String title){
		
		AlertDialog alertDialog;
		ChangePassword change = new ChangePassword(this);
		alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(title);
		alertDialog.setView(change.returnView());
		alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				/*implement the OK button*/
				
			}
		});
		alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		alertDialog.show();
	}
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(requestid == intent.getLongExtra("originalReqeustid", 0)){
				if (intent.getBooleanExtra("Successful", false)) {
					loading.dismiss();
				Uri.Builder uri = new Uri.Builder();
				uri = new Uri.Builder();
				uri.authority(DatabaseContract.PROVIDER_NAME);
				uri.path(DatabaseContract.TABLE_USERS);
				uri.scheme("content");

				ContentResolver cr = getContentResolver();
				
				Log.i(tag, "query elott");
				Cursor c = cr.query(uri.build(), null, TABLE_USERS.USERNAME+"='"+PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("username", "")+"'", null, null);
				Log.i(tag, "query utan");
				
				if(c.moveToFirst()){
					Log.i(tag, "ifben");
					//setArrayList("Vizer Arnold", "Arni00", "0742764458", "vizer_arnold@yahoo.com");
					String name=c.getString(c.getColumnIndex(TABLE_USERS.USERRESOURCENAME));
					String username=c.getString(c.getColumnIndex(TABLE_USERS.USERNAME));
					String tel=c.getString(c.getColumnIndex(TABLE_USERS.USERPHONENUMBER));
					String email=c.getString(c.getColumnIndex(TABLE_USERS.USEREMAIL));
					Log.i(tag, name+" "+username+" "+tel+" "+email);
					
					setArrayList(name, username, tel, email);

			        ListView listView = (ListView) findViewById(R.id.ListViewId);
			        listView.setAdapter(new ListItemAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, users));
			        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
			                    int pos, long id) {
			                // TODO Auto-generated method stub
			            	if(pos<4){
			            		editDialog("Change" + " " + users.get(pos).getItem() ,users.get(pos).getSubitem(),pos);
			            	}else{
			            		if(pos == 4){
			            			editPasswordDialog("Change Password");
			            		}
			            	}
			            	return true;
			            }
			        }); 
				}
				}else{
					loading.dismiss();
					messageBoxShow(Constants.getErrorMessage(intent
							.getIntExtra("error", 0)), "Error");
				}
			}
		}
	};
}