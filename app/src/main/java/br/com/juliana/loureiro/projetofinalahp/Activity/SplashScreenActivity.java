package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

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

        /*Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = findViewById(R.id.lnr);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        imgsplash.clearAnimation();
        imgsplash.startAnimation(anim);*/

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                imgsplash.animate().rotationBy(360).withEndAction(this).setDuration(3000).setInterpolator(new LinearInterpolator()).start();
            }
        };

        imgsplash.animate().rotationBy(360).withEndAction(runnable).setDuration(3000).setInterpolator(new LinearInterpolator()).start();
        imgsplash.animate().rotationBy(2000);
    }

    private void exibirTelaPrincipal() {
        Intent intent = new Intent(SplashScreenActivity.this,
                Saudacao.class);
        startActivity(intent);
        finish();
    }

}