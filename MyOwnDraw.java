package com.example.erkan.my_bluetooth_controller;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by erkan on 2015-10-21.
 */
public class MyOwnDraw extends View {

    private float mLastTouchX;
    private float mLastTouchY;

    private float mPosX;
    private float mPosY;

    private ShapeDrawable mBall;

    private  TextView throttle_val;
    private  TextView yaw_val;

    private int throttle;
    private int yaw;

    private MainActivity ma;

    private manageConnection send;

    private AcceptThread connection;
    private DrawBall myBall2;

    public MyOwnDraw(Context context, int[] coordinates, AcceptThread connection, DrawBall myBall2) {
        super(context);

        coordinates[0] = throttle;
        coordinates[1] = yaw;

        mBall = new ShapeDrawable(new OvalShape());
        mBall.setBounds(0, 0, 100, 100);

        throttle_val = (TextView) ((Activity)context).findViewById(R.id.throttle_val);
        yaw_val = (TextView) ((Activity)context).findViewById(R.id.yaw_val);

        ma = new MainActivity();
        this.connection = connection;
        this.myBall2 = myBall2;
    }



    public boolean onTouchEvent(MotionEvent ev){
        final int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:{
                final float x = ev.getX();
                final float y = ev.getY();

                mLastTouchX = x;
                mLastTouchY = y;
                throttle = (int)x;
                yaw = (int)y;
                invalidate();
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                final float x = ev.getX();
                final float y = ev.getY();

                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;

                mPosX += dx;
                mPosY += dy;

                throttle = (710/2-50/2) - (int) mPosY ;
                yaw =(int) mPosX - (860/2-50/2) ;




                int[] inputs = new int[]{throttle,yaw};
                Log.d("inputs", java.util.Arrays.toString(inputs));

                connection.onPostExcecute(java.util.Arrays.toString(inputs));


                mLastTouchX = x;
                mLastTouchY = y;

                getThrottleNdYaw();
                invalidate();
                break;

            }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        throttle_val.setText("" + throttle);
        yaw_val.setText("" + yaw);

        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mPosX, mPosY);
        mBall.draw(canvas);
        canvas.restore();

    }

    public int[] getThrottleNdYaw()
    {
        int[] vals = new int[]{throttle,yaw};
        return vals;
    }
}
