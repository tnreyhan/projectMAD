package com.example.interactivevideo;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private VideoView bgVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Video Background Setup
        bgVideo = findViewById(R.id.bgVideo);
        bgVideo.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.bgbaru);
        bgVideo.start();
        bgVideo.setOnPreparedListener(mp -> mp.setLooping(true));

        // Enter Button to navigate to the next screen
        Button enterButton = findViewById(R.id.enterButton);
        enterButton.setOnClickListener(v -> startNextActivity());

        // Apply animations
        TextView jumpText = findViewById(R.id.jumpText);
        Animation fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation slideInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        jumpText.startAnimation(fadeInAnim);
        enterButton.startAnimation(slideInAnim);
    }

    private void startNextActivity() {
        Intent intent = new Intent(MainActivity.this, StoryActivity.class);
        startActivity(intent);
    }
}
