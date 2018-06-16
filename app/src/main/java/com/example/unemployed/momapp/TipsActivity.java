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
                        intent.putExtra("information",Readfile("tip.txt"));
                        intent.putExtra("header","ความสำคัญและประโยชน์ของการนับลูกดิ้น");
                        startActivity(intent);
                        return true;
                    case R.id.tip2:
                        intent.putExtra("information",Readfile("tip2.txt"));
                        intent.putExtra("header","เมื่อลูกดิ้นคุณเเม่จะรู้สึกอย่างไร");
                        startActivity(intent);
                        return true;
                    case R.id.tip3:
                        intent.putExtra("information",Readfile("tip3.txt"));
                        intent.putExtra("header","วิธีการนับลูกดิ้นแบบนับถึงสิบ");
                        startActivity(intent);
                        return true;
                    case R.id.tip4:
                        intent.putExtra("information",Readfile("tip4.txt"));
                        intent.putExtra("header","  เมื่อลูกดิ้นเราจะสร้างความสัมพันธภาพกับลูกน้อยได้อย่างไร");
                        startActivity(intent);
                        return true;
                    case R.id.tip5:
                        intent.putExtra("information",Readfile("tip5.txt"));
                        intent.putExtra("header","อาการผิดปกติที่ควรมาโรงพยาบาล");
                        startActivity(intent);
                        return true;
                    case R.id.ti6:
                        intent.putExtra("information",Readfile("tip4.txt"));
                        intent.putExtra("header","  เมื่อลูกดิ้นเราจะสร้างความสัมพันธภาพกับลูกน้อยได้อย่างไร");
                        startActivity(intent);
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
       return text;
    }
}
