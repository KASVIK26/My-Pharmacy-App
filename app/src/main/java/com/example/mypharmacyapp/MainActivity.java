package com.example.mypharmacyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView txtwishing;
    Button btnowner,btnuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPreferencesUtil.isLoggedIn(this)) {
            String userType = SharedPreferencesUtil.getUserType(this);
            if ("owner".equals(userType)) {
                Intent intent = new Intent(MainActivity.this, owner_scrn.class);
                startActivity(intent);
                finish(); // Close MainActivity so the user can't go back to it
                return;
            }else if ("user".equals(userType)) {
                // Redirect to user_scrn if user is logged in
                Intent intent = new Intent(MainActivity.this, user_scrn.class);
                startActivity(intent);
                finish(); // Close MainActivity so the user can't go back to it
                return;
            }
        }
        setContentView(R.layout.activity_main);
        txtwishing = findViewById(R.id.txtwishing);
        // Get the current hour
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        // Determine the appropriate greeting message
        String greeting;
        if (hourOfDay >= 0 && hourOfDay < 12) {
            greeting = "Good Morning";
        } else if (hourOfDay >= 12 && hourOfDay < 17) {
            greeting = "Good Afternoon";
        } else if (hourOfDay >= 17 && hourOfDay < 21) {
            greeting = "Good Evening";
        } else {
            greeting = "Good Night";
        }

        // Set the greeting message in the TextView
        txtwishing.setText(greeting);

        btnowner = findViewById(R.id.btnowner);
        btnuser = findViewById(R.id.btnuser);
        btnowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPreferencesUtil.isLoggedIn(MainActivity.this)) {
                    // Redirect directly to owner_scrn if already logged in
                    String userType = SharedPreferencesUtil.getUserType(MainActivity.this);
                    if ("owner".equals(userType)) {
                        Intent i = new Intent(MainActivity.this, owner_scrn.class);
                        startActivity(i);
                        finish(); // Close MainActivity so the user can't go back to it
                    }
                } else {
                    // Redirect to login if not logged in
                    Intent i = new Intent(MainActivity.this, owner_login.class);
                    startActivity(i);
                }
            }
        });
        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPreferencesUtil.isLoggedIn(MainActivity.this)) {
                    // Redirect directly to user_scrn if already logged in
                    String userType = SharedPreferencesUtil.getUserType(MainActivity.this);
                    if ("user".equals(userType)) {
                        Intent i = new Intent(MainActivity.this, user_scrn.class);
                        startActivity(i);
                        finish(); // Close MainActivity so the user can't go back to it
                    }
                } else {
                    // Redirect to login if not logged in
                    Intent i = new Intent(MainActivity.this, user_login.class);
                    startActivity(i);
                }
            }
        });






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}