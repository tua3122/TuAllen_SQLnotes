package com.contact.tua3122.contactapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editPhone, editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_name);
        editPhone = findViewById(R.id.editText_phone);
        editAddress = findViewById(R.id.editText_address);

        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity: Instantiated DatabaseHelper");

    }

    public void addData(View view){
        Log.d("MyContactApp", "MainActivity: Add contact button pressed");
        boolean isInserted = myDb.insertData(editName.getText().toString(), editPhone.getText().toString(), editAddress.getText().toString());

        if(isInserted=true){
            Toast.makeText(MainActivity.this, "Success - contact inserted", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Failed - contact not inserted", Toast.LENGTH_LONG).show();
        }
    }

    public void viewData(View view){
        Cursor res = myDb.getAllData();
        Log.d("MyContactApp", "MainActivity: viewData: received cursor " + res.getCount());
        if(res.getCount()==0){
            showMessage("Error", "Database empty.");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            buffer.append(res.getString(0) + ": ");
            buffer.append(res.getString(1) + ", ");
            buffer.append(res.getString(2) + ", ");
            buffer.append(res.getString(3) + "\n");
        }
        Log.d("MyContactApp", "MainActivity: viewData: assembled StringBuffer()");
        showMessage("Data", buffer.toString());
    }

    public void showMessage(String title, String message) {
        Log.d("MyContactApp", "MainActivity: showMessage: Building alert dialogue");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public static final String EXTRA_MESSAGE = "com.contact.tua3122.contactapp_p1.MESSAGE";
    public void searchRecord(View view) {
        Log.d("MyContactApp", "MainActivity: Launching search activity");
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(EXTRA_MESSAGE, editName.getText().toString());
        startActivity(intent);

    }

    public void clearData(View view){
        myDb.clearData();
        showMessage("Success", "Cleared all contacts.");
    }

}
