package com.example.tinyexplorer;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Button;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        String difficulty = getIntent().getStringExtra("DIFFICULTY");

        ImageButton backButton = findViewById(R.id.backButton);
        Button mathButton = findViewById(R.id.mathButton);
        Button shapesButton = findViewById(R.id.shapesButton);

        backButton.setOnClickListener(v -> finish());

        mathButton.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryActivity.this, MathQuizActivity.class);
            intent.putExtra("DIFFICULTY", difficulty);
            startActivity(intent);
        });

        shapesButton.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryActivity.this, ShapeQuizActivity.class);
            intent.putExtra("DIFFICULTY", difficulty);
            startActivity(intent);
        });
    }
}
