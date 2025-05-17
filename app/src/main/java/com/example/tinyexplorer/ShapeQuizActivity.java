package com.example.tinyexplorer;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShapeQuizActivity extends AppCompatActivity {
    private int currentQuestion = 0;
    private final int TOTAL_QUESTIONS = 4;
    private long startTime;
    private ImageView shapeImage;
    private GridLayout optionsContainer;
    private final int[] shapeImages = {
        R.drawable.square, R.drawable.circle, R.drawable.star, R.drawable.triangle,
        R.drawable.diamond, R.drawable.octagon, R.drawable.pentagon
    };
    private final String[] shapeNames = {
        "SQUARE", "CIRCLE", "STAR", "TRIANGLE",
        "DIAMOND", "OCTAGON", "PENTAGON"
    };
    private String difficulty;
    private Handler handler = new Handler();
    private ArrayList<Integer> questionOrder;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_quiz);

        startTime = System.currentTimeMillis();
        difficulty = getIntent().getStringExtra("DIFFICULTY");
        shapeImage = findViewById(R.id.shapeImage);
        optionsContainer = findViewById(R.id.optionsContainer);
        ImageButton backButton = findViewById(R.id.backButton);

        initializeQuestionOrder();
        backButton.setOnClickListener(v -> finish());
        showQuestion();
    }

    private void initializeQuestionOrder() {
        questionOrder = new ArrayList<>();
        ArrayList<Integer> allQuestions = new ArrayList<>();
        
        for (int i = 0; i < shapeImages.length; i++) {
            allQuestions.add(i);
        }
        
        while (questionOrder.size() < TOTAL_QUESTIONS && !allQuestions.isEmpty()) {
            int randomIndex = random.nextInt(allQuestions.size());
            questionOrder.add(allQuestions.remove(randomIndex));
        }
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

        int questionIndex = questionOrder.get(currentQuestion);
        shapeImage.setImageResource(shapeImages[questionIndex]);
        createOptions(questionIndex);
    }

    private void createOptions(int questionIndex) {
        optionsContainer.removeAllViews();
        List<String> options = new ArrayList<>(Arrays.asList(shapeNames));
        String correctAnswer = shapeNames[questionIndex];
        options.remove(correctAnswer);
        Collections.shuffle(options);

        int numOptions;
        switch (difficulty) {
            case "EASY":
                numOptions = 2;
                break;
            case "MEDIUM":
                numOptions = 3;
                break;
            default:
                numOptions = 5;
                break;
        }

        List<String> finalOptions = new ArrayList<>();
        finalOptions.add(correctAnswer);
        
        if (numOptions > shapeNames.length) {
            finalOptions.addAll(options.subList(0, Math.min(numOptions - 1, options.size())));
            while (finalOptions.size() < numOptions) {
                finalOptions.add(options.get(random.nextInt(options.size())));
            }
        } else {
            finalOptions.addAll(options.subList(0, numOptions - 1));
        }
        Collections.shuffle(finalOptions);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.titanone_regular);
        for (int i = 0; i < finalOptions.size(); i++) {
            Button button = new Button(this);
            button.setText(finalOptions.get(i));
            button.setBackgroundColor(0xFF292D39);
            button.setTextColor(getColor(android.R.color.white));
            button.setTypeface(typeface);
            button.setTextSize(20);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 400;
            params.height = 150;
            params.setMargins(16, 16, 16, 16);

            if (i == finalOptions.size() - 1 && finalOptions.size() % 2 == 1) {
                params.columnSpec = GridLayout.spec(0, 2, GridLayout.CENTER);
            }

            button.setLayoutParams(params);

            final String option = finalOptions.get(i);
            button.setOnClickListener(v -> {
                disableAllButtons();
                
                if (option.equals(correctAnswer)) {
                    button.setBackgroundTintList(getColorStateList(R.color.green));
                    handler.postDelayed(() -> {
                        currentQuestion++;
                        showQuestion();
                    }, 1000);
                } else {
                    button.setBackgroundTintList(getColorStateList(R.color.red));
                    handler.postDelayed(() -> {
                        createOptions(questionIndex);
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
