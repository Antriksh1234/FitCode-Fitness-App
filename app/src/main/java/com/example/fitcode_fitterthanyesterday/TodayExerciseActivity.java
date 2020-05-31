package com.example.fitcode_fitterthanyesterday;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TodayExerciseActivity extends AppCompatActivity {

   static ArrayList<String> exerciseList = new ArrayList<>();
   static ArrayList<String> durationList = new ArrayList<>();
   static int no_of_exercise_done_today = 0;

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this,HomeScreenActivity.class);
        startActivity(intent);
    }

    public void startExercise(View view) {
        finish();
        Intent intent = new Intent(this,ExerciseActivity.class);
        startActivity(intent);
    }

    class MyExerciseAdapter extends ArrayAdapter{

        ArrayList<String> exerciseName;
        ArrayList<String> durationList;

        MyExerciseAdapter(Context context, ArrayList<String> exerciseName, ArrayList<String> durationList) {
            super(context,R.layout.exercise_listview,exerciseName);
            this.exerciseName = exerciseName;
            this.durationList = durationList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View rowView = getLayoutInflater().inflate(R.layout.exercise_listview,null,true);

            TextView exerciseTitle = rowView.findViewById(R.id.exerciseTitle);
            TextView durationTitle = rowView.findViewById(R.id.duration);

            exerciseTitle.setText(exerciseName.get(position));
            durationTitle.setText(durationList.get(position));
            return rowView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_exercise);

        ListView listView = findViewById(R.id.exericise_list);

        exerciseList = new ArrayList<>();
        durationList = new ArrayList<>();

        HashMap<String,String> hashMap = new HashMap<String,String>();

        hashMap.put("Push Ups Beginner","1:00 Minute");
        hashMap.put("Pull Ups Beginner","1:00 Minute");
        hashMap.put("Cobra Stretch Beginner","2:00 Minutes");
        hashMap.put("Crunches Beginner","2:00 Minutes");
        hashMap.put("Plank Beginner","1:00 Minute");
        hashMap.put("Lunges Beginner","2:00 Minutes");
        hashMap.put("Side Plank Beginner","2:00 Minutes");
        hashMap.put("Squats Beginner","1:00 Minute");
        hashMap.put("Skipping Up Beginner","2:00 Minutes");

        hashMap.put("Push Ups Advanced","2:00 Minute");
        hashMap.put("Pull Ups Advanced","2:00 Minute");
        hashMap.put("Cobra Stretch Advanced","3:00 Minutes");
        hashMap.put("Crunches Advanced","3:00 Minutes");
        hashMap.put("Plank Advanced","2:00 Minute");
        hashMap.put("Lunges Advanced","3:00 Minutes");
        hashMap.put("Side Plank Advanced","3:00 Minutes");
        hashMap.put("Squats Advanced","2:00 Minute");
        hashMap.put("Skipping Up Advanced","3:00 Minutes");

        ArrayList<Integer> exercise_no = new ArrayList<>();
        int random_number;
        while (exercise_no.size() <= 5)
        {
            random_number = new Random().nextInt(9) + 1;
            if (!exercise_no.contains(random_number))
            {
                exercise_no.add(random_number);
            }
        }

        for (int i = 1; i <= 5; i++ )
        {
            switch (exercise_no.get(i))
            {
                case 1:
                    exerciseList.add("Push Ups");
                    break;
                case 2:
                    exerciseList.add("Pull Ups");
                    break;
                case 3:
                    exerciseList.add("Cobra Stretch");
                    break;
                case 4:
                    exerciseList.add("Crunches");
                    break;
                case 5:
                    exerciseList.add("Plank");
                    break;
                case 6:
                    exerciseList.add("Lunges");
                    break;
                case 7:
                    exerciseList.add("Side Plank");
                    break;
                case 8:
                    exerciseList.add("Squats");
                    break;
                case 9:
                    exerciseList.add("Skipping");
                    break;
            }
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.fitcode_fitterthanyesterday",MODE_PRIVATE);
        boolean isBeginner = sharedPreferences.getBoolean("isBeginner",true);

        if (isBeginner)
        {
            for (int i = 1; i <= 5; i++)
            {
                switch (exercise_no.get(i))
                {
                    case 1:
                        durationList.add(hashMap.get("Push Ups Beginner"));
                        break;
                    case 2:
                        durationList.add(hashMap.get("Pull Ups Beginner"));
                        break;
                    case 3:
                        durationList.add(hashMap.get("Cobra Stretch Beginner"));
                        break;
                    case 4:
                        durationList.add(hashMap.get("Crunches Beginner"));
                        break;
                    case 5:
                        durationList.add(hashMap.get("Plank Beginner"));
                        break;
                    case 6:
                        durationList.add(hashMap.get("Lunges Beginner"));
                        break;
                    case 7:
                        durationList.add(hashMap.get("Side Plank Beginner"));
                        break;
                    case 8:
                        durationList.add(hashMap.get("Squats Beginner"));
                        break;
                    case 9:
                        durationList.add(hashMap.get("Skipping Up Beginner"));
                        break;
                }
            }
        }
        else
        {
            for (int i = 1; i <= 5; i++)
            {
                switch (exercise_no.get(i))
                {
                    case 1:
                        durationList.add(hashMap.get("Push Ups Advanced"));
                        break;
                    case 2:
                        durationList.add(hashMap.get("Pull Ups Advanced"));
                        break;
                    case 3:
                        durationList.add(hashMap.get("Cobra Stretch Advanced"));
                        break;
                    case 4:
                        durationList.add(hashMap.get("Crunches Advanced"));
                        break;
                    case 5:
                        durationList.add(hashMap.get("Plank Advanced"));
                        break;
                    case 6:
                        durationList.add(hashMap.get("Lunges Advanced"));
                        break;
                    case 7:
                        durationList.add(hashMap.get("Side Plank Advanced"));
                        break;
                    case 8:
                        durationList.add(hashMap.get("Squats Advanced"));
                        break;
                    case 9:
                        durationList.add(hashMap.get("Skipping Up Advanced"));
                        break;
                }
            }
        }
     /*   exerciseList.add("Push Ups");
        exerciseList.add("Pull Ups");
        exerciseList.add("Cobra stretch");
        exerciseList.add("Crunches");
        exerciseList.add("Plank");

        durationList.add("2:00 Minutes");
        durationList.add("1:00 Minutes");
        durationList.add("3:00 Minutes");
        durationList.add("4:00 Minutes");
        durationList.add("1:00 Minutes");*/

        MyExerciseAdapter adapter = new MyExerciseAdapter(this,exerciseList,durationList);

        listView.setAdapter(adapter);
    }
}
