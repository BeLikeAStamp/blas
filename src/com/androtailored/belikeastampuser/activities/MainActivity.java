package com.androtailored.belikeastampuser.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androtailored.belikeastampuser.R;

public class MainActivity extends Activity {

	private Button project;
	private Button tuto;
	private Button workshop;
	private Button join;
	private Button blog;
	private ImageView avatar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSharedPreferences("BLAS", MODE_PRIVATE).edit().putString("conceptor_email", "lemacon.audrey@gmail.com").commit();

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
				if(isOnline())
				{
					String url = getResources().getString(R.string.belikeastamp);
					Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(i);
				}
				else
					alertOffLine();
			}
		});

		tuto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isOnline()) {
					Intent intent = new Intent(MainActivity.this,TutorialsActivity.class);
					startActivity(intent);
				}
				else
					alertOffLine();

			}
		});


		workshop.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isOnline()) {
					Intent intent = new Intent(MainActivity.this,WorkshopsActivity.class);
					startActivity(intent);
				}
				else
					alertOffLine();
			}
		});
		
		join.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isOnline()) {
					//Intent intent = new Intent(MainActivity.this,TutorialsActivity.class);
					//startActivity(intent);
				}
				else
					alertOffLine();

			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

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
