package com.example.emmanagement;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface EmployeeApi {
    // Get all employees
    @GET("/employees")
    Call<List<Employee>> getAllEmployees();

    // Get an employee by ID
    @GET("/employees/get/{id}")
    Call<Employee> getEmployeeById(@Path("id") int id);

    // Add a new employee
    @POST("/employees/add")
    Call<Void> addEmployee(@Body Employee employee);

    // Update an employee's details
    @PUT("/employees/edit/{id}")
    Call<Void> updateEmployee(@Path("id") int id, @Body Employee employee);

    // Delete an employee
    @DELETE("/employees/delete/{id}")
    Call<Void> deleteEmployee(@Path("id") int id);

    @GET("/notifications")
    Call<List<Notification>> getNotifications();

    @POST("/notifications/send")
    Call<Void> sendNotification(@Body Notification notification);



}
