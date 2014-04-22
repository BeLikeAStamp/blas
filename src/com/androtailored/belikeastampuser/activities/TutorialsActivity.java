package com.androtailored.belikeastampuser.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androtailored.belikeastampuser.R;
import com.androtailored.belikeastampuser.db.dao.TutorialsData;
import com.androtailored.belikeastampuser.db.model.Tutorials;

public class TutorialsActivity extends Activity {
	
	private TutorialsData datasource;
	private ListView tutolist;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorials);
        tutolist = (ListView) findViewById(R.id.tutolist);
        
        datasource = new TutorialsData(this);
        datasource.open();

        List<Tutorials> values = datasource.getAllTutorials();

        ArrayAdapter<Tutorials> adapter = new ArrayAdapter<Tutorials>(this,
            android.R.layout.simple_list_item_1, values);
        tutolist.setAdapter(adapter);
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
