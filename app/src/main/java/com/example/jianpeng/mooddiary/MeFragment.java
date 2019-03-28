package com.example.jianpeng.mooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


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
        LinearLayout Lin_Collection=getActivity().findViewById(R.id.Lin_Collection);
        LinearLayout Lin_Shar=getActivity().findViewById(R.id.Lin_Shartofriend);
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
        Lin_Collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("This feature will be implemented in version 2.0, so stay tuned", Toast.LENGTH_LONG);
            }
        });
        Lin_Shar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("This feature will be implemented in version 2.0, so stay tuned", Toast.LENGTH_LONG);
            }
        });
    }

    public void changeHead(){
        int headid=User.getHeadPictureID();
        ivhead.setImageResource(headid);
    }



    //show Toast
    public void showToast(String str, int showTime)
    {
        Toast toast = Toast.makeText(getActivity(), str, showTime);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL , 0, 0);  //set the display location
        toast.show();
    }
}
