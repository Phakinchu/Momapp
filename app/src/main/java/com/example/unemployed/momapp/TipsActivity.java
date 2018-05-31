package com.example.unemployed.momapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class TipsActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private TextView information;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tips_activity);
        navigationView = findViewById(R.id.nav_view);
        information = (TextView) findViewById(R.id.information);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = new Intent(TipsActivity.this, InformationActivity.class);
                switch (item.getItemId()) {
                    case R.id.tip1:
                        intent.putExtra("information",Readfile("test.txt"));
                        startActivity(intent);
                        return true;
                    case R.id.tip2:
                        intent.putExtra("information",Readfile("test2.txt"));
                        startActivity(intent);
                        return true;
                    case R.id.tip3:
                        return true;
                    case R.id.tip4:
                        return true;
                }
                return false;
            }
        });

    }

    public String Readfile(String name) {
        String text = "";

        try {
            InputStream is = getAssets().open(name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(TipsActivity.this, text, Toast.LENGTH_SHORT).show();
       return text;
    }
}
