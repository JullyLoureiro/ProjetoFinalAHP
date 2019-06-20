package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.zip.Inflater;

import br.com.juliana.loureiro.projetofinalahp.Bean.ObjetivoBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.ObjetivoDao;
import br.com.juliana.loureiro.projetofinalahp.ListAdapter.ObjetivosList;
import br.com.juliana.loureiro.projetofinalahp.R;

public class TelaPrincipal extends AppCompatActivity {

    //private RelativeLayout rltPrincipal;
    public static ListView listObjetivos;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        declaraObjetos();

        navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setSelectedItemId(R.id.navigation_home);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected( MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_ahp:
                    navigation.setSelectedItemId(R.id.navigation_home);
                    Intent intent = new Intent(TelaPrincipal.this, SobreAHP.class);
                    startActivity(intent);
                    finish();
                    return true;
            }
            return false;
        }
    };

    private void declaraObjetos() {
        //rltPrincipal = findViewById(R.id.rltPrincipal);
        listObjetivos = findViewById(R.id.listObjetivos);

        List<ObjetivoBean> listaObjetivos = new ObjetivoDao(this).carregaObjetivos();
        listObjetivos.setAdapter(new ObjetivosList(listaObjetivos, this));
    }

    public void iniciarAHP(View view) {
        Intent intent = new Intent(this, TelaFuncaoAHP.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.optionsmenu, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void ajuda(MenuItem item) {
        Intent intent = new Intent(this, Tutorial.class);
        startActivity(intent);
    }
}
