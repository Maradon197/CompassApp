package com.example.compassapp;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

public class LightFragment extends Fragment  implements SensorEventListener
{

    private SensorManager sensorManager;
    private Sensor lightmeter;
    private final float[] lastLight = new float[1];
    private boolean haveLight = false;
    private TextView lightTextView;
    private View fragmentView;

    public LightFragment(){ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // fragment view
        fragmentView = inflater.inflate(R.layout.fragment_light, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lightTextView = view.findViewById(R.id.peek_a_boo_text);
        // sensor setup
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        lightmeter = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (lightmeter == null) {
            // no sensor
            Toast.makeText(requireContext(), "light sensor gone", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        // sensor listener
        sensorManager.registerListener(this, lightmeter, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        // unregister listener
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT)
        {
            lastLight[0] = event.values[0];
            haveLight= true;
        }
        if(haveLight)
        {
            // light level
            float lux = lastLight[0];
            if (lux == 0)
            {
                // dark mode
                lightTextView.setText(R.string.hiding);
                lightTextView.setTextColor(Color.WHITE);
                fragmentView.setBackgroundColor(Color.BLACK);
            }
            else
            {
                // light mode
                lightTextView.setText(R.string.peeking);
                lightTextView.setTextColor(Color.BLACK);
                fragmentView.setBackgroundColor(Color.WHITE);
            }
        }
    }
}
