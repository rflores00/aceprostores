package com.adv.aceprostores.helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Ruben Flores on 11/22/2016.
 */

public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);
        if(!isInEditMode()) {
            //Typeface face = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_name));
            //this.setTypeface(face);
        }
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()) {
            //Typeface face = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_name));
            //this.setTypeface(face);
        }
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(!isInEditMode()) {
            //Typeface face = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_name));
            //this.setTypeface(face);
        }
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}