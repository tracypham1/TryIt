package com.example.tryit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button button_createAcc = findViewById(R.id.butt_sign_in_create_account);

        final TextInputLayout email_layout = findViewById(R.id.create_email_text_layout);
        final TextInputLayout password_layout = findViewById(R.id.create_password_text_layout);

    }

    private void createAccount(String email, String password) {
        final String e = email;
        final String p = password;
        mAuth.createUserWithEmailAndPassword(e, p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            //add user to Firebase user collection
                            user.getUid();

                            //sign in user
                            mAuth.signInWithEmailAndPassword(e, p);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Problem occurred. Try again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}