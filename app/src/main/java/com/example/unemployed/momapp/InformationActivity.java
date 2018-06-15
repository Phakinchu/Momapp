package com.example.unemployed.momapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity{
    TextView information_tip;
    TextView header_tip;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_layout);
        information_tip = findViewById(R.id.information);
        header_tip = findViewById(R.id.header_tip);
        Intent income = getIntent();
        String text = income.getStringExtra("information");
        String headertext = income.getStringExtra("header");
        information_tip.setText(text);
        header_tip.setText(headertext);
    }
}
