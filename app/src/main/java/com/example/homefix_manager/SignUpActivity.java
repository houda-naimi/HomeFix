package com.example.homefix_manager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignUpActivity extends AppCompatActivity {
    private EditText etUsername, etPassword, etConfirmPassword;
    private Button btnCreateAccount;
    private DbHelperUser dbHelperUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        dbHelperUser = new DbHelperUser(this);

        btnCreateAccount.setOnClickListener(view -> createAccount());
    }

    private void createAccount() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelperUser.checkUser(username, password)) {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            return;
        }


        String role = username.toLowerCase().contains("admin") ? "admin" : "user";

        if (dbHelperUser.addUser(username, password, role) != -1) {
            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error creating account", Toast.LENGTH_SHORT).show();
        }
    }

}
