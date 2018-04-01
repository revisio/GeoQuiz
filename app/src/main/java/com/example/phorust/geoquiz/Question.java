package com.example.phorust.geoquiz;

/**
 * Created by phorust on 3/31/18.
 */

public class Question {
    private int mQuestion;
    private boolean mTrueQuestion;
    private boolean mUsedHint;

    public Question(int question,boolean trueQuestion, boolean usedHint){
        mQuestion = question;
        mTrueQuestion= trueQuestion;
        mUsedHint = usedHint;
    }

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public boolean isTrueQuestion() {
        return mTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        mTrueQuestion = trueQuestion;
    }

    public boolean isUsedHint() {
        return mUsedHint;
    }

    public void setUsedHint(boolean usedHint) {
        mUsedHint = usedHint;
    }
}
