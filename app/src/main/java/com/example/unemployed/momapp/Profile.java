package com.example.unemployed.momapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    TextView age, pregnancy, duedate, period, logout;
    Button tohome, edit ;
    DatabaseReference dref;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();

        age = findViewById(R.id.age);
        pregnancy = findViewById(R.id.pregnancy);
        duedate = findViewById(R.id.duedate);
        period = findViewById(R.id.period);
        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);

        tohome = findViewById(R.id.tohome);

        Query userQuery = dref.child("User").child(user.getUid());
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String Age = dataSnapshot.child("age").getValue(String.class);
                    String Duedate = dataSnapshot.child("duedate").getValue(String.class);
                    String Period = dataSnapshot.child("period").getValue(String.class);
                    String Pregnancy = dataSnapshot.child("pregnancy").getValue(String.class);

                    age.setText(Age);
                    duedate.setText(Duedate);
                    period.setText(Period);
                    pregnancy.setText(Pregnancy);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.getMessage();
            }
        });

        tohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(getApplicationContext(), HomeActivity.class);
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
                finish();
                Intent x = new Intent(getApplicationContext(), Login.class);
                startActivity(x);
            }
        });
    }
}
