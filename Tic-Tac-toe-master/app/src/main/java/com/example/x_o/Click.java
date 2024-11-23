package com.example.x_o;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.view.View;

public class Click {
    public static void animateClick(View view) {
        MediaPlayer click = MediaPlayer.create(view.getContext(),R.raw.clicko);
        click.start();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.90f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.90f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(100);
        animatorSet.start();
    }
}
