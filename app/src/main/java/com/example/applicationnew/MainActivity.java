package com.example.applicationnew;

import android.os.Bundle;
import android.util.Log;  // Import for logging
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Import necessary packages for View and Intent
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    // Declare DBHandler object
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the DBHandler (this triggers the database creation if it doesn't exist)
        dbHandler = new DBHandler(this);

        // Add a sample user to the database when the activity is created
        dbHandler.addUser("user", "user", "client");

        // Check if a user exists in the database (for login, for example)
        boolean isValidUser = dbHandler.checkUsernamePassword("JohnDoe", "password123");
        Log.d("MainActivity", "Is valid user: " + isValidUser);
    }

    // Onclick method for Register Button
    public void register(View v) {
        // Clicking on the Register button launches RegisterActivity
        Intent i = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    // Onclick method for Login Button
    public void login(View v) {
        // Clicking on the Login button launches LoginActivity
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
