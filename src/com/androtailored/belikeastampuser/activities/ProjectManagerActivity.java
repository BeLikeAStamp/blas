package com.androtailored.belikeastampuser.activities;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.androtailored.belikeastampuser.R;
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
		
		TableLayout submitProjects = (TableLayout) findViewById(R.id.submitProjectList);
		TableLayout waitingProjects = (TableLayout) findViewById(R.id.waitingProjectList);
		
		String[] status = getResources().getStringArray(R.array.status_arrays);
		
		datasource = new ProjectsData(getApplicationContext());
		datasource.open();

		List<Project> myProjects = datasource.getAllSubmitProjects();

		for (final Project projet : myProjects) {
			// création d'une nouvelle TableRow
			TableRow row = new TableRow(getApplicationContext());
			TextView tName = new TextView(getApplicationContext());
			tName.setPadding(10, 10, 10, 10);
			tName.setBackgroundColor(Color.WHITE);
			tName.setTextColor(Color.DKGRAY);
			tName.setTextSize(16);
			tName.setGravity(Gravity.LEFT);
			tName.setText(projet.getName()+" ("+status[projet.getStatus()]+") "+projet.getRemoteId());
			row.addView(tName);

			row.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(ProjectManagerActivity.this,SubmitProjectDetails.class);
					intent.putExtra("project",projet);
					startActivity(intent);
				}
			});

			submitProjects.addView(row,new TableLayout.LayoutParams());
		}
		
		List<Project> myProjects2 = datasource.getAllWaitingProjects();

		for (final Project projet : myProjects2) {
			// création d'une nouvelle TableRow
			TableRow row = new TableRow(getApplicationContext());
			TextView tName = new TextView(getApplicationContext());
			tName.setPadding(10, 10, 10, 10);
			tName.setBackgroundColor(Color.WHITE);
			tName.setTextColor(Color.DKGRAY);
			tName.setTextSize(16);
			tName.setGravity(Gravity.LEFT);
			tName.setText(projet.getName());
			row.addView(tName);

			row.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(ProjectManagerActivity.this,WaitingProjectDetails.class);
					intent.putExtra("project",projet);
					startActivity(intent);
				}
			});

			waitingProjects.addView(row,new TableLayout.LayoutParams());
		}
		

		go.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isOnline()) {
					Intent intent = new Intent(ProjectManagerActivity.this,CardTypeActivity.class);
					startActivity(intent);
				}
				else
					alertOffLine();
				
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
	
	
	public boolean isOnline() {
		ConnectivityManager cm =
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	private void alertOffLine() {
		// Toast + ouverture panneau de conf
		Log.d("MainActivity","OFF LINE");
		AlertDialog.Builder builder = new AlertDialog.Builder(ProjectManagerActivity.this);

		builder.setTitle("Info");
		builder.setMessage("This function need some network! Cross check your internet connectivity and try again");
		//alertDialog.setIcon(R.drawable.);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User clicked OK button
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
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


