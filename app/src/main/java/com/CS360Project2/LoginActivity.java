package com.CS360Project2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText msUserName, msPassword;
    Button msButton, btnRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        db.insertDefaultUsers();
        db.insertDefaultProducts();

        msUserName = findViewById(R.id.msUserName);
        msPassword = findViewById(R.id.msPassword);
        msButton = findViewById(R.id.msButton);
        btnRegister = findViewById(R.id.btnRegister);

        msButton.setOnClickListener(v -> {

            String username = msUserName.getText().toString().trim();
            String password = msPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.checkUser(username, password)) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainMenuActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}