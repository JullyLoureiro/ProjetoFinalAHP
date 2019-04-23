package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.com.juliana.loureiro.projetofinalahp.R;

public class Preferencias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        declaraObjetos();
    }

    @Override
    public void onBackPressed() {
        //todo: alert confirma sair
        finish();
        super.onBackPressed();
    }

    private void declaraObjetos() {

    }

}
