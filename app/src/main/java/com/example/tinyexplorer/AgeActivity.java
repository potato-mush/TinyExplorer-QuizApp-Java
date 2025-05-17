package com.example.tinyexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class AgeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);

        ImageButton backButton = findViewById(R.id.backButton);
        Button age4Button = findViewById(R.id.age4Button);
        Button age5Button = findViewById(R.id.age5Button);
        Button age6Button = findViewById(R.id.age6Button);
        Button age7Button = findViewById(R.id.age7Button);

        backButton.setOnClickListener(v -> finish());

        age4Button.setOnClickListener(v -> launchDifficultyScreen("4"));
        age5Button.setOnClickListener(v -> launchDifficultyScreen("5"));
        age6Button.setOnClickListener(v -> launchDifficultyScreen("6"));
        age7Button.setOnClickListener(v -> launchDifficultyScreen("7"));
    }

    private void launchDifficultyScreen(String age) {
        Intent intent = new Intent(this, DifficultyActivity.class);
        intent.putExtra("AGE", age);
        startActivity(intent);
    }
}
