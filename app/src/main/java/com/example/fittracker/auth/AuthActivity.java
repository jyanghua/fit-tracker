package com.example.fittracker.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.fittracker.MainActivity;
import com.example.fittracker.R;
import com.parse.ParseUser;


public class AuthActivity extends AppCompatActivity {

    public static final String TAG = "AuthActivity";

    Button btnCreateNewAccountAuth;
    TextView tvLogInAuth;
    RelativeLayout authContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        btnCreateNewAccountAuth = findViewById(R.id.btnCreateNewAccountAuth);
        tvLogInAuth = findViewById(R.id.tvLogInAuth);
        authContainer = findViewById(R.id.authContainer);

        btnCreateNewAccountAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authContainer.setVisibility(View.GONE);
                // Begin the transaction
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                // Replace the contents of the container with the new fragment
                ft.replace(R.id.auth_container, new SignUpFragment())
                        .addToBackStack("SignUpFragment")
                        .commit();
            }
        });

        tvLogInAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authContainer.setVisibility(View.GONE);
                // Begin the transaction
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                // Replace the contents of the container with the new fragment
                ft.replace(R.id.auth_container, new LoginFragment())
                        .addToBackStack("LoginFragment")
                        .commit();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        authContainer.setVisibility(View.VISIBLE);
        super.onBackPressed();
    }
}
