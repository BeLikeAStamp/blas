package com.androtailored.belikeastampuser.activities;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.androtailored.belikeastampuser.R;
import com.androtailored.belikeastampuser.db.DatabaseHandler;
import com.androtailored.belikeastampuser.db.dao.ProjectsData;
import com.androtailored.belikeastampuser.db.model.Project;

public class ProjectManagerActivity extends Activity {
	private Button go;
	private Button welcome;
	private ProjectsData datasource;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_management);
		go = (Button) findViewById(R.id.go);
		welcome = (Button) findViewById(R.id.welcome);
		TableLayout table = (TableLayout) findViewById(R.id.projectList);
		String[] status = getResources().getStringArray(R.array.status_arrays);
		
		datasource = new ProjectsData(getApplicationContext());
		datasource.open();

		/*Log.d("Insert: ", "Inserting .."); 
		datasource.addProjects(new Project("projet1", "data1", 1, "theme1", "type1", "data1", 3));
		*/

		List<Project> myProjects = datasource.getAllProjects();

		for (final Project projet : myProjects) {
			// création d'une nouvelle TableRow
			TableRow row = new TableRow(getApplicationContext());
			TextView tName = new TextView(getApplicationContext());
			tName.setPadding(10, 10, 10, 10);
			tName.setBackgroundColor(Color.WHITE);
			tName.setTextColor(Color.DKGRAY);
			tName.setTextSize(16);
			tName.setGravity(Gravity.LEFT);
			tName.setText(projet.getName()+" ("+status[projet.getStatus()]+")");
			row.addView(tName);
			
			Button tDetails = new Button(getApplicationContext());
			tDetails.setBackground(getResources().getDrawable(R.drawable.btn_action_green));
			tDetails.setText(getResources().getString(R.string.details));
			tDetails.setTextSize(12);
			
			tDetails.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(ProjectManagerActivity.this,ProjectDetails.class);
					intent.putExtra("project",projet);
					startActivity(intent);
				}
			});
			
			row.addView(tDetails);
			table.addView(row,new TableLayout.LayoutParams());
		}

		go.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ProjectManagerActivity.this,CardTypeActivity.class);
				startActivity(intent);
			}
		});

		welcome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ProjectManagerActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}


