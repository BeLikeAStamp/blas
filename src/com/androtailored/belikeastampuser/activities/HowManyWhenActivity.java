package com.androtailored.belikeastampuser.activities;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.androtailored.belikeastampuser.R;
import com.androtailored.belikeastampuser.util.DatePickerDialogFragment;
import com.androtailored.belikeastampuser.util.ProjectData;

public class HowManyWhenActivity extends Activity {

	private Button selectDate;
	private Button suivant;
	private Button precedent;
	private EditText number;
	final static int BACKINTIME = 1;
	final static int IMPOSSIBLE = 2;
	final static int NOINFOS = 3;
	final static int FAISABLE = 4;
	final static long WEEK = 604800000L;
	private long today = System.currentTimeMillis();
	private long delay ;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_submission_howmany_and_when);
		final ProjectData globalVariable = (ProjectData) getApplicationContext();

		selectDate = (Button) findViewById(R.id.select_date);  
		suivant = (Button) findViewById(R.id.suiv);  
		precedent = (Button) findViewById(R.id.prev);
		number = (EditText) findViewById(R.id.numberOfCards);

		setDate(today);
		globalVariable.setSubmitDate(getDate(today));

		selectDate.setOnClickListener(new OnClickListener() {  
			@Override  
			public void onClick(View v) {  
				DialogFragment newFragment = new DatePickerDialogFragment(callback);  
				newFragment.show(getFragmentManager(), "datePicker");
			}  
		});


		suivant.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				switch (isFaisable()) {

				case FAISABLE:
					globalVariable.setNumberOfCards(number.getText().toString());
					globalVariable.setOrderDate(selectDate.getText().toString());
					Intent intent = new Intent(HowManyWhenActivity.this,ColorPaletteActivity.class);
					startActivity(intent);
					break;
				case IMPOSSIBLE:
					Toast.makeText(getApplicationContext(), getResources().getText(R.string.mission_impossible), Toast.LENGTH_LONG).show();
					break;
				case NOINFOS:
					Toast.makeText(getApplicationContext(), getResources().getText(R.string.infos_manquantes), Toast.LENGTH_LONG).show();
					break;
				case BACKINTIME:
					Toast.makeText(getApplicationContext(), getResources().getText(R.string.back_in_time), Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			}
		});



		precedent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HowManyWhenActivity.this,CardTypeActivity.class);
				startActivity(intent);
			}
		});

	}

	protected int isFaisable() {
		int faisable = -1;
		
		Integer n = Integer.parseInt(number.getText().toString());
		if(delay == today || number.getText().toString().isEmpty() || n.equals(0)) faisable = NOINFOS;
		else if (delay < today) {
			faisable = BACKINTIME;
		}
		else if (delay - today < WEEK) {
			faisable = IMPOSSIBLE;
		}
		else faisable = FAISABLE;
		
		return faisable;
	}

	private void setDate(long millisecond){  
		int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR  
				| DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_MONTH  
				| DateUtils.FORMAT_ABBREV_WEEKDAY;  
		String dateString = DateUtils.formatDateTime(this,millisecond, flags);
		selectDate.setText(dateString);  

	}

	private String getDate(long millisecond){  
		int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR  
				| DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_MONTH  
				| DateUtils.FORMAT_ABBREV_WEEKDAY;  
		String dateString = DateUtils.formatDateTime(this,millisecond, flags);  
		return dateString;  
	}

	OnDateSetListener callback = new OnDateSetListener() {  

		@Override  
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {  
			Calendar c = Calendar.getInstance();  
			c.set(year, monthOfYear, dayOfMonth);
			delay = c.getTime().getTime();
			setDate(c.getTime().getTime());  

		}  
	};  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*
	@Override
	public void onBackPressed() {
	    Intent intent = new Intent(HowManyWhenActivity.this,CardTypeActivity.class);
		//startActivity(intent);
	}*/
}
