package com.example.emmanagement;
public class Employee {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String department;
    private double salary;
    private String joiningdate;
    private int leaves;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getJoiningdate() { return joiningdate; }
    public void setJoiningdate(String joiningdate) { this.joiningdate = joiningdate; }

    public int getLeaves() { return leaves; }
    public void setLeaves(int leaves) { this.leaves = leaves; }
}
