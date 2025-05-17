package com.example.tinyexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        progressBar.setMax(100);

        // Start delayed to ensure layout is ready
        handler.postDelayed(() -> {
            // Set initial progress
            progressBar.setProgress(10);
            
            // Create smooth animation for progress bar
            ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 10, 100);
            animation.setDuration(4000);
            animation.setInterpolator(new LinearInterpolator());
            animation.start();

            handler.postDelayed(() -> {
                Intent intent = new Intent(LoadingActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }, 4000);
        }, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
