package com.contact.tua3122.contactapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SearchActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = findViewById(R.id.textView_search);

        textView.setText(message);
        Log.d("MyContactApp", "SearchActivity: Set searched message");

        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "SearchActivity: Initiated DatabaseHelper");

        searchData(message);
    }


    public String searchData(String message){
        Cursor res = myDb.getAllData();
        Log.d("MyContactApp", "SearchActivity: Cursor Initiated");

        if(res.getCount()==0){
            return "No matches";
        }

        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            if(res.getString(1).indexOf(message)!=-1){
                buffer.append(res.getString(0) + ": ");
                buffer.append(res.getString(1) + ", ");
                buffer.append(res.getString(2) + ", ");
                buffer.append(res.getString(3) + "\n\n");
            }
        }

        showMessage(buffer.toString());
        Log.d("MyContactApp", "SearchActivity: Successfully compiled message");

        return buffer.toString();

    }

    public void showMessage(String message) {
        Log.d("MyContactApp", "SearchActivity: Building text dialog");
        TextView text = findViewById(R.id.results);
        text.setText(message);
    }

    public void onUpdate(View view){
        TextView textView = findViewById(R.id.textView_search);
        searchData(textView.getText().toString());
    }
}
