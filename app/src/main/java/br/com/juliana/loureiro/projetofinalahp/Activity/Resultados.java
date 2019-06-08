package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.AlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaAlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.MatrizCriterioNormalizadaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ObjetivoBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.PesoCriteriosBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.SubcriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.AlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaAlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaCriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.MatrizCriterioNormalizadaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ObjetivoDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.SomaColunaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.PesoCriteriosDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.SubcriteriosDao;
import br.com.juliana.loureiro.projetofinalahp.R;
import br.com.juliana.loureiro.projetofinalahp.Util.FormatGraph;
import br.com.juliana.loureiro.projetofinalahp.Util.Utils;

public class Resultados extends AppCompatActivity {
    private int idobjetivo;
    private ListView listResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listResultado = findViewById(R.id.listResultado);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {
                if (params.getInt("verResult", 0) != 0) {
                    idobjetivo = params.getInt("verResult", 0);
                    calculaCriterios();
                }
            } else {
                calculaAlternativasTemp();
            }
        } else {
            calculaAlternativasTemp();
        }



    }

    private void calculaAlternativas(int idobjetivo) {
        //NORMALIZAÇÃO PARTE 1 - SOMA DAS COLUNAS
        List<CriterioBean> listaCriterios = new CriterioDao(this).carregaCriteriosObjetivo(idobjetivo);
        for (int i = 0; i < listaCriterios.size(); i++) {
            new SomaColunaDao(this).somaColunasAlternativas2(listaCriterios.get(i).getId(), idobjetivo);
        }

        List<ComparaAlternativaBean> listaComparacao = new ComparaAlternativaDao(this).carregaComparacoes3(idobjetivo);

        for (int i = 0; i < listaComparacao.size(); i++) {
            float soma = new SomaColunaDao(this).retornaSomaAlternativa(listaComparacao.get(i).getIdalternativa2(), listaComparacao.get(i).getIdcriterio());

            MatrizCriterioNormalizadaBean matrizCriterioNormalizadaBean = new MatrizCriterioNormalizadaBean();
            matrizCriterioNormalizadaBean.setIdalternativa1(listaComparacao.get(i).getIdalternativa1());
            matrizCriterioNormalizadaBean.setIdalternativa2(listaComparacao.get(i).getIdalternativa2());
            matrizCriterioNormalizadaBean.setIdcriterio(listaComparacao.get(i).getIdcriterio());

            double imp = listaComparacao.get(i).getImportancia();

            double importancia = imp / soma;

            matrizCriterioNormalizadaBean.setImportancia((float) importancia);
            new MatrizCriterioNormalizadaDao(this).insereMatrizNormalizadaAlternativa(matrizCriterioNormalizadaBean);

        }

        //NORMALIZAÇÃO PARTE 2 - MÉDIA ARITMÉTICA DAS LINHAS (PESO)
        int qtdAlt = new AlternativaDao(this).retornaQtd2(idobjetivo);
        List<PesoCriteriosBean> pesosAlternativas = new PesoCriteriosDao(this).somaLinhasAlternativa(qtdAlt);

        for (int i = 0; i < pesosAlternativas.size(); i++) {
            double pesocriterio = new PesoCriteriosDao(this).retornaPeso(pesosAlternativas.get(i).getIdcrit());


            double multi = pesocriterio * pesosAlternativas.get(i).getSoma();
            pesosAlternativas.get(i).setTotaldivisao((float) multi);
            new PesoCriteriosDao(this).atualizaTotal(pesosAlternativas.get(i));

        }

        List<PesoCriteriosBean> resultado = new PesoCriteriosDao(this).retornaResultado();

        geraGrafico(resultado);

        geraTabela(resultado);
    }

    private void calculaAlternativasTemp() {
        //NORMALIZAÇÃO PARTE 1 - SOMA DAS COLUNAS
        List<CriterioBean> listaCriterios = new CriterioDao(this).carregaCriterios();
        for (int i = 0; i < listaCriterios.size(); i++) {
            new SomaColunaDao(this).somaColunasAlternativas(listaCriterios.get(i).getId());
        }

        List<ComparaAlternativaBean> listaComparacao = new ComparaAlternativaDao(this).carregaComparacoes2();

        for (int i = 0; i < listaComparacao.size(); i++) {
            float soma = new SomaColunaDao(this).retornaSomaAlternativa(listaComparacao.get(i).getIdalternativa2(), listaComparacao.get(i).getIdcriterio());

            MatrizCriterioNormalizadaBean matrizCriterioNormalizadaBean = new MatrizCriterioNormalizadaBean();
            matrizCriterioNormalizadaBean.setIdalternativa1(listaComparacao.get(i).getIdalternativa1());
            matrizCriterioNormalizadaBean.setIdalternativa2(listaComparacao.get(i).getIdalternativa2());
            matrizCriterioNormalizadaBean.setIdcriterio(listaComparacao.get(i).getIdcriterio());

            double imp = listaComparacao.get(i).getImportancia();

            double importancia = imp / soma;

            matrizCriterioNormalizadaBean.setImportancia((float) importancia);
            new MatrizCriterioNormalizadaDao(this).insereMatrizNormalizadaAlternativa(matrizCriterioNormalizadaBean);

        }

        //NORMALIZAÇÃO PARTE 2 - MÉDIA ARITMÉTICA DAS LINHAS (PESO)
        int qtdAlt = new AlternativaDao(this).retornaQtd();
        List<PesoCriteriosBean> pesosAlternativas = new PesoCriteriosDao(this).somaLinhasAlternativa(qtdAlt);

        for (int i = 0; i < pesosAlternativas.size(); i++) {
            double pesocriterio = new PesoCriteriosDao(this).retornaPeso(pesosAlternativas.get(i).getIdcrit());


            double multi = pesocriterio * pesosAlternativas.get(i).getSoma();
            pesosAlternativas.get(i).setTotaldivisao((float) multi);
            new PesoCriteriosDao(this).atualizaTotal(pesosAlternativas.get(i));

        }

        List<PesoCriteriosBean> resultado = new PesoCriteriosDao(this).retornaResultado();

        geraGrafico(resultado);
        geraTabela(resultado);
    }

    private void geraTabela(List<PesoCriteriosBean> resultado){

    }

    private void geraGrafico(List<PesoCriteriosBean> resultado) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < resultado.size(); i++) {
            entries.add(new BarEntry(i, (float) resultado.get(i).getPerc()));
        }

        BarDataSet dataset = new BarDataSet(entries, "legenda");

        //BarChart chart = new BarChart(this);
        BarChart chart = findViewById(R.id.barchart);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);
        chart.setFitBars(false);
        chart.setHighlightFullBarEnabled(false);

        //setContentView(chart);

        BarData data = new BarData(dataset);
        //BarData data = new BarData(getDataSet(resultado));

        chart.setData(data);

        Description description = new Description();
        description.setText("");
        chart.setDescription(description);

        dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);

        XAxis xAxis = chart.getXAxis();
        // xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);

        chart.animateY(1000);
        chart.invalidate();
        //chart.saveToGallery("mychart.jpg", 85);
        //<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

        Utils.deletaTemp(this);

        salvaDados();
    }

    private void salvaDados() {
        ObjetivoBean objetivoBean = new ObjetivoDao(this).carregaObjetivosTemp();
        int id = new ObjetivoDao(this).insereObjetivo2(objetivoBean);


        List<CriterioBean> criterios = new CriterioDao(this).carregaCriterios();
        for (int i = 0; i < criterios.size(); i++) {
            int idcriterio = new CriterioDao(this).insereCriterio2(criterios.get(i), id);
            new ComparaCriterioDao(this).atualizaIdCriterio(idcriterio, criterios.get(i).getId());
            new ComparaAlternativaDao(this).atualizaIdCriterio(idcriterio, criterios.get(i).getId());
        }


        List<SubcriterioBean> subcriterios = new SubcriteriosDao(this).carregaCriterios();
        for (int i = 0; i < subcriterios.size(); i++) {
            new SubcriteriosDao(this).insereCriterio(subcriterios.get(i), id);
        }

        List<AlternativaBean> alternativas = new AlternativaDao(this).carregaAlternativas();
        for (int i = 0; i < alternativas.size(); i++) {
            int idalt = new AlternativaDao(this).insereAlternativa2(alternativas.get(i), id);
            new ComparaAlternativaDao(this).atualizaIdAlternativa(idalt, alternativas.get(i).getId());
        }


        List<ComparaCriterioBean> compcriterios = new ComparaCriterioDao(this).carregaComparacoesTemp();
        for (int i = 0; i < compcriterios.size(); i++) {
            new ComparaCriterioDao(this).insereComparacoes2(compcriterios.get(i), id);
        }

        List<ComparaAlternativaBean> compalternativas = new ComparaAlternativaDao(this).carregaComparacoesTemp();
        for (int i = 0; i < compalternativas.size(); i++) {
            new ComparaAlternativaDao(this).insereComparacoes2(compalternativas.get(i), id);
        }

    }

    private ArrayList<IBarDataSet> getDataSet(List<PesoCriteriosBean> resultado) {
        ArrayList<IBarDataSet> dataSets;
        dataSets = new ArrayList<>();

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        for (int i = 0; i < resultado.size(); i++) {
            BarEntry v1e1 = new BarEntry(i, (float) resultado.get(i).getPerc());
            valueSet1.add(v1e1);
            BarDataSet barDataSet1;
            barDataSet1 = new BarDataSet(valueSet1, new AlternativaDao(this).retornaDescricao(resultado.get(i).getIdalternativa()));
            // barDataSet1.setColor(getResources().getColor(R.color.botaocielo));
            //barDataSet1.setValueFormatter(new FormatGraph());
            barDataSet1.setValueTextSize(15);
            dataSets.add(barDataSet1);
        }

        return dataSets;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;

    }

    @Override
    public void onBackPressed() {
        Utils.deletaTemp(this);

        //Intent intent = new Intent(this, TelaPrincipal.class);
       // startActivity(intent);
        finish();
        super.onBackPressed();
    }

    private void calculaCriterios() {

        /*List<CriterioBean> listaCriterios = new CriterioDao(this).carregaCriterios2(idobjetivo);
        for(int i = 0; i < listaCriterios.size(); i++) {
            new CriterioDao(this).insereCriterio(listaCriterios.get(i));
        }

        List<AlternativaBean> listaAlternativa = new AlternativaDao(this).carregaAlternativas(idobjetivo);
        for(int i = 0; i < listaAlternativa.size(); i++) {
            new AlternativaDao(this).insereAlternativa(listaAlternativa.get(i));
        }

        List<ComparaCriterioBean> listaCompCrit = new ComparaCriterioDao(this).carregaComparacoesObjetivo(idobjetivo);
        for(int i = 0; i < listaCompCrit.size(); i++) {
            new ComparaCriterioDao(this).insereComparacoes(listaCompCrit.get(i));
        }

        List<ComparaAlternativaBean> listaCompAlt = new ComparaAlternativaDao(this).carregaComparacoesObjetivo(idobjetivo);
        for(int i = 0; i < listaCompAlt.size(); i++) {
            new ComparaAlternativaDao(this).insereComparacoes(listaCompAlt.get(i));
        }*/

        Utils.calculacriterios(this, idobjetivo);
        calculaAlternativas(idobjetivo);
    }

}
