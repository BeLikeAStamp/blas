package com.androtailored.belikeastampuser.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androtailored.belikeastampuser.R;
import com.androtailored.belikeastampuser.util.ProjectData;

public class SubmissionActivity extends Activity {
	private Button envoyer;
	private Button precedent;
	private TextView cardType;
	private TextView cardTheme;
	private TextView howWhen;
	private ImageView color1;
	private ImageView color2;
	private ImageView color3;
	
	
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
        howWhen = (TextView) findViewById(R.id.how_when);
        
        envoyer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "ENVOYER !", Toast.LENGTH_SHORT).show();
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
        
        cardType.setText(" : "+globalVariable.getProjectType());
        cardTheme.setText(" : "+globalVariable.getProjectTheme());
        howWhen.setText(" : "+globalVariable.getNumberOfCards()+ " carte(s) pour le " +globalVariable.getOrderDate());
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
