package com.example.unemployed.momapp;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private static final String Tag = "HomeActivity";
    private CalendarView calendarView;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        calendarView = (CalendarView)findViewById(R.id.calendar);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_add:
                      long date = calendarView.getDate();
                      String DateOnCalendar = new SimpleDateFormat("MM//dd/yyyy").format(new Date(date));

                        Toast.makeText(HomeActivity.this,"date "+date,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this,DayActivity.class);
                        intent.putExtra("date",DateOnCalendar);
                        startActivity(intent);
                        break;
                    case R.id.action_tips:
                        Intent tips_intent = new Intent(HomeActivity.this,TipsActivity.class);
                        startActivity(tips_intent);
                        break;
                    case R.id.action_contract:
                        Intent contract_intent = new Intent(HomeActivity.this,ContractActicity.class);
                        startActivity(contract_intent);
                        break;
                    case R.id.action_setting:
                        Intent setting_intent = new Intent(HomeActivity.this,Profile.class);
                        startActivity(setting_intent);

                }
                return false;
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String DateOnCalendar = dayOfMonth+"/"+(month+1)+"/"+year;
                Log.d(Tag,"onSelectedDayChange:mm/dd/yyyy"+DateOnCalendar);

                Intent intent = new Intent(HomeActivity.this,DayActivity.class);
                intent.putExtra("date",DateOnCalendar);
                startActivity(intent);
            }
        });


    }
}
