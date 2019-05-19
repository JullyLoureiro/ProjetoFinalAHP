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
import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.AlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaAlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaCriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.MatrizCriterioNormalizadaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ObjetivoDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.PesoCriteriosDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.SomaColunaDao;
import br.com.juliana.loureiro.projetofinalahp.R;
import br.com.juliana.loureiro.projetofinalahp.Util.Utils;

public class TelaFuncaoAHP extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    //public static float [][] matrizCrit;
    // public static float [][] matrizCritNormalizada;

    //  public static float [][] matrizAlt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo_ahp);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        declaraObjetos();
        new ObjetivoDao(this).deletaTemp();
        new CriterioDao(this).deletaTemp();
        new AlternativaDao(this).deletaTemp();
        new ComparaCriterioDao(this).deletaTemp();
        new ComparaAlternativaDao(this).deletaTemp();
        new MatrizCriterioNormalizadaDao(this).deleta();
        new MatrizCriterioNormalizadaDao(this).deletaAlternativa();
        new PesoCriteriosDao(this).deleta();
        new PesoCriteriosDao(this).deletaAlternativa();
        new SomaColunaDao(this).deleta();
        new SomaColunaDao(this).deletaAlternativa();
    }


    // MÉTODOS NATIVOS

    @Override
    public void onBackPressed() {
        Utils.alerta(this, "Tem certeza que deseja voltar?");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.avancar:

                List<CriterioBean> listaCriterios = new CriterioDao(this).carregaCriterios();
                //  matrizCrit = new float [listaCriterios.size()][listaCriterios.size()];
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

                List<AlternativaBean> listaAlternativas = new AlternativaDao(this).carregaAlternativas();

                for (int x = 0; x < listaCriterios.size(); x++) {
                    for (int i = 0; i < listaAlternativas.size(); i++) {
                        for (int j = 0; j < listaAlternativas.size(); j++) {
                            ComparaAlternativaBean alternativaBean = new ComparaAlternativaBean();
                            alternativaBean.setIdalternativa1(listaAlternativas.get(i).getId());
                            alternativaBean.setIdalternativa2(listaAlternativas.get(j).getId());
                            alternativaBean.setIdcriterio(listaCriterios.get(x).getId());
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

                Intent intent = new Intent(this, Preferencias.class);
                startActivity(intent);
                finish();
                break;
            default:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
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
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        MenuItem ajuda = menu.findItem(R.id.ajuda);
        MenuItem avancar = menu.findItem(R.id.avancar);
        ajuda.setVisible(false);
        avancar.setVisible(true);
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

}