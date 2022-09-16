package com.android.Blood_Bank_App.Rakht_Spot.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.Blood_Bank_App.Rakht_Spot.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button signin, signup, resetpass;
    private EditText inputemail, inputpassword;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(true);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
            finish();
        }


        inputemail = findViewById(R.id.input_username);
        inputpassword = findViewById(R.id.input_password);

        signin = findViewById(R.id.button_login);
        signup = findViewById(R.id.button_register);
        resetpass = findViewById(R.id.button_forgot_password);

        signin.setOnClickListener(v -> {

            final String email = inputemail.getText().toString()+"";
            final String password = inputpassword.getText().toString()+"";

            try {
                if(password.length()>0 && email.length()>0) {
                    pd.show();
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, task -> {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                            "Authentication Failed",
                                            Toast.LENGTH_LONG).show();
                                    Log.v("error", task.getException().getMessage());
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                    startActivity(intent);
                                    finish();
                                }
                                pd.dismiss();
                            });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please fill all the field.", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        signup.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });

        resetpass.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RestorePassword.class);
            startActivity(intent);
        });


    }

}
