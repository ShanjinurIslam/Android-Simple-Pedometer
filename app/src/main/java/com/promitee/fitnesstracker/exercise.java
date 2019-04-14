package com.promitee.fitnesstracker;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class exercise extends Fragment implements SensorEventListener {

    //step related variables
    boolean val ;
    private ArcView count;
    private ArcView calorie;
    private ArcView mile;
    boolean activityRunning;
    int steps ;
    int counts = 0 ;
    float cal ;
    Button b ;
    float miles ;
    int lapcount = 0 ;
    int client_id ;
    private SensorManager sensorManager;
    sqliteHelper database ;

    //stop watch related variables
    TextView stopwatch ;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;

    public exercise() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise, container, false);

        val = false ;
        count = view.findViewById(R.id.myArc) ;
        b = view.findViewById(R.id.button) ;
        calorie = view.findViewById(R.id.Calories) ;
        mile = view.findViewById(R.id.kms) ;
        database = new sqliteHelper(view.getContext()) ;

        client_id = getArguments().getInt("client_id") ;
        steps = database.getSteps(client_id) ;
        mile.invalidate();
        count.invalidate();
        calorie.invalidate();

        Log.d("steps",String.valueOf(steps)) ;
        cal = (float) (1500);
        miles = (float) (steps*(1.0/2200.0));

        Toast.makeText(view.getContext(),"Current Settings : Max Steps: "+steps+" Max Cal: "+cal+" Max Distance: "+miles,Toast.LENGTH_SHORT).show();

        sensorManager = (SensorManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SENSOR_SERVICE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });


        client_id = getArguments().getInt("client_id");
        Log.d("in exercise",String.valueOf(client_id)) ;

        MillisecondTime = 0L ;
        StartTime = 0L ;
        TimeBuff = 0L ;
        UpdateTime = 0L ;
        Seconds = 0 ;
        Minutes = 0 ;
        MilliSeconds = 0 ;
        stopwatch = view.findViewById(R.id.watch) ;
        handler = new Handler() ;

        return view;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning) {
            if (event.values[0] == 1.0f && val==true) {
                counts++ ;
                if(counts%100==0){
                    lapcount++ ;
                    String s = "Lap No: "+String.valueOf(lapcount)+ " Remaining Steps : "+ String.valueOf(steps-counts) ;
                    Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_LONG).show();
                }
            }
            count.setProgress((int)(counts*(1.0/(float)database.getSteps(client_id))*100),String.valueOf(counts)) ;
            count.invalidate();

            calorie.setProgress((int)(getArguments().getFloat("weight")*counts*0.26/cal),String.valueOf(new DecimalFormat(".##").format(getArguments().getFloat("weight")*counts*0.26/cal))) ;
            calorie.invalidate();

            mile.setProgress((int)(counts*1.0/(2200*miles)),String.valueOf(new DecimalFormat(".##").format(counts*(1.0/2200.0)))) ;
            mile.invalidate();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }
        else {

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        activityRunning = false;
        // if you unregister the last listener, the hardware will stop detecting step events
//        sensorManager.unregisterListener(this);
    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            stopwatch.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };

    public void start(){
        if(val==false){
            val = true ;
            Toast.makeText(getActivity().getApplicationContext(),"Start Walking. Max Calibration Time Required : 30s",Toast.LENGTH_SHORT).show();
            b.setText("Stop");
            b.setBackgroundResource(R.drawable.stopshape);

            StartTime = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);
        }
        else{
            val = false ;
            handler.removeCallbacks(runnable);

            database.insertToClientHistory(getArguments().getInt("client_id"),counts,(float)(getArguments().getFloat("weight")*counts*0.26/cal)) ;

            counts = 0 ;
            MillisecondTime = 0L ;
            StartTime = 0L ;
            TimeBuff = 0L ;
            UpdateTime = 0L ;
            Seconds = 0 ;
            Minutes = 0 ;
            MilliSeconds = 0 ;
            stopwatch.setText("00:00:00");

            Toast.makeText(getActivity().getApplicationContext(),"Workout Finished",Toast.LENGTH_SHORT).show();
            b.setText("Start");

            b.setBackgroundResource(R.drawable.buttonshape);

            count.setProgress(0,String.valueOf(0)) ;
            count.invalidate();

            calorie.setProgress(0,String.valueOf(0)) ;
            calorie.invalidate();

            mile.setProgress(0,String.valueOf(0)) ;
            mile.invalidate();

        }
    }
}




