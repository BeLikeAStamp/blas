package com.androtailored.belikeastampuser.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androtailored.belikeastampuser.R;
import com.androtailored.belikeastampuser.db.DatabaseHandler;
import com.androtailored.belikeastampuser.db.model.Project;

public class SubmitProjectDetails extends Activity {
	
	TextView projectName;
	TextView type;
	TextView theme;
	TextView nbrCards;
	TextView orderDate;
	TextView progress;
	TextView protolink;
	Button suivant;
	Button precedent;
	Button ok_proto;
	Button no_proto;
	Button send;
	LinearLayout valid_proto;
	LinearLayout send_msg;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submitproject_details);
		Project project = (Project) getIntent().getSerializableExtra("project");
		final String[] status = getResources().getStringArray(R.array.status_arrays);
		
		projectName = (TextView) findViewById(R.id.project_name);
		type = (TextView) findViewById(R.id.card_type);
		theme = (TextView) findViewById(R.id.card_theme);
		nbrCards = (TextView) findViewById(R.id.number);
		orderDate = (TextView) findViewById(R.id.date);
		progress = (TextView) findViewById(R.id.progression);
		protolink = (TextView) findViewById(R.id.proto_link);
		precedent = (Button) findViewById(R.id.prev);
		ok_proto = (Button) findViewById(R.id.ok_proto);
		no_proto = (Button) findViewById(R.id.no_proto);
		send =  (Button) findViewById(R.id.send);
		valid_proto = (LinearLayout) findViewById(R.id.valid_proto);
		send_msg = (LinearLayout) findViewById(R.id.send_msg);

		
		projectName.setText(project.getName());
		type.setText(project.getType());
		theme.setText(project.getTheme());
		nbrCards.setText(""+project.getQuantity());
		orderDate.setText(project.getOrderDate());
		

		if(project.getStatus() >= DatabaseHandler.PROJ_SUBMIT) { // TO DO MODIFIER !!!!!
			// RECUPERATION DE LIMAGE EN BACKGROUND
			protolink.setText("Recuperer l'image");
			valid_proto.setVisibility(View.VISIBLE);
			no_proto.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.d("SubmitProjectDetails", "NO PROTO");
					send_msg.setVisibility(View.VISIBLE);
				}		
			});
			
			ok_proto.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.d("SubmitProjectDetails", "OK PROTO");
				}		
			});
			
			send.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.d("SubmitProjectDetails", "NO PROTO -  SEND MESSAGE");
				}		
			});
			
		}
		else
		{
			protolink.setText(getResources().getString(R.string.no_prototype));
		}
		
		
		progress.setCompoundDrawablesWithIntrinsicBounds(0,0,0,getStatusImg(project.getStatus()));
		
		progress.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StringBuffer text = new StringBuffer();
				for (int i = 0 ; i < status.length ; i++ ) {
					text.append(i+1+":"+status[i]+"\n");
				}
				Toast.makeText(getApplicationContext(), text.toString(), Toast.LENGTH_LONG).show();
			}
		});
		
		precedent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SubmitProjectDetails.this,ProjectManagerActivity.class);
				startActivity(intent);
			}
		});
		
	}
	
	
	private int getStatusImg(int statusId) {
		int statut = 0;
		switch (statusId) {
		case DatabaseHandler.PROJ_SUBMIT:
			statut = R.drawable.step1;
			break;
		case DatabaseHandler.PROJ_ACCEPTED:
			statut = R.drawable.step2;
			break;
		case DatabaseHandler.PROTO_INPROGRESS:
			statut = R.drawable.step3;
			break;
		case DatabaseHandler.PROTO_PENDING:
			statut = R.drawable.step4;
			break;
		case DatabaseHandler.REAL_INPROGRESS:
			statut = R.drawable.step5;
			break;
		case DatabaseHandler.REAL_DONE:
			statut = R.drawable.step6;
			break;		
		default:
			break;
		}

		return statut;
	}
}
