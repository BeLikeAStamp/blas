package com.androtailored.belikeastampuser.activities;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androtailored.belikeastampuser.R;
import com.androtailored.belikeastampuser.db.model.User;
import com.androtailored.belikeastampuser.util.UserController;

public class FirstConnectionActivity extends Activity {
	Button register;
	EditText firstname, name, phone, email;
	Long id = Long.valueOf(-1);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(isRegistred()) {
			Log.i("MainActivity","im not registered");
			Intent i = new Intent(FirstConnectionActivity.this, MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}

		setContentView(R.layout.first_connection);

		register = (Button) findViewById(R.id.reg);
		firstname = (EditText) findViewById(R.id.firstname);
		name = (EditText) findViewById(R.id.name);
		phone = (EditText) findViewById(R.id.tel);
		email = (EditText) findViewById(R.id.email);

		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (firstname.getText().toString().length() != 0
						&& name.getText().toString().length() != 0
						&& phone.getText().toString().length() != 0
						&& email.getText().toString().length() != 0
						) {

					User user =  new User(firstname.getText().toString(), 
							name.getText().toString(), phone.getText().toString(), 
							email.getText().toString());

					try {
						if(new AddUserTask().execute(user).get())
						{
							Toast.makeText(getApplicationContext(), R.string.reg_succed, Toast.LENGTH_SHORT).show();
							getSharedPreferences("REGISTERED USER", MODE_PRIVATE).edit()
							.putBoolean("registered", true).commit();
							getSharedPreferences("USER ID", MODE_PRIVATE).edit().putLong("user_id", id).commit();
							Intent i = new Intent(FirstConnectionActivity.this, MainActivity.class);
							i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(i);
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
				else
				{
					Toast.makeText(getApplicationContext(), R.string.warning_reg, Toast.LENGTH_SHORT).show();
				}


			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private Boolean isRegistred() {
		// TODO Auto-generated method stub
		Boolean isRegistered = getSharedPreferences("REGISTERED USER", MODE_PRIVATE)
				.getBoolean("registered", false);

		return isRegistered;
	}

	private class AddUserTask extends AsyncTask<User, Void, Boolean> {

		@Override
		protected Boolean doInBackground(User... params) {
			// TODO Auto-generated method stub
			User u = params[0];
			u.setAddress("");
			u.setIsHost(false);
			u.setIsPartener(false);

			final UserController c = new UserController();
			try {
				// check user id d'abord...
				id = c.getUserId(u.getEmail());
				if(id.equals(Long.valueOf(-1))) {
					Log.d("addUser", "unknown user");
					c.create(u);
					id = c.getUserId(u.getEmail());
					Log.d("addUser", "user id = "+id);
				}
				else
					Log.d("addUser", "already known user");
			} catch (Exception e) {
				e.printStackTrace();
			}

			if(id.equals(Long.valueOf(-1)))
				return false;
			else
				return true;
		}

	}

}
