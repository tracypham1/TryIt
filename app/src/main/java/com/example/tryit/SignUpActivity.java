package com.example.tryit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String email, password, fName, lName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button button_createAcc = findViewById(R.id.butt_create_account);

        final TextInputLayout fName_layout = findViewById(R.id.create_fName_layout);
        final TextInputLayout lName_layout = findViewById(R.id.create_lName_layout);
        final TextInputLayout email_layout = findViewById(R.id.create_email_layout);
        final TextInputLayout password_layout = findViewById(R.id.create_password_layout);

        button_createAcc.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                fName = fName_layout.getEditText().getText().toString().trim();
                lName = lName_layout.getEditText().getText().toString().trim();
                email = email_layout.getEditText().getText().toString().trim();
                password = password_layout.getEditText().getText().toString().trim();

                int emptyCount = 0;

                if(fName.isEmpty()) {
                    fName_layout.setError("Enter name");
                    fName_layout.setErrorEnabled(true);
                    emptyCount++;
                } else  fName_layout.setErrorEnabled(false);

                if(lName.isEmpty()) {
                    lName_layout.setError("Enter name");
                    lName_layout.setErrorEnabled(true);
                    emptyCount++;
                } else  lName_layout.setErrorEnabled(false);

                if(email.isEmpty()) {
                    email_layout.setError("Enter name");
                    email_layout.setErrorEnabled(true);
                    emptyCount++;
                } else  email_layout.setErrorEnabled(false);

                if(password.isEmpty()) {
                    password_layout.setError("Enter name");
                    password_layout.setErrorEnabled(true);
                    emptyCount++;
                } else  password_layout.setErrorEnabled(false);

                if(emptyCount == 0) createAccount();
            }
        });

    }

    private void createAccount() {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            //add user to Firebase user collection
                            String userID = user.getUid();
                            Map<String, Object> usr = new HashMap<>();
                            usr.put("userID", userID);
                            usr.put("email", user.getEmail());
                            usr.put("fName", fName);
                            usr.put("lName", lName);

                            db.collection("users").document(userID)
                                    .set(usr)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            System.out.println("Account Created");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignUpActivity.this, "Error Occurred! Try again.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            //sign in user
                            mAuth.signInWithEmailAndPassword(email, password);
                            startHome();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Problem occurred. Try again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void startHome() {
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}