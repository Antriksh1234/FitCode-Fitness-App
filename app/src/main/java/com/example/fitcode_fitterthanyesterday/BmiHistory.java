package com.example.fitcode_fitterthanyesterday;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BmiHistory extends AppCompatActivity {

    ListView bmiListView;

    class MyBMIAdapter extends ArrayAdapter {
        ArrayList title = new ArrayList<String>();
        ArrayList description = new ArrayList<String>();

        public MyBMIAdapter(Context context, ArrayList<String> titles, ArrayList<String> description){
            super(context,R.layout.profile_listview,titles);
            title= titles;
            this.description = description;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View rowView = getLayoutInflater().inflate(R.layout.profile_listview,null,true);

            TextView titleText = rowView.findViewById(R.id.title);
            TextView descriptionText = rowView.findViewById(R.id.description);

            titleText.setText(title.get(position).toString());
            descriptionText.setText(description.get(position).toString());

            return rowView;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_history);

        bmiListView = findViewById(R.id.bmiListView);

        ArrayList<String> timePeriod = new ArrayList<>();
        ArrayList<String> bmi = new ArrayList<>();

        SQLiteDatabase database = openOrCreateDatabase("exercise",MODE_PRIVATE,null);

        Cursor c = database.rawQuery("SELECT * FROM bmi",null);

        int monthIndex = c.getColumnIndex("month");
        int yearIndex = c.getColumnIndex("year");
        int bmiIndex = c.getColumnIndex("bmi");

        c.moveToFirst();

        while (!c.isAfterLast())
        {
            String month = c.getString(monthIndex);
            int year = c.getInt(yearIndex);
            String bmiString = c.getString(bmiIndex);
            //Setting the timePeriod text and adding it
            timePeriod.add(month + " " + year);
            //Adding BMI of that period to ArrayList BMI
            bmi.add(bmiString);
            c.moveToNext();
        }

        MyBMIAdapter adapter = new MyBMIAdapter(this,timePeriod,bmi);

        bmiListView.setAdapter(adapter);
    }
}
