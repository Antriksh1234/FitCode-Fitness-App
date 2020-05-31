package com.example.fitcode_fitterthanyesterday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class PlanActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    RadioGroup modeGroup;
    RadioGroup dayGroup;
    static boolean passedToHomeScreen = false;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        passedToHomeScreen = true;
        Intent intent = new Intent(this,HomeScreenActivity.class);
        HomeScreenActivity.fragment = new MeFragment();
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        modeGroup = findViewById(R.id.moderadiogroup);
        dayGroup = findViewById(R.id.dayradiogroup);

        sharedPreferences = getSharedPreferences("com.example.fitcode_fitterthanyesterday",MODE_PRIVATE);
    }

    public void EditPlan(View view) {
        //Editing mode plan
        if (modeGroup.getCheckedRadioButtonId() == R.id.beginner)
            sharedPreferences.edit().putBoolean("isBeginner",true).apply();
        else
            sharedPreferences.edit().putBoolean("isBeginner",false).apply();

        //Editing day plan
        if (dayGroup.getCheckedRadioButtonId() == R.id.duration_fifteen_days)
            sharedPreferences.edit().putInt("days",15).apply();
        else if (dayGroup.getCheckedRadioButtonId() == R.id.duration_thirty_days)
            sharedPreferences.edit().putInt("days",30).apply();
        else if (dayGroup.getCheckedRadioButtonId() == R.id.duration_fortyfive_days)
            sharedPreferences.edit().putInt("days",45).apply();
        else if (dayGroup.getCheckedRadioButtonId() == R.id.duration_sixty_days)
            sharedPreferences.edit().putInt("days",60).apply();
        else if (dayGroup.getCheckedRadioButtonId() == R.id.duration_ninety_days)
            sharedPreferences.edit().putInt("days",90).apply();

        int days = sharedPreferences.getInt("days",15);
        SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("exercise",MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("DELETE FROM exerciseTable");
        for (int i = 1; i <= days; i++)
        {
            sqLiteDatabase.execSQL("INSERT INTO exerciseTable(done) VALUES ('Pending')");
        }

        passedToHomeScreen = !sharedPreferences.getBoolean("firstTime", false);

        sharedPreferences.edit().putBoolean("firstTime",false).apply();
        Calendar calendar = Calendar.getInstance();
        int previousDay = calendar.get(Calendar.DATE);
        int previousMonth = calendar.get(Calendar.MONTH);
        int previousYear = calendar.get(Calendar.YEAR);

        sharedPreferences.edit().putInt("Date",previousDay).apply();
        sharedPreferences.edit().putInt("Month",previousMonth).apply();
        sharedPreferences.edit().putInt("Year",previousYear).apply();

        sharedPreferences.edit().putInt("day_number",1).apply();
        Toast.makeText(this, "Fitness regime changed", Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent(this,HomeScreenActivity.class);
        startActivity(intent);
    }
}
