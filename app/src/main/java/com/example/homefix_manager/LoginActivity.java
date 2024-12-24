package com.example.homefix_manager;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin, btnSignUp;
    private DbHelperUser dbHelperUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        dbHelperUser = new DbHelperUser(this);
        ImageButton btnTogglePassword = findViewById(R.id.btnTogglePassword);

        btnLogin.setOnClickListener(view -> loginUser());
        btnSignUp.setOnClickListener(view -> createUser());

        btnSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        btnTogglePassword.setOnClickListener(view -> {
            if (etPassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                btnTogglePassword.setImageResource(R.drawable.show);
            } else {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                btnTogglePassword.setImageResource(R.drawable.hide);
            }
            etPassword.setSelection(etPassword.getText().length());
        });
    }

    private boolean validateInputs(String username, String password) {
        boolean isValid = true;

        if (username.isEmpty()) {
            etUsername.setBackgroundResource(R.drawable.edittext_error_background);
            isValid = false;
        } else {
            etUsername.setBackgroundResource(R.drawable.rounded_edittext);
        }

        if (password.isEmpty()) {
            etPassword.setBackgroundResource(R.drawable.edittext_error_background);
            isValid = false;
        } else {
            etPassword.setBackgroundResource(R.drawable.rounded_edittext);
        }

        return isValid;
    }

    private void loginUser() {

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();


        if (!validateInputs(username, password)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dbHelperUser.checkUser(username, password)) {
            String role = dbHelperUser.getRole(username);


            if (role != null) {
                if (role.contains("admin")) {
                    startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
                } else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        } else {

            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void createUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();


        String role = username.toLowerCase().contains("admin") ? "admin" : "user";


        if (dbHelperUser.addUser(username, password, role) != -1) {
            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error creating account", Toast.LENGTH_SHORT).show();
        }
    }






}
