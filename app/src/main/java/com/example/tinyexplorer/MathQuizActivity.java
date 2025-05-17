package com.example.tinyexplorer;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MathQuizActivity extends AppCompatActivity {
    private int currentQuestion = 0;
    private final int TOTAL_QUESTIONS = 4;
    private long startTime;
    private TextView number1TextView;
    private TextView number2TextView;
    private TextView operatorTextView;
    private GridLayout optionsContainer;
    private String difficulty;
    private Handler handler = new Handler();
    private Random random = new Random();
    private int correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_quiz);

        startTime = System.currentTimeMillis();

        difficulty = getIntent().getStringExtra("DIFFICULTY");
        number1TextView = findViewById(R.id.number1);
        number2TextView = findViewById(R.id.number2);
        operatorTextView = findViewById(R.id.operator);
        optionsContainer = findViewById(R.id.optionsContainer);
        ImageButton backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());
        showQuestion();
    }

    private void showQuestion() {
        if (currentQuestion >= TOTAL_QUESTIONS) {
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime) / 1000; // Convert to seconds
            Intent intent = new Intent(this, NameInputActivity.class);
            intent.putExtra("QUIZ_TIME", duration);
            startActivity(intent);
            finish();
            return;
        }

        int num1, num2;
        String operator;
        
        switch (difficulty) {
            case "EASY":
                num1 = random.nextInt(10) + 1;
                num2 = random.nextInt(10) + 1;
                operator = "+";
                break;
            case "MEDIUM":
                operator = random.nextBoolean() ? "+" : "-";
                if (operator.equals("-")) {
                    num1 = random.nextInt(20) + 11; // 11-30
                    num2 = random.nextInt(10) + 1;  // 1-10
                } else {
                    num1 = random.nextInt(20) + 1;
                    num2 = random.nextInt(20) + 1;
                }
                break;
            default: // HARD
                String[] operators = {"+", "-", "×"};
                operator = operators[random.nextInt(operators.length)];
                
                if (operator.equals("×")) {
                    num1 = random.nextInt(10) + 1;
                    num2 = random.nextInt(10) + 1;
                } else if (operator.equals("-")) {
                    num1 = random.nextInt(20) + 11; // 11-30
                    num2 = random.nextInt(10) + 1;  // 1-10
                } else {
                    num1 = random.nextInt(30) + 1;
                    num2 = random.nextInt(30) + 1;
                }
                break;
        }

        switch (operator) {
            case "+":
                correctAnswer = num1 + num2;
                break;
            case "-":
                correctAnswer = num1 - num2;
                break;
            case "×":
                correctAnswer = num1 * num2;
                break;
            default:
                correctAnswer = num1 + num2;
        }

        number1TextView.setText(String.valueOf(num1));
        number2TextView.setText(String.valueOf(num2));
        operatorTextView.setText(operator);

        createOptions();
    }

    private void createOptions() {
        optionsContainer.removeAllViews();
        List<Integer> options = new ArrayList<>();
        options.add(correctAnswer);

        int numOptions;
        switch (difficulty) {
            case "EASY":
                numOptions = 2;
                break;
            case "MEDIUM":
                numOptions = 3;
                break;
            default:
                numOptions = 4;
                break;
        }

        while (options.size() < numOptions) {
            int maxOffset = Math.min(10, correctAnswer / 2);
            if (maxOffset < 1) maxOffset = 1;
            
            int offset = random.nextInt(maxOffset) + 1;
            int wrongAnswer;
            if (correctAnswer <= 5) {
                wrongAnswer = correctAnswer + offset;
            } else {
                wrongAnswer = correctAnswer + (random.nextBoolean() && (correctAnswer - offset) > 0 ? -offset : offset);
            }
            
            if (!options.contains(wrongAnswer) && wrongAnswer > 0) {
                options.add(wrongAnswer);
            }
        }

        Collections.shuffle(options);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.titanone_regular);
        for (int i = 0; i < options.size(); i++) {
            Button button = new Button(this);
            button.setText(String.valueOf(options.get(i)));
            button.setBackgroundColor(0xFF292D39);
            button.setTextColor(getColor(android.R.color.white));
            button.setTypeface(typeface);
            button.setTextSize(20);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 400;
            params.height = 150;
            params.setMargins(16, 16, 16, 16);

            if (i == options.size() - 1 && options.size() % 2 == 1) {
                params.columnSpec = GridLayout.spec(0, 2, GridLayout.CENTER);
            }

            button.setLayoutParams(params);

            final int answer = options.get(i);
            button.setOnClickListener(v -> {
                disableAllButtons();
                
                if (answer == correctAnswer) {
                    button.setBackgroundTintList(getColorStateList(R.color.green));
                    handler.postDelayed(() -> {
                        currentQuestion++;
                        showQuestion();
                    }, 1000);
                } else {
                    button.setBackgroundTintList(getColorStateList(R.color.red));
                    handler.postDelayed(() -> {
                        createOptions();
                    }, 1000);
                }
            });

            optionsContainer.addView(button);
        }
    }

    private void disableAllButtons() {
        for (int i = 0; i < optionsContainer.getChildCount(); i++) {
            View child = optionsContainer.getChildAt(i);
            if (child instanceof Button) {
                child.setEnabled(false);
            }
        }
    }
}
