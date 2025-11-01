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

public class LightFragment extends Fragment  implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor lightmeter;
    private final float[] lastLight = new float[1];
    private boolean haveLight = false;

    public LightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_light, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        lightmeter = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (lightmeter == null) {
            // no sensors
            Toast.makeText(requireContext(), "light sensor gone", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        sensorManager.registerListener(this, lightmeter, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT)
        {
            lastLight[0] = event.values[0];
            haveLight= true;
        }

        if(haveLight) {
            float lux = lastLight[0];

        }
    }
}