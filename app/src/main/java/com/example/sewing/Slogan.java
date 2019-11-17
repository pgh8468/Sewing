package com.example.sewing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class Slogan extends AppCompatActivity {

    private ImageView SloganIv;
    private ImageView SloganTxIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slogan);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Slogan.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, 2000); //넘어가는 시간 설정.
    }
}
