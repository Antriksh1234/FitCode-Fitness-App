package com.example.fitcode_fitterthanyesterday;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    static int day_number = 1;
    static Button button;
    static MyAdapter adapter;
    class MyAdapter extends ArrayAdapter{

        ArrayList<String> dayList = new ArrayList<>();
        ArrayList<String> doneList =  new ArrayList<>();

        MyAdapter(Context context,ArrayList<String> dayList,ArrayList<String> doneList)
        {
            super(context,R.layout.listview_custom,dayList);
            this.dayList = dayList;
            this.doneList = doneList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (position+1 == day_number)
            {
                View rowView = getLayoutInflater().inflate(R.layout.today_layout,null,true);
                TextView dayTextView = rowView.findViewById(R.id.today);
                ImageView imageView = rowView.findViewById(R.id.imageViewToday);

                dayTextView.setText(dayList.get(position));
                if (doneList.get(position).contentEquals("Pending"))
                    imageView.setImageResource(R.drawable.ic_lock_black_24dp);
                else
                    imageView.setImageResource(R.drawable.ic_check_black_24dp);

                return rowView;
            }
            else
            {
                View rowView = getLayoutInflater().inflate(R.layout.listview_custom,null,true);
                TextView dayTextView = rowView.findViewById(R.id.dayTextView);
                ImageView imageView = rowView.findViewById(R.id.statusImageView);

                dayTextView.setText(dayList.get(position));

                if (doneList.get(position).contentEquals("No"))
                    imageView.setImageResource(R.drawable.ic_clear_black_24dp);
                else if (doneList.get(position).contentEquals("Yes"))
                    imageView.setImageResource(R.drawable.ic_check_black_24dp);
                else
                    imageView.setImageResource(R.drawable.ic_lock_black_24dp);
                return rowView;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        ListView listView = view.findViewById(R.id.listView);

        button = view.findViewById(R.id.button);

        setHasOptionsMenu(true);

        SharedPreferences sp = getActivity().getSharedPreferences("com.example.fitcode_fitterthanyesterday", Context.MODE_PRIVATE);

        if (sp.getBoolean("doneToday",false))
        {
            button.setEnabled(false);
        }

        ArrayList<String> list = new ArrayList<String>();

        int no_of_days = sp.getInt("days",15);

        list.clear();

        for (int i = 1; i <= no_of_days; i++)
        {
            list.add("Day " + i);
        }

        day_number = sp.getInt("day_number",1);

        SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase("exercise",Context.MODE_PRIVATE,null);

        Calendar calendar = Calendar.getInstance();

        Calendar previousOpenCalendar = Calendar.getInstance();

        int date = sp.getInt("Date",0);
        int month = sp.getInt("Month",0);
        int year = sp.getInt("Year",0);

        previousOpenCalendar.set(Calendar.DATE,date);
        previousOpenCalendar.set(Calendar.MONTH,month);
        previousOpenCalendar.set(Calendar.YEAR,year);

        ArrayList<String> doneList = new ArrayList<>();

        doneList.clear();

        Cursor c =  sqLiteDatabase.rawQuery("SELECT * FROM exerciseTable",null);

        int doneIndex = c.getColumnIndex("done");

        c.moveToFirst();

        while (!c.isAfterLast())
        {
            doneList.add(c.getString(doneIndex));
            c.moveToNext();
        }

        while (calendar.after(previousOpenCalendar))
        {
            previousOpenCalendar.add(Calendar.DATE,1);
            if (doneList.get(day_number-1).contentEquals("Pending"))
                sqLiteDatabase.execSQL("UPDATE exerciseTable set done = 'No' WHERE day_id = "+day_number+"");
            day_number++;
        }

        if (doneList.get(day_number-1).contentEquals("Yes"))
        {
             button.setEnabled(false);
        }

        sp.edit().putInt("Date",calendar.get(Calendar.DATE)).apply();
        sp.edit().putInt("Month",calendar.get(Calendar.MONTH)).apply();
        sp.edit().putInt("Year",calendar.get(Calendar.YEAR)).apply();

        sp.edit().putInt("day_number",day_number).apply();

        doneList.clear();

        c =  sqLiteDatabase.rawQuery("SELECT * FROM exerciseTable",null);

        doneIndex = c.getColumnIndex("done");

        c.moveToFirst();

        while (!c.isAfterLast())
        {
            doneList.add(c.getString(doneIndex));
            c.moveToNext();
        }
        adapter= new MyAdapter(getActivity(),list,doneList);
        listView.setAdapter(adapter);
        c.close();
        return view;
    }

}
