package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.AlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaAlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaSubCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ObjetivoBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.SubcriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.AlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaAlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaCriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaSubcriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.MatrizCriterioNormalizadaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ObjetivoDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.PesoCriteriosDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.SomaColunaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.SubcriteriosDao;
import br.com.juliana.loureiro.projetofinalahp.R;
import br.com.juliana.loureiro.projetofinalahp.Util.Utils;

public class TelaFuncaoAHP extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static String tituloobj = "";
    public static String descricaoobj = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo_ahp);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Utils.deletaTemp(this);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {
                if (params.getInt("idobjetivo", 0) != 0) {
                    int idobjetivo = params.getInt("idobjetivo");

                    passaDadosTemp(idobjetivo);
                }
            }
        }

        declaraObjetos();


    }


    @Override
    public void onBackPressed() {
        Utils.alerta(this, "Tem certeza que deseja voltar?");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    public void nextPreferencias(View v) {

        //COMPARA CRITÉRIOS
        List<CriterioBean> listaCriterios = new CriterioDao(this).carregaCriterios();

        for (int i = 0; i < listaCriterios.size(); i++) {
            for (int j = 0; j < listaCriterios.size(); j++) {
                ComparaCriterioBean comparaCriterioBean = new ComparaCriterioBean();
                comparaCriterioBean.setIdcrit1(listaCriterios.get(i).getId());
                comparaCriterioBean.setIdcrit2(listaCriterios.get(j).getId());
                if (i == j) {
                    comparaCriterioBean.setImportancia(1);
                } else {
                    comparaCriterioBean.setImportancia(0);
                }

                new ComparaCriterioDao(this).insereComparacoes(comparaCriterioBean);
            }
        }

        //COMPARA SUBCRITERIOS
        for (int k = 0; k < listaCriterios.size(); k++) {
            List<SubcriterioBean> listaSubCriterios = new SubcriteriosDao(this).carregaCriterios(listaCriterios.get(k).getId());

            for (int i = 0; i < listaSubCriterios.size(); i++) {
                for (int j = 0; j < listaSubCriterios.size(); j++) {
                    ComparaSubCriterioBean comparaCriterioBean = new ComparaSubCriterioBean();
                    comparaCriterioBean.setIdsubcrit1(listaSubCriterios.get(i).getId());
                    comparaCriterioBean.setIdsubcrit2(listaSubCriterios.get(j).getId());
                    comparaCriterioBean.setIdcriterio(listaCriterios.get(k).getId());
                    comparaCriterioBean.setImportancia(1);


                    new ComparaSubcriterioDao(this).insereComparacoes(comparaCriterioBean);
                }
            }
        }


        //COMPARA ALTERNATIVAS
        List<AlternativaBean> listaAlternativas = new AlternativaDao(this).carregaAlternativas();

        List<CriterioBean> listaCriterios2 = new CriterioDao(this).carregaCriteriosSub();

        for (int x = 0; x < listaCriterios2.size(); x++) {
            for (int i = 0; i < listaAlternativas.size(); i++) {
                for (int j = 0; j < listaAlternativas.size(); j++) {
                    ComparaAlternativaBean alternativaBean = new ComparaAlternativaBean();
                    alternativaBean.setIdalternativa1(listaAlternativas.get(i).getId());
                    alternativaBean.setIdalternativa2(listaAlternativas.get(j).getId());
                    alternativaBean.setIdcriterio(listaCriterios2.get(x).getId());
                    alternativaBean.setIdsubcriterio(listaCriterios2.get(x).getIdsubcriterio());
                    alternativaBean.setIdobjetivo(0);
                    if (i == j) {
                        alternativaBean.setImportancia(1);
                    } else {
                        alternativaBean.setImportancia(0);
                    }
                    new ComparaAlternativaDao(this).insereComparacoes(alternativaBean);

                }
            }
        }


        //GRAVA OBJETIVO
        ObjetivoBean objetivoBean = new ObjetivoBean();
        objetivoBean.setTitulo(tituloobj);
        objetivoBean.setDescricao(descricaoobj);
        new ObjetivoDao(this).insereObjetivo(objetivoBean);


        //VALIDA CAMPOS E VAI PARA OS JULGAMENTOS
        if(validaCampos()){
            Intent intent = new Intent(this, TelaExplicacao.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean validaCampos() {
        if(OneFragment.edttitulo.getText().length()==0){
            Toast.makeText(this, "Informe seu objetivo!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(new CriterioDao(this).carregaCriterios().isEmpty()){
            Toast.makeText(this, "Inclua critérios!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(new AlternativaDao(this).carregaAlternativas().isEmpty()){
            Toast.makeText(this, "Inclua suas alternativas!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void declaraObjetos() {
        //matrizCrit = new float[][]{};
        //matrizAlt = new float[][]{};

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(this), "OBJETIVO");
        adapter.addFragment(new TwoFragment(this), "CRITÉRIOS");
        adapter.addFragment(new ThreeFragment(this), "ALTERNATIVAS");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void passaDadosTemp(int idobjetivo) {
        ObjetivoBean objetivoBean = new ObjetivoDao(this).carregaObjetivo(idobjetivo);
        new ObjetivoDao(this).insereObjetivo(objetivoBean);

        List<ComparaCriterioBean> compcriterios = new ComparaCriterioDao(this).carregaComparacoesObjetivo(idobjetivo);
        for (int i = 0; i < compcriterios.size(); i++) {
            new ComparaCriterioDao(this).insereComparacoesTemp(compcriterios.get(i), idobjetivo);
        }

        List<ComparaAlternativaBean> compalternativas = new ComparaAlternativaDao(this).carregaComparacoesObjetivo(idobjetivo);
        for (int i = 0; i < compalternativas.size(); i++) {
            new ComparaAlternativaDao(this).insereComparacoesTemp(compalternativas.get(i), idobjetivo);
        }

        List<CriterioBean> criterios = new CriterioDao(this).carregaCriteriosObjetivo(idobjetivo);
        for (int i = 0; i < criterios.size(); i++) {
            int id = new CriterioDao(this).insereCriterio(criterios.get(i), idobjetivo);
            new ComparaCriterioDao(this).atualizaIdCriterio(id, criterios.get(i).getId());
            new ComparaAlternativaDao(this).atualizaIdCriterio(id, criterios.get(i).getId());
        }


        List<SubcriterioBean> subcriterios = new SubcriteriosDao(this).carregaCriterio(idobjetivo);
        for (int i = 0; i < subcriterios.size(); i++) {
            new SubcriteriosDao(this).insereCriterio2(subcriterios.get(i), idobjetivo);
        }

        List<AlternativaBean> alternativas = new AlternativaDao(this).carregaAlternativas(idobjetivo);
        for (int i = 0; i < alternativas.size(); i++) {
            int id = new AlternativaDao(this).insereAlternativaTemp(alternativas.get(i), idobjetivo);
             new ComparaAlternativaDao(this).atualizaIdAlternativa(id, alternativas.get(i).getId());
        }



    }

}