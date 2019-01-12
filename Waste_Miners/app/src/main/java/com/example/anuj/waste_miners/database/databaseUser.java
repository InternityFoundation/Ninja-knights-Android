package com.example.anuj.waste_miners.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseUser extends SQLiteOpenHelper {

    // Database Version
    private static final int databaseVersionUser = 1;

    // Database Name
    private static final String databaseNameUser = "Login.db";

    // User table name
    private static final String tableUser = "userLogin";

    // User Table Columns names
    private static final String columnIdUser = "user_id";
    private static final String columnPhoneUser = "user_name";
    private static final String columnPasswordUser = "user_password";

    // create table sql query
    private String createTableUser = "CREATE TABLE " + tableUser + "("
            + columnIdUser + " INTEGER PRIMARY KEY AUTOINCREMENT," + columnPhoneUser + " TEXT,"
            + columnPasswordUser + " TEXT" + ")";

    // drop table sql query
    private String dropTableUser = "DROP TABLE IF EXISTS " + tableUser;

    public databaseUser(Context context) {
        super(context, databaseNameUser, null, databaseVersionUser);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTableUser);
    }


    //add a new user in the database
    public boolean addUser(adminDetails details) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(columnPhoneUser, details.getNumber());
        values.put(columnPasswordUser, details.getPassword());

        // Inserting Row
        long result = db.insert(tableUser, null, values);

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
    public boolean checkUser(String userName, String password) {

        // array of columns to fetch
        String[] columns = {
                columnIdUser
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = columnPhoneUser + " = ?" + " AND " + columnPasswordUser + " = ?";

        // selection arguments
        String[] selectionArgs = {userName, password};

        // query user table with conditions
        Cursor cursor = db.query(tableUser, //Table to query
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
    public boolean checkUserToupdate(String userName) {

        // array of columns to fetch
        String[] columns = {
                columnIdUser
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = columnPhoneUser + " = ?";

        // selection argument
        String[] selectionArgs = {userName};

        // query user table with condition
        Cursor cursor = db.query(tableUser, //Table to query
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
        values.put(columnPhoneUser, details.getNumber());
        values.put(columnPasswordUser, details.getPassword());

        // updating row
        db.update(tableUser, values, columnIdUser + " = ?",
                new String[]{String.valueOf(details.getId())});
        db.close();
    }

}
