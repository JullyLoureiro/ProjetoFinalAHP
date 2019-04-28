package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.MatrizCriterioNormalizadaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.PesoCriteriosBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaCriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.MatrizCriterioNormalizadaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.SomaColunaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.PesoCriteriosDao;
import br.com.juliana.loureiro.projetofinalahp.R;

public class Resultados extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calculaResultados();
    }

    private void calculaResultados() {

        //NORMALIZAÇÃO PARTE 1 - SOMA DAS COLUNAS
        new SomaColunaDao(this).somaColunas();
        List<ComparaCriterioBean> listaComparacao = new ComparaCriterioDao(this).carregaComparacoes();

        for (int i = 0; i < listaComparacao.size(); i++) {
            float soma = new SomaColunaDao(this).retornaSoma(listaComparacao.get(i).getIdcrit2());

            MatrizCriterioNormalizadaBean matrizCriterioNormalizadaBean = new MatrizCriterioNormalizadaBean();
            matrizCriterioNormalizadaBean.setIdcrit1(listaComparacao.get(i).getIdcrit1());
            matrizCriterioNormalizadaBean.setIdcrit2(listaComparacao.get(i).getIdcrit2());

            float importancia = listaComparacao.get(i).getImportancia() / soma;

            matrizCriterioNormalizadaBean.setImportancia(importancia);
            new MatrizCriterioNormalizadaDao(this).insereMatrizNormalizada(matrizCriterioNormalizadaBean);

        }

        //NORMALIZAÇÃO PARTE 2 - MÉDIA ARITMÉTICA DAS LINHAS (PESO)
        int qtd = new CriterioDao(this).retornaQtdCriterios();
        List<PesoCriteriosBean> pesos = new PesoCriteriosDao(this).somaLinhas(qtd);


        List<Float> vetorobtido = new ArrayList<>();

        for (int i = 0; i <qtd; i++) {
            vetorobtido.add((float) 0);
        }

        for (int i = 0; i < pesos.size(); i++) {
            List<ComparaCriterioBean> listaComp = new ComparaCriterioDao(this).carregaComparacoes(pesos.get(i).getIdcrit());
            for (int j = 0; j < listaComp.size(); j++) {
                float mult = pesos.get(i).getSoma() * listaComp.get(j).getImportancia();
                vetorobtido.set(j, mult + vetorobtido.get(i));
            }
        }

        //CÁLCULO DE INCONSISTÊNCIA
        float Ymax = 0;
        float CI = (Ymax - qtd) / (qtd - 1);
    }

}
