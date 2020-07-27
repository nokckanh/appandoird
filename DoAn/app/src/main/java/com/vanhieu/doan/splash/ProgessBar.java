package com.vanhieu.doan.splash;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vanhieu.doan.MainActivity;
import com.vanhieu.doan.login.LoginActivity;

public class ProgessBar extends Animation {

    Context context;
    ProgressBar progressbar;
    TextView textview;
    float from;
    float to;
    private float value;
    private Thread th;

    public ProgessBar(Context context, ProgressBar progressbar ,TextView textview, float from , float to){
        this.context = context;
        this.progressbar = progressbar;
        this.textview = textview;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        value = from + ((to - from) * interpolatedTime);
        progressbar.setProgress((int) value);
        textview.setText((int) value + " %");

        th = new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    try {
                        sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        if (value == to) {
            context.startActivities(new Intent[]{new Intent(context, LoginActivity.class)});
        }
        th.start();

    }
}
