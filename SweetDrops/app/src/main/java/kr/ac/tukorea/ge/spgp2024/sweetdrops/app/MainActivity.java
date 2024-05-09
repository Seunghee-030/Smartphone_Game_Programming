package kr.ac.tukorea.ge.spgp2024.sweetdrops.app;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.spgp2024.sweetdrops.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createAnimator();
    }

    private void createAnimator() {
        /*animator = ObjectAnimator.ofFloat(binding.treeImageView, "translationX", 0.0f, -1995f);
        animator.setDuration(30000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        animator.pause();*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        //animator.resume();
    }

    @Override
    protected void onPause() {
        //animator.pause();
        super.onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startActivity(new Intent(this, SweetDropsActivity.class));
        }
        return super.onTouchEvent(event);
    }
}