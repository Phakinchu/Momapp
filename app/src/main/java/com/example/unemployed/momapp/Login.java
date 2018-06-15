package com.example.unemployed.momapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Login extends AppCompatActivity {
    TextView username, password, forgetpass , signup ,welcome;
    Button login ;
    DatabaseReference dref ;
    FirebaseAuth mAunt ;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAunt = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        welcome = findViewById(R.id.textView8);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "chicle.ttf");
        welcome.setTypeface(typeface);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        forgetpass = findViewById(R.id.forgetpass);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x =new Intent(getApplicationContext(),Register.class) ;
                startActivity(x);
            }
        });


//        forgetpass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent x =new Intent(getApplicationContext(),TipsActivity.class) ;
//                startActivity(x);
//            }
//        });
    }

    private void userLogin() {
        String email = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        progressDialog.setMessage("Login User ...");
        progressDialog.show();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }


        mAunt.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    finish();
                    //check if it is user or store
                    Query userQuery = dref.child("User").orderByKey().equalTo(mAunt.getCurrentUser().getUid());
                    userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                calendar.set(Calendar.HOUR_OF_DAY,8);
                                calendar.set(Calendar.MINUTE, 00);
                                calendar.set(Calendar.SECOND, 00);
                                if(Calendar.getInstance().before(calendar)){
                                    Intent j = new Intent(getApplicationContext(),Noti_morning.class) ;
                                    Log.i("AlarmManager Moning", "set !!!!!!!");
                                    PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 3, j, 0);
                                    AlarmManager alarmManager =  (AlarmManager) getSystemService(ALARM_SERVICE);
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                                }
                                else if(Calendar.getInstance().after(calendar)){
                                    calendar.add( Calendar.DATE, 1 );
                                    calendar.set(Calendar.HOUR_OF_DAY,8);
                                    calendar.set(Calendar.MINUTE, 00);
                                    calendar.set(Calendar.SECOND, 00);

                                    Intent j = new Intent(getApplicationContext(),Noti_morning.class) ;
                                    PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 3, j, 0);
                                    AlarmManager alarmManager =  (AlarmManager) getSystemService(ALARM_SERVICE);
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                                    Log.i("AlarmManager tmr moning", "set !!!!!!!");
                                    //do nothing
                                }
                                finish();
                                startActivity(new Intent(getApplicationContext(), Profile.class));
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            databaseError.getMessage();
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Could not sign in, please check your username and password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
