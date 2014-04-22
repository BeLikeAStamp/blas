package com.androtailored.belikeastampuser.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.androtailored.belikeastampuser.R;

public class PersonnalizationActivity extends Activity {
	private Button suivant;
	private Button precedent;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_submission_perso);
        
        suivant = (Button) findViewById(R.id.suiv);
        precedent = (Button) findViewById(R.id.prev);
        
        suivant.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonnalizationActivity.this,SubmissionActivity.class);
				startActivity(intent);
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
}
