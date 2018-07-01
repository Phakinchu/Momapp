package com.example.unemployed.momapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Profile extends AppCompatActivity {
    TextView age,pregnancy ,duedate, period, logout, mom;
    Button tohome, edit, home;
    long days ;
    DatabaseReference dref;
    FirebaseAuth mAuth;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Utils.getDatabase();
        mAuth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        session = new Session(this);
        age = findViewById(R.id.age);
        duedate = findViewById(R.id.duedate);
        period = findViewById(R.id.period);
        logout = findViewById(R.id.logout);
        pregnancy = findViewById(R.id.pregnancy);
        edit = findViewById(R.id.edit);
        mom = findViewById(R.id.mom);
        home = findViewById(R.id.home);
        tohome = findViewById(R.id.tohome);

        Query userQuery = dref.child("User").child(user.getUid());
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String Age = dataSnapshot.child("age").getValue(String.class);
                    String Duedate = dataSnapshot.child("duedate").getValue(String.class);
                    String Period = dataSnapshot.child("period").getValue(String.class);
//                    String Pregnancy = dataSnapshot.child("pregnancy").getValue(String.class);
                    String timesetting = dataSnapshot.child("time").getValue(String.class);

                    try {
                        String[] arrB = Period.split("/");
                        for (int i = 0; i < arrB.length; i++) {
                            Log.i("split", arrB[i]);
                        }
                        Integer day = Integer.parseInt(arrB[0]);
                        Integer month = Integer.parseInt(arrB[1]);
                        Integer year = Integer.parseInt(arrB[2]);

                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.setTimeInMillis(System.currentTimeMillis());

                        Calendar calendar3 = Calendar.getInstance();
                        calendar3.setTimeInMillis(System.currentTimeMillis());
                        calendar3.set(Calendar.DAY_OF_MONTH,day);
                        calendar3.set(Calendar.MONTH,month-1 );
                        calendar3.set(Calendar.YEAR, year-543);

                        long kuy = calendar2.getTimeInMillis();
                        long kuy2 = calendar3.getTimeInMillis();

                        long diff = kuy - kuy2;

                        long subda = Math.round((diff/(24 * 60 * 60 * 1000))/7) ;
                        days = subda ;
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "ํตั้งค่าวันที่ผิดรูปแบบ", Toast.LENGTH_LONG).show();
                    }

                    age.setText(Age);
                    duedate.setText(Duedate);
                    period.setText(Period);
                    pregnancy.setText(Long.toString(days));
                    mom.setText(timesetting);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.getMessage();
            }
        });

//        StartThread("23:55:00",addTime("6",timeinday),addTime("12",timeinday));

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(x);
            }
        });

        tohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(getApplicationContext(), Setting_time.class);
                startActivity(x);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(x);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                session.isLogin(false);
                if (mAuth == null) {
                    Log.i("user is", "null");
                    Log.i("useris", " " + mAuth);
                } else {
                    Log.i("user is", " not null ");
                    Log.i("useris", " " + mAuth);
                }
                finish();
                Intent x = new Intent(getApplicationContext(), Login.class);
                startActivity(x);
            }
        });
    }

    private Integer findHour(String time6or12, String time) {

        String[] arrB = time.split("/");
        for (int i = 0; i < arrB.length; i++) {
            Log.i("split", arrB[i]);
        }
        Integer x = Integer.parseInt(arrB[0]);
        x = x + 6;
        Log.i("x", "" + x);
        return x;

    }
}
