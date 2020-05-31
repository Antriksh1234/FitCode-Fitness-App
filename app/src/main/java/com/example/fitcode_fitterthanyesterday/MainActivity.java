package com.example.fitcode_fitterthanyesterday;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView headerTextView = findViewById(R.id.appheading);
        TextView subheading = findViewById(R.id.subheading);

        headerTextView.setAlpha(0);
        subheading.setAlpha(0);

        headerTextView.setTranslationY(150F);
        subheading.setTranslationY(150F);

        headerTextView.animate().alpha(1F).translationYBy(-150F).setDuration(700);
        subheading.animate().alpha(1F).translationYBy(-150F).setDuration(700);

        SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("exercise",MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS exerciseTable(day_id INTEGER PRIMARY KEY ,done VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS bmi(month VARCHAR, year INTEGER , bmi VARCHAR)");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("com.example.fitcode_fitterthanyesterday",MODE_PRIVATE);
                boolean firstTime = sharedPreferences.getBoolean("firstTime",true);
                finish();
                Intent intent;
                if (firstTime)
                {
                    intent = new Intent(MainActivity.this,BodyActivity.class);

                    Calendar calendar = Calendar.getInstance();
                    int previousDay = calendar.get(Calendar.DATE);
                    int previousMonth = calendar.get(Calendar.MONTH);
                    int previousYear = calendar.get(Calendar.YEAR);

                    sharedPreferences.edit().putInt("Date",previousDay).apply();
                    sharedPreferences.edit().putInt("Month",previousMonth).apply();
                    sharedPreferences.edit().putInt("Year",previousYear).apply();

                }
                else
                {
                   intent = new Intent(MainActivity.this,HomeScreenActivity.class);
                }
                startActivity(intent);
            }
        },3000);

    }
}
