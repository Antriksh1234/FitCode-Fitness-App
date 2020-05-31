package com.example.fitcode_fitterthanyesterday;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class MeFragment extends Fragment {

    class MyProfileAdapter extends ArrayAdapter{
        ArrayList title = new ArrayList<String>();
        ArrayList description = new ArrayList<String>();

        public MyProfileAdapter(Context context, ArrayList<String> titles, ArrayList<String> description){
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me,container,false);

        setHasOptionsMenu(true);

        ListView profileListView = view.findViewById(R.id.profileListView);

        TextView plan_Text = view.findViewById(R.id.plan_text);

        ArrayList<String> heading = new ArrayList<String>();
        heading.add("Plan ");
        heading.add("Plan days ");
        heading.add("My Height ");
        heading.add("My Weight ");
        ArrayList<String> descrip = new ArrayList<String>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.example.fitcode_fitterthanyesterday",Context.MODE_PRIVATE);

        boolean isBeginner = sharedPreferences.getBoolean("isBeginner",true);
        if (isBeginner) {
            plan_Text.setText("Beginner Plan");
            descrip.add("Beginner");
        }
        else {
            plan_Text.setText("Advanced Plan");
            descrip.add("Advanced");
        }
        int days = sharedPreferences.getInt("days",15);
        switch (days)
        {
            case 15:
                descrip.add("15 days");
                break;
            case 30:
                descrip.add("30 days");
                break;
            case 45:
                descrip.add("45 days");
                break;
            case 60:
                descrip.add("60 days");
                break;
            case 90:
                descrip.add("90 days");
                break;
        }


        descrip.add(Float.toString(sharedPreferences.getFloat("height",0)) + " cm");
        descrip.add(Float.toString(sharedPreferences.getFloat("weight",0)) + " kg");
        MyProfileAdapter adapter = new MyProfileAdapter(getActivity(),heading,descrip);

        profileListView.setAdapter(adapter);

        return view;

    }
}
