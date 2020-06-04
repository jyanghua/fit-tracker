package com.example.fittracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.fittracker.fragments.AddWorkoutFragment;
import com.example.fittracker.fragments.HistoryFragment;
import com.example.fittracker.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,
                new ProfileFragment()).commit();
    }

    @NotNull
    public FragmentManager getSupportFragmentManager () {
        return super.getSupportFragmentManager();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    String tag = "";

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new ProfileFragment();
                            tag = ProfileFragment.TAG;
                            break;
                        case R.id.nav_new_post:
                            selectedFragment = new AddWorkoutFragment(getParent());
                            tag = AddWorkoutFragment.TAG;
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new HistoryFragment();
                            tag = HistoryFragment.TAG;
                            break;
                    }
                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,
                            selectedFragment, tag).commit();

                    return true;
                }
            };
}