package com.example.jianpeng.mooddiary;

public class CardDiaryItem {
    private int mTextResource;
    private int mTitleResource;

    public CardDiaryItem(int title, int text) {
        mTitleResource = title;
        mTextResource = text;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }
}
