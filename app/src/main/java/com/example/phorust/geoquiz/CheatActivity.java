package com.example.phorust.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by phorust on 3/31/18.
 */

public class CheatActivity extends Activity {
    @BindView(R.id.show_answer)
    TextView show_answer_text_view;
    @BindView(R.id.hint_button_show)
    Button hint_button_show;
    @BindView(R.id.token1)
    ImageView token1_image_view;
    @BindView(R.id.token2)
    ImageView token2_image_view;
    @BindView(R.id.token3)
    ImageView token3_image_view;

    public static final String EXTRA_ANSWER_IS_TRUE =
            "com.example.phorust.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_IS_SHOWN =
            "com.example.phorust.geoquiz.answer_is_shown";
    public static final String EXTRA_TOKEN_USED_COUNT =
            "com.example.phorust.geoquiz.token_used_count";

    public final String KEY_ANSWER = "answer";
    public final String KEY_TOKEN_USED_COUNT = "used_tokens";
    public final String KEY_HINT_USED = "hint_used";
    public final String KEY_SAME_QUESTION = "same_question";
    private int mTokenUsedCount;
    private boolean mAnswerIsTrue;
    private boolean mQuestionHintUsed;
    private boolean sameQuestion;

    private void setAnswerShownResult(boolean isAnswerShown, int tokenUsedCount) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_IS_SHOWN, isAnswerShown);
        data.putExtra(EXTRA_TOKEN_USED_COUNT, tokenUsedCount);
        setResult(RESULT_OK, data);
    }

    private void updateTokenDisplay(int tokenUsedCount){
        List<ImageView> tokenList = Arrays.asList(token3_image_view,token2_image_view,token1_image_view);
        if (mTokenUsedCount > 0) {
            for (int i = 0; i < tokenUsedCount; i++) {
                //i = (i == 0 ? i = 2 : 3 - i);
                (tokenList.get(i)).setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_TOKEN_USED_COUNT, mTokenUsedCount);
        savedInstanceState.putBoolean(KEY_ANSWER, mAnswerIsTrue);
        savedInstanceState.putBoolean(KEY_HINT_USED, mQuestionHintUsed);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mAnswerIsTrue = savedInstanceState.getBoolean(KEY_ANSWER, false);
            mTokenUsedCount = savedInstanceState.getInt(KEY_TOKEN_USED_COUNT, 0);
            mQuestionHintUsed = savedInstanceState.getBoolean(KEY_HINT_USED,false);
            sameQuestion = mQuestionHintUsed;
        } else {
            mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
            mTokenUsedCount = getIntent().getIntExtra(EXTRA_TOKEN_USED_COUNT, 0);
            mQuestionHintUsed = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_SHOWN, false);
            sameQuestion = mQuestionHintUsed;
        }

        setAnswerShownResult(mQuestionHintUsed, mTokenUsedCount);
        updateTokenDisplay(mTokenUsedCount);

        hint_button_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTokenUsedCount < 3) {
                    if (mAnswerIsTrue) {
                        show_answer_text_view.setText(R.string.true_button);
                    } else {
                        show_answer_text_view.setText(R.string.false_button);
                    }

                    if (!sameQuestion){
                        mTokenUsedCount++;
                    }

                    setAnswerShownResult(true, mTokenUsedCount);
                    updateTokenDisplay(mTokenUsedCount);
                    sameQuestion = true;
                    
                } else {
                    Toast.makeText(CheatActivity.this,"Sorry, you are out of tokens!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

