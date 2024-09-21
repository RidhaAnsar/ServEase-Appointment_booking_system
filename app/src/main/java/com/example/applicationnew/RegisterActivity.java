package com.example.applicationnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    // Declare the EditTexts
    private EditText editName, editUsername, editPassword;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize EditText objects
        editName = findViewById(R.id.text_name);
        editUsername = findViewById(R.id.text_uname);
        editPassword = findViewById(R.id.text_pwd);

        // Create an object for DBHandler
        dbHandler = new DBHandler(RegisterActivity.this);
    }

    // OnClick method for the register button
    public void addUser(View view) {
        // Retrieve user input from EditTexts
        String name = editName.getText().toString();
        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();

        // Check if inputs are not empty
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Call the addNewUser method from DBHandler
        dbHandler.addUser(name, username, password);

        // Show a success message
        Toast.makeText(RegisterActivity.this, "New user added successfully!", Toast.LENGTH_LONG).show();

        // Clear the EditTexts after user is added
        editName.setText("");
        editUsername.setText("");
        editPassword.setText("");
    }

    // OnClick method for back to home button
    public void backToHome(View view) {
        // Launch MainActivity
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
