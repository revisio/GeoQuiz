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
    private int mTokenUsedCount;
    private boolean mAnswerIsTrue;
    private boolean mQuestionHintUsed;

    private void setAnswerShownResult(boolean isAnswerShown, int tokenUsedCount) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_IS_SHOWN, isAnswerShown);
        data.putExtra(EXTRA_TOKEN_USED_COUNT, tokenUsedCount);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        ButterKnife.bind(this);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_SHOWN, false);

        setAnswerShownResult(false, mTokenUsedCount);

        if (savedInstanceState != null) {
            mAnswerIsTrue = savedInstanceState.getBoolean(KEY_ANSWER, false);
            mTokenUsedCount = savedInstanceState.getInt(KEY_TOKEN_USED_COUNT, 0);
        } else {
            mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
            mTokenUsedCount = getIntent().getIntExtra(EXTRA_TOKEN_USED_COUNT, 0);
        }

        hint_button_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTokenUsedCount < 3) {
                    if (mAnswerIsTrue) {
                        show_answer_text_view.setText(R.string.true_button);
                    } else {
                        show_answer_text_view.setText(R.string.false_button);
                    }

                    setAnswerShownResult(true, mTokenUsedCount);
                }
            }
        });
    }
}

