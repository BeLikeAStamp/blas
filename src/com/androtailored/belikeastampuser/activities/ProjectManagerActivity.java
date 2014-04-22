package com.androtailored.belikeastampuser.activities;

import com.androtailored.belikeastampuser.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ProjectManagerActivity extends Activity {
	private Button go;
	private Button welcome;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_management);
        go = (Button) findViewById(R.id.go);
        welcome = (Button) findViewById(R.id.welcome);
        
        Resources res = (Resources)getResources();
		String[] cardTypes = {"bla","blo","bli"};
		ListView listView = (ListView) findViewById(R.id.listMenu);

		listView.setAdapter(new ArrayAdapter<String>(this, R.layout.card_type_item, cardTypes));
		/*listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CardTypeActivity.this,HowManyWhenActivity.class);
				startActivity(intent);
			}

		});*/
        
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
