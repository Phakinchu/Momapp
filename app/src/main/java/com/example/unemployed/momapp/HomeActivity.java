package com.example.unemployed.momapp;


import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
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
    CardView cardView_add,cardView_tips,cardView_setting,cardView_contract;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Utils.getDatabase();
        mAuth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference();
//        first_text = findViewById(R.id.first_text);
//        second_text = findViewById(R.id.second_text);
        calendarView = (CalendarView) findViewById(R.id.calendar);
        cardView_add = findViewById(R.id.cardadd);
        cardView_tips= findViewById(R.id.cardtip);
        cardView_setting = findViewById(R.id.cardsetting);
        cardView_contract = findViewById(R.id.cardcontract);
        //----------------------------------------------------------
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
            if (Calendar.getInstance().before(calendar)) {

                Log.i("AlarmManager Moning", "set !!!!!!!");
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                intent.putExtra("AlarmAt", "morning");
                PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(), 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);

            } else if (Calendar.getInstance().after(calendar)) {
                //เพิ่ม Noti_6,Noti_12 at Home//
                FirebaseUser user = mAuth.getCurrentUser();
                final Query userQuery = dref.child("User").child(user.getUid());
                userQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            String time = dataSnapshot.child("time").getValue(String.class);

                            Calendar calendar2 = Calendar.getInstance();
                            calendar2.setTimeInMillis(System.currentTimeMillis());
                            calendar2.set(Calendar.HOUR_OF_DAY, findHour("6",time));
                            calendar2.set(Calendar.MINUTE, findMin("6",time));
                            calendar2.set(Calendar.SECOND, 00);

                            Calendar calendar3 = Calendar.getInstance();
                            calendar3.setTimeInMillis(System.currentTimeMillis());
                            calendar3.set(Calendar.HOUR_OF_DAY, findHour("12",time));
                            calendar3.set(Calendar.MINUTE, findMin("12",time));
                            calendar3.set(Calendar.SECOND, 00);

                            if(Calendar.getInstance().before(calendar2)){

                                Log.i("AlarmManager 6", "set !!!!!!!");
                                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                                Intent intent = new Intent(getApplicationContext(),AlarmReceiver.class);
                                intent.putExtra("AlarmAt","six");
                                PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),broadcast);

                            }
                            else if(Calendar.getInstance().after(calendar2)){
                                Log.i("AlarmManager 6", "not set !!!!!!!");
                                //do nothing
                            }

                            if(Calendar.getInstance().before(calendar3)){
                                Log.i("AlarmManager 12", "set !!!!!!!");
                                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                                Intent intent = new Intent(getApplicationContext(),AlarmReceiver.class);
                                intent.putExtra("AlarmAt","twelve");
                                PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(),1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar3.getTimeInMillis(),broadcast);
                            }
                            else if(Calendar.getInstance().after(calendar3)){
                                Log.i("AlarmManager 12", "not set !!!!!!!");
                                //do nothing
                            }


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("setting time", "error "+databaseError);
                    }
                });
                //เพิ่ม Noti_morning forwards 7 days//
                for(int i =3 ;i<10;i++) {
                    calendar.add(Calendar.DATE, i-2);
                    calendar.set(Calendar.HOUR_OF_DAY, 8);
                    calendar.set(Calendar.MINUTE, 00);
                    calendar.set(Calendar.SECOND, 00);

                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                    intent.putExtra("AlarmAt", "morning");
                    PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(), i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);
                    Log.i("AlarmManager tmr moning", "set !!!!!!!");
                }
            }

        //-------------------------------------------------------------------------
        cardView_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long date = calendarView.getDate();
                String DateOnCalendar = new SimpleDateFormat("dd/MM/yyyy").format(new Date(date));
                final String DateOnCalendarforfirebase = new SimpleDateFormat("d+M+yyyy").format(new Date(date));
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
                                long  kuy = 0 ;
                                dref.child("User").child(user.getUid()).child("Date").child(DateOnCalendarforfirebase).setValue(kuy);
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
        cardView_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tips_intent = new Intent(HomeActivity.this, TipsActivity.class);
                startActivity(tips_intent);

            }
        });
        cardView_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setting_intent = new Intent(HomeActivity.this, Profile.class);
                startActivity(setting_intent);

            }
        });
        cardView_contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contract_intent = new Intent(HomeActivity.this, ContractActicity.class);
                startActivity(contract_intent);

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
                                long  kuy = 0 ;
                                dref.child("User").child(user.getUid()).child("Date").child(DateOnCalendarforfirebase).setValue(kuy);
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


    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK && isTaskRoot()) {
            //Ask the user if they want to quit
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Baby Kick Click")
                    .setIcon(R.drawable.icon2)
                    .setMessage("ต้องการออกจากแอปพลิเคชัน ?")
                    .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            mAuth.signOut();
                            finish();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("ไม่ใช่", null)
                    .show();

            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
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
    private Integer findHour(String time6or12 ,String time){
        if(time6or12 == "6"){
            String[] arrB = time.split(":");
            for(int i = 0; i<arrB.length; i++)
            {
                Log.i("split",arrB[i]);
            }
            Integer x = Integer.parseInt(arrB[0]);
            x=x+6 ;
            Log.i("x",""+x);
            return x ;
        }
        else if(time6or12 == "12"){
            String[] arrB = time.split(":");
            for(int i = 0; i<arrB.length; i++)
            {
                Log.i("split",arrB[i]);
            }
            Integer x = Integer.parseInt(arrB[0]);
            x=x+12 ;
            Log.i("x",""+x);
            return x ;
        }
        return null ;
    }

    private Integer findMin(String time6or12 ,String time){
        if(time6or12 == "6"){
            String[] arrB = time.split(":");
            for(int i = 0; i<arrB.length; i++)
            {
                Log.i("split",arrB[i]);
            }
            Integer x = Integer.parseInt(arrB[1]);
            Log.i("x",""+x);
            return x ;
        }
        else if(time6or12 == "12"){
            String[] arrB = time.split(":");
            for(int i = 0; i<arrB.length; i++)
            {
                Log.i("split",arrB[i]);
            }
            Integer x = Integer.parseInt(arrB[1]);
            Log.i("x",""+x);
            return x ;
        }
        return null ;
    }

}
