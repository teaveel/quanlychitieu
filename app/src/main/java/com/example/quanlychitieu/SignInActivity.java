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
    private FirebaseAuth mAuth;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = "";
                String password = "";
//                mAuth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
////                                    Log.d(TAG, "signInWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                                    updateUI(user);
//                                } else {
//                                    // If sign in fails, display a message to the user.
////                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
////                                    updateUI(null);
//                                }
//
//                                // ...
//                            }
//                        });
                Intent intent = new Intent();
                intent.setClass(SignInActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // check user hien tai
        mAuth = FirebaseAuth.getInstance();

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }
    public void updateUi(FirebaseUser user)
    {

    }
}