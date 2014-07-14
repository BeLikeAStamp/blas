package com.androtailored.belikeastampuser.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.androtailored.belikeastampuser.R;

public class WorkshopsActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workshops);
      
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
