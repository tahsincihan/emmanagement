package com.example.emmanagement;

private void deleteEmployee() {
    // Show confirmation dialog
    new AlertDialog.Builder(this)
            .setTitle("Delete Employee")
            .setMessage("Are you sure you want to delete this employee?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Perform API call to delete the employee
                    EmployeeApi api = RetrofitClient.getRetrofitInstance().create(EmployeeApi.class);
                    Call<Void> call = api.deleteEmployee(employeeId); // Use the appropriate ID

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(EmployeeEditActivity.this, "Employee deleted successfully", Toast.LENGTH_SHORT).show();
                                finish(); // Close the activity after successful deletion
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
