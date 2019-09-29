package com.cti.statscsgo.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cti.statscsgo.R;
import com.cti.statscsgo.drawImg.DrawImage;

import java.io.File;
import java.util.HashMap;

public class TotalStats extends Fragment {


    //Steam icon
    DrawImage img;

    //Total time played
    TextView textView;

    //Full statistic from db from previous Activity
    HashMap<String, String> hm;

    public TotalStats() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hm = (HashMap<String, String>) getActivity().getIntent().getSerializableExtra("HASH_MAP");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_total_stats, container, false);

        File imgFile = new  File(view.getContext().getDir(Environment.DIRECTORY_PICTURES,
                Context.MODE_PRIVATE)
                .getAbsolutePath());

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath() + "/" + hm.get("customURL").toLowerCase() + ".jpg");

            ImageView img = (ImageView) view.findViewById(R.id.imageIcon);

            img.setImageBitmap(myBitmap);

        }

        textView = (TextView) view.findViewById(R.id.nickName);
        textView.setText(hm.get("steamID"));
        textView = (TextView) view.findViewById(R.id.realName);
        textView.setText(hm.get("realname"));

        textView = (TextView) view.findViewById(R.id.timePlayed);
        String totalTimeStr = hm.get("total_time_played");
        int totalTime = totalTimeStr == null || totalTimeStr == "null" ? 0 : Integer.valueOf(totalTimeStr);
        textView.setText(String.valueOf(totalTime/3600)+" h");

        textView = (TextView) view.findViewById(R.id.totalKills);
        textView.setText(hm.get("total_kills"));

        textView = (TextView) view.findViewById(R.id.totalMVP);
        textView.setText(hm.get("total_mvps"));

        textView = (TextView) view.findViewById(R.id.KD);
        textView.setText(String.valueOf( Float.valueOf(hm.get("total_kills"))/
                Float.valueOf(hm.get("total_deaths")) ));

        textView = (TextView) view.findViewById(R.id.headshots);
        textView.setText(hm.get("total_kills_headshot"));

        textView = (TextView) view.findViewById(R.id.bomb_set);
        textView.setText(hm.get("total_planted_bombs"));

        textView = (TextView) view.findViewById(R.id.bomb_defuse);
        textView.setText(hm.get("total_defused_bombs"));

        img = (DrawImage) view.findViewById(R.id.imageIcon);
        return view;
    }

}
