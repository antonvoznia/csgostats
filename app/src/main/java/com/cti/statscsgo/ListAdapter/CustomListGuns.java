package com.cti.statscsgo.ListAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cti.statscsgo.R;
import com.cti.statscsgo.variable.StatsConst;

import java.util.HashMap;

public class CustomListGuns extends ArrayAdapter<String> {

    static class ViewHolder {
        TextView kills, shots, hits, name;
        ImageView img;
    }

    private HashMap<String, String> hm;

    private Context context;


    private StatsConst cnst;

    public CustomListGuns(Context context, StatsConst cnst, HashMap<String, String> hm) {
        super(context, R.layout.list_custom, cnst.MS_GUNS_KILLS);
        this.cnst = cnst;
        this.hm = hm;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textView = (TextView) view.findViewById(R.id.textView_killGun2);
        ImageView img = (ImageView) view.findViewById(R.id.imageGun);
        img.setImageResource(cnst.MS_ID_GUNS[position]);
        textView.setText(hm.get(cnst.MS_GUNS_KILLS[position]));
        textView = (TextView) view.findViewById(R.id.textView_hitsGun2);
        textView.setText(hm.get(cnst.MS_GUNS_HITS[position]));
        textView = (TextView) view.findViewById(R.id.textView_shotsGun2);
        textView.setText(hm.get(cnst.MS_GUNS_SHOTS[position]));
        textView = (TextView) view.findViewById(R.id.guns_name);
        textView.setText(cnst.MS_GUNS_NAMES[position]);
*/
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            view = inflater.inflate(R.layout.list_item_guns, parent, false);
            ViewHolder h = new ViewHolder();
            h.img = (ImageView) view.findViewById(R.id.imageGun);
            h.kills = (TextView) view.findViewById(R.id.textView_killGun2);
            h.shots = (TextView) view.findViewById(R.id.textView_shotsGun2);
            h.hits = (TextView) view.findViewById(R.id.textView_hitsGun2);
            h.name = (TextView) view.findViewById(R.id.guns_name);
            view.setTag(h);
        }

        ViewHolder h = (ViewHolder) view.getTag();
        h.img.setImageResource(cnst.MS_ID_GUNS[position]);
        h.kills.setText(hm.get(cnst.MS_GUNS_KILLS[position]));
        h.shots.setText(hm.get(cnst.MS_GUNS_SHOTS[position]));
        h.hits.setText(hm.get(cnst.MS_GUNS_HITS[position]));
        h.name.setText(cnst.MS_GUNS_NAMES[position]);

        return view;
    }
}
