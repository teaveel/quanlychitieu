package com.example.quanlychitieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.*;
import android.widget.*;
import android.view.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    TextView inputEmail, inputPass;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        btnSignIn = findViewById(R.id.btnSignIn);
        inputEmail = findViewById(R.id.inputEmail);
        inputPass = findViewById(R.id.inputPass);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPass.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    Toast.makeText(SignInActivity.this, "Authentication successfully.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.setClass(SignInActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
//                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // check user hien tai
        firebaseAuth = FirebaseAuth.getInstance();

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        if(currentUser)
//        {
//
//        }
//        updateUI(currentUser);
    }
    public void updateUi(FirebaseUser user)
    {

    }
}