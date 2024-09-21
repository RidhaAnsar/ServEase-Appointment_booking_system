package com.example.applicationnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class activity_view_services extends AppCompatActivity {

    private ListView servicesListView;
    private Button bookServiceButton;
    private Button backToDashboardButton; // Declare the new button
    private ArrayList<String> services;
    private String selectedService;
    private int selectedServiceId; // New variable for service ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_services);

        servicesListView = findViewById(R.id.services_list);
        bookServiceButton = findViewById(R.id.book_service_button);
        backToDashboardButton = findViewById(R.id.back_to_dashboard_button); // Initialize the button

        // Sample services (replace with actual service names and IDs)
        services = new ArrayList<>();
        services.add("Hair cair services"); // ID: 1
        services.add("Massage and SPA services"); // ID: 2
        services.add("Wellness & Holistic Treatments"); // ID: 3
        services.add("Consultation (General Practitioner)"); // ID: 4
        services.add("Personal Training"); // ID: 5
        services.add("Yoga Class"); // ID: 6
        services.add("Facial and Skin Care Services");// ID: 7
        services.add("Makeup Services");
        services.add("Nutritional Counseling");
        services.add("Therapy");// ID: 8
        services.add("Fitness");
        services.add("Weight Management Workshops");

        // Set up the ListView with an ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, services);
        servicesListView.setAdapter(adapter);
        servicesListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Handle item click
        servicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedService = services.get(position);
                selectedServiceId = position + 1; // Assuming service IDs are 1-based
            }
        });

        // Handle booking button click
        bookServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedService != null) {
                    // Redirect to Manage Services Activity
                    Intent intent = new Intent(activity_view_services.this, activity_manage_services.class);
                    intent.putExtra("SELECTED_SERVICE_ID", selectedServiceId); // Pass the selected service ID
                    intent.putExtra("SELECTED_SERVICE_NAME", selectedService); // Pass the selected service name
                    startActivity(intent);
                } else {
                    Toast.makeText(activity_view_services.this, "Please select a service", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle back to dashboard button click
        backToDashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Dashboard Activity
                Intent intent = new Intent(activity_view_services.this, activity_dashboard.class);
                startActivity(intent);
                finish(); // Close this activity
            }
        });
    }
}
