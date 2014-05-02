package com.androtailored.belikeastampuser.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.androtailored.belikeastampuser.R;
import com.androtailored.belikeastampuser.util.PersoSubject;
import com.androtailored.belikeastampuser.util.ProjectData;

public class PersonnalizationActivity extends Activity {
	private Button suivant;
	private Button precedent;
	private Spinner spinner;
	private EditText otherStyle;
	private EditText firstname;
	private EditText age;
	private RadioGroup gender;
	
	private PersoSubject perso = new PersoSubject();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_submission_perso);
		
		final ProjectData globalVariable = (ProjectData) getApplicationContext();

		firstname = (EditText) findViewById(R.id.firstname);
		age = (EditText) findViewById(R.id.age);
		gender = (RadioGroup) findViewById(R.id.sexechoice);
		
		otherStyle = (EditText) findViewById(R.id.otherStyle);
		suivant = (Button) findViewById(R.id.suiv);
		precedent = (Button) findViewById(R.id.prev);
		spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(arg2 != arg0.getCount() - 1) {
					globalVariable.setProjectStyle(arg0.getItemAtPosition(arg2).toString());
				}
				else
				{
					otherStyle.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		otherStyle.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				globalVariable.setProjectStyle(s.toString());
			}
		});
		
		
		firstname.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				perso.setName(s.toString());
			}
		});
		
		
		age.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				perso.setAge(s.toString());
			}
		});

		
		perso.setSexe(gender.getCheckedRadioButtonId() == 0 ? "M" : "F");
		
		suivant.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				globalVariable.setPerso(perso);
				Intent intent = new Intent(PersonnalizationActivity.this,SubmissionActivity.class);
				startActivity(intent);
			}
		});

		precedent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonnalizationActivity.this,CardThemeActivity.class);
				startActivity(intent);
			}
		});


	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
