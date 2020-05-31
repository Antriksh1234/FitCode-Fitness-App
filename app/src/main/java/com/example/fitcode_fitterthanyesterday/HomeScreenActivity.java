package com.example.fitcode_fitterthanyesterday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

public class HomeScreenActivity extends AppCompatActivity {

    static Fragment fragment;
    ArrayList no_of_days;

    public void changeWeightHeight(View view)
    {
        finish();
        Intent intent = new Intent(this,BodyActivity.class);
        startActivity(intent);
    }

    public void bmiHistory(View view)
    {
        Intent intent = new Intent(this,BmiHistory.class);
        startActivity(intent);
    }

    public void startWorkout(View view) {
        finish();
        Intent intent = new Intent(this,TodayExerciseActivity.class);
        startActivity(intent);
    }

    public void changePlan(View view) {
        finish();
        Intent intent = new Intent(this,PlanActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contactmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        Intent intent = new Intent(this,ContactDevelopers.class);
        startActivity(intent);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        androidx.appcompat.widget.Toolbar myToolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigate);

        navigationView.setOnNavigationItemSelectedListener(navListener);

        if (BodyActivity.passedToHomeScreen) {
            fragment = new ReportFragment();
            BodyActivity.passedToHomeScreen = false;
        }
        else if (PlanActivity.passedToHomeScreen) {
            fragment = new MeFragment();
            PlanActivity.passedToHomeScreen = false;
        }
        else
            fragment = new HomeFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId())
            {
                case R.id.home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.You:
                    selectedFragment = new MeFragment();
                    break;
                case R.id.Reports:
                    selectedFragment = new ReportFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }
    };

}
