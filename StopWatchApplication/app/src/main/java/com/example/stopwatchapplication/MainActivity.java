package com.example.stopwatchapplication;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvStopwatch;
    private Button btnStart, btnStop, btnHold;

    private boolean isRunning = false;
    private boolean isHold = false;
    private int seconds = 0;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                seconds++;
                updateStopwatchText();
                playCountdownSound();
            }
            handler.postDelayed(this, 1000);
        }
    };

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStopwatch = findViewById(R.id.tvStopwatch);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnHold = findViewById(R.id.btnHold);

        mediaPlayer = MediaPlayer.create(this, R.raw.countdown_sound);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopwatch();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStopwatch();
            }
        });

        btnHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holdStopwatch();
            }
        });
    }

    private void startStopwatch() {
        isRunning = true;
        handler.postDelayed(runnable, 0);
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorRunning));
    }

    private void stopStopwatch() {
        isRunning = false;
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorStopped));
    }

    private void holdStopwatch() {
        isHold = !isHold;
        if (isHold) {
            handler.removeCallbacks(runnable);
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorHold));
        } else {
            handler.postDelayed(runnable, 0);
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorRunning));
        }
    }

    private void updateStopwatchText() {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        String timeString = String.format("%02d:%02d:%02d", hours, minutes, secs);
        tvStopwatch.setText(timeString);
    }

    private void playCountdownSound() {
        if (seconds >= 1 && seconds <= 3) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
