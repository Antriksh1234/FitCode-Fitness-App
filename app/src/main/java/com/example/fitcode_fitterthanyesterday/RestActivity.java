package com.example.fitcode_fitterthanyesterday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;
import android.widget.TextView;

public class RestActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        //Don't allow to go back on pressing back button
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        final TextView break_timer_text = findViewById(R.id.break_timer);

        CountDownTimer timer = new CountDownTimer(40000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timer_text = "";
                int seconds = (int) millisUntilFinished / 1000;

                if (seconds < 10)
                    timer_text = "0:0"+seconds;
                else
                    timer_text = "0:" + seconds;

                break_timer_text.setText(timer_text);
            }

            @Override
            public void onFinish() {
                break_timer_text.setText("0:00");
                finish();
                Intent intent = new Intent(RestActivity.this,ExerciseActivity.class);
                startActivity(intent);
            }
        }.start();
    }
}
