package com.example.compassapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // nav bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        // default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CompassFragment()).commit();
    }

    // nav listener
    private final BottomNavigationView.OnItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;

        int itemId = item.getItemId();
        if (itemId == R.id.navigation_compass) {
            selectedFragment = new CompassFragment();
        } else if (itemId == R.id.navigation_light) {
            selectedFragment = new LightFragment();
        }

        // switch fragment
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
        return false;
    };
}
