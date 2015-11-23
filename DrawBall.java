package com.example.erkan.my_bluetooth_controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by erkan on 2015-10-03.
 */
public class DrawBall extends View {
   private ShapeDrawable mDrawable;
    private Rect hitBox;

    private float mPosX;
    private float mPosY;

    private float mLastTouchX;
    private float mLastTouchY;

    private  TextView throttle_val;
    private  TextView yaw_val;
    private  TextView pitch_val;
    private  TextView roll_val;

    private int screen_width;
    private int screen_height;

    private View drawBallView;

    private RelativeLayout ll;
    private RelativeLayout rr;


    private int roll;
    private int pitch;



    private static final int INVALID_POINTER_ID = -1;

    private int mActivePointerId = INVALID_POINTER_ID;

    private AcceptThread connection;

    public DrawBall(Context context, AcceptThread connection)
    {
        this(context,null,0);
        this.connection = connection;

    }

    public DrawBall(Context context, AttributeSet attr)
    {
        this(context, attr, 0);

    }

    public DrawBall(Context context,AttributeSet attr,int defStyle)
    {
        super(context, attr, defStyle);


        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.setBounds(0, 0, 100, 100);

        Log.d("attr?",defStyle+"");

       //  throttle_val = (TextView) ((Activity)context).findViewById(R.id.throttle_val);
        // yaw_val = (TextView) ((Activity)context).findViewById(R.id.yaw_val);
        roll_val = (TextView) ((Activity)context).findViewById(R.id.roll_val);
        pitch_val = (TextView) ((Activity)context).findViewById(R.id.pitch_val);


        ll = (RelativeLayout) ((Activity)context).findViewById(R.id.left_layout);
        rr = (RelativeLayout) ((Activity)context).findViewById(R.id.right_layout);


        Log.d("WIDTH", ll.getWidth() + "");

    }



    protected void onDraw(Canvas canvas)
    {

        pitch_val.setText(""+pitch);
        roll_val.setText(""+roll);
        //throttle_val.setText(""+throttle);
        //yaw_val.setText(""+yaw);

        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mPosX, mPosY);
        mDrawable.draw(canvas);
        canvas.restore();
    }



    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        hitBox = new Rect((int) mPosX,(int) mPosY,(int) mPosX + 200,(int) mPosY + 200);


        if(hitBox.contains((int) ev.getX(), (int) ev.getY())) {
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN: {
                    final float x = ev.getX();
                    final float y = ev.getY();

                    mLastTouchX = x;
                    mLastTouchY = y;
                    mActivePointerId = ev.getPointerId(0);
                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                    final float x = ev.getX();
                    final float y = ev.getY();

                    final float dx = x - mLastTouchX;
                    final float dy = y - mLastTouchY;

                    mPosX += dx;
                    mPosY += dy;


                    int[] inputs = new int[]{pitch,roll};
                    Log.d("inputs", java.util.Arrays.toString(inputs));
                    connection.sendshit("PR" + java.util.Arrays.toString(inputs));

                    pitch = (710/2-50/2) - (int) mPosY;
                    roll = (int) mPosX - (860/2 - 50/2);


                    mLastTouchX = x;
                    mLastTouchY = y;

                    invalidate();
                    break;
                }

                case MotionEvent.ACTION_UP: {
                    final float x = ev.getX();
                    final float y = ev.getY();

                    screen_width = ll.getWidth();
                    screen_height = ll.getHeight();



                    mPosX = screen_width/2-50;
                    mPosY = screen_height/2-50;



                    mLastTouchX = screen_width/2-100;
                    mLastTouchY = screen_height/2-100;

                    mActivePointerId = INVALID_POINTER_ID;



                    invalidate();
                    break;
                }

                case MotionEvent.ACTION_CANCEL: {
                    screen_width = ll.getWidth();
                    screen_height = ll.getHeight();
                    mPosX = screen_width/2-100;
                    mPosY = screen_height/2-100;



                    pitch = 0;
                    roll = 0;

                    mActivePointerId = INVALID_POINTER_ID;
                    invalidate();
                    break;
                }
                case MotionEvent.ACTION_POINTER_UP:{
                    final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                            >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

                    final int pointerId = ev.getPointerId(pointerIndex);

                    if(pointerId == mActivePointerId){
                        final int newPointerIndex = pointerIndex == 0 ? 1:0;
                        mLastTouchX = ev.getX(newPointerIndex);
                        mLastTouchY = ev.getY(newPointerIndex);
                        mActivePointerId = ev.getPointerId(newPointerIndex);
                    }
                    break;
                }


            }
            return true;
        }
        return false;
    }


}
