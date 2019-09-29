package com.cti.statscsgo.custom;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cti.statscsgo.R;

import java.util.HashMap;

/**
 * Created by anton on 13.07.15.
 */
public class CustomTextView extends TextView {


    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    public static final HashMap<String, Typeface> sTypefaceCache = new HashMap<String, Typeface>();
    public static final Typeface getTypeface(AssetManager mgr, String path) {
        if(!sTypefaceCache.containsKey(path)) {
            Typeface tf = Typeface.createFromAsset(mgr, path);
            sTypefaceCache.put(path, tf);
        }
        return sTypefaceCache.get(path);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String customFont = a.getString(R.styleable.CustomTextView_customFont);
        setTypeface(getTypeface(ctx.getAssets(), customFont));
        a.recycle();
    }
}
