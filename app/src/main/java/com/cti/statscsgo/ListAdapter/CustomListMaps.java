package com.cti.statscsgo.ListAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cti.statscsgo.R;

import java.util.HashMap;

public class CustomListMaps extends ArrayAdapter<String> {

    static class ViewHolder {
        TextView wins, rounds, masp_name;
        ImageView img;
    }

    //Список картинок в ListView
    int[] img_items;

    private Context context;
    private String[] MS_MAPS_WINS, MS_MAPS_ROUNDS, MS_MAPS_NAME;

    private HashMap<String, String> hm;

    public CustomListMaps(Context context, int[] img_items, HashMap<String, String> hm,
                          String[] MS_MAPS_WINS, String[] MS_MAPS_ROUNDS,
                          String[] MS_MAPS_NAME) {
        super(context, R.layout.list_custom, MS_MAPS_WINS);
        this.img_items = img_items;
        this.context = context;
        this.MS_MAPS_WINS = MS_MAPS_WINS;
        this.hm = hm;
        this.MS_MAPS_ROUNDS = MS_MAPS_ROUNDS;
        this.MS_MAPS_NAME = MS_MAPS_NAME;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            view = inflater.inflate(R.layout.list_item_maps, parent, false);
            ViewHolder h = new ViewHolder();
            h.img = (ImageView) view.findViewById(R.id.imageMap);
            h.wins = (TextView) view.findViewById(R.id.textViewWins);
            h.rounds = (TextView) view.findViewById(R.id.textViewRounds);
            h.masp_name = (TextView) view.findViewById(R.id.textMAPS_NAME);
            view.setTag(h);
        }
        ViewHolder h = (ViewHolder) view.getTag();
        h.img.setImageResource(img_items[position]);
        String wins = hm.get(MS_MAPS_WINS[position]), rounds = hm.get(MS_MAPS_ROUNDS[position]);
        h.wins.setText(wins + " (" + String.valueOf(
                Integer.valueOf(wins) * 100 /
                        ( rounds.equals("0") ? 1 : Integer.valueOf(rounds) )) + " %)");
        h.rounds.setText(rounds);
        h.masp_name.setText(MS_MAPS_NAME[position]);
        return view;
    }

    /*@Override
    public int getCount() {
        return 4;
    }*/
}
