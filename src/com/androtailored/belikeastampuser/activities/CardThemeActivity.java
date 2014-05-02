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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.androtailored.belikeastampuser.R;
import com.androtailored.belikeastampuser.util.ProjectData;

public class CardThemeActivity extends Activity {
	private Button suivant;
	private Button precedent;
	private EditText otherTheme;
	
	private HashMap<String, String[]> themes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_submission_card_theme);
		setThemeList();

		suivant = (Button) findViewById(R.id.suiv);
		precedent = (Button) findViewById(R.id.prev);
		otherTheme = (EditText) findViewById(R.id.otherTheme);
		

		final ProjectData globalVariable = (ProjectData) getApplicationContext();

		final String[] cardThemes = themes.get(globalVariable.getProjectType());
		ListView listView = (ListView) findViewById(R.id.listMenu);

		listView.setAdapter(new ArrayAdapter<String>(this, R.layout.card_type_item, cardThemes));
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				if (arg2 != cardThemes.length - 1) {
					globalVariable.setProjectTheme(cardThemes[arg2]);
					Intent intent = new Intent(CardThemeActivity.this,PersonnalizationActivity.class);
					startActivity(intent);
				}
				else
				{
					otherTheme.setVisibility(View.VISIBLE);
				}
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
				suivant.setVisibility(View.VISIBLE);
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
	}
}
