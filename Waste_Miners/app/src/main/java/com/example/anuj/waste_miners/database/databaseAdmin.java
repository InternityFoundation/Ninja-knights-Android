package com.example.anuj.waste_miners.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseAdmin extends SQLiteOpenHelper {

    // Database Version
    private static final int databaseVersionAdmin = 1;

    // Database Name
    private static final String databaseNameAdmin = "Login.db";

    // User table name
    private static final String tableAdmin = "userLogin";

    // User Table Columns names
    private static final String columnIdAdmin = "user_id";
    private static final String columnPhoneAdmin = "user_name";
    private static final String columnPasswordAdmin = "user_password";

    // create table sql query
    private String createTableAdmin = "CREATE TABLE " + tableAdmin + "("
            + columnIdAdmin + " INTEGER PRIMARY KEY AUTOINCREMENT," + columnPhoneAdmin + " TEXT,"
            + columnPasswordAdmin + " TEXT" + ")";

    // drop table sql query
    private String dropTableAdmin = "DROP TABLE IF EXISTS " + tableAdmin;

    public databaseAdmin(Context context) {
        super(context, databaseNameAdmin, null, databaseVersionAdmin);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createTableAdmin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(dropTableAdmin);
        onCreate(db);
    }



    //add a new user in the database
    public boolean addUser(adminDetails details) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(columnPhoneAdmin, details.getNumber());
        values.put(columnPasswordAdmin, details.getPassword());

        // Inserting Row
        long result = db.insert(tableAdmin, null, values);

        if(result > 0)
        {
            db.close();
            return true;
        }

        else
        {
            db.close();
            return false;
        }
    }


    //checks whether a user exists or not
    public boolean checkUser(String username, String password) {

        // array of columns to fetch
        String[] columns = {
                columnIdAdmin
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = columnPhoneAdmin + " = ?" + " AND " + columnPasswordAdmin + " = ?";

        // selection arguments
        String[] selectionArgs = {username, password};

        // query user table with conditions
        Cursor cursor = db.query(tableAdmin, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    //check user for updation if it exists or not
    public boolean checkUserToupdate(String username) {

        // array of columns to fetch
        String[] columns = {
                columnIdAdmin
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = columnPhoneAdmin + " = ?";

        // selection argument
        String[] selectionArgs = {username};

        // query user table with condition
        Cursor cursor = db.query(tableAdmin, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    //update user
    public void updateUser(adminDetails details) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(columnPasswordAdmin, details.getNumber());
        values.put(columnPasswordAdmin, details.getPassword());

        // updating row
        db.update(tableAdmin, values, columnIdAdmin + " = ?",
                new String[]{String.valueOf(details.getId())});
        db.close();
    }

}
