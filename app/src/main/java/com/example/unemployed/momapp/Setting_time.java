package com.example.unemployed.momapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
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

public class Setting_time extends AppCompatActivity {
    Button settime ;
    TimePicker timePicker ;
    Button cancel ;
    DatabaseReference dref;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_time);
        Utils.getDatabase();
        mAuth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = mAuth.getCurrentUser();

        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));
        }
        dref = FirebaseDatabase.getInstance().getReference();

        settime = findViewById(R.id.settime);
        timePicker = findViewById(R.id.timePicker);
        cancel = findViewById(R.id.cancel);

        settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (( timePicker.getCurrentHour()>12) || (timePicker.getCurrentHour()==12 && timePicker.getCurrentMinute()>=0 )) {
                    Toast.makeText(getApplicationContext(), "กรุณาอย่าตั้งเวลาเกิน 12 นาฬิกา", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    return;
                }

                String kuy = Integer.toString(timePicker.getCurrentHour());
                String kuy2 = Integer.toString(timePicker.getCurrentMinute());

                FirebaseUser user = mAuth.getCurrentUser();
                dref.child("User").child(user.getUid()).child("time").setValue(kuy+":"+kuy2+":00");

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
                            else if(Calendar.getInstance().after(calendar2)){
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

                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

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
