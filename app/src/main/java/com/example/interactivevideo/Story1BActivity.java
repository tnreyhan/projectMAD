package com.example.interactivevideo;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Story1BActivity extends AppCompatActivity {

    private VideoView videoView;
    private View optionsContainer;
    private VideoView bgVideo;

    // Gyroscope variables
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private SensorEventListener gyroscopeListener;
    private boolean hasMoved = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story1_bactivity);

        videoView = findViewById(R.id.interactiveVideo);
        optionsContainer = findViewById(R.id.optionsContainer);
        optionsContainer.setVisibility(View.GONE);  // Hide options initially

        // Load and play the interactive video
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video1b); // Ensure video1.mp4 is in res/raw folder
        videoView.setVideoURI(videoUri);
        videoView.start();

        // Show options when the video ends
        videoView.setOnCompletionListener(mp -> optionsContainer.setVisibility(View.VISIBLE));

        // Set up option button listeners
        Button optionA = findViewById(R.id.optionA);
        Button optionB = findViewById(R.id.optionB);

        optionA.setOnClickListener(v -> {
            Intent intent = new Intent(Story1BActivity.this, StoryActivity2.class);
            startActivity(intent);
            finish(); // Close current activity
        });

        optionB.setOnClickListener(v -> {
            Intent intent = new Intent(Story1BActivity.this, StoryActivity2.class);
            startActivity(intent);
            finish(); // Close current activity
        });

        // Video Background Setup
        bgVideo = findViewById(R.id.bgVideo);
        Uri bgVideoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bgdua); // Ensure indexbg.mp4 is in res/raw
        bgVideo.setVideoURI(bgVideoUri);
        bgVideo.setOnPreparedListener(mp -> {
            mp.setLooping(true); // Ensures background video loops
            // Mute the background video by setting volume to 0
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setVolume(0f, 0f); // Mute background video
        });
        bgVideo.start(); // Start playing the background video

        // Gyroscope setup
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }

        gyroscopeListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event != null && !hasMoved) {
                    float x = event.values[0];

                    if (x > 2.0f) { // Tilt to the right
                        hasMoved = true; // Set flag to prevent multiple triggers
                        Intent intent = new Intent(Story1BActivity.this, StoryActivity2.class);
                        startActivity(intent);
                        finish();
                    } else if (x < -2.0f) { // Tilt to the left
                        hasMoved = true; // Set flag to prevent multiple triggers
                        Intent intent = new Intent(Story1BActivity.this, StoryActivity2.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Not needed for this example
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        hasMoved = false; // Reset the flag when coming back to this activity
        if (gyroscopeSensor != null) {
            sensorManager.registerListener(gyroscopeListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gyroscopeSensor != null) {
            sensorManager.unregisterListener(gyroscopeListener);
        }
    }
}
