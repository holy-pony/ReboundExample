package ru.nitrobubbles.reboundexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    View view;
    Spring spring;
    final static double MAX = 1.0;
    final static double MIN = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.imageView);
        SpringSystem springSystem = SpringSystem.create();
        spring = springSystem.createSpring();
        spring.setSpringConfig(new SpringConfig(30, 3));
        spring.setCurrentValue(MAX, true);
        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {

                double springValue = spring.getCurrentValue();
                double value = MIN;
                if (springValue > MIN && springValue < MAX) {
                    value = springValue;
                } else if (springValue >= MAX) {
                    value = MAX;
                }
                view.setAlpha((float) value);
                view.setScaleX((float) springValue);
                view.setScaleY((float) springValue);
            }
        });
        view.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                spring.setEndValue(MIN);
                break;

            case MotionEvent.ACTION_UP:
                spring.setEndValue(MAX);
                break;
        }
        return true;
    }
}
