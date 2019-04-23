package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.AlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.AlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaCriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
import br.com.juliana.loureiro.projetofinalahp.ListAdapter.AlternativasList;
import br.com.juliana.loureiro.projetofinalahp.ListAdapter.CriteriosList;
import br.com.juliana.loureiro.projetofinalahp.R;
import br.com.juliana.loureiro.projetofinalahp.Util.Utils;

public class TelaFuncaoAHP extends AppCompatActivity {

    private RelativeLayout rltobjetivo, rltcriterio, rltalternativa;
    private BottomNavigationView navigation;
    public static ListView listCriterios;
    public static ListView listAlternativas;

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
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.obj:
                    rltobjetivo.setVisibility(View.VISIBLE);
                    rltcriterio.setVisibility(View.GONE);
                    rltalternativa.setVisibility(View.GONE);
                    return true;
                case R.id.crit:
                    rltobjetivo.setVisibility(View.GONE);
                    rltcriterio.setVisibility(View.VISIBLE);
                    rltalternativa.setVisibility(View.GONE);
                    return true;
                case R.id.alt:
                    rltobjetivo.setVisibility(View.GONE);
                    rltcriterio.setVisibility(View.GONE);
                    rltalternativa.setVisibility(View.VISIBLE);
                    cadastraCompCriterio();
                    return true;
            }
            return false;
        }
    };

    private void cadastraCompCriterio() {
        List<CriterioBean> listaCriterios = new CriterioDao(this).carregaCriterios();

        for (int i = 0; i < listaCriterios.size(); i++) {
            for (int j = 0; j < listaCriterios.size(); j++) {
                ComparaCriterioBean comparaCriterioBean = new ComparaCriterioBean();
                comparaCriterioBean.setIdcrit1(listaCriterios.get(i).getId());
                comparaCriterioBean.setIdcrit2(listaCriterios.get(j).getId());
                if(i==j) {
                    comparaCriterioBean.setImportancia(1);
                } else {
                    comparaCriterioBean.setImportancia(0);
                }

                new ComparaCriterioDao(this).insereComparacoes(comparaCriterioBean);
            }
        }

    }


    // MÉTODOS NATIVOS

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

        listCriterios = findViewById(R.id.listCriterios);

        listAlternativas = findViewById(R.id.listAlternativas);

    }

    //CLICKS

    public void addCriterio(View view) {
        EditText edtcriterio = findViewById(R.id.edtcriterio);
        if (edtcriterio.getText().length() > 0) {
            CriterioBean criterioBean = new CriterioBean();
            criterioBean.setDescricao(edtcriterio.getText().toString());
            new CriterioDao(this).insereCriterio(criterioBean);
            List<CriterioBean> lista = new CriterioDao(this).carregaCriterios();
            listCriterios.setAdapter(new CriteriosList(lista, this));
            edtcriterio.setText("");
        } else {
            edtcriterio.setError("Informe a descrição!");
            edtcriterio.requestFocus();
        }

        Utils.hideKeyboard(this, edtcriterio);
    }

    public void addAlternativa(View view) {
        EditText edtalternativa = findViewById(R.id.edtalternativa);
        if (edtalternativa.getText().length() > 0) {
            AlternativaBean alternativaBean = new AlternativaBean();
            alternativaBean.setDescricao(edtalternativa.getText().toString());
            new AlternativaDao(this).insereAlternativa(alternativaBean);
            List<AlternativaBean> lista = new AlternativaDao(this).carregaAlternativas();
            listAlternativas.setAdapter(new AlternativasList(lista, this));
            edtalternativa.setText("");
        } else {
            edtalternativa.setError("Informe a descrição!");
            edtalternativa.requestFocus();
        }

        Utils.hideKeyboard(this, edtalternativa);
    }

    public void continuar(View v) {
        if (rltobjetivo.getVisibility() == View.VISIBLE) {
            navigation.setSelectedItemId(R.id.crit);
            rltobjetivo.setVisibility(View.GONE);
            rltcriterio.setVisibility(View.VISIBLE);
            rltalternativa.setVisibility(View.GONE);
        } else if (rltcriterio.getVisibility() == View.VISIBLE) {
            navigation.setSelectedItemId(R.id.alt);
            rltobjetivo.setVisibility(View.GONE);
            rltcriterio.setVisibility(View.GONE);
            rltalternativa.setVisibility(View.VISIBLE);
        } else if (rltalternativa.getVisibility() == View.VISIBLE) {
            cadastraCompCriterio();
        }
    }
}