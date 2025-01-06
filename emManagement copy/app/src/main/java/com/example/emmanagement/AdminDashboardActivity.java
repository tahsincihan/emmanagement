package com.example.emmanagement;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminDashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EmployeeAdapter adapter; // Assume this is your RecyclerView adapter for employees

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        recyclerView = findViewById(R.id.recyclerView); // Ensure you have a RecyclerView in your XML
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchEmployees();
    }

    private void fetchEmployees() {
        EmployeeApi api = RetrofitClient.getRetrofitInstance().create(EmployeeApi.class);
        Call<List<Employee>> call = api.getAllEmployees();

        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Employee> employees = response.body();
                    adapter = new EmployeeAdapter(employees);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(AdminDashboardActivity.this, "Failed to fetch employees", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Toast.makeText(AdminDashboardActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
