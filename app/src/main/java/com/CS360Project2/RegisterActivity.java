package com.CS360Project2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText regUsername, regPassword;
    Button btnCreateAccount;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        regUsername = findViewById(R.id.regUsername);
        regPassword = findViewById(R.id.regPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        btnCreateAccount.setOnClickListener(v -> {

            String username = regUsername.getText().toString().trim();
            String password = regPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = db.registerUser(username, password);

            if (success) {
                Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT).show();
                finish(); // Return to login
            } else {
                Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            }
        });
    }
}