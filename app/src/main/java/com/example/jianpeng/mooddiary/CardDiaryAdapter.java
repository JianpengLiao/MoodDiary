package com.example.jianpeng.mooddiary;

import android.support.v7.widget.CardView;

public interface CardDiaryAdapter {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();

}
