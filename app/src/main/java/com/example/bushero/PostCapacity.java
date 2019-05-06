package com.example.bushero;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PostCapacity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    SQLiteDatabase myDatabase;
    String curr_busid;
    BusRouteAdapter adapter;
    BusRouteAdapter.BusRouteViewHolder view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_capacity);


        radioGroup = findViewById(R.id.radio_group);
        Button postBtn = findViewById(R.id.post_button);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                String buttonText = (String) radioButton.getText();
                finish();
//                adapter.onBindViewHolder();
//                TextView status = (TextView) findViewById(R.id.capacity);
//                Log.i("status",status.toString());
//                status.setText(buttonText);
                //myDatabase.execSQL("UPDATE Bus SET occupation = ? WHERE bus_id = ?", new String[]{buttonText, curr_busid});
            }
        });

    }

}