package com.example.unemployed.momapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DayActivity extends AppCompatActivity {
    private static final String Tag = "DayActivity";
    private TextView textView, babycount;                  //date
    private Button backbtn, up, down;
    DatabaseReference dref;
    FirebaseAuth mAuth;
    private static final int [] idArray ={R.id.checkBox1,R.id.checkBox2,R.id.checkBox3,R.id.checkBox4,R.id.checkBox5,
            R.id.checkBox6,R.id.checkBox7,R.id.checkBox8,R.id.checkBox9,R.id.checkBox10,R.id.checkBox11,R.id.checkBox12,
            R.id.checkBox13,R.id.checkBox14,R.id.checkBox15,R.id.checkBox16,R.id.checkBox17,R.id.checkBox18,R.id.checkBox19,
            R.id.checkBox20,R.id.checkBox21,R.id.checkBox22,R.id.checkBox23,R.id.checkBox24};
    private CheckBox[] checkBoxes = new CheckBox[idArray.length];
    private static int number;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        mAuth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = mAuth.getCurrentUser();

        textView = (TextView) findViewById(R.id.date);
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
        final Query userQuery = dref.child("User").child(user.getUid()).child("Date");
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Long count = dataSnapshot.child(dateforfirebase).getValue(Long.class);
                    babycount.setText(String.valueOf(count));
                    /*for(int i =0;i<idArray.length;i++){
                        checkBoxes[i]=(CheckBox)findViewById(idArray[i]);
                        if(i<count){
                            checkBoxes[i].setChecked(true);
                        }else {
                            checkBoxes[i].setChecked(false);
                        }
                    }*/
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


}
