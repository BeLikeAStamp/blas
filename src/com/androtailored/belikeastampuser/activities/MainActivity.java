package com.androtailored.belikeastampuser.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androtailored.belikeastampuser.R;
import com.androtailored.belikeastampuser.db.model.User;
import com.androtailored.belikeastampuser.util.UserController;

public class MainActivity extends Activity {

	private Button project;
	private Button tuto;
	private Button workshop;
	private Button join;
	private Button blog;
	private ImageView avatar;
	//private Button scrap;
	private String userEmail = "";
	private Long id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		id = getSharedPreferences("BLAS_USER", MODE_PRIVATE).getLong("user_id", Long.valueOf(-1));
		Log.i("MainActivity","user id = "+id);
		
		setContentView(R.layout.activity_main);		
		DisplayMetrics displaymetrics = new DisplayMetrics();

		final String scrapDef = "Bonjour à tous et à toutes !\nJe m'appelle Rachel et " +
				"je suis ravie de vous accueillir sur ma nouvelle application." +
				"Si vous désirez me proposer des projets de réalisations personalisées, " +
				"participer à un atelier, partager votre passion, " +
				"je vous répondrai avec la joie et la bonne humeur " +
				"qui me caractérise!\nA très bientot :)";

		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int banniere_height = dpToPx(150);
		int height_factor = (height - banniere_height)/5;

		project = (Button) findViewById(R.id.project);
		tuto = (Button) findViewById(R.id.tuto);
		workshop = (Button) findViewById(R.id.workshop);
		join = (Button) findViewById(R.id.team);
		blog = (Button) findViewById(R.id.blog);
		avatar = (ImageView) findViewById(R.id.avatar);

		ViewGroup.LayoutParams params = project.getLayoutParams();
		params.height = height_factor;		
		params.width = height_factor;
		project.setLayoutParams(params);

		params = tuto.getLayoutParams();
		params.height = height_factor*3/5;		
		params.width = height_factor*3/5;
		tuto.setLayoutParams(params);

		params = workshop.getLayoutParams();
		params.height = height_factor;		
		params.width = height_factor;
		workshop.setLayoutParams(params);

		avatar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),scrapDef, Toast.LENGTH_LONG).show();
			}
		});
		
	/*	params = scrap.getLayoutParams();
		params.height = height_factor*3;		
		params.width = height_factor*3;
		scrap.setLayoutParams(params);
		scrap.setText(scrapDef);*/

		params = blog.getLayoutParams();
		params.height = height_factor*4/5;		
		params.width = height_factor*4/5;
		blog.setLayoutParams(params);

		params = join.getLayoutParams();
		params.height = height_factor*3/4;		
		params.width = height_factor*3/4;
		join.setLayoutParams(params);

		project.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,ProjectManagerActivity.class);
				startActivity(intent);
			}
		});

		blog.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = getResources().getString(R.string.belikeastamp);

				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(i);
			}
		});

		tuto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,TutorialsActivity.class);
				startActivity(intent);
			}
		});


		workshop.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,WorkshopsActivity.class);
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

	public int dpToPx(int dp) {
		DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
		int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
		return px;
	}

	public int pxToDp(int px) {
		DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
		int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return dp;
	}

}
