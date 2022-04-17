package com.example.quanlychitieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
public class MainActivity extends AppCompatActivity {

    Button btnToSignIn, btnToSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}