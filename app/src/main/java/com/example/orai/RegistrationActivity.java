package com.example.orai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EditText userName = findViewById(R.id.user_name);
        EditText password = findViewById(R.id.password);
        EditText email = findViewById(R.id.email);
        Button registration = findViewById(R.id.registration);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName.setError(null);
                password.setError(null);
                email.setError(null);
                if (Validation.isUserNameValid(userName.getText().toString())) {
                    if (Validation.isPasswordValid(password.getText().toString())) {
                        if (Validation.isEmailValid(email.getText().toString())) {

                            Intent goToMainActivity = new Intent(RegistrationActivity.this,
                                    LoginActivity.class);

                            startActivity(goToMainActivity);

                        } else {
                            email.setError(getResources().getString(R.string.registration_wrong_input));
                            email.requestFocus();
                        }
                    } else {
                        password.setError(getResources().getString(R.string.registration_wrong_input));
                        password.requestFocus();
                    }
                } else {
                    userName.setError(getResources().getString(R.string.registration_wrong_input));
                    userName.requestFocus();
                }
            }
            });

        }
    }