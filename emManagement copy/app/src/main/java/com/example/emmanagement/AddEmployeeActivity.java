package com.example.emmanagement;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmployeeActivity extends AppCompatActivity {

    private EditText etName, etContact, etAddress, etEmail, etPosition, etSalary, etJoiningDate;
    private Button btnSave, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        // Initialize UI elements
        etName = findViewById(R.id.employeeNameInput);
        etContact = findViewById(R.id.employeeContactInput);
        etAddress = findViewById(R.id.employeeAddressInput);
        etEmail = findViewById(R.id.employeeEmailInput);
        etPosition = findViewById(R.id.employeePositionInput);
        etSalary = findViewById(R.id.employeeSalaryInput);
        etJoiningDate = findViewById(R.id.employeeJoinDateInput);

        btnSave = findViewById(R.id.saveButton);
        btnDelete = findViewById(R.id.deleteButton);

        // Set click listeners
        btnSave.setOnClickListener(v -> saveEmployee());
        btnDelete.setOnClickListener(v -> clearFields());
    }

    private void saveEmployee() {
        // Get input values
        String name = etName.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String position = etPosition.getText().toString().trim();
        String salaryStr = etSalary.getText().toString().trim();
        String joiningDate = etJoiningDate.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(address) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(position) || TextUtils.isEmpty(salaryStr) ||
                TextUtils.isEmpty(joiningDate)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        double salary;
        try {
            salary = Double.parseDouble(salaryStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid salary input", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create Employee object
        Employee newEmployee = new Employee(
                name,          // First Name
                "Unknown",     // Last Name placeholder (modify if needed)
                contact,
                address,
                email,
                position,
                salary,
                joiningDate
        );

        // Make API call using Retrofit
        EmployeeApi api = RetrofitClient.getRetrofitInstance().create(EmployeeApi.class);
        Call<Void> call = api.addEmployee(newEmployee);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddEmployeeActivity.this, "Employee added successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                } else {
                    Toast.makeText(AddEmployeeActivity.this, "Failed to add employee", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddEmployeeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        etName.setText("");
        etContact.setText("");
        etAddress.setText("");
        etEmail.setText("");
        etPosition.setText("");
        etSalary.setText("");
        etJoiningDate.setText("");
        Toast.makeText(this, "Fields cleared", Toast.LENGTH_SHORT).show();
    }
}
