package com.example.quanlychitieu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    EditText inputName, inputEmail, inputPass, inputConfirmPass;
    Button btnSignUp, btnAlreadyAcc;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

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

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


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
                    inputPass.setError("Password length must be have more 6 characters");
                    return;
                }
                if(confirmPassword.length() != password.length())
                {
                    inputConfirmPass.setError("Password is not match!");
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Map<String, Object> newUser = new HashMap<>();
                            newUser.put("email", email);
                            newUser.put("name", name);

                            db.collection("user")
                                    .add(newUser)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(getApplicationContext(), "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent();
                                            intent.setClass(SignUpActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("ADD_INCOME", "Error adding document", e);
                                        }
                                    });
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}