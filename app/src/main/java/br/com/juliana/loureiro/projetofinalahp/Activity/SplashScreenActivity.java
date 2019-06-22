package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.juliana.loureiro.projetofinalahp.R;

public class SplashScreenActivity extends AppCompatActivity {
    private ImageView imgsplash;
    private TextView txvsplash;

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
        }, 3000);

    }

    private void declaraObjetos() {
        imgsplash = findViewById(R.id.imgsplash);
        txvsplash = findViewById(R.id.txvsplash);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = findViewById(R.id.lnr);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        imgsplash.clearAnimation();
        imgsplash.startAnimation(anim);

        txvsplash.clearAnimation();
        txvsplash.startAnimation(anim);

    }

    private void exibirTelaPrincipal() {
        Intent intent = new Intent(SplashScreenActivity.this,
                Saudacao.class);
        startActivity(intent);
        finish();
    }

}