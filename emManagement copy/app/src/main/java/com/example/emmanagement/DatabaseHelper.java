package com.example.emmanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserManagement.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create tables and populate initial data
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        String CREATE_USERS_TABLE = "CREATE TABLE Users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL UNIQUE, " +
                "password TEXT NOT NULL, " +
                "role TEXT NOT NULL)";
        db.execSQL(CREATE_USERS_TABLE);

        // Insert demo users
        db.execSQL("INSERT INTO Users (username, password, role) VALUES ('admin', 'admin123', 'admin')");
        db.execSQL("INSERT INTO Users (username, password, role) VALUES ('employee', 'emp123', 'employee')");
    }

    // Handle database upgrades
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);
    }

    // Validate user credentials
    public String validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT role FROM Users WHERE username=? AND password=?",
                new String[]{username, password});

        String role = null;
        if (cursor.moveToFirst()) {
            role = cursor.getString(0); // Get the role (admin or employee)
        }
        cursor.close();
        db.close();
        return role;
    }
}
