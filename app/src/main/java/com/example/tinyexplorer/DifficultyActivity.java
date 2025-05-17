package com.example.tinyexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Button;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class DifficultyActivity extends AppCompatActivity {
    private static final String TAG = "DifficultyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        ImageButton backButton = findViewById(R.id.backButton);
        Button easyButton = findViewById(R.id.easyButton);
        Button mediumButton = findViewById(R.id.mediumButton);
        Button hardButton = findViewById(R.id.hardButton);

        backButton.setOnClickListener(v -> finish());

        easyButton.setOnClickListener(v -> {
            Log.d(TAG, "Easy button clicked");
            Intent intent = new Intent(DifficultyActivity.this, CategoryActivity.class);
            intent.putExtra("DIFFICULTY", "EASY");
            startActivity(intent);
        });

        mediumButton.setOnClickListener(v -> {
            Log.d(TAG, "Medium button clicked");
            Intent intent = new Intent(DifficultyActivity.this, CategoryActivity.class);
            intent.putExtra("DIFFICULTY", "MEDIUM");
            startActivity(intent);
        });

        hardButton.setOnClickListener(v -> {
            Log.d(TAG, "Hard button clicked");
            Intent intent = new Intent(DifficultyActivity.this, CategoryActivity.class);
            intent.putExtra("DIFFICULTY", "HARD");
            startActivity(intent);
        });
    }
}
