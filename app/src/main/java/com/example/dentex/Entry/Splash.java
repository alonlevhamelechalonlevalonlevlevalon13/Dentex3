package com.example.dentex.Entry;

import android.content.Intent;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dentex.R;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

    public class Splash extends AppCompatActivity {
        public static int SPLASH_TIME_OUT=5500;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);
            VideoView videoView = findViewById(R.id.VideoV);
            String videoPath = "andoird.resouce://"+getPackageName()+"/"+R.raw.load;
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
            videoView.start();
            loading();
        }
        public void loading(){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent= new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_TIME_OUT);
        }
    }