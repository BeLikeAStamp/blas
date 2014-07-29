package com.androtailored.belikeastampuser.activities;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androtailored.belikeastampuser.R;
import com.androtailored.belikeastampuser.db.dao.ProjectsData;
import com.androtailored.belikeastampuser.db.model.Project;
import com.androtailored.belikeastampuser.db.model.User;
import com.androtailored.belikeastampuser.util.ProjectController;
import com.androtailored.belikeastampuser.util.ProjectData;
import com.androtailored.belikeastampuser.util.UserController;

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
	private Spinner emailSpinner;
	private ProjectsData datasource;
	private String userEmail;
	private Long id;
	private Long pid;
	private ProgressDialog pd;

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
		emailSpinner= (Spinner) findViewById(R.id.email);
		getUserEmail();

		cardType.setText(" : "+globalVariable.getProjectType());
		cardTheme.setText(" : "+globalVariable.getProjectTheme());
		cardStyle.setText(" : "+globalVariable.getProjectStyle());
		howMany.setText(" : "+globalVariable.getNumberOfCards());
		when.setText(" "+globalVariable.getOrderDate());
		perso.setText(" : "+(globalVariable.getPerso() == null ? "anonymous" : globalVariable.getPerso() ));

		final StringBuffer colors = new StringBuffer();

		int color = globalVariable.getColor1();
		if (color != -1) {
			color1.setBackgroundResource(color);
			colors.append(ProjectData.colorName.get(color));
			Log.d("Submission", "col 1 "+colors.toString());
		}
		
		color = globalVariable.getColor2();
		if (color != -1) {
			color2.setBackgroundResource(color);
			colors.append(";"+ProjectData.colorName.get(color));
			Log.d("Submission","col 2 "+ colors.toString());
		}
		
		color = globalVariable.getColor3();
		if (color != -1) {
			color3.setBackgroundResource(color);
			colors.append(";"+ProjectData.colorName.get(color));
			Log.d("Submission", "col 3 "+colors.toString());
		}
		
		
		Log.d("Submission", colors.toString());
		envoyer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(projectName.getText().toString().length() == 0)
					Toast.makeText(getApplicationContext(), "Merci d'indiquer le nom du projet ", Toast.LENGTH_SHORT).show();
				else {
					// 0. Check en interne si un projet du meme non n'existe pas
					datasource = new ProjectsData(getApplicationContext());
					datasource.open();

					if (datasource.checkUnicity(projectName.getText().toString())) {
						envoyer.setEnabled(false);
						/*
						 * Comment faire ça en transactionnel ?????
						 */

						// 1. Creation d'un project
						Project p = new Project(projectName.getText().toString(), 
								globalVariable.getSubmitDate(), 0, globalVariable.getProjectTheme(),
								globalVariable.getProjectType(), globalVariable.getOrderDate(),
								Integer.valueOf(globalVariable.getNumberOfCards()),
								(globalVariable.getPerso() == null ? "anonymous" : globalVariable.getPerso().toString()));

						p.setColors(colors.toString());
						
						// 2.Ajout du projet sur serveur distant
						// 2.1 essayer de recuperer l'id user ou l'enregistrer sur la db

						new WaitTask().execute();

						if(isRegistred()) {
							Log.d("Submission", "is reg "+id);
						}
						else
						{
							Log.d("Submission", "not reg");
							registration();
							Log.d("Submission", "after reg "+id);
						}

						if(isRegistred()) {

							Log.d("Submission", "project registration");
							pid = Long.valueOf(-1);
							AddProjectTask task = new AddProjectTask();
							task.execute(p);
							Log.d("Submission", "after project registration : "+pid);
							p.setRemoteId(pid);
							
							//3.ajout du projet du projet dans la base interne
							datasource.addProjects(p);
							datasource.close();

							Toast.makeText(getApplicationContext(), "GO !", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(SubmissionActivity.this,ProjectManagerActivity.class);
							startActivity(intent);

						}

						
						/*
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
					}*/


					}
					else 
					{
						Toast.makeText(getApplicationContext(), "Ce nom de projet existe déjà ! Il faut innover ;-)", Toast.LENGTH_SHORT).show();
					}
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


	private void getUserEmail() {

		Account[] accounts = AccountManager.get(SubmissionActivity.this).getAccountsByType("com.google");
		final List<String> items =  new ArrayList<String>();

		for (int i = 0; i < accounts.length; i++)
			items.add(accounts[i].name);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,items);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		emailSpinner.setAdapter(dataAdapter);
		emailSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				userEmail = (String)arg0.getSelectedItem(); 
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});
	}


	private Boolean isRegistred() {
		// TODO Auto-generated method stub
		id = getSharedPreferences("BLAS_USER", MODE_PRIVATE).getLong("user_id", Long.valueOf(-1));
		Log.d("Submission", "USER ID "+id);
		return (!(id.equals(Long.valueOf(-1))));
	}

	private void registration() {
		// TODO Auto-generated method stub
		Log.d("Submission","go to AddUserTask");
		User user =  new User(userEmail);
		try {
			Log.d("Submission", "registration "+id);
			if(new AddUserTask().execute(user).get())
			{
				Toast.makeText(getApplicationContext(), R.string.reg_succed, Toast.LENGTH_SHORT).show();
				getSharedPreferences("BLAS_USER", MODE_PRIVATE).edit().putLong("user_id", id).commit();
				//Intent i = new Intent(getApplicationContext(), ProjectManagerActivity.class);
				//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//startActivity(i);
			}
			else
			{
				Toast.makeText(getApplicationContext(), R.string.reg_failed, Toast.LENGTH_SHORT).show();
				//SubmissionActivity.this.finish();
			}
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private class WaitTask extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			Log.i("Submission", "WaitTask onPreExecute");
			pd = new ProgressDialog(SubmissionActivity.this);
			pd.setTitle("Processing...");
			pd.setMessage("Please wait.");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		} 

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void unused) {
			super.onPostExecute(unused);
			Log.i("Submission", "WaitTask onPostExecute");
			if (pd!=null) {
				pd.dismiss();
				envoyer.setEnabled(true);
			}
		} 

	}

	private class AddUserTask extends AsyncTask<User, Void, Boolean> {



		@Override
		protected Boolean doInBackground(User... params) {
			// TODO Auto-generated method stub
			Log.d("Submission","AddUserTask doInBackground");

			User u = params[0];
			u.setFirstname("");
			u.setName("");
			u.setPhone("");
			u.setAddress("");
			u.setIsHost(false);
			u.setIsPartener(false);

			final UserController c = new UserController();
			try {

				// check user id d'abord...
				id = c.getUserId(u.getEmail());
				if(id.equals(Long.valueOf(-1))) {
					Log.d("Submission", "AddUserTask unknown user");
					c.create(u);
					id = c.getUserId(u.getEmail());
					Log.d("Submission", "AddUserTask user id = "+id);
				}
				else
					Log.d("Submission", "AddUserTask already known user : "+id);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return (!(id.equals(Long.valueOf(-1))));
		}	
	}

	private class AddProjectTask extends AsyncTask<Project, Void, Boolean> {


		@Override
		protected Boolean doInBackground(Project... params) {
			// TODO Auto-generated method stub
			Log.d("Submission","AddProjectTask doInBackground");
			Project p = params[0];

			final ProjectController c = new ProjectController();
			try {
				c.create(p,id);
				pid = c.getProjectRemoteId(p.getName(), id);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return (!(pid.equals(Long.valueOf(-1))));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override 
	protected void onDestroy() {
		if (pd!=null) {
			pd.dismiss();
			envoyer.setEnabled(true);
			super.onDestroy();
		}
	}


	/*@Override
	public void onBackPressed() {
		Intent intent = new Intent(SubmissionActivity.this,PersonnalizationActivity.class);
		//startActivity(intent);
	}*/
}	
