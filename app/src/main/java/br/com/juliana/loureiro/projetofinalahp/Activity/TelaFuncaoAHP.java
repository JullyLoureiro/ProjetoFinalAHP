package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.juliana.loureiro.projetofinalahp.R;

public class TelaFuncaoAHP extends AppCompatActivity {

    private RelativeLayout rltobjetivo, rltcriterio, rltalternativa, rltpreferencia;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo_ahp);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        declaraObjetos();

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.obj:
                    rltobjetivo.setVisibility(View.VISIBLE);
                    rltcriterio.setVisibility(View.GONE);
                    rltalternativa.setVisibility(View.GONE);
                    rltpreferencia.setVisibility(View.GONE);
                    return true;
                case R.id.crit:
                    rltobjetivo.setVisibility(View.GONE);
                    rltcriterio.setVisibility(View.VISIBLE);
                    rltalternativa.setVisibility(View.GONE);
                    rltpreferencia.setVisibility(View.GONE);
                    return true;
                case R.id.alt:
                    rltobjetivo.setVisibility(View.GONE);
                    rltcriterio.setVisibility(View.GONE);
                    rltalternativa.setVisibility(View.VISIBLE);
                    rltpreferencia.setVisibility(View.GONE);
                    return true;
                case R.id.pref:
                    rltpreferencia.setVisibility(View.VISIBLE);
                    rltobjetivo.setVisibility(View.GONE);
                    rltcriterio.setVisibility(View.GONE);
                    rltalternativa.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void declaraObjetos() {
        rltobjetivo = findViewById(R.id.rltobjetivo);
        rltcriterio = findViewById(R.id.rltcriterio);
        rltalternativa = findViewById(R.id.rltalternativa);
        rltpreferencia = findViewById(R.id.rltpreferencia);
    }

    public void continuar(View v) {
        if(rltobjetivo.getVisibility() == View.VISIBLE) {
            navigation.setSelectedItemId(R.id.crit);
            rltobjetivo.setVisibility(View.GONE);
            rltcriterio.setVisibility(View.VISIBLE);
            rltalternativa.setVisibility(View.GONE);
            rltpreferencia.setVisibility(View.GONE);
        } else if(rltcriterio.getVisibility() == View.VISIBLE) {
            navigation.setSelectedItemId(R.id.alt);
            rltobjetivo.setVisibility(View.GONE);
            rltcriterio.setVisibility(View.GONE);
            rltalternativa.setVisibility(View.VISIBLE);
            rltpreferencia.setVisibility(View.GONE);
        } else if(rltalternativa.getVisibility() == View.VISIBLE) {
            navigation.setSelectedItemId(R.id.pref);
            rltpreferencia.setVisibility(View.VISIBLE);
            rltobjetivo.setVisibility(View.GONE);
            rltcriterio.setVisibility(View.GONE);
            rltalternativa.setVisibility(View.GONE);
        } else {

        }
    }
}
