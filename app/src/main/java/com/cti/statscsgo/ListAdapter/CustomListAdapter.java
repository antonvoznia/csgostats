package com.cti.statscsgo.ListAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cti.statscsgo.R;

public class CustomListAdapter extends ArrayAdapter<String> {

    //Array of Items in ListView
    private String[] str;

    // List/Array of images in ListView
    int[] img_items;

    Typeface tp;

    private Context context;

    public CustomListAdapter(Context context, String[] str, int[] img_items) {
        super(context, R.layout.list_custom, str);
        this.str = str;
        this.img_items = img_items;
        this.context = context;
        tp = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoLight.ttf");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_custom, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.textView_menu);
        ImageView img = (ImageView) view.findViewById(R.id.image_menu_icon);
        img.setImageResource(img_items[position]);

        textView.setText(str[position]);
        textView.setTypeface(tp);

        return view;
    }
}
