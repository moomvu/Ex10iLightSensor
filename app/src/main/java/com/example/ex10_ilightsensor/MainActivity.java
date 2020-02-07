package com.example.ex10_ilightsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    SensorManager sensorManager;
    Sensor lightSensor;
    SensorEventListener listener;
    TextView bt1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1=(TextView)findViewById(R.id.txtNumber);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        listener = new SensorEventListener()
        {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Toast.makeText(MainActivity.this, "accuracy changed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSensorChanged(SensorEvent event)
            {
               bt1.setText(String.valueOf(event.values[0]));
                int grayShade = (int) event.values[0];
                if (grayShade > 255) grayShade = 255;

                // http://www.android-examples.com/set-change-screen-brightness-in-android-programmatically/
                //Settings.System.putInt(MainActivity.this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, grayShade);

                bt1.setTextColor(Color.rgb(255 - grayShade, 255 - grayShade, 255 - grayShade));
                bt1.setBackgroundColor(Color.rgb(grayShade, grayShade, grayShade));
            }
        };
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(listener);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener(listener, lightSensor,sensorManager.SENSOR_DELAY_NORMAL);
    }
}
