package com.example.unemployed.momapp;


import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private static final String Tag = "HomeActivity";
    private CalendarView calendarView;
    TextView first_text,second_text ;
    DatabaseReference dref;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference();
//        first_text = findViewById(R.id.first_text);
//        second_text = findViewById(R.id.second_text);
        calendarView = (CalendarView) findViewById(R.id.calendar);
        //----------------------------------------------------------

        //-------------------------------------------------------------------------
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_add:
                        long date = calendarView.getDate();
                        String DateOnCalendar = new SimpleDateFormat("dd/MM/yyyy").format(new Date(date));
                        final String DateOnCalendarforfirebase = new SimpleDateFormat("M+d+yyyy").format(new Date(date));
                        Log.i("Time", DateOnCalendarforfirebase);
                        final FirebaseUser user = mAuth.getCurrentUser();

                        Query userQuery = dref.child("User").child(user.getUid());
                        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Log.i("datesanpshot", "exitsts: ");
                                    if (dataSnapshot.hasChild("Date/" + DateOnCalendarforfirebase) == false) {
                                        Log.i("datesanpshot", "not have child DateOnfirebase: ");
                                        dref.child("User").child(user.getUid()).child("Date").child(DateOnCalendarforfirebase).setValue(0);
                                    } else if (dataSnapshot.hasChild("Date/" + DateOnCalendarforfirebase) == true) {
                                        Log.i("datesanpshot", "have child DateOnfirebase: ");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                databaseError.getMessage();
                            }
                        });
                        Intent intent = new Intent(HomeActivity.this, DayActivity.class);
                        intent.putExtra("date", DateOnCalendar);
                        intent.putExtra("dateforfirebase", DateOnCalendarforfirebase);
                        startActivity(intent);
                        break;
                    case R.id.action_tips:
                        Intent tips_intent = new Intent(HomeActivity.this, TipsActivity.class);
                        startActivity(tips_intent);
                        break;
                    case R.id.action_contract:
                        Intent contract_intent = new Intent(HomeActivity.this, ContractActicity.class);
                        startActivity(contract_intent);
                        break;
                    case R.id.action_setting:
                        Intent setting_intent = new Intent(HomeActivity.this, Profile.class);
                        startActivity(setting_intent);

                }
                return false;
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String DateOnCalendar = dayOfMonth + "/" + (month + 1) + "/" + year;
                final String DateOnCalendarforfirebase = dayOfMonth + "+" + (month + 1) + "+" + year;
                Log.d(Tag, "onSelectedDayChange:mm/dd/yyyy" + DateOnCalendar);

                final FirebaseUser user = mAuth.getCurrentUser();



                Query userQuery = dref.child("User").child(user.getUid());
                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Log.i("datesanpshot", "exitsts: ");
                            if (dataSnapshot.hasChild("Date/" + DateOnCalendarforfirebase) == false) {
                                Log.i("datesanpshot", "not have child DateOnfirebase: ");
                                dref.child("User").child(user.getUid()).child("Date").child(DateOnCalendarforfirebase).setValue(0);
                            } else if (dataSnapshot.hasChild("Date/" + DateOnCalendarforfirebase) == true) {
                                Log.i("datesanpshot", "have child DateOnfirebase: ");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        databaseError.getMessage();
                    }
                });

                Intent intent = new Intent(HomeActivity.this, DayActivity.class);
                intent.putExtra("date", DateOnCalendar);
                intent.putExtra("dateforfirebase", DateOnCalendarforfirebase);
                startActivity(intent);
            }
        });

//        first_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(isMyServiceRunning(Noti_add6.class)){
//                    Log.i("OOOOOOOOO :","Noti_add6 Online" );
//                }
//                else if(isMyServiceRunning(Noti_add6.class)!= true){
//                    Log.i("OOOOOOOOO :","Noti_add6 offline" );
//                }
//
//
//            }
//        });
//
//        second_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                stopService(new Intent(getApplicationContext(), Noti_addsix.class));
//            }
//        });

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
