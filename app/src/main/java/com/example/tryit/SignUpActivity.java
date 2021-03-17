package com.example.tryit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button button_signIn = findViewById(R.id.butt_sign_in);
        Button button_signInAnon = findViewById(R.id.butt_sign_in_anon);
        Button button_createAcc = findViewById(R.id.butt_create_account);

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
                String email = email_layout.getEditText().getText().toString().trim();
                String password = password_layout.getEditText().getText().toString().trim();

                int emptyCount = 0;

                if (email.isEmpty()) {
                    email_layout.setError("Please enter a valid email");
                    email_layout.setErrorEnabled(true);
                    emptyCount++;
                } else email_layout.setErrorEnabled(false);

                if (password.isEmpty()) {
                    password_layout.setError("Please enter a valid password");
                    password_layout.setErrorEnabled(true);
                    emptyCount++;
                } else password_layout.setErrorEnabled(false);

                if (emptyCount == 0) createAccount(email, password);
            }
        });

    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Problem occurred. Try again.",
                                    Toast.LENGTH_SHORT).show();
                        }
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
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Incorrect email/password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }
}