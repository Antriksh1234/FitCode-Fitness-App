package com.example.fitcode_fitterthanyesterday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FinishedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);
    }

    public void goHome(View view) {
        finish();
        Intent intent = new Intent(this,HomeScreenActivity.class);
        startActivity(intent);
    }
}
