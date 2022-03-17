package com.example.springanimation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private View circle;
    float dX;
    float dY;
    int lastAction;
    float previous_location;
    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circle = findViewById(R.id.circle_imageView);
        relativeLayout = findViewById(R.id.relative_layout);
        relativeLayout.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                previous_location = event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                view.setY(event.getRawY() + dY);
                view.setX(event.getRawX() + dX);
                lastAction = MotionEvent.ACTION_MOVE;
                float length = event.getRawY() - previous_location;
                backToInitialPosition(circle,length);

                break;

            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN)
                    Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show();
                break;

            default:
                return false;
        }
        return true;
    }

    public void backToInitialPosition(View view,float length){
        SpringAnimation springAnimation = new SpringAnimation(view,DynamicAnimation.TRANSLATION_Y,-length);    // above view y is negative and below view it's positive
        springAnimation.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);       // property to add bounce to the object when
        springAnimation.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);                   // property to denote that how fast return to the final state -> high to low means fast to slow
        springAnimation.start();
    }
}
