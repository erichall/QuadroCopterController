package com.example.erkan.my_bluetooth_controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by erkan on 2015-10-20.
 */
public class HandleInputs implements View.OnTouchListener {
    private Rect hitBox;

    private float mPosX;
    private float mPosY;

    private float mLastTouchX;
    private float mLastTouchY;

    private TextView throttle_val;
    private  TextView yaw_val;

    private int screen_width;
    private int screen_height;

    private View drawBallView;

    private RelativeLayout ll;
    private RelativeLayout rr;

    private ShapeDrawable mDrawable;


    public boolean onTouch(View v, MotionEvent ev) {
        final int action = ev.getAction();
        hitBox = new Rect((int) mPosX,(int) mPosY,(int) mPosX + 200,(int) mPosY + 200);


        if(hitBox.contains((int) ev.getX(), (int) ev.getY())) {
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    final float x = ev.getX();
                    final float y = ev.getY();

                    Log.d("a", "KOM JAG ENS hiT");
                    mLastTouchX = x;
                    mLastTouchY = y;
                    v.invalidate();
                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    final float x = ev.getX();
                    final float y = ev.getY();

                    final float dx = x - mLastTouchX;
                    final float dy = y - mLastTouchY;

                    mPosX += dx;
                    mPosY += dy;

                    mLastTouchX = x;
                    mLastTouchY = y;

                   v.invalidate();
                    break;
                }

                case MotionEvent.ACTION_UP: {
                    final float x = ev.getX();
                    final float y = ev.getY();

                   // screen_width = ll.getWidth();
                    //screen_height = ll.getHeight();

                    mPosX = screen_width/2-50;
                    mPosY = screen_height/2-50;

                    mLastTouchX = screen_width/2-100;
                    mLastTouchY = screen_height/2-100;

                    v.invalidate();
                    break;
                }

                case MotionEvent.ACTION_CANCEL: {
                   // screen_width = ll.getWidth();
                   // screen_height = ll.getHeight();
                    mPosX = screen_width/2-100;
                    mPosY = screen_height/2-100;

                   v.invalidate();
                    break;
                }


            }
            return true;
        }
        return false;
    }

}
