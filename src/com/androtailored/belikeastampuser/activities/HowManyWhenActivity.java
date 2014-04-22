package com.androtailored.belikeastampuser.activities;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
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


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_submission_howmany_and_when);
		final ProjectData globalVariable = (ProjectData) getApplicationContext();

		selectDate = (Button) findViewById(R.id.select_date);  
		suivant = (Button) findViewById(R.id.suiv);  
		precedent = (Button) findViewById(R.id.prev);
		number = (EditText) findViewById(R.id.numberOfCards);

		setDate(System.currentTimeMillis());
		globalVariable.setSubmitDate(getDate(System.currentTimeMillis()));

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
				globalVariable.setNumberOfCards(Integer.valueOf(number.getText().toString()));
				globalVariable.setOrderDate(selectDate.getText().toString());
				Intent intent = new Intent(HowManyWhenActivity.this,ColorPaletteActivity.class);
				startActivity(intent);
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

		/*Resources res = (Resources)getResources();
        String[] cardTypes = res.getStringArray(R.array.cardtypes);
        ListView listView = (ListView) findViewById(R.id.listMenu);

		listView.setAdapter(new ArrayAdapter<String>(this, R.layout.card_type_item, cardTypes));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HowManyWhenActivity.this,FirstConnectionActivity.class);
				startActivity(intent);
			}

		});*/
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
			setDate(c.getTime().getTime());  

		}  
	};  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
