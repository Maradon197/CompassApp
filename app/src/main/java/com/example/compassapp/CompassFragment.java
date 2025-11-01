package com.example.compassapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class CompassFragment extends Fragment implements SensorEventListener
{

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];
    private final float[] lastAcc = new float[3];
    private final float[] lastMag = new float[3];


    private boolean haveAcc = false;
    private boolean haveMag = false;

    private CompassView cv;

    public CompassFragment()
    {
        // constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_compass, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cv = view.findViewById(R.id.compass_view); //compass view from constructor in java class
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (accelerometer == null || magnetometer == null) {
            // no sensors
            Toast.makeText(requireContext(), "sensors gone", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        // faster
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        // less filter
        final float alpha = 0.1f; 
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            lastAcc[0] = alpha * lastAcc[0] + (1 - alpha) * event.values[0];
            lastAcc[1] = alpha * lastAcc[1] + (1 - alpha) * event.values[1];
            lastAcc[2] = alpha * lastAcc[2] + (1 - alpha) * event.values[2];
            haveAcc = true;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            lastMag[0] = alpha * lastMag[0] + (1 - alpha) * event.values[0];
            lastMag[1] = alpha * lastMag[1] + (1 - alpha) * event.values[1];
            lastMag[2] = alpha * lastMag[2] + (1 - alpha) * event.values[2];
            haveMag = true;
        }

        if (haveAcc && haveMag)
        {
            //orientation
            SensorManager.getRotationMatrix(rotationMatrix, null, lastAcc, lastMag);
            SensorManager.getOrientation(rotationMatrix, orientationAngles);

            //azimuth in here
            float azimuthInRadian = orientationAngles[0];
            float azimuthInDegrees = (float) Math.toDegrees(azimuthInRadian);
            cv.setAzimuth(azimuthInDegrees);

        }
    }
}
