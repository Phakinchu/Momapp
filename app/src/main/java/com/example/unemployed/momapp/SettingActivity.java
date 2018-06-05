package com.example.unemployed.momapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingActivity extends AppCompatActivity {
    EditText age , pregnancy , duedate , period ;
    Button save , next ;
    DatabaseReference dref;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        Log.i("kuy","kuykyu");
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));
        }
        dref = FirebaseDatabase.getInstance().getReference();

        age = findViewById(R.id.age) ;
        pregnancy = findViewById(R.id.pregnancy);
        duedate = findViewById(R.id.duedate);
        period = findViewById(R.id.period);

        save = findViewById(R.id.save);
        next = findViewById(R.id.next);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInformation();
                finish();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });


    }

    private void saveUserInformation() {
        String Age = age.getText().toString().trim();
        String Pregnancy = pregnancy.getText().toString().trim();
        String Duedate = duedate.getText().toString().trim();
        String Period = period.getText().toString().trim();

        if (TextUtils.isEmpty(Age)) {
            Toast.makeText(this, "Could not save information, Age is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Pregnancy)) {
            Toast.makeText(this, "Could not save information, Pregnancy number is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Duedate)) {
            Toast.makeText(this, "Could not save information, Duedate number is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Period)) {
            Toast.makeText(this, "Could not save information, Period number is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Userinformation userInformation = new Userinformation(Age, Pregnancy,Duedate,Period);

        FirebaseUser user = mAuth.getCurrentUser();

        dref.child("User").child(user.getUid()).setValue(userInformation);

        Toast.makeText(this, "Information Saved", Toast.LENGTH_SHORT).show();
    }

}
