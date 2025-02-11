package com.example.dentex.view;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dentex.R;

import android.widget.VideoView;

public class Splash extends AppCompatActivity {
        public static int SPLASH_TIME_OUT=1700;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);
            VideoView videoView = findViewById(R.id.VideoV);
            Uri uri = getUriFromRawFile(this,R.raw.load);
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
        public static Uri getUriFromRawFile(Context context,@RawRes int rawResourceId) {
            return new Uri.Builder()
                    .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                    .authority(context.getPackageName())
                    .path(String.valueOf(rawResourceId))
                    .build();
        }
    }