package com.cti.statscsgo.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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
import com.cti.statscsgo.variable.StatsConst;

import java.io.File;
import java.util.HashMap;

public class LastMatch extends Fragment {


    //Steam icon
    DrawImage img;

    //Total time played
    TextView textView;

    //Full statistic from db
    // from previous Activity
    HashMap<String, String> hm;

    public LastMatch() {
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
        View view = inflater.inflate(R.layout.last_match, container, false);
        TextView txt = (TextView) view.findViewById(R.id.last_wins);
        txt.setText(hm.get("last_match_wins"));

        txt = (TextView) view.findViewById(R.id.last_kills);
        txt.setText(hm.get("last_match_kills"));

        txt = (TextView) view.findViewById(R.id.last_deaths);
        txt.setText(hm.get("last_match_deaths"));

        txt = (TextView) view.findViewById(R.id.last_mvp);
        txt.setText(hm.get("last_match_mvps"));

        txt = (TextView) view.findViewById(R.id.last_damage);
        txt.setText(hm.get("last_match_damage"));

        txt = (TextView) view.findViewById(R.id.last_money);
        txt.setText(hm.get("last_match_money_spent"));

        StatsConst cnst = new StatsConst();
        String id = hm.get("last_match_favweapon_id");
        for (int i = 0, n = cnst.MS_GUNS_ID.length; i < n; i++ ) {
            if (id.equals(cnst.MS_GUNS_ID[i])) {
                txt = (TextView) view.findViewById(R.id.guns_name);
                txt.setText(cnst.MS_GUNS_NAMES[i]);
                ImageView img = (ImageView) view.findViewById(R.id.imageGun);
                img.setImageResource(cnst.MS_ID_GUNS[i]);
                txt = (TextView) view.findViewById(R.id.textView_killGun2);
                txt.setText(hm.get("last_match_favweapon_kills"));
                txt = (TextView) view.findViewById(R.id.textView_hitsGun2);
                txt.setText(hm.get("last_match_favweapon_hits"));
                txt = (TextView) view.findViewById(R.id.textView_shotsGun2);
                txt.setText(hm.get("last_match_favweapon_shots"));
                break;
            }
        }

        return view;
    }

}
