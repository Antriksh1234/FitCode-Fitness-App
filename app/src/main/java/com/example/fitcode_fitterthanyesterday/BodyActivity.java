package com.example.fitcode_fitterthanyesterday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class BodyActivity extends AppCompatActivity {

    EditText heightText,weightText;
    static boolean passedToHomeScreen;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        passedToHomeScreen = true;
        Intent intent = new Intent(this,HomeScreenActivity.class);
        HomeScreenActivity.fragment = new ReportFragment();
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);

        weightText = findViewById(R.id.editText2);
        heightText = findViewById(R.id.editText3);

    }

    public void SaveBodyDim(View view) {

        SharedPreferences sp = getSharedPreferences("com.example.fitcode_fitterthanyesterday",MODE_PRIVATE);
        boolean firstTime = sp.getBoolean("firstTime",true);

        if (weightText.getText().toString().length() > 0 && heightText.getText().toString().length() > 0 && heightText.getText().toString().charAt(heightText.getText().toString().length()-1) != '.' && weightText.getText().toString().charAt(weightText.getText().toString().length()-1) != '.') {
            if (firstTime) {
                float weight = Float.parseFloat(weightText.getText().toString());
                float height = Float.parseFloat(heightText.getText().toString());
                sp.edit().putFloat("height", height).apply();
                sp.edit().putFloat("weight", weight).apply();
                sp.edit().putFloat("minweight", weight).apply();
                sp.edit().putFloat("maxweight", weight).apply();
                finish();
                Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, PlanActivity.class);
                startActivity(intent);
            } else {
                float weight = Float.parseFloat(weightText.getText().toString());
                float height = Float.parseFloat(heightText.getText().toString());
                sp.edit().putFloat("height", height).apply();
                sp.edit().putFloat("weight", weight).apply();
                float maxWeight = sp.getFloat("maxweight",0);
                float minWeight = sp.getFloat("minweight",0);

                SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("exercise",MODE_PRIVATE,null);

                Calendar calendar = Calendar.getInstance();

                int month = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);
                float bmi = weight / ((height/100F) * (height/100F));
                String bmiString = Float.toString(bmi);
                String monthString = "";
                switch (month)
                {
                    case 1:
                        monthString = "January";
                        break;
                    case 2:
                        monthString = "February";
                        break;
                    case 3:
                        monthString = "March";
                        break;
                    case 4:
                        monthString = "April";
                        break;
                    case 5:
                        monthString = "May";
                        break;
                    case 6:
                        monthString = "June";
                        break;
                    case 7:
                        monthString = "July";
                        break;
                    case 8:
                        monthString = "August";
                        break;
                    case 9:
                        monthString = "September";
                        break;
                    case 10:
                        monthString = "October";
                        break;
                    case 11:
                        monthString = "November";
                        break;
                    case 12:
                        monthString = "December";
                        break;
                }
                sqLiteDatabase.execSQL("INSERT INTO bmi (month,year,bmi) VALUES ('"+monthString+"',"+year+",'"+bmiString+"')");

                //Updating max weight and  min weight values
                if (minWeight > weight)
                    sp.edit().putFloat("minweight", weight).apply();
                else if (maxWeight < weight)
                    sp.edit().putFloat("maxweight", weight).apply();
                finish();
                passedToHomeScreen = true;
                Intent intent = new Intent(this,HomeScreenActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Height weight updated", Toast.LENGTH_SHORT).show();
            }
        }
        else
            Toast.makeText(this, "Please enter complete details", Toast.LENGTH_SHORT).show();
    }
}
