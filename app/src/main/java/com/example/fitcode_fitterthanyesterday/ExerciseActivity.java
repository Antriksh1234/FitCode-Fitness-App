package com.example.fitcode_fitterthanyesterday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ExerciseActivity extends AppCompatActivity {

    TextView exercise_name,timer_text;
    ImageView exercise_photo;
    MediaPlayer mediaPlayer;
    long duration = 60000;

    @Override
    public void onBackPressed() {
        //Don't allow to go back on pressing back button
    }

    public void startCountDownTimer(long duration)
    {

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.music);
        CountDownTimer timer = new CountDownTimer(duration,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String text_of_timer = "";
                int minutes = (int) millisUntilFinished /60000;
                int seconds = (int) (millisUntilFinished %60000)/1000;

                AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);

                mediaPlayer.start();
                String secondString  = "";

                if (seconds < 10)
                {
                    secondString = "0" + seconds;
                }
                else
                    secondString = Integer.toString(seconds);

                text_of_timer = minutes +":"+ secondString;

                timer_text.setText(text_of_timer);
            }

            @Override
            public void onFinish() {
                timer_text.setText("0:00");
                TodayExerciseActivity.no_of_exercise_done_today++;
                finish();
                Intent intent;
                mediaPlayer.pause();
                if (TodayExerciseActivity.no_of_exercise_done_today == 5)
                {
                    SQLiteDatabase sqLiteDatabase  = openOrCreateDatabase("exercise",MODE_PRIVATE,null);
                    SharedPreferences sp = getSharedPreferences("com.example.fitcode_fitterthanyesterday",MODE_PRIVATE);
                    int day_number = sp.getInt("day_number",1);
                    sqLiteDatabase.execSQL("UPDATE exerciseTable set done = 'Yes' WHERE day_id = "+day_number+"");
                    intent = new Intent(ExerciseActivity.this,FinishedActivity.class);
                }
                else
                {
                    intent = new Intent(ExerciseActivity.this,RestActivity.class);
                }
                startActivity(intent);
            }
        }.start();
    }

    public void startTimer(View view)
    {
        Button go_button = (Button) view;
        go_button.setEnabled(false);
        startCountDownTimer(duration);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        exercise_name = findViewById(R.id.exercise_name);
        timer_text = findViewById(R.id.timer_text);
        exercise_photo = findViewById(R.id.exercise_photo);
        Button go_button = findViewById(R.id.button4);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        exercise_name.setText(TodayExerciseActivity.exerciseList.get(TodayExerciseActivity.no_of_exercise_done_today));

        timer_text.setText(TodayExerciseActivity.durationList.get(TodayExerciseActivity.no_of_exercise_done_today));

        switch (TodayExerciseActivity.durationList.get(TodayExerciseActivity.no_of_exercise_done_today)) {
            case "1:00 Minute":
                duration = 60000;
                break;
            case "2:00 Minutes":
                duration = 120000;
                break;
            case "3:00 Minutes":
                duration = 180000;
                break;
        }

        if (TodayExerciseActivity.no_of_exercise_done_today >= 1)
        {
            go_button.setVisibility(View.INVISIBLE);
            startCountDownTimer(duration);
        }

        String exercise = TodayExerciseActivity.exerciseList.get(TodayExerciseActivity.no_of_exercise_done_today);

        switch (exercise)
        {
            case "Push Ups":
                exercise_photo.setImageResource(R.drawable.pushups);
                break;
            case "Pull Ups":
                exercise_photo.setImageResource(R.drawable.pullups);
                break;
            case "Cobra Stretch":
                exercise_photo.setImageResource(R.drawable.cobra);
                break;
            case "Crunches":
                exercise_photo.setImageResource(R.drawable.crunches);
                break;
            case "Plank":
                exercise_photo.setImageResource(R.drawable.plank);
                break;
            case "Lunges":
                exercise_photo.setImageResource(R.drawable.lunges);
                break;
            case "Side Plank":
                exercise_photo.setImageResource(R.drawable.sideplank);
                break;
            case "Squats":
                exercise_photo.setImageResource(R.drawable.squats);
                break;
            case "Skipping":
                exercise_photo.setImageResource(R.drawable.skipping);
                break;
        }
    }
}
