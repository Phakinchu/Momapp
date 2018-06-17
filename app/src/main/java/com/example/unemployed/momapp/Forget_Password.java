package com.example.unemployed.momapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forget_Password extends AppCompatActivity {
    Button login ;
    EditText email ;
    String Email ;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);
        Utils.getDatabase();
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = email.getText().toString().trim();
                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(getApplication(), "Enter your email id", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.sendPasswordResetEmail(Email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Firebase", "Email sent.");
                                    Toast.makeText(getApplicationContext(), "Email send", Toast.LENGTH_LONG).show();
                                    Intent x =new Intent(getApplicationContext(),Login.class) ;
                                    startActivity(x);
                                }
                                else {
                                    Log.d("Firebase", "Not send: ");
                                }
                            }
                        });
//                Toast.makeText(getApplicationContext(), "Clicked on Button", Toast.LENGTH_LONG).show();
            }
        });
    }

}
