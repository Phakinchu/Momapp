package com.example.unemployed.momapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import java.util.regex.Pattern;

public class ContractActicity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_activity);
        Utils.getDatabase();
//        TextView contr ;
//        contr = findViewById(R.id.information);
//        contr.setMovementMethod(LinkMovementMethod.getInstance());
//        Linkify.addLinks(contr, Linkify.PHONE_NUMBERS);
    }
}
