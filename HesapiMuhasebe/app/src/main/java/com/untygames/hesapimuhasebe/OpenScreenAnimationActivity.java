package com.untygames.hesapimuhasebe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class OpenScreenAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_screen_animation);

        ImageView image = findViewById(R.id.open_screen_image);

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.zoomin);
        image.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                startActivity(new Intent(OpenScreenAnimationActivity.this,LoginActicity.class));
                finish();
            }
        });

    }
}