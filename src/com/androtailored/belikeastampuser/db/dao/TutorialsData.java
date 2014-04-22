package com.androtailored.belikeastampuser.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.androtailored.belikeastampuser.db.DatabaseHandler;
import com.androtailored.belikeastampuser.db.model.Tutorials;

public class TutorialsData {
	
    // Labels table name
    private static final String TABLE_TUTO = "tutorials";
  
    // Labels Table Columns names
    private static final int COLUMN_ID = 0;
    private static final int COLUMN_NAME = 1;
    private static final int COLUMN_THEME = 2;
    private static final int COLUMN_PRICE = 3;
    private static final int COLUMN_FILE = 4;
    private static final int COLUMN_PHOTO = 5;
    
	private SQLiteDatabase database;
	private DatabaseHandler dbHandler;
	
	public TutorialsData(Context context) {
		    dbHandler = new DatabaseHandler(context);
		  }

		  public void open() throws SQLException {
		    setDatabase(dbHandler.getWritableDatabase());
		  }

		  public void close() {
			  dbHandler.close();
		  }

		  public List<Tutorials> getAllTutorials(){
		        List<Tutorials> tutos = new ArrayList<Tutorials>();
		         
		        // Select All Query
		        String selectQuery = "SELECT  * FROM " + TABLE_TUTO;
		      
		        SQLiteDatabase db = this.getDatabase();
		        Cursor cursor = db.rawQuery(selectQuery, null);
		      
		        // looping through all rows and adding to list
		        if (cursor.moveToFirst()) {
		            do {
		                tutos.add(cursorToTuto(cursor));
		            } while (cursor.moveToNext());
		        }
		         
		        // closing connection
		        cursor.close();
		        db.close();
		         
		        // returning tutorials
		        return tutos;
		    }

			public SQLiteDatabase getDatabase() {
				return database;
			}

			public void setDatabase(SQLiteDatabase database) {
				this.database = database;
			}
			
			private Tutorials cursorToTuto(Cursor cursor) {
				Tutorials tuto = new Tutorials();
			    tuto.setId(cursor.getLong(COLUMN_ID));
			    tuto.setName(cursor.getString(COLUMN_NAME));
			    tuto.setTheme(cursor.getString(COLUMN_THEME));
			    tuto.setPrice(cursor.getFloat(COLUMN_PRICE));
			    tuto.setFilePath(cursor.getString(COLUMN_FILE));
			    //tuto.setPhoto(cursor.getBlob(COLUMN_PHOTO));			    
			    return tuto;
			  }
	
}
