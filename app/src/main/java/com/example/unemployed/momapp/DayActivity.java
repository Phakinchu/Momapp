package com.example.unemployed.momapp;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DayActivity extends AppCompatActivity {
    private static final String Tag = "DayActivity";
    private TextView textView, babycount;                  //date
    private Button backbtn, up, down;
    DatabaseReference dref;
    FirebaseAuth mAuth;
    Integer checkcheckbox ;
    private int [] idArray ={R.id.checkBox1,R.id.checkBox2,R.id.checkBox3,R.id.checkBox4,R.id.checkBox5,
            R.id.checkBox6,R.id.checkBox7,R.id.checkBox8,R.id.checkBox9,R.id.checkBox10,R.id.checkBox11,R.id.checkBox12,
            R.id.checkBox13,R.id.checkBox14,R.id.checkBox15,R.id.checkBox16,R.id.checkBox17,R.id.checkBox18,R.id.checkBox19,
            R.id.checkBox20,R.id.checkBox21,R.id.checkBox22,R.id.checkBox23,R.id.checkBox24};
    private CheckBox[] checkBoxes = new CheckBox[idArray.length];
    private String number;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        Utils.getDatabase();
        mAuth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference();
//        final FirebaseUser user = mAuth.getCurrentUser();

        textView = (TextView) findViewById(R.id.date);
        checkBoxes[0] = findViewById(R.id.checkBox1);
        checkBoxes[1] = findViewById(R.id.checkBox2);
        checkBoxes[2] = findViewById(R.id.checkBox3);
        checkBoxes[3] = findViewById(R.id.checkBox4);
        checkBoxes[4] = findViewById(R.id.checkBox5);
        checkBoxes[5] = findViewById(R.id.checkBox6);
        checkBoxes[6] = findViewById(R.id.checkBox7);
        checkBoxes[7] = findViewById(R.id.checkBox8);
        checkBoxes[8] = findViewById(R.id.checkBox9);
        checkBoxes[9] = findViewById(R.id.checkBox10);
        checkBoxes[10] = findViewById(R.id.checkBox11);
        checkBoxes[11] = findViewById(R.id.checkBox12);
        checkBoxes[12] = findViewById(R.id.checkBox13);
        checkBoxes[13] = findViewById(R.id.checkBox14);
        checkBoxes[14] = findViewById(R.id.checkBox15);
        checkBoxes[15] = findViewById(R.id.checkBox16);
        checkBoxes[16] = findViewById(R.id.checkBox17);
        checkBoxes[17] = findViewById(R.id.checkBox18);
        checkBoxes[18] = findViewById(R.id.checkBox19);
        checkBoxes[19] = findViewById(R.id.checkBox20);
        checkBoxes[20] = findViewById(R.id.checkBox21);
        checkBoxes[21] = findViewById(R.id.checkBox22);
        checkBoxes[22] = findViewById(R.id.checkBox23);
        checkBoxes[23] = findViewById(R.id.checkBox24);

        babycount = findViewById(R.id.babycount);
        long day = new Date().getTime();
        String to_day = new SimpleDateFormat("dd/MM/yyyy").format(new Date(day));
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        backbtn = (Button) findViewById(R.id.Backbutton);
        Intent income = getIntent();
        final String date = income.getStringExtra("date");
        final String dateforfirebase = income.getStringExtra("dateforfirebase");
        textView.setText(date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date strDate = null;
        Date currDate = null;
        try {
            strDate = sdf.parse(date);
            currDate = sdf.parse(to_day);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!strDate.equals(currDate)) {
            up.setVisibility(View.GONE);
            down.setVisibility(View.GONE);
        }


        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            Log.i("user is", "null");
            Log.i("useris", " "+user);
        }
        else {
            Log.i("user is", " not null ");
            Log.i("useris", " "+user);
        }
        dref.child("User").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    FirebaseUser user = mAuth.getCurrentUser();
                    Log.i("fire dref", "is: "+dref);
                    Log.i("fire user", "is: "+mAuth.getCurrentUser());
                    Log.i("print user infor", " "+dref.child("User").child(user.getUid()));
                    if (dataSnapshot.hasChild("Date/" + dateforfirebase) == false) {
                        Log.i("datesanpshot", "not have child DateOnfirebase: ");
                        long  kuy = 0 ;
                        dref.child("User").child(user.getUid()).child("Date").child(dateforfirebase).setValue(kuy);

                    } else if (dataSnapshot.hasChild("Date/" + dateforfirebase) == true) {
                        Log.i("datesanpshot", "have child DateOnfirebase: ");
                    }
                    Log.i("print user infor", " "+dref.child("User").child(user.getUid()));

                    String Age = dataSnapshot.child("age").getValue(String.class);
                    Log.i("Age from firebase", " "+ Age);

                    try{
                        long count = (long) dataSnapshot.child("Date").child(dateforfirebase).getValue();
                        Log.i("got null Point", "Noooooo ");
                        babycount.setText(String.valueOf(count));
                        for(int i =0 ; i<= count-1 ; i++){
                            if((int) (long) count==0 || count > 24){
                                break;
                            }
                            else {
                                checkBoxes[i].setChecked(true);
                            }
                        }

                        for(int i =23 ; i>=count ; i--){
                            if((int) (long) count<=0){
                                checkBoxes[0].setChecked(false);
                                break;
                            }
                            else {
                                checkBoxes[i].setChecked(false);
                            }
                        }
                    }
                    catch (NullPointerException e){
                        Log.i("got null Point", "yes ");
                        long count = 0 ;
                        babycount.setText(String.valueOf(count));
                        for(int i =0 ; i<= count-1 ; i++){
                            if((int) (long) count==0 || count > 24){
                                break;
                            }
                            else {
                                checkBoxes[i].setChecked(true);
                            }
                        }

                        for(int i =23 ; i>=count ; i--){
                            if((int) (long) count<=0){
                                checkBoxes[0].setChecked(false);
                                break;
                            }
                            else {
                                checkBoxes[i].setChecked(false);
                            }
                        }
                    }

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
                        Intent j = new Intent(getApplicationContext(),Noti_add6.class) ;
                        Log.i("AlarmManager 6", "set !!!!!!!");
                        PendingIntent pendingIntent2 = PendingIntent.getService(getApplicationContext(), 0, j, 0);
                        AlarmManager alarmManager2 =  (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager2.set(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent2);

                    }
                    else if(Calendar.getInstance().after(calendar2)){
                        Log.i("AlarmManager 6", "not set !!!!!!!");
                        //do nothing
                    }

                    if(Calendar.getInstance().before(calendar3)){
                        Intent k = new Intent(getApplicationContext(),Noti_add12.class) ;
                        Log.i("AlarmManager 12", "set !!!!!!!");
                        PendingIntent pendingIntent3 = PendingIntent.getService(getApplicationContext(), 1, k, 0);
                        AlarmManager alarmManager3 =  (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager3.set(AlarmManager.RTC_WAKEUP, calendar3.getTimeInMillis(), pendingIntent3);
                    }
                    else if(Calendar.getInstance().after(calendar2)){
                        Log.i("AlarmManager 12", "not set !!!!!!!");
                        //do nothing
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                babycount.setText("Failed: " + databaseError.getMessage());
            }
        });


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                dref.child("User").child(user.getUid()).child("Date").child(dateforfirebase).runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        Integer p = mutableData.getValue(Integer.class);

                        p = p + 1;
                        // Set value and report transaction success
                        mutableData.setValue(p);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b,
                                           DataSnapshot dataSnapshot) {
                        // Transaction completed
                        Log.d("tran", "postTransaction:onComplete:" + databaseError);
                    }
                });

            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                dref.child("User").child(user.getUid()).child("Date").child(dateforfirebase).runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        Integer p = mutableData.getValue(Integer.class);

                        p = p - 1;
                        // Set value and report transaction success

                        mutableData.setValue(p);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b,
                                           DataSnapshot dataSnapshot) {
                        // Transaction completed
                        Log.d("tran", "postTransaction:onComplete:" + databaseError);
                    }
                });
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
