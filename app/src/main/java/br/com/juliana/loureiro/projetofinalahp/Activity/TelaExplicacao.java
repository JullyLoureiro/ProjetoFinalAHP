package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import br.com.juliana.loureiro.projetofinalahp.R;

public class TelaExplicacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


        setContentView(R.layout.activity_explicacao);


    }

    public void comecar(View v) {
        Intent intent = new Intent(this, Preferencias.class);
        startActivity(intent);
        finish();
    }
}
