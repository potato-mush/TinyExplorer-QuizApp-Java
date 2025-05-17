package com.example.tinyexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class NameInputActivity extends AppCompatActivity {
    private EditText nameInput;
    private Button confirmButton;
    private long quizTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_input);

        quizTime = getIntent().getLongExtra("QUIZ_TIME", 0);

        nameInput = findViewById(R.id.nameInput);
        confirmButton = findViewById(R.id.confirmButton);

        // Set maximum length filter
        nameInput.setFilters(new InputFilter[] {
            new InputFilter.LengthFilter(8)
        });

        confirmButton.setOnClickListener(v -> {
            String playerName = nameInput.getText().toString().trim();
            if (!playerName.isEmpty()) {
                Intent intent = new Intent(this, RankingActivity.class);
                intent.putExtra("PLAYER_NAME", playerName);
                intent.putExtra("QUIZ_TIME", quizTime);
                startActivity(intent);
                finish();
            }
        });
    }
}
