package com.example.erkan.my_bluetooth_controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

/**
 * Created by erkan on 2015-10-21.
 */
public class TryHard  {

    private ShapeDrawable mShape;

    public TryHard()
    {
        mShape = new ShapeDrawable(new OvalShape());
        mShape.setBounds(0,0,100,100);
    }

    protected void onDraw(Canvas canvas)
    {
        mShape.draw(canvas);
    }
}
