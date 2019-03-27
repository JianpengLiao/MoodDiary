package com.example.jianpeng.mooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MeFragment extends Fragment {

    public static MeFragment instance = null;
    ImageView ivhead;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_me,container,false);
        instance = this;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayout Lin_Setting = getActivity().findViewById(R.id.Lin_Setting);
        LinearLayout Lin_PersonInfo=getActivity().findViewById(R.id.Lin_PersonInfo);
        ivhead=getActivity().findViewById(R.id.iv_Head);
        int headid=User.getHeadPictureID();
        ivhead.setImageResource(headid);

        Lin_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });

        Lin_PersonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),PersonInfoActivity.class));
            }
        });
    }

    public void changeHead(){
        int headid=User.getHeadPictureID();
        ivhead.setImageResource(headid);
    }
}
