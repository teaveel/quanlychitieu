package com.example.quanlychitieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;


import com.google.firebase.auth.*;
import android.widget.*;
import android.view.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

public class MainActivity extends AppCompatActivity {

    Button btnToSignIn, btnToSignUp;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e)
        {

        }

        btnToSignIn = findViewById(R.id.btnToSignIn);
        btnToSignUp = findViewById(R.id.btnToSignUp);

        btnToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        btnToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null)
        {
//            startActivity(new Intent(MainActivity.this, SignInActivity.class));
        }
        else
        {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        }
    }
}