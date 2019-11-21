package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends LoggingActivity {

    private static final String KEY_CURRENT_INDEX = "key_current_index";
    private static final String KEY_SAVE_ANSWER = "key_save_answer";

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;
    private int allQuestion = mQuestionBank.length;
    private int[] checkAnswer = new int[allQuestion];
    private int oneTrueAnswer = 2;
    private int oneFalseAnswer = 1;
    private int notAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
            checkAnswer = savedInstanceState.getIntArray(KEY_SAVE_ANSWER);
        }

        final TextView questionString = findViewById(R.id.question_string);
        final Question currentQuestion = mQuestionBank[mCurrentIndex];
        questionString.setText(currentQuestion.getQuestionResId());

        Button trueButton = findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked(true);
            }
        });

        Button falseButton = findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked(false);
            }
        });

        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;

                final Question currentQuestion = mQuestionBank[mCurrentIndex];
                questionString.setText(currentQuestion.getQuestionResId());
            }
        });

        Button checkButton = findViewById(R.id.check_button);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int sumAllAnswer = 0;
               int sumTrueAnswer = 0;
               for (int e: checkAnswer) {
                   if (e != notAnswer) {
                       sumAllAnswer++;
                       if (e == oneTrueAnswer) {
                           sumTrueAnswer++;
                       }
                   }
               }
                StringBuilder sb = new StringBuilder()
                        .append("Отвечено ")
                        .append(sumAllAnswer)
                        .append("\\")
                        .append(allQuestion)
                        .append(" вопросов\nПравильных ответов: ")
                        .append(sumTrueAnswer);
                String toastCheck = sb.toString();

                Toast.makeText(
                        MainActivity.this,
                        toastCheck,
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_INDEX, mCurrentIndex);
        outState.putIntArray(KEY_SAVE_ANSWER, checkAnswer);
    }

    private void onButtonClicked(boolean answer) {
        Question currentQuestion = mQuestionBank[mCurrentIndex];
        boolean mCheck = (currentQuestion.isCorrectAnswer() == answer);
        int toastMessage;
        if (mCheck) {
            toastMessage = R.string.correct_toast;
            checkAnswer[mCurrentIndex] = oneTrueAnswer;
        }
        else {
            toastMessage = R.string.incorrect_toast;
            if (checkAnswer[mCurrentIndex] == notAnswer) {
                checkAnswer[mCurrentIndex] = oneFalseAnswer;
            }
        }
        Toast.makeText(
                MainActivity.this,
                toastMessage,
                Toast.LENGTH_LONG
        ).show();
    }
}
