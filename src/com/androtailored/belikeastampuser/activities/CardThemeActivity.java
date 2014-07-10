package com.androtailored.belikeastampuser.activities;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.androtailored.belikeastampuser.R;
import com.androtailored.belikeastampuser.util.ProjectData;

public class CardThemeActivity extends Activity {
	private Button suivant;
	private Button precedent;
	private EditText otherTheme;
	private Spinner spinnerTheme;
	private Spinner spinnerStyle;
	private EditText otherStyle;
	private HashMap<String, String[]> themes;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_submission_card_theme);
		setThemeList();

		suivant = (Button) findViewById(R.id.suiv);
		precedent = (Button) findViewById(R.id.prev);
		otherTheme = (EditText) findViewById(R.id.otherTheme);
		spinnerTheme = (Spinner) findViewById(R.id.spinner0);
		
		final ProjectData globalVariable = (ProjectData) getApplicationContext();

		final String[] cardThemes = themes.get(globalVariable.getProjectType());
		
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cardThemes);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerTheme.setAdapter(spinnerArrayAdapter);
		spinnerTheme.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(arg2 != arg0.getCount() - 1) {
					globalVariable.setProjectTheme(arg0.getItemAtPosition(arg2).toString());
					otherTheme.setVisibility(View.INVISIBLE);
				}
				else
				{
					otherTheme.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		otherTheme.addTextChangedListener(new TextWatcher() {

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
				globalVariable.setProjectTheme(s.toString());
			}
		});


		otherStyle = (EditText) findViewById(R.id.otherStyle);
		spinnerStyle = (Spinner) findViewById(R.id.spinner1);
		spinnerStyle.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(arg2 != arg0.getCount() - 1) {
					globalVariable.setProjectStyle(arg0.getItemAtPosition(arg2).toString());
					otherStyle.setVisibility(View.INVISIBLE);
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


		suivant.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CardThemeActivity.this,PersonnalizationActivity.class);
				startActivity(intent);
			}
		});

		precedent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CardThemeActivity.this,ColorPaletteActivity.class);
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


	private void setThemeList() {
		themes = new HashMap<String, String[]>();
		themes.put("Save The Date", new String[]{"std1","std2","std3","std4","Autre"});
		themes.put("Remerciements", new String[]{"rem1","rem2","rem3","rem4","rem5", "Autre"});
		themes.put("Fêtes", new String[]{"fet1","fet2","fet3","fet4","Autre"});
		themes.put("Anniversaires", new String[]{"ann1","ann2","ann3","ann4","Autre"});
		themes.put("Voeux", new String[]{"voe1","voe2","voe3","voe4","Autre"});
		themes.put("Saint Valentin", new String[]{"stv1","stv2","Autre"});
		themes.put("Félicitations", new String[]{"fel1","fel2","Autre"});
	}
	
	@Override
	public void onBackPressed() {
	    Intent intent = new Intent(CardThemeActivity.this,ColorPaletteActivity.class);
		startActivity(intent);
	}
}
