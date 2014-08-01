package com.androtailored.belikeastampuser.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.androtailored.belikeastampuser.R;
import com.androtailored.belikeastampuser.util.PersoSubject;
import com.androtailored.belikeastampuser.util.ProjectData;

public class PersonnalizationActivity extends Activity {
	private Button suivant;
	private Button precedent;
	private EditText firstname;
	private EditText age;
	private RadioGroup gender;
	private RadioGroup namedCard;
	private LinearLayout layout2;
	boolean anonymous = true;

	private PersoSubject perso = new PersoSubject();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_submission_perso);

		final ProjectData globalVariable = (ProjectData) getApplicationContext();

		firstname = (EditText) findViewById(R.id.firstname);
		age = (EditText) findViewById(R.id.age);
		gender = (RadioGroup) findViewById(R.id.sexechoice);
		namedCard = (RadioGroup) findViewById(R.id.nominalchoice);
		layout2 = (LinearLayout) findViewById(R.id.layout2);
		suivant = (Button) findViewById(R.id.suiv);
		precedent = (Button) findViewById(R.id.prev);


		namedCard.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == R.id.not_anonyme) {
					layout2.setVisibility(View.VISIBLE);
					anonymous = false;
				}
				else
				{
					layout2.setVisibility(View.INVISIBLE);
					anonymous = true;
					perso.setAge("");perso.setName("");perso.setSexe("");
				}
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


		gender.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == R.id.male) {
					perso.setSexe("M");
					
				}
				else
				{
					perso.setSexe("F");
				}
			}
		});

		suivant.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (!anonymous)
				{
					if (perso.getAge().isEmpty() 
							|| perso.getName().isEmpty() 
							|| perso.getSexe().isEmpty()) {
						Toast.makeText(getApplicationContext(), getResources().getText(R.string.infos_manquantes), Toast.LENGTH_LONG).show();
					}
					else
					{
						globalVariable.setPerso(perso);
						Intent intent = new Intent(PersonnalizationActivity.this,SubmissionActivity.class);
						startActivity(intent);
					}
				}
				else {

					Intent intent = new Intent(PersonnalizationActivity.this,SubmissionActivity.class);
					startActivity(intent);
				}
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

	/*
	@Override
	public void onBackPressed() {
	    Intent intent = new Intent(PersonnalizationActivity.this,CardThemeActivity.class);
		//startActivity(intent);
	}*/
}
