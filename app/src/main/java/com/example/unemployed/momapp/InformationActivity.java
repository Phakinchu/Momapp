package com.example.unemployed.momapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity{
    TextView information_tip;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_layout);
        information_tip = findViewById(R.id.information);
        Intent income = getIntent();
        String text = income.getStringExtra("information");
        information_tip.setText(text);
    }
}
