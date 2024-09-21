package com.example.applicationnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class activity_manage_services extends AppCompatActivity {

    private TextView selectedServiceTextView;
    private Spinner timeslotSpinner;
    private Button confirmButton;
    private String selectedService;
    private String selectedTimeSlot;
    private int selectedServiceId; // New variable for service ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_services);

        // Retrieve the selected service from the intent
        Intent intent = getIntent();
        selectedServiceId = intent.getIntExtra("SELECTED_SERVICE_ID", -1); // Get the service ID
        selectedService = intent.getStringExtra("SELECTED_SERVICE_NAME"); // Get the service name

        // Initialize views
        selectedServiceTextView = findViewById(R.id.selected_service_textview);
        timeslotSpinner = findViewById(R.id.timeslot_spinner);
        confirmButton = findViewById(R.id.confirm_button);

        // Set the selected service text if available
        if (selectedService != null && !selectedService.isEmpty()) {
            selectedServiceTextView.setText(selectedService);
        } else {
            selectedServiceTextView.setText("No service selected");
        }

        // Set up the Spinner with time slots
        String[] timeSlots = {"9:00 AM", "10:00 AM", "11:00 AM", "1:00 PM", "2:00 PM", "3:00 PM"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeSlots);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeslotSpinner.setAdapter(spinnerAdapter);

        // Handle time slot selection
        timeslotSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTimeSlot = timeSlots[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedTimeSlot = null;
            }
        });

        // Handle confirm button click
        confirmButton.setOnClickListener(v -> {
            if (selectedService != null && selectedTimeSlot != null) {
                // Add booking to the database
                DBHandler dbHandler = new DBHandler(activity_manage_services.this);
                dbHandler.addBooking(selectedService, selectedTimeSlot);

                // Redirect to Confirmation Activity
                Intent intent1 = new Intent(activity_manage_services.this, activity_confirmation.class);
                intent1.putExtra("SERVICE_NAME", selectedService);
                intent1.putExtra("TIME_SLOT", selectedTimeSlot);
                startActivity(intent1);
                finish(); // Close the current activity
            } else {
                Toast.makeText(activity_manage_services.this, "Please select a service and a time slot", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
