package com.example.tinyexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button playButton = findViewById(R.id.playButton);
        Button rankingButton = findViewById(R.id.rankingButton);
        Button quitButton = findViewById(R.id.quitButton);

        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, AgeActivity.class);
            startActivity(intent);
        });

        rankingButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, RankingActivity.class);
            startActivity(intent);
        });

        quitButton.setOnClickListener(v -> finish());
    }
}
