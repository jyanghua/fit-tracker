package com.example.fittracker.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fittracker.MainActivity;
import com.example.fittracker.R;
import com.google.android.material.textfield.TextInputLayout;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.Objects;

public class LoginFragment extends Fragment {

    public static final String TAG = "LoginFragment";

    private TextInputLayout tiUsernameLogIn, tiPasswordLogIn;
    private TextView tvDoNotHaveAccount;
    private Button btnLogin;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        //Back pressed Logic for fragment
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Objects.requireNonNull(getActivity()).onBackPressed();
                        return true;
                    }
                }
                return false;
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tiUsernameLogIn = view.findViewById(R.id.tiUsernameLogIn);
        tiPasswordLogIn = view.findViewById(R.id.tiPasswordLogIn);
        tvDoNotHaveAccount = view.findViewById(R.id.tvDoNotHaveAccount);
        btnLogin = view.findViewById(R.id.btnLogin);

        String firstPart = "Don't have an account? ";
        String secondPart = "Sign up.";

        Spannable spannable = new SpannableString(firstPart);
        spannable.setSpan(new ForegroundColorSpan(0xFFd7d7d7), 0, firstPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDoNotHaveAccount.setText(spannable, TextView.BufferType.SPANNABLE);
        spannable = new SpannableString(secondPart);
        spannable.setSpan(new ForegroundColorSpan(0xFF043b6d), 0, secondPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvDoNotHaveAccount.append(spannable);

        tvDoNotHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.auth_container, new SignUpFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = Objects.requireNonNull(tiUsernameLogIn.getEditText()).getText().toString().trim();
                String password = Objects.requireNonNull(tiPasswordLogIn.getEditText()).getText().toString().trim();
                loginUser(username, password);
            }
        });
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to login user " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {

                    // TODO: better error handling
                    Log.e(TAG, "Issue with login", e);
                    return;
                } else {
                    // Navigate to the main activity if the user has signed in properly
                    goMainActivity();
                    Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
        Objects.requireNonNull(getActivity()).finish();
    }

}
