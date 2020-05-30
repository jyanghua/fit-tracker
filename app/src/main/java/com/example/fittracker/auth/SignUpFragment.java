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
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Objects;

public class SignUpFragment extends Fragment {

    public static final String TAG = "SignUpFragment";

    private TextInputLayout tiUsernameSignUp, tiPasswordSignUp;
    private TextView tvAlreadyHaveAccount;
    private Button btnCreateNewAccountSignUp;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);

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

        tiUsernameSignUp = view.findViewById(R.id.tiUsernameSignUp);
        tiPasswordSignUp = view.findViewById(R.id.tiPasswordSignUp);
        tvAlreadyHaveAccount = view.findViewById(R.id.tvAlreadyHaveAccount);
        btnCreateNewAccountSignUp = view.findViewById(R.id.btnCreateNewAccountSignUp);

        String firstPart = "Already have an account? ";
        String secondPart = "Log in.";

        Spannable spannable = new SpannableString(firstPart);
        spannable.setSpan(new ForegroundColorSpan(0xFFd7d7d7), 0, firstPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvAlreadyHaveAccount.setText(spannable, TextView.BufferType.SPANNABLE);
        spannable = new SpannableString(secondPart);
        spannable.setSpan(new ForegroundColorSpan(0xFF043b6d), 0, secondPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvAlreadyHaveAccount.append(spannable);

        tvAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.auth_container, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnCreateNewAccountSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick create account button");
                String username = Objects.requireNonNull(tiUsernameSignUp.getEditText()).getText().toString().trim();
                String password = Objects.requireNonNull(tiPasswordSignUp.getEditText()).getText().toString().trim();
                createUser(username, password);
            }
        });
    }

    private void createUser(String username, String password) {
        Log.i(TAG, "Attempting to create user " + username);

        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {

                    // TODO: better error handling
                    Log.e(TAG, "Issue with creating user", e);
                    return;
                } else {
                    // Navigate to the main activity if the user has signed up properly
                    goMainActivity();
                    Toast.makeText(getContext(), "Sign up successful!", Toast.LENGTH_SHORT);
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
