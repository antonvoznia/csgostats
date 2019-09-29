package com.cti.statscsgo.ListAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cti.statscsgo.R;

import java.io.File;
import java.util.ArrayList;

public class CustomListMainAdapter extends ArrayAdapter<String> {

    //Список Items в ListView
    private ArrayList<String> al;

    Typeface tp;

    private Context context;

    public CustomListMainAdapter(Context context, ArrayList<String> al) {
        super(context, R.layout.list_custom, al);
        this.al = al;
        this.context = context;
        tp = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoLight.ttf");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_list_item_main, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.textView_main);

        File imgFile = new  File(view.getContext().getDir(Environment.DIRECTORY_PICTURES,
                Context.MODE_PRIVATE)
                .getAbsolutePath());

        if(imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath() + "/" + al.get(position).toLowerCase() + "_m.jpg");
            ImageView img = (ImageView) view.findViewById(R.id.image_main_avatar);
            img.setImageBitmap(myBitmap);

        }

        textView.setText(al.get(position));
        textView.setTypeface(tp);

        return view;
    }
}
