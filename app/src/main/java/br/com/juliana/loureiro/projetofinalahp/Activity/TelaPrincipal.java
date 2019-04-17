package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.ObjetivoBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.ObjetivoDao;
import br.com.juliana.loureiro.projetofinalahp.ListAdapter.ObjetivosList;
import br.com.juliana.loureiro.projetofinalahp.R;

public class TelaPrincipal extends AppCompatActivity {

    private RelativeLayout rltPrincipal;
    private ListView listObjetivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        declaraObjetos();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:
                    Intent intent = new Intent(TelaPrincipal.this, SobreAHP.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };

    private void declaraObjetos() {
        rltPrincipal = findViewById(R.id.rltPrincipal);
        listObjetivos = findViewById(R.id.listObjetivos);

        List<ObjetivoBean> listaObjetivos = new ObjetivoDao(this).carregaObjetivos();
        listObjetivos.setAdapter(new ObjetivosList(listaObjetivos, this));
    }

    public void iniciarAHP(View view) {
        Intent intent = new Intent(this, TelaFuncaoAHP.class);
        startActivity(intent);
    }


}
