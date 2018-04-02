package com.example.phorust.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizActivity extends AppCompatActivity {
    @BindView(R.id.next_question) Button next_question_button;
    @BindView(R.id.prev_question) Button prev_question_button;
    @BindView(R.id.hint_button) Button hint_button;
    @BindView(R.id.true_button) Button true_button;
    @BindView(R.id.false_button) Button false_button;
    @BindView(R.id.mQuestion) TextView mQuestionTextView;
    private int mCurrentIndex = 0;
    private int mTokenUsedCount = 0;
    private boolean mUsedCheat;
    private final String KEY_INDEX = "index_key";
    private final String KEY_USED_HINT = "used_hint";
    private final String KEY_TOKEN_USED_COUNT = "token_counter";

    private boolean[] mUsedHintPreviously = new boolean[]{
            false,
            false,
            false,
            false,
    };

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_text1, true, false),
            new Question(R.string.question_text2,false, false),
            new Question(R.string.question_text3, true, false),
            new Question(R.string.question_text4,true,false),
    };

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        savedInstanceState.putInt(KEY_TOKEN_USED_COUNT,mTokenUsedCount);
        //savedInstanceState.putBoolean(KEY_USED_HINT,mUsedCheat);
        savedInstanceState.putBooleanArray(KEY_USED_HINT, mUsedHintPreviously);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (data == null){
            return;
        }
        mUsedCheat = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_IS_SHOWN, false);
        mTokenUsedCount = data.getIntExtra(CheatActivity.EXTRA_TOKEN_USED_COUNT, 0);
        mQuestionBank[mCurrentIndex].setUsedHint(mUsedCheat);
        mUsedHintPreviously[mCurrentIndex] = mUsedCheat;

    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int messageID;
        if (!mQuestionBank[mCurrentIndex].isUsedHint() && !mUsedHintPreviously[mCurrentIndex]) {
            if (userPressedTrue == answerIsTrue) {
                messageID = R.string.correct_toast;
            } else {
                messageID = R.string.incorrect_toast;
            }
            Toast.makeText(QuizActivity.this, messageID, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(QuizActivity.this, "You used a hint!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);

        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mTokenUsedCount = savedInstanceState.getInt(KEY_TOKEN_USED_COUNT,0);
            mUsedHintPreviously = savedInstanceState.getBooleanArray(KEY_USED_HINT);
            //mUsedCheat = savedInstanceState.getBoolean(KEY_USED_HINT,false);
        }

        true_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        false_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        next_question_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        prev_question_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex == 0 ? mCurrentIndex = mQuestionBank.length - 1 : mCurrentIndex - 1);
                updateQuestion();
            }
        });

        hint_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                i.putExtra(CheatActivity.EXTRA_TOKEN_USED_COUNT, mTokenUsedCount);
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_SHOWN,mUsedHintPreviously[mCurrentIndex]);
                startActivityForResult(i,0);
            }
        });

        updateQuestion();
    }
}
