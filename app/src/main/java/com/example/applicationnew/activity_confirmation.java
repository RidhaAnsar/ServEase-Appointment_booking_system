package com.example.applicationnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class activity_confirmation extends AppCompatActivity {

    private TextView confirmationTextView;
    private Button backToDashboardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        confirmationTextView = findViewById(R.id.confirmation_textview);
        backToDashboardButton = findViewById(R.id.back_to_dashboard_button);

        // Retrieve booking details from intent
        Intent intent = getIntent();
        String serviceName = intent.getStringExtra("SERVICE_NAME");
        String timeSlot = intent.getStringExtra("TIME_SLOT");

        // Display the booking confirmation
        confirmationTextView.setText("Booking confirmed!\nService: " + serviceName + "\nTime Slot: " + timeSlot);

        // Handle back to dashboard button click
        backToDashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Dashboard Activity
                Intent intent = new Intent(activity_confirmation.this, activity_dashboard.class);
                startActivity(intent);
                finish(); // Close this activity
            }
        });
    }
}
