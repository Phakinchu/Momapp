package com.example.unemployed.momapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DayActivity extends AppCompatActivity {
    private static final String Tag = "DayActivity";
    private TextView textView;                  //date
    private Button backbtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        textView = (TextView)findViewById(R.id.date);
        backbtn = (Button)findViewById(R.id.Backbutton);

        Intent income = getIntent();
        String date = income.getStringExtra("date");
        textView.setText(date);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
