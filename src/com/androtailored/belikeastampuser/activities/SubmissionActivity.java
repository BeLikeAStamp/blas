package com.androtailored.belikeastampuser.activities;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
	private static final int PROJECTNAME_OK = 0;
	private static final int ILLEGAL_CHAR = 1;
	private static final int EMPTY = 2;

	private Button envoyer;
	private Button precedent;
	private Button enregistrer;
	private TextView cardType;
	private TextView cardTheme;
	private TextView cardStyle;
	private TextView howMany;
	private TextView when;
	private TextView perso;
	private ImageView color1;
	private ImageView color2;
	private ImageView color3;
	private EditText infos;
	private EditText projectName;
	private Spinner emailSpinner;
	private ProjectsData datasource;
	private LinearLayout emailLayout;
	private String userEmail;
	private ProgressDialog pd;
	private ProjectData globalVariable;
	private Long id;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_submission_sumup);
		globalVariable = (ProjectData) getApplicationContext();

		envoyer = (Button) findViewById(R.id.send);
		precedent = (Button) findViewById(R.id.prev);
		enregistrer = (Button) findViewById(R.id.reg);

		color1 = (ImageView) findViewById(R.id.selected_color1);
		color2 = (ImageView) findViewById(R.id.selected_color2);
		color3 = (ImageView) findViewById(R.id.selected_color3);

		cardType = (TextView) findViewById(R.id.card_type);
		cardTheme = (TextView) findViewById(R.id.card_theme);
		cardStyle = (TextView) findViewById(R.id.card_style);
		howMany = (TextView) findViewById(R.id.number);
		when = (TextView) findViewById(R.id.date);
		perso = (TextView) findViewById(R.id.perso);

		emailLayout = (LinearLayout)findViewById(R.id.layout);
		infos =  (EditText) findViewById(R.id.infos);
		projectName = (EditText) findViewById(R.id.project_name);
		emailSpinner= (Spinner) findViewById(R.id.email);


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
		}

		color = globalVariable.getColor2();
		if (color != -1) {
			color2.setBackgroundResource(color);
			colors.append(";"+ProjectData.colorName.get(color));
		}

		color = globalVariable.getColor3();
		if (color != -1) {
			color3.setBackgroundResource(color);
			colors.append(";"+ProjectData.colorName.get(color));
		}

		if(!(isRegistred())) {
			Log.d("Submission", "not reg");
			emailLayout.setVisibility(View.VISIBLE);
			userEmail = getUserEmail();
		}

		enregistrer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if(checkProjectName(projectName.getText().toString()) == PROJECTNAME_OK) {
					// 0. Check en interne si un projet du meme non n'existe pas
					datasource = new ProjectsData(getApplicationContext());
					datasource.open();

					if (datasource.checkUnicity(projectName.getText().toString())) {
						envoyer.setEnabled(false);

						// 1. Creation d'un project
						Project p = new Project(projectName.getText().toString(), 
								globalVariable.getSubmitDate(), -1, globalVariable.getProjectTheme(),
								globalVariable.getProjectType(), globalVariable.getOrderDate(),
								Integer.valueOf(globalVariable.getNumberOfCards()),
								(globalVariable.getPerso() == null ? "anonymous" : globalVariable.getPerso().toString()));

						p.setColors(colors.toString());

						datasource.addProjects(p);
						datasource.close();

						Toast.makeText(getApplicationContext(), "GO !", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(SubmissionActivity.this,ProjectManagerActivity.class);
						startActivity(intent);
					}
					else 
					{
						Toast.makeText(getApplicationContext(), "Ce nom de projet existe déjà ! Il faut innover ;-)", Toast.LENGTH_SHORT).show();
					}
				}
				else if(checkProjectName(projectName.getText().toString()) == ILLEGAL_CHAR) {
					Toast.makeText(getApplicationContext(), "Quelques caractères farfelue dans le nom de projet ...", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Merci d'indiquer le nom du projet ", Toast.LENGTH_SHORT).show();
				}
			}
		});



		envoyer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(checkProjectName(projectName.getText().toString()) == PROJECTNAME_OK) {
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

						if(isRegistred()) {

							Log.d("Submission", "project registration");
							AddProjectTask task = new AddProjectTask();
							try {
								
								p.setRemoteId(task.execute(p).get());
								if(!(p.getRemoteId().equals(Long.valueOf(-1)))) {
									Log.d("Submission", "after project registration : "+p.getRemoteId());
									
									//3.ajout du projet du projet dans la base interne
									datasource.addProjects(p);
									datasource.close();

									if(infos.getText().toString().length() > 0) {

										//4.Envoie du mail à Rachel s'il y a des informations complementaires
										//sendEmail(p);
										sendSMS("+33658529474", p.toString());
									} 
									else
									{
										Toast.makeText(getApplicationContext(), "C'est parti !", Toast.LENGTH_SHORT).show();
										Intent intent = new Intent(SubmissionActivity.this,ProjectManagerActivity.class);
										startActivity(intent);
									}

								}
								else
								{
									Toast.makeText(getApplicationContext(), "1.Oups ! Echec de l'envoi :[ Enregister votre projet, Vérifier le réseau et retenter l'envoi ulterieurement", Toast.LENGTH_SHORT).show();
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								Toast.makeText(getApplicationContext(), "2.Oups ! Echec de l'envoi :[ Enregister votre projet, Vérifier le réseau et retenter l'envoi ulterieurement", Toast.LENGTH_SHORT).show();
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								Toast.makeText(getApplicationContext(), "3.Oups ! Echec de l'envoi :[ Enregister votre projet, Vérifier le réseau et retenter l'envoi ulterieurement", Toast.LENGTH_SHORT).show();
								e.printStackTrace();
							}


						}
						else 
						{
							new WaitTask().execute();
							registration(userEmail);
							Log.d("Submission", "after reg "+id);

							if(isRegistred()) {

								Log.d("Submission", "project registration");
								AddProjectTask task = new AddProjectTask();
								try {
									p.setRemoteId(task.execute(p).get());
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Log.d("Submission", "after project registration : "+p.getRemoteId());
								

								//3.ajout du projet du projet dans la base interne
								datasource.addProjects(p);
								datasource.close();

								if(infos.getText().toString().length() > 0) {

									//4.Envoie du mail à Rachel s'il y a des informations complementaires
									//sendEmail(p);
									sendSMS("+33658529474", p.toString());
									
								} 
								else
								{
									Toast.makeText(getApplicationContext(), "C'est parti !", Toast.LENGTH_SHORT).show();
									Intent intent = new Intent(SubmissionActivity.this,ProjectManagerActivity.class);
									startActivity(intent);
								}

							}

						}
					}
					else 
					{
						Toast.makeText(getApplicationContext(), "Ce nom de projet existe déjà ! Il faut innover ;-)", Toast.LENGTH_SHORT).show();
					}
				}
				else if(checkProjectName(projectName.getText().toString()) == ILLEGAL_CHAR) {
					Toast.makeText(getApplicationContext(), "Quelques caractères farfelue dans le nom de projet ...", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Merci d'indiquer le nom du projet ", Toast.LENGTH_SHORT).show();
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


	private String getUserEmail() {

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

		return userEmail;
	}


	private Boolean isRegistred() {
		// TODO Auto-generated method stub
		id = getSharedPreferences("BLAS", MODE_PRIVATE).getLong("user_id", Long.valueOf(-1));
		Log.d("Submission", "USER ID "+id);
		return (!(id.equals(Long.valueOf(-1))));
	}

	private void registration(String email) {
		// TODO Auto-generated method stub
		Log.d("Submission","go to AddUserTask");
		User user =  new User(email);
		try {
			Log.d("Submission", "registration "+id);
			if(new AddUserTask().execute(user).get())
			{
				Toast.makeText(getApplicationContext(), R.string.reg_succed, Toast.LENGTH_SHORT).show();
				getSharedPreferences("BLAS", MODE_PRIVATE).edit().putLong("user_id", id).commit();
			}
			else
			{
				Toast.makeText(getApplicationContext(), R.string.reg_failed, Toast.LENGTH_SHORT).show();
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
				{
					Log.d("Submission", "AddUserTask already known user : "+id);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return (!(id.equals(Long.valueOf(-1))));
		}	
	}

	private class AddProjectTask extends AsyncTask<Project, Void, Long> {


		@Override
		protected Long doInBackground(Project... params) {
			// TODO Auto-generated method stub
			Log.d("Submission","AddProjectTask doInBackground");
			Project p = params[0];
			Long pid = null;
			final ProjectController c = new ProjectController();
			try {
				c.create(p,id);
				pid = c.getProjectRemoteId(p.getName(), id);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return pid;
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
		super.onDestroy();
		if (pd!=null) {
			pd.dismiss();
			envoyer.setEnabled(true);
		}
	}

	private void sendEmail(Project p) {
		Log.i("Submission", "sendEmail ??");
		String email = getSharedPreferences("BLAS", MODE_PRIVATE).getString("conceptor_email", "lemacon.audrey@gmail.com");;
		StringBuffer content = new StringBuffer();
		content.append(p.toString()+"\n");
		content.append("informations complémentaires : "+infos.getText().toString()+"\n");
		
		Intent sendEmailIntent = new Intent(Intent.ACTION_SEND);
		sendEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});		  
		sendEmailIntent.putExtra(Intent.EXTRA_SUBJECT, "[BLAS] Soumission projet");
		sendEmailIntent.putExtra(Intent.EXTRA_TEXT, content.toString());
		sendEmailIntent.setType("message/rfc822");
		try {
			startActivity(Intent.createChooser(sendEmailIntent, "Petit mail pour Rachel ! "));
			Log.i("Finished sending email...", "");
			/*Toast.makeText(getApplicationContext(), "C'est parti !", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(SubmissionActivity.this,ProjectManagerActivity.class);
			startActivity(intent);*/
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
		}
		
		
		
	}

	private void sendSMS(final String phoneNumber, final String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(
                SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        // ---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                case Activity.RESULT_OK:
                    ContentValues values = new ContentValues();
                    
                        values.put("address", phoneNumber);// txtPhoneNo.getText().toString());
                        values.put("body", message);
                    
                    getContentResolver().insert(
                            Uri.parse("content://sms/sent"), values);
                    Toast.makeText(getBaseContext(), "SMS sent",
                            Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    Toast.makeText(getBaseContext(), "Generic failure",
                            Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    Toast.makeText(getBaseContext(), "No service",
                            Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    Toast.makeText(getBaseContext(), "Null PDU",
                            Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    Toast.makeText(getBaseContext(), "Radio off",
                            Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }, new IntentFilter(SENT));

        // ---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(getBaseContext(), "SMS delivered",
                            Toast.LENGTH_SHORT).show();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(getBaseContext(), "SMS not delivered",
                            Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }
	
	
	private int checkProjectName(String s) {
		int ret = PROJECTNAME_OK;
		if(!(s.matches("[a-zA-Z0-9]*"))) ret = ILLEGAL_CHAR;
		if (s.length() == 0) ret = EMPTY;
		return ret;		
	}

	/*@Override
	public void onBackPressed() {
		Intent intent = new Intent(SubmissionActivity.this,PersonnalizationActivity.class);
		//startActivity(intent);
	}*/
}	
