package com.example.applicationnew;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

public class LoginActivity extends AppCompatActivity {
    // Declarations
    private EditText edit_uname, edit_pwd;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize widgets
        edit_uname = findViewById(R.id.text_user);
        edit_pwd = findViewById(R.id.text_pass);
        // Create an object for DBHandler class
        dbHandler = new DBHandler(LoginActivity.this);
    }

    // OnClick method for Login Button
    // OnClick method for Login Button
    public void loginCheck(View v) {
        String username = edit_uname.getText().toString();
        String password = edit_pwd.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_LONG).show();
        } else {
            Boolean check = dbHandler.checkUsernamePassword(username, password);  // Ensure this method exists
            edit_uname.setText("");
            edit_pwd.setText("");

            if (check) {
                Toast.makeText(LoginActivity.this, "Successful Login", Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginActivity.this, activity_dashboard.class);
                i.putExtra("USERNAME", username);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
            }
        }
    }
}
