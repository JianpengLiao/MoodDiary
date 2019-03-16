package com.example.jianpeng.mooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageButton BtnCardDiary = getActivity().findViewById(R.id.imgBtn_CardDiary);
        ImageButton BtnTravelDiary = getActivity().findViewById(R.id.imgBtn_TravelDiary);
        ImageButton BtnMoodTag = getActivity().findViewById(R.id.imgBtn_MoodTag);
        ImageButton BtnWorkRecord = getActivity().findViewById(R.id.imgBtn_WorkRecord);

        TextView tvCardDiary = getActivity().findViewById(R.id.tv_CardDiary);
        TextView tvTravelDiary = getActivity().findViewById(R.id.tv_TravelDiary);
        TextView tvMoodTag = getActivity().findViewById(R.id.tv_MoodTag);
        TextView tvWorkRecord = getActivity().findViewById(R.id.tv_WorkRecord);

        BtnCardDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CardDiaryActivity.class));
            }
        });
        tvCardDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CardDiaryActivity.class));
            }
        });

        BtnTravelDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TravelDiaryActivity.class));
            }
        });
        tvTravelDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CardDiaryActivity.class));
            }
        });

        BtnMoodTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MoodTagActivity.class));
            }
        });
        tvMoodTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MoodTagActivity.class));
            }
        });

        BtnWorkRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WorkRecordActivity.class));
            }
        });
        tvWorkRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WorkRecordActivity.class));
            }
        });
    }





}
