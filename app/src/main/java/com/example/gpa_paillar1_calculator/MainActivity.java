package com.example.gpa_paillar1_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText[] gradeInputs = new EditText[5];
    TextView gpaDisplay;
    Button computeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        initializeViews();

        computeButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (allFieldsFilled()) {
                    computeGPA();
                    computeButton.setText("Clear Form");
                } else {
                    Toast.makeText(MainActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        for (EditText gradeInput : gradeInputs) {
            gradeInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        EditText editText = (EditText) v;
                        if (editText.getText().toString().isEmpty()) {
                            editText.setError("Grade cannot be empty");
                        }
                    }
                }
            });

            gradeInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    // Enable the button if any field is edited
                    computeButton.setText("Compute GPA");
                }
            });
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Handle configuration changes here if needed
    }

    private void initializeViews() {
        gradeInputs[0] = findViewById(R.id.course1Grade);
        gradeInputs[1] = findViewById(R.id.course2Grade);
        gradeInputs[2] = findViewById(R.id.course3Grade);
        gradeInputs[3] = findViewById(R.id.course4Grade);
        gradeInputs[4] = findViewById(R.id.course5Grade);
        gpaDisplay = findViewById(R.id.gpaDisplay);
        computeButton = findViewById(R.id.computeButton);
    }

    @SuppressLint("DefaultLocale")
    private void computeGPA() {
        double totalGrades = 0;
        int validFields = 0;

        for (EditText gradeInput : gradeInputs) {
            if (!gradeInput.getText().toString().isEmpty()) {
                totalGrades += Double.parseDouble(gradeInput.getText().toString());
                validFields++;
            }
        }

        if (validFields == 0) {
            gpaDisplay.setText("N/A");
            return;
        }

        double gpa = totalGrades / validFields;

        // GPA Display
        gpaDisplay.setText(String.format("%.2f", gpa));

        // Change background color based on GPA range
        if (gpa < 60) {
            findViewById(android.R.id.content).setBackgroundColor(Color.RED);
        } else if (gpa >= 60 && gpa <= 79) {
            findViewById(android.R.id.content).setBackgroundColor(Color.YELLOW);
        } else {
            findViewById(android.R.id.content).setBackgroundColor(Color.GREEN);
        }
    }

    private boolean allFieldsFilled() {
        for (EditText gradeInput : gradeInputs) {
            if (gradeInput.getText().toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
