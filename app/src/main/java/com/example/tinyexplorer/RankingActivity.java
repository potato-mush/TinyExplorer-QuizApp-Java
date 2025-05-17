package com.example.tinyexplorer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RankingActivity extends AppCompatActivity {
    private LinearLayout namesColumn;
    private LinearLayout timesColumn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        namesColumn = findViewById(R.id.namesColumn);
        timesColumn = findViewById(R.id.timesColumn);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        });

        // Save new score if provided
        String playerName = getIntent().getStringExtra("PLAYER_NAME");
        long quizTime = getIntent().getLongExtra("QUIZ_TIME", -1);
        if (playerName != null && quizTime != -1) {
            saveScore(playerName, quizTime);
        }

        displayRankings();
    }

    private void saveScore(String name, long time) {
        SharedPreferences prefs = getSharedPreferences("Rankings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(name, time);
        editor.apply();
    }

    private void displayRankings() {
        SharedPreferences prefs = getSharedPreferences("Rankings", MODE_PRIVATE);
        Map<String, Long> scores = new TreeMap<>();
        
        for (Map.Entry<String, ?> entry : prefs.getAll().entrySet()) {
            scores.put(entry.getKey(), (Long) entry.getValue());
        }

        List<Map.Entry<String, Long>> sortedScores = new ArrayList<>(scores.entrySet());
        Collections.sort(sortedScores, Map.Entry.comparingByValue());

        // Display rankings (either real scores or placeholders)
        for (int i = 0; i < 10; i++) {
            if (i < sortedScores.size()) {
                Map.Entry<String, Long> entry = sortedScores.get(i);
                TextView nameView = createRankingTextView(String.format("%d. %s", i + 1, entry.getKey()));
                TextView timeView = createRankingTextView(formatTime(entry.getValue()));
                namesColumn.addView(nameView);
                timesColumn.addView(timeView);
            } else {
                // Add placeholder for empty slots
                TextView nameView = createRankingTextView(String.format("%d. %s", i + 1, "-"));
                TextView timeView = createRankingTextView("-");
                namesColumn.addView(nameView);
                timesColumn.addView(timeView);
            }
        }
    }

    private TextView createRankingTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        textView.setTextSize(18);
        textView.setPadding(8, 12, 8, 12);
        textView.setTypeface(getResources().getFont(R.font.titanone_regular));
        textView.setGravity(android.view.Gravity.CENTER);
        return textView;
    }

    private String formatTime(long seconds) {
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }
}
