package com.contact.tua3122.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Target;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contacts.db";
    public static final String TABLE_NAME = "Contacts_table";
    public static final String ID = "ID";
    public static final String COLUMN_NAME_CONTACT = "contact";
    public static final String COLUMN_NAME_PHONE = "number";
    public static final String COLUMN_NAME_ADDRESS = "address";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME_CONTACT + " TEXT, " +
                    COLUMN_NAME_PHONE + " TEXT, " + COLUMN_NAME_ADDRESS + " TEXT)";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("MyContactApp", "DatabaseHelper: Constructed DatabaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MyContactApp", "DatabaseHelper: Creating database");
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("MyContactApp", "DatabaseHelper: Upgrading database");
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }

    public boolean insertData(String name, String phone, String address){
        Log.d("MyContactApp", "DatabaseHelper: Inserting data");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValue = new ContentValues();
        contentValue.put(COLUMN_NAME_CONTACT, name);
        contentValue.put(COLUMN_NAME_PHONE, phone);
        contentValue.put(COLUMN_NAME_ADDRESS, address);

        Long result = db.insert(TABLE_NAME, null, contentValue);
        if(result == -1){
            Log.d("MyContactApp", "DatabaseHelper: Contact insert failed");
            return false;
        }
        else{
            Log.d("MyContactApp", "DatabaseHelper: Contact insert passed");
            return true;
        }

    }

    public Cursor getAllData(){
        Log.d("MyContactApp", "DatabaseHelper: calling getAllData()");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        Log.d("MyContactApp", "DatabaseHelper: res initiated");
        return res;
    }
    
}
