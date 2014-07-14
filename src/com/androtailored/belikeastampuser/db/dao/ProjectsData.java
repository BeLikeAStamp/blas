package com.androtailored.belikeastampuser.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.androtailored.belikeastampuser.db.DatabaseHandler;
import com.androtailored.belikeastampuser.db.model.Project;

public class ProjectsData {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public ProjectsData(Context context) {
		dbHelper = DatabaseHandler.getInstance(context);
	}  

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}


	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	public void addProjects(Project project) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHandler.P_NAME, project.getProject_name());
		values.put(DatabaseHandler.P_SUBDATE, project.getSub_date());
		values.put(DatabaseHandler.P_STATUS, project.getProject_status());
		values.put(DatabaseHandler.P_THEME, project.getTheme());
		values.put(DatabaseHandler.P_TYPE, project.getType());
		values.put(DatabaseHandler.P_ORDERDATA, project.getOrder_date());
		values.put(DatabaseHandler.P_NBRCARDS, project.getNbr_cards());

		// Inserting Row
		database.insert(DatabaseHandler.TABLE_PROJECTS, null, values);

	}

	public Project getProjects(String name) {
		Cursor cursor = database.query(DatabaseHandler.TABLE_PROJECTS, new String[] { 
				DatabaseHandler.P_NAME, 
				DatabaseHandler.P_SUBDATE,
				DatabaseHandler.P_STATUS,
				DatabaseHandler.P_THEME,
				DatabaseHandler.P_TYPE,
				DatabaseHandler.P_ORDERDATA,
				DatabaseHandler.P_NBRCARDS,
				DatabaseHandler.P_REMOTEID,
		}, DatabaseHandler.P_NAME + "=?",
		new String[] { name }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Project project = cursorToProject(cursor);
		return project;
	}

	public List<Project> getAllProjects() {
		List<Project> projects = new ArrayList<Project>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_PROJECTS;

		Cursor cursor = database.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Project project = cursorToProject(cursor);
				projects.add(project);
			} while (cursor.moveToNext());
		}

		return projects;
	}

	public int getProjectsCount() {
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_PROJECTS;
		Cursor cursor = database.rawQuery(selectQuery, null);
		cursor.close();
		return cursor.getCount();
	}

	public int updateProjectRemoteId(Project project, int remoteId) {
		
		ContentValues values = new ContentValues();
	    values.put(DatabaseHandler.P_REMOTEID, remoteId);
		
	    return database.update(DatabaseHandler.TABLE_PROJECTS, values, DatabaseHandler.P_NAME  + " = ?",
	            new String[] { project.getProject_name() });
	}

	public void deleteProject(String name) {
		database.delete(DatabaseHandler.TABLE_PROJECTS, DatabaseHandler.P_NAME + "=?",
				new String[] {name });		
	}

	private Project cursorToProject(Cursor cursor) {
		Project project = new Project();
		project.setProject_name(cursor.getString(0));
		project.setSub_date(cursor.getString(1));
		project.setTheme(cursor.getString(2));
		project.setType(cursor.getString(3));
		project.setOrder_date(cursor.getString(4));
		project.setProject_status(cursor.getInt(5));
		project.setNbr_cards(cursor.getInt(6));
		project.setRemoteId(cursor.getInt(7));
		return project;
	}

}