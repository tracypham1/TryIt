package com.example.tryit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.material.textfield.TextInputLayout;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button button_signIn = findViewById(R.id.butt_sign_in);
        Button button_createAcc = findViewById(R.id.butt_sign_in_create_account);

        final TextInputLayout email_layout = findViewById(R.id.email_text_layout);
        final TextInputLayout password_layout = findViewById(R.id.password_text_layout);


        button_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_layout.getEditText().getText().toString().trim();
                String password = password_layout.getEditText().getText().toString().trim();

                int emptyCount = 0;

                if (email.isEmpty() || email == null) {
                    email_layout.setError("Please enter a valid email");
                    email_layout.setErrorEnabled(true);
                    emptyCount++;
                } else email_layout.setErrorEnabled(false);

                if (password.isEmpty() || password == null) {
                    password_layout.setError("Please enter a valid password");
                    password_layout.setErrorEnabled(true);
                    emptyCount++;
                } else password_layout.setErrorEnabled(false);

                if (emptyCount == 0) {
                    signIn(email, password);
                }
            }
        });

        button_createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUp();

            }
        });

    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success,
                            startHome();
                            System.out.println("signed in");
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInActivity.this, "Incorrect email/password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private void startHome() {
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void startSignUp() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

}