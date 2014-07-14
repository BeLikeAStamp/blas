package com.androtailored.belikeastampuser.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androtailored.belikeastampuser.R;
import com.androtailored.belikeastampuser.db.dao.ProjectsData;
import com.androtailored.belikeastampuser.db.model.Project;
import com.androtailored.belikeastampuser.util.ProjectData;

public class SubmissionActivity extends Activity {
	private Button envoyer;
	private Button precedent;
	private TextView cardType;
	private TextView cardTheme;
	private TextView cardStyle;
	private TextView howMany;
	private TextView when;
	private TextView perso;
	private ImageView color1;
	private ImageView color2;
	private ImageView color3;
	private EditText projectName;
	private ProjectsData datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_submission_sumup);
		final ProjectData globalVariable = (ProjectData) getApplicationContext();

		envoyer = (Button) findViewById(R.id.send);
		precedent = (Button) findViewById(R.id.prev);

		color1 = (ImageView) findViewById(R.id.selected_color1);
		color2 = (ImageView) findViewById(R.id.selected_color2);
		color3 = (ImageView) findViewById(R.id.selected_color3);

		cardType = (TextView) findViewById(R.id.card_type);
		cardTheme = (TextView) findViewById(R.id.card_theme);
		cardStyle = (TextView) findViewById(R.id.card_style);
		howMany = (TextView) findViewById(R.id.number);
		when = (TextView) findViewById(R.id.date);
		perso = (TextView) findViewById(R.id.perso);

		projectName = (EditText) findViewById(R.id.project_name);

		cardType.setText(" : "+globalVariable.getProjectType());
		cardTheme.setText(" : "+globalVariable.getProjectTheme());
		cardStyle.setText(" : "+globalVariable.getProjectStyle());
		howMany.setText(" : "+globalVariable.getNumberOfCards());
		when.setText(" "+globalVariable.getOrderDate());
		perso.setText(" : "+(globalVariable.getPerso() == null ? "anonymous" : globalVariable.getPerso() ));

		int color = globalVariable.getColor1();
		if (color != -1)
			color1.setBackgroundResource(color);
		color = globalVariable.getColor2();
		if (color != -1)
			color2.setBackgroundResource(color);
		color = globalVariable.getColor3();
		if (color != -1)
			color3.setBackgroundResource(color);
		
		envoyer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(projectName.getText().toString().length() == 0)
					Toast.makeText(getApplicationContext(), "Merci d'indiquer le nom du projet ", Toast.LENGTH_SHORT).show();
				else {

					/*
					 * Comment faire ça en transactionnel ?????
					 */

					// 1.Ajout du projet sur serveur distant



					//2.Envoie du mail à Rachel
					String email = "lemacon.audrey@gmail.com";
					StringBuffer content = new StringBuffer();
					content.append("project name : "+projectName.getText().toString());
					content.append("card type : "+globalVariable.getProjectType());
					content.append("card theme : "+globalVariable.getProjectTheme());
					content.append("card style : "+globalVariable.getProjectStyle());
					content.append("how many cards : "+globalVariable.getNumberOfCards());
					content.append("nedded for : "+globalVariable.getOrderDate());
					content.append("personnalisation : "+(globalVariable.getPerso() == null ? "anonymous" : globalVariable.getPerso() ));
					
					
					Intent sendEmailIntent = new Intent(Intent.ACTION_SEND);
					sendEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});		  
					sendEmailIntent.putExtra(Intent.EXTRA_SUBJECT, "[BLAS] Soumission projet");
					sendEmailIntent.putExtra(Intent.EXTRA_TEXT, content.toString());
					sendEmailIntent.setType("message/rfc822");
					try {
						startActivity(Intent.createChooser(sendEmailIntent, "Choose an Email client :"));
						
						Log.i("Finished sending email...", "");
					} catch (android.content.ActivityNotFoundException ex) {
						Toast.makeText(getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
					}



					//3.ajout du projet du projet dans la base interne
					datasource = new ProjectsData(getApplicationContext());
					datasource.open();

					datasource.addProjects(new Project(projectName.getText().toString(), 
							globalVariable.getOrderDate(),0, 
							globalVariable.getProjectTheme(),
							globalVariable.getProjectType(),
							globalVariable.getSubmitDate(),
							Integer.valueOf(globalVariable.getNumberOfCards())));

					datasource.close();


					Toast.makeText(getApplicationContext(), "GO !", Toast.LENGTH_SHORT).show();
					//Intent intent = new Intent(SubmissionActivity.this,ProjectManagerActivity.class);
					//startActivity(intent);
				}

			}
		});

		precedent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SubmissionActivity.this,PersonnalizationActivity.class);
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

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(SubmissionActivity.this,PersonnalizationActivity.class);
		//startActivity(intent);
	}
}
