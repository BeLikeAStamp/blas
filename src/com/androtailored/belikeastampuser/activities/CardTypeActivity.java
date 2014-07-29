package com.androtailored.belikeastampuser.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.androtailored.belikeastampuser.R;
import com.androtailored.belikeastampuser.util.ProjectData;

public class CardTypeActivity extends Activity {
	private Button precedent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_submission_card_type);
		precedent = (Button) findViewById(R.id.prev);

		final ProjectData globalVariable = (ProjectData) getApplicationContext();

		Resources res = (Resources)getResources();
		final String[] cardTypes = res.getStringArray(R.array.cardtypes);
		ListView listView = (ListView) findViewById(R.id.listMenu);

		listView.setAdapter(new ArrayAdapter<String>(this, R.layout.card_type_item, cardTypes));
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				globalVariable.setProjectType(cardTypes[arg2]);
				Intent intent = new Intent(CardTypeActivity.this,HowManyWhenActivity.class);
				startActivity(intent);
			}

		});

		precedent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CardTypeActivity.this,ProjectManagerActivity.class);
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

	/*
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(CardTypeActivity.this,ProjectManagerActivity.class);
		//startActivity(intent);
	}
	*/
}
