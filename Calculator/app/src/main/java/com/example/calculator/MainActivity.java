package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView resultDisplay;
    private TextView equationDisplay;

    private String currentInput = "";
    private String equation = "";
    private double result = 0;
    private String currentOperator = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(false);
        getWindow().setStatusBarColor(Color.BLACK);

        // Initialize the display TextViews
        resultDisplay = findViewById(R.id.resultDisplay);
        equationDisplay = findViewById(R.id.equationDisplay);

        // Initialize the buttons and set click listeners
        initializeButtons();
    }

    private void initializeButtons() {
        int[] buttonIDs = new int[]{
                R.id.ac_btn, R.id.dot_btn, R.id.dzero_btn, R.id.zero_btn, R.id.total_btn,
                R.id.three_btn, R.id.one_btn, R.id.two_btn, R.id.sub_btn, R.id.six_btn,
                R.id.four_btn, R.id.five_btn, R.id.add_btn, R.id.nine_btn, R.id.seven_btn,
                R.id.eight_btn, R.id.div_btn, R.id.mod_btn, R.id.mul_btn
        };

        for (int id : buttonIDs) {
            findViewById(id).setOnClickListener(buttonClickListener);
        }
    }

    private final View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            String buttonText = button.getText().toString();
            int id = v.getId();

            if (id == R.id.ac_btn) {
                resetCalculator();
            } else if (id == R.id.total_btn) {
                calculateResult();
            } else if (id == R.id.add_btn || id == R.id.sub_btn || id == R.id.mul_btn || id == R.id.div_btn || id == R.id.mod_btn) {
                handleOperator(buttonText);
            } else {
                handleNumberInput(buttonText);
            }
        }
    };

    private void resetCalculator() {
        currentInput = "";
        equation = "";
        result = 0;
        currentOperator = "";
        resultDisplay.setText("0");
        equationDisplay.setText("");
    }

    private void handleNumberInput(String input) {
        currentInput += input;
        equation += input;
        equationDisplay.setText(equation);
    }

    private void handleOperator(String operator) {
        if (!currentInput.isEmpty()) {
            if (result == 0) {
                result = Double.parseDouble(currentInput);
            } else {
                calculateResult();
            }
            currentOperator = operator;
            currentInput = "";
            equation += " " + operator + " ";
            equationDisplay.setText(equation);
        }
    }

    private void calculateResult() {
        if (!currentInput.isEmpty() && !currentOperator.isEmpty()) {
            double currentNumber = Double.parseDouble(currentInput);
            switch (currentOperator) {
                case "+":
                    result += currentNumber;
                    break;
                case "-":
                    result -= currentNumber;
                    break;
                case "*":
                    result *= currentNumber;
                    break;
                case "/":
                    if (currentNumber != 0) {
                        result /= currentNumber;
                    } else {
                        resultDisplay.setText("Error");
                        return;
                    }
                    break;
                case "%":
                    result %= currentNumber;
                    break;
            }
            resultDisplay.setText(String.valueOf(result));
            currentInput = String.valueOf(result);
            currentOperator = "";
        }
    }
}
