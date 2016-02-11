package edu.uw.motiondemo;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.media.JetPlayer;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class MotionActivity extends Activity {

    private static final String TAG = "**MOTION**";

    private DrawingSurfaceView view;

    private GestureDetectorCompat mDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion);

        view = (DrawingSurfaceView)findViewById(R.id.drawingView);

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(1000);
        animator.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v(TAG, "" + event);

        boolean gesture = mDetector.onTouchEvent(event);
        if(gesture) return true;


        int action = MotionEventCompat.getActionMasked(event);

        switch(action){
            case MotionEvent.ACTION_DOWN:
                Log.v(TAG,"Finger down!");

                //pass in the param-to-change in String and Android will find its setter
                //ObjectAnimator anim = ObjectAnimator.ofFloat(view.ball, "radius", 100, 200);
                //anim.setDuration(1000);
                //anim.setRepeatCount(ValueAnimator.INFINITE);
                //anim.setRepeatMode(ValueAnimator.REVERSE);
                //anim.start();

                AnimatorSet radiusAnim = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animations);

                ObjectAnimator xAnim = ObjectAnimator.ofFloat(view.ball, "x", event.getX());
                xAnim.setDuration(1000);
                ObjectAnimator yAnim = ObjectAnimator.ofFloat(view.ball, "x", event.getY());
                yAnim.setDuration(1500);

                AnimatorSet set = new AnimatorSet();
                set.playTogether(yAnim, xAnim);
                set.start();

                //should be synchronized!
                //view.ball.cx = event.getX();
                //view.ball.cy = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                return true;
            case MotionEvent.ACTION_MOVE:
                //shold be synchronized!
//                view.ball.cx = event.getX();
//                view.ball.cy = event.getY();
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {

            return true; //we've got this
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            float scaleFactor = .03f;

            //fling!
            Log.v(TAG, "Fling! "+ velocityX + ", " + velocityY);
            view.ball.dx = velocityX*scaleFactor;
            view.ball.dy = -1*velocityY*scaleFactor;

            return true; //we got this
        }
    }
}
