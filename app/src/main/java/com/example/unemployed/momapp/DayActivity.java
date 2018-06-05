package com.example.unemployed.momapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class DayActivity extends AppCompatActivity {
    private static final String Tag = "DayActivity";
    private TextView textView,babycount;                  //date
    private Button backbtn,up,down;
    DatabaseReference dref;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        mAuth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = mAuth.getCurrentUser();

        textView = (TextView)findViewById(R.id.date);
        babycount = findViewById(R.id.babycount);

        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        backbtn = (Button)findViewById(R.id.Backbutton);

        Intent income = getIntent();
        final String date = income.getStringExtra("date");
        final String dateforfirebase = income.getStringExtra("dateforfirebase");
        textView.setText(date);


        final Query userQuery = dref.child("User").child(user.getUid()).child("Date");
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Long count = dataSnapshot.child(dateforfirebase).getValue(Long.class);
                    babycount.setText(String.valueOf(count));
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
                Intent intent = new Intent(DayActivity.this,HomeActivity.class);
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

                        p = p+1 ;
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

                        p = p-1 ;
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
