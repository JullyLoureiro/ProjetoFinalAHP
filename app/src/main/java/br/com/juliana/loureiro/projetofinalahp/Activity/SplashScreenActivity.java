package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import br.com.juliana.loureiro.projetofinalahp.R;

public class SplashScreenActivity extends AppCompatActivity {
    private ImageView imgsplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        declaraObjetos();

        Handler handle = new Handler();

        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                exibirTelaPrincipal();
            }
        }, 2000);

    }

    private void declaraObjetos() {
        imgsplash = findViewById(R.id.imgsplash);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = findViewById(R.id.lnr);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        imgsplash.clearAnimation();
        imgsplash.startAnimation(anim);

    }

    private void exibirTelaPrincipal() {
        Intent intent = new Intent(SplashScreenActivity.this,
                Tutorial.class);
        startActivity(intent);
        finish();
    }

}