package com.example.emmanagement;

package com.example.emmanagement;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeEditActivity extends AppCompatActivity {

    private EditText etName, etContact, etAddress, etEmail, etPosition, etSalary, etDepartment;
    private Button btnSave, btnDelete;
    private int employeeId; // This will hold the employee ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_edit);

        // Initialize UI elements
        etName = findViewById(R.id.employeeName);
        etContact = findViewById(R.id.employeeContact);
        etAddress = findViewById(R.id.addressText);
        etEmail = findViewById(R.id.emailText);
        etPosition = findViewById(R.id.positionText);
        etSalary = findViewById(R.id.salaryText);
        etDepartment = findViewById(R.id.departmentText);

        btnSave = findViewById(R.id.saveButton);
        btnDelete = findViewById(R.id.deleteButton);

        // Retrieve employee ID from the intent
        employeeId = getIntent().getIntExtra("employeeId", -1);

        // Load employee details
        loadEmployeeDetails();

        // Set listeners for buttons
        btnSave.setOnClickListener(v -> saveEmployeeDetails());
        btnDelete.setOnClickListener(v -> deleteEmployee());
    }

    private void loadEmployeeDetails() {
        // Fetch the employee details from the API
        EmployeeApi api = RetrofitClient.getRetrofitInstance().create(EmployeeApi.class);
        Call<Employee> call = api.getEmployeeById(employeeId);

        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Employee employee = response.body();
                    // Populate the fields with employee data
                    etName.setText(employee.getFirstname() + " " + employee.getLastname());
                    etContact.setText(employee.getContact());
                    etAddress.setText(employee.getAddress());
                    etEmail.setText(employee.getEmail());
                    etPosition.setText(employee.getPosition());
                    etSalary.setText(String.valueOf(employee.getSalary()));
                    etDepartment.setText(employee.getDepartment());
                } else {
                    Toast.makeText(EmployeeEditActivity.this, "Failed to load employee details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Toast.makeText(EmployeeEditActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveEmployeeDetails() {
        // Get updated details
        String name = etName.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String position = etPosition.getText().toString().trim();
        String salaryStr = etSalary.getText().toString().trim();
        String department = etDepartment.getText().toString().trim();

        if (name.isEmpty() || contact.isEmpty() || address.isEmpty() || email.isEmpty() || position.isEmpty() || salaryStr.isEmpty() || department.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double salary;
        try {
            salary = Double.parseDouble(salaryStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid salary format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an updated Employee object
        Employee updatedEmployee = new Employee(
                name.split(" ")[0], // First name
                name.split(" ").length > 1 ? name.split(" ")[1] : "", // Last name
                contact, address, email, position, salary, department
        );

        // Make API call to update the employee
        EmployeeApi api = RetrofitClient.getRetrofitInstance().create(EmployeeApi.class);
        Call<Void> call = api.updateEmployee(employeeId, updatedEmployee);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EmployeeEditActivity.this, "Employee details updated successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                } else {
                    Toast.makeText(EmployeeEditActivity.this, "Failed to update employee details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EmployeeEditActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteEmployee() {
        // Show confirmation dialog
        new AlertDialog.Builder(this)
                .setTitle("Delete Employee")
                .setMessage("Are you sure you want to delete this employee?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Make API call to delete employee
                        EmployeeApi api = RetrofitClient.getRetrofitInstance().create(EmployeeApi.class);
                        Call<Void> call = api.deleteEmployee(employeeId);

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(EmployeeEditActivity.this, "Employee deleted successfully", Toast.LENGTH_SHORT).show();
                                    finish(); // Close the activity
                                } else {
                                    Toast.makeText(EmployeeEditActivity.this, "Failed to delete employee", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(EmployeeEditActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
