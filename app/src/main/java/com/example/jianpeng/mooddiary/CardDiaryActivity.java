package com.example.jianpeng.mooddiary;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

public class CardDiaryActivity extends BaseCompatActivity {

    private ViewPager mViewPager;
    private CardDiaryPagerAdapter mCardAdapter;
    private CardDiaryShadowTransformer mCardCardDiaryShadowTransformer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_diary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mCardAdapter = new CardDiaryPagerAdapter();
        mCardAdapter.addCardItem(new CardDiaryItem(R.string.title_1, R.string.text_1));
        mCardAdapter.addCardItem(new CardDiaryItem(R.string.title_2, R.string.text_2));
        mCardAdapter.addCardItem(new CardDiaryItem(R.string.title_3, R.string.text_3));
        mCardAdapter.addCardItem(new CardDiaryItem(R.string.title_4, R.string.text_4));

        mCardCardDiaryShadowTransformer = new CardDiaryShadowTransformer(mViewPager, mCardAdapter);
        mCardCardDiaryShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardCardDiaryShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

    }

    public void onClickAddCardDiary(View view) {



    }
}
