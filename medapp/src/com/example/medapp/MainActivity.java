package com.example.medapp;

import java.util.ArrayList;
import java.util.Calendar;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public final static String apts = "com.example.medapp.editapts";
	static final int PICK_DATE_REQUEST = 1;
	Boolean edit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Intent i = getIntent();
		final String username = i.getStringExtra("user");
		final String userType = i.getStringExtra("type");
		

		if (userType.equalsIgnoreCase("Administrator") || userType.equalsIgnoreCase("Doctor")){
			edit = true;
		}else {
			edit = false;
		}
		
		//Get current time
		
		Calendar c = Calendar.getInstance(); 
		
		String minute = String.valueOf(c.get(Calendar.MINUTE));
		String hour = String.valueOf(c.get(Calendar.HOUR));
		String day = String.valueOf(c.get(Calendar.DATE));
		String month = String.valueOf(c.get(Calendar.MONTH));
		String year = String.valueOf(c.get(Calendar.YEAR));
		
		long currentTime = Integer.parseInt(year + month + day + hour + minute);
		
		//Get alert time and appointment title from database
		long alertTime = 201301011640l ; 
		String alertApt = "STUFF STUFF STUFF";
		
		//for creating dialog
		if (alertTime - currentTime <= 59){
			//condition: show up dialog for appointments coming up in 59 minutes
			
						final Dialog dialog = new Dialog(this);
						dialog.setContentView(R.layout.popup);
						dialog.setTitle("Appointment Alert");
			 
						TextView text1 = (TextView) dialog.findViewById(R.id.text1);
						text1.setText(String.valueOf(alertTime));
						
						TextView text2 = (TextView) dialog.findViewById(R.id.text2);
						text2.setText(alertApt);
						
						Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
						// if button is clicked, close the custom dialog
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
						});
			 
						dialog.show();
		}
	}
	

	
	public void open(View view) {
        // Do something in response to button
		DatePicker dp = (DatePicker)findViewById(R.id.datePicker1);		
		int date = Integer.parseInt(String.valueOf(dp.getYear()) + String.valueOf(dp.getMonth()) + String.valueOf(dp.getDayOfMonth()));
		
		//load DATABASE
		
		//start showing the list
		final ListView listview = (ListView) findViewById(R.id.listView1);
	    String[] values = new String[] { "apt1", "apt2", "apt3",
	        "apt4", "apt5", "apt6", "apt7", "apt8" };
	    final ArrayList<String> list = new ArrayList<String>();
	    for (int i = 0; i < values.length; ++i) {
	      list.add(values[i]);
	    }
	    final StableArrayAdapter adapter = new StableArrayAdapter(this,
	            android.R.layout.simple_list_item_1, list);
	    
	    if (edit = true){
	    listview.setAdapter(adapter);

	    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	      @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
		@Override
	      public void onItemClick(AdapterView<?> parent, final View view,
	          int position, long id) {
	        final String item = (String) parent.getItemAtPosition(position);
	        view.animate().setDuration(0).alpha(0)
	            .withEndAction(new Runnable() {
	              @Override
	              public void run() {
	            	Intent intent = new Intent(view.getContext(),editapts.class);
		            intent.putExtra(apts,item);
	                adapter.notifyDataSetChanged();
	                view.setAlpha(1);
	                startActivity(intent);
	              }
	            });
	      }

	    });
	    }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	

}
