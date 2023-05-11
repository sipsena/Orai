package com.example.orai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Kai langas bus skurtas, koki vaizda jam uzkarausime
        setContentView(R.layout.activity_login);

        EditText userName = findViewById(R.id.user_name);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);

        //mygtuko paspaudimas
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aprasomas kodas kaip bus paspaudziamas mygtukas
                //klaidu zurnalo isvalymas
                userName.setError(null);
                password.setError(null);
                if (Validation.isUserNameValid(userName.getText().toString())) {
                    if (Validation.isPasswordValid(password.getText().toString())) {

                        //jeigu duomenys vartotojo suvesti duomenys teisingi. pereiti is
                        // prisijungimo lango i pagrindini
                        //sukuriamas kvietimas pereiti is prisijungimo lango (1 parametras) i
                        //Main langa(2 parametras)

                        Intent goToMainActivity=new Intent(LoginActivity.this,
                                MainActivity.class);

                        //startuoja nauja langas(butina perduoti auksciau sukurta kietinima)
                        //reikia nurodyti kur kietiname eiti

                        startActivity(goToMainActivity);

                        /*Toast.makeText(
                                getApplicationContext(),
                                "Prisijungimo vardas:" + userName.getText() + "\n Slaptazodis:" +
                                        password.getText(),
                                Toast.LENGTH_LONG).show(); */

                    } else {
                        password.setError(getResources().getString(R.string.login_wrong_input));
                        password.requestFocus();

                        /*Toast.makeText(getApplicationContext(),
                                R.string.login_wrong_input,
                                Toast.LENGTH_LONG).show();
                        //Toast - informacijos isvedimui i ekrana*/


                    }
                } else {
                    userName.setError(getResources().getString(R.string.login_wrong_input));
                    userName.requestFocus();

                    /*Toast.makeText(getApplicationContext(),
                            R.string.login_wrong_input,
                            Toast.LENGTH_LONG).show();*/
                }
            }
        }); //set on click listener
    }
}