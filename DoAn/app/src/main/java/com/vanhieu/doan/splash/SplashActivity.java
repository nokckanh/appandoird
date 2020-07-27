package com.vanhieu.doan.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vanhieu.doan.R;

public class SplashActivity extends AppCompatActivity {
    ProgressBar progressbar;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Anhxa();
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressbar.setMax(100);
        progressbar.setScaleY(3f);

        ProgessBar();
    }

    public void Anhxa(){
        progressbar = findViewById(R.id.progress_bar);
        textview = findViewById(R.id.text_view);
    }

    public void ProgessBar() {
        ProgessBar probar = new ProgessBar(this, progressbar,textview,0f,100f);
        probar.setDuration(3000);
        progressbar.setAnimation(probar);

    }
}