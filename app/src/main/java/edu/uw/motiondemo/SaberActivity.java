package edu.uw.motiondemo;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.view.MotionEventCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class SaberActivity extends Activity implements SensorEventListener {

    private static final String TAG = "**SABER**";

    //TODO: Add instance variables
    private SoundPool soundPool;
    private int[] soundIds;
    private boolean[] soundLoad;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saber);

        initializeSoundPool();

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        /*
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL); //get all types of sensors
        for (Sensor sensor : sensors) {
            Log.v(TAG, sensor + "");
        }
        */

        //get the first option for the particular sensor
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (mAccelerometer == null) { //if we don't have accelerometer, exit/finish
            Log.v(TAG, "No accelerometer");
            finish();
        }
    }

    //we only register sensor when actually using the app to save battery
    @Override
    protected void onResume() {
        //register sensor
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        super.onResume();
    }

    @Override
    protected void onPause() {
        //unregister sensor
        mSensorManager.unregisterListener(this, mAccelerometer);

        super.onPause();
    }

    //helper method for setting up the sound pool
    @SuppressWarnings("deprecation")
    private void initializeSoundPool(){
        //TODO: Create the SoundPool
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            //API >= 21
            AudioAttributes aBuilder =
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .setUsage(AudioAttributes.USAGE_GAME)
                            .build();
            SoundPool.Builder sBuilder = new SoundPool.Builder();
            soundPool = sBuilder.setMaxStreams(2)
                    .setAudioAttributes(aBuilder)
                    .build();
        }
        else {
            //API < 21
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        }
        //TODO: Load the sounds
        final int soundOn = soundPool.load(this, R.raw.saber_on, 1);
        final int sound1 = soundPool.load(this, R.raw.saber_swing1, 1);
        final int sound2 = soundPool.load(this, R.raw.saber_swing2, 1);
        final int sound3 = soundPool.load(this, R.raw.saber_swing3, 1);
        final int sound4 = soundPool.load(this, R.raw.saber_swing4, 1);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                /*if (status == 0) {
                    Log.v(TAG, "Load " + sampleId);
                    soundPool.play(soundOn, 1, 1, 1, -1, 1);
                } else if (status == 1) {
                    Log.v(TAG, "Load " + sampleId);
                    soundPool.play(sound1, 1, 1, 1, -1, 1);
                } else if (status == 2) {
                    Log.v(TAG, "Load " + sampleId);
                    soundPool.play(sound2, 1, 1, 1, -1, 1);
                } else if (status == 3) {
                    Log.v(TAG, "Load " + sampleId);
                    soundPool.play(sound3, 1, 1, 1, -1, 1);
                } else if (status == 4) {
                    Log.v(TAG, "Load " + sampleId);
                    soundPool.play(sound4, 1, 1, 1, -1, 1);
                } else {
                    Log.v(TAG, "Not loading");
                }*/
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                handleTap(event.getX(), event.getY());
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    //helper method for handling tap logic
    public void handleTap(double x, double y){
        View view = findViewById(R.id.saberView);
        int width = view.getWidth();
        int height = view.getHeight();

        int quadrant;
        if(x > width/2 && y < height/2) quadrant = 1;
        else if(x < width/2 && y < height/2) quadrant = 2;
        else if(x < width/2 && y > height/2) quadrant = 3;
        else quadrant = 4;

        Log.v(TAG, "Tap in quadrant: "+quadrant);

        //TODO: Play sound depending on quadrant!
        if (quadrant == 1) {

        } else if (quadrant == 2) {

        } else if (quadrant == 3) {

        } else if (quadrant == 4) {

        }

    }

    //for immersive full-screen (from API guide)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            final View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.v(TAG, event + "");

        if (Math.abs(event.values[0]) > 1.0) {
            Log.v(TAG, "Shook left" + event.values[0]);
            //playSound(1);
        } else if (Math.abs(event.values[1]) > 1.0) {
            Log.v(TAG, "Shook up" +event.values[1]);
            //paySound(0);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
