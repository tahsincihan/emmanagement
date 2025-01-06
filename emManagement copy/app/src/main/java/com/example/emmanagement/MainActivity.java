package com.example.emmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        etUsername = findViewById(R.id.emailInput);
        etPassword = findViewById(R.id.passwordInput);
        btnLogin = findViewById(R.id.btnLogin);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Set Login button click listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
            }
        });
    }

    // Validate the login credentials
    private void validateLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Check if fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate user with DatabaseHelper
        String role = dbHelper.validateUser(username, password);

        if (role != null) {
            Toast.makeText(this, role + " Login Successful", Toast.LENGTH_SHORT).show();

            // Navigate based on role
            if (role.equals("admin")) {
                navigateToAdminDashboard();
            } else if (role.equals("employee")) {
                navigateToEmployeeDashboard();
            }
        } else {
            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }
    }

    // Navigate to Admin Dashboard
    public void navigateToAdminDashboard() {
        Intent intent = new Intent(this, AdminDashboardActivity.class);
        startActivity(intent);
    }

    // Navigate to Employee Dashboard
    public void navigateToEmployeeDashboard() {
        Intent intent = new Intent(this, EmployeeDashboardActivity.class);
        startActivity(intent);
    }
}