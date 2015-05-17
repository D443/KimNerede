package com.soysalmustafa.kimnerede;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
public class Animasyon extends Activity {
    private Handler mHandler = new Handler();

    private void startTime() {
        mHandler.removeCallbacks(mUpdateTimeTask);
        mHandler.postDelayed(mUpdateTimeTask, 5000);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            // buraya ne yapmak istiyorsak o kodu yazcaz..
            startActivity(new Intent("android.intent.action.uye_giris"));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animasyon);

        Animation anim= AnimationUtils.loadAnimation(this,R.anim.animasyon);
        ImageView giris=(ImageView)findViewById(R.id.img_animate);
        anim.reset();
        giris.clearAnimation();
        giris.startAnimation(anim);
        startTime();

    }


}
