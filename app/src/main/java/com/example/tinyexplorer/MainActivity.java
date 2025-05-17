package com.example.tinyexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.tinyexplorer.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enable edge-to-edge layout using newer API
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        
        // Add button click listener
        findViewById(R.id.startButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoadingActivity.class);
            startActivity(intent);
        });
        
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        if (controller != null) {
            controller.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );
        }

        // Handle window insets using newer API
        View mainView = findViewById(R.id.main);
        mainView.setOnApplyWindowInsetsListener((view, windowInsets) -> {
            WindowInsetsCompat insetsCompat = WindowInsetsCompat.toWindowInsetsCompat(windowInsets);
            int left = insetsCompat.getSystemWindowInsetLeft();
            int top = insetsCompat.getSystemWindowInsetTop();
            int right = insetsCompat.getSystemWindowInsetRight();
            int bottom = insetsCompat.getSystemWindowInsetBottom();
            
            view.setPadding(left, top, right, bottom);
            return windowInsets;
        });
    }
}
