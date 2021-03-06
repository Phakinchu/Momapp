package com.example.unemployed.momapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    EditText username ,password ;
    Button signup;
    FirebaseAuth mAuth ;
    DatabaseReference dref ;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        progressDialog = new ProgressDialog(this);
        signup = findViewById(R.id.signup);
        try{
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    registerUser();
                }
            });

        }
        catch (Exception e) {
            Log.i("Error", "Exception = "+e);
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    registerUser();
                }
            });
        }

    }

    private void registerUser() {
        String email = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        progressDialog.setMessage("Registering User ...");
        progressDialog.show();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.i("hi", "kuy a rai arrrr");

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();

                    FirebaseUser user = mAuth.getCurrentUser();
                    dref = FirebaseDatabase.getInstance().getReference();
                    dref.child("User").child(user.getUid()).child("age").setValue("0");
                    dref.child("User").child(user.getUid()).child("duedate").setValue("0/0/0");
                    dref.child("User").child(user.getUid()).child("period").setValue("0/0/0");
                    dref.child("User").child(user.getUid()).child("pregnancy").setValue("0");
                    dref.child("User").child(user.getUid()).child("time").setValue("8:0:00");

                    mAuth.signOut();
                    if(mAuth == null){
                        Log.i("user is", "null");
                        Log.i("useris", " "+mAuth);
                    }
                    else {
                        Log.i("user is", " not null ");
                        Log.i("useris", " "+mAuth);
                    }
                    finish();
                    Intent x = new Intent(getApplicationContext(), Login.class);
                    startActivity(x);

                    Toast.makeText(getApplicationContext(), "Register successful", Toast.LENGTH_SHORT).show();
                    Log.i("hi", "kuy a rai ar");
                } else {
                    progressDialog.dismiss();
//                    Log.e(TAG, "onComplete: Failed =" + task.getException().getMessage());
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                    Log.i("hi", "kuy a rai ar2");
                }
            }
        });

    }

}
