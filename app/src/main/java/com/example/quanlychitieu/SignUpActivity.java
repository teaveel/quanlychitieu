package com.example.quanlychitieu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
public class SignUpActivity extends AppCompatActivity {
    EditText inputName, inputEmail, inputPass, inputConfirmPass;
    Button btnSignUp, btnAlreadyAcc;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPass = findViewById(R.id.inputPass);
        inputConfirmPass = findViewById(R.id.inputConfirmPass);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnAlreadyAcc = findViewById(R.id.alreadyAcc);

//        if(firebaseAuth.getCurrentUser() != null)
//        {
//            //da dang nhap
////            startActivity(new Intent(getApplicationContext()), );
////            finish();
//        }


        btnAlreadyAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPass.getText().toString().trim();
                String confirmPassword = inputConfirmPass.getText().toString().trim();
                if(TextUtils.isEmpty(name))
                {
                    inputName.setError("Name is required!");
                    return;
                }
                if(TextUtils.isEmpty(email))
                {
                    inputEmail.setError("Email is required!");
                    return;
                }
                if(password.length() < 6)
                {
                    inputEmail.setError("Password is required");
                    return;
                }
                if(confirmPassword.length() != password.length())
                {
                    inputEmail.setError("Password is not match!");
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}