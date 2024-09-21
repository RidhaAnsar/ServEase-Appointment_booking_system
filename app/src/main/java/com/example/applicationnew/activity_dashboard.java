package com.example.applicationnew;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;

public class activity_dashboard extends AppCompatActivity {

    private Button viewServicesButton;
    private Button manageAppointmentsButton;
    private Button logoutButton;
    private TextView welcomeTextView; // New TextView for welcome message
    private TextView bookingsTextView; // TextView to display bookings

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize buttons and TextViews
        viewServicesButton = findViewById(R.id.button2);
        manageAppointmentsButton = findViewById(R.id.button3);
        logoutButton = findViewById(R.id.button5);
        welcomeTextView = findViewById(R.id.welcomeTextView); // Ensure this ID exists in your XML
        bookingsTextView = findViewById(R.id.bookingsTextView); // Ensure this ID exists in your XML

        // Retrieve username from intent
        String username = getIntent().getStringExtra("USERNAME");
        if (username != null) {
            welcomeTextView.setText("Welcome, " + username); // Display welcome message
        }

        // Set onClickListener for View Services button
        viewServicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to View Services Activity
                Intent intent = new Intent(activity_dashboard.this, activity_view_services.class);
                startActivity(intent);
            }
        });

        // Set onClickListener for Manage Appointments button
        manageAppointmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Manage Appointments Activity
                Intent intent = new Intent(activity_dashboard.this, activity_manage_services.class);
                startActivity(intent);
            }
        });

        // Set onClickListener for Logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout
                Toast.makeText(activity_dashboard.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                // Redirect to the Login Activity
                Intent intent = new Intent(activity_dashboard.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close this activity
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Fetch and display current bookings or updates
        loadCurrentBookings();
    }

    private void loadCurrentBookings() {
        DBHandler dbHandler = new DBHandler(this);
        Cursor cursor = dbHandler.getAllBookings(); // You should implement this method in DBHandler

        if (cursor.getCount() == 0) {
            bookingsTextView.setText("No current bookings."); // Update the UI
        } else {
            StringBuilder bookings = new StringBuilder("Current Bookings:\n");
            while (cursor.moveToNext()) {
                String serviceName = cursor.getString(cursor.getColumnIndex("booking_service_name"));
                String timeSlot = cursor.getString(cursor.getColumnIndex("booking_time_slot"));
                bookings.append(serviceName).append(" at ").append(timeSlot).append("\n");
            }
            bookingsTextView.setText(bookings.toString()); // Update the UI with bookings
        }
        cursor.close(); // Close the cursor to avoid memory leaks
    }
}
