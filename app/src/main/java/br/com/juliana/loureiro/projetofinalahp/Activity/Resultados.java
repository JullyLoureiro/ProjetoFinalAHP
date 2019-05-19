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

import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaAlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.MatrizCriterioNormalizadaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.PesoCriteriosBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.AlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaAlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaCriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.MatrizCriterioNormalizadaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.SomaColunaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.PesoCriteriosDao;
import br.com.juliana.loureiro.projetofinalahp.R;
import br.com.juliana.loureiro.projetofinalahp.Util.FormatGraph;
import br.com.juliana.loureiro.projetofinalahp.Util.Utils;

public class Resultados extends AppCompatActivity {
    private int qtd;
    private int qtdAlt;

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
        List<ComparaCriterioBean> listaComparacao = new ComparaCriterioDao(this).carregaComparacoes2();

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
        qtd = new CriterioDao(this).retornaQtdCriterios();
        List<PesoCriteriosBean> pesos = new PesoCriteriosDao(this).somaLinhas(qtd);

        for (int i = 0; i < pesos.size(); i++) {
            List<ComparaCriterioBean> listaComp = new ComparaCriterioDao(this).carregaComparacoes(pesos.get(i).getIdcrit());
            for (int j = 0; j < listaComp.size(); j++) {
                float mult = pesos.get(i).getSoma() * listaComp.get(j).getImportancia();
                new PesoCriteriosDao(this).atualizaYMax(listaComp.get(j).getIdcrit1(), mult);
            }
        }

        //Com o vetor obtido, deve-se dividi-lo pelos pesos de cada critério
        List<PesoCriteriosBean> listaYMax = new PesoCriteriosDao(this).carregaYMax();
        for (int i = 0; i < listaYMax.size(); i++) {
            float div = listaYMax.get(i).getYmax() / listaYMax.get(i).getSoma();
            new PesoCriteriosDao(this).atualizaTotalDivisao(listaYMax.get(i).getIdcrit(), div);
        }


        //CÁLCULO DE INCONSISTÊNCIA
        float YmaxMedia = new PesoCriteriosDao(this).calculaMedia();
        float CI = (YmaxMedia - qtd) / (qtd - 1);

        float CR, RI = 0;
        //TABELA RI
        switch (qtd) {
            case 1:
                RI = 0;
                break;
            case 2:
                RI = 0;
                break;
            case 3:
                RI = (float) 0.52;
                break;
            case 4:
                RI = (float) 0.9;
                break;
            case 5:
                RI = (float) 1.12;
                break;
            case 6:
                RI = (float) 1.25;
                break;
            case 7:
                RI = (float) 1.35;
                break;
            case 8:
                RI = (float) 1.42;
                break;
            case 9:
                RI = (float) 1.46;
                break;
            case 10:
                RI = (float) 1.49;
                break;
            case 11:
                RI = (float) 1.52;
                break;
            case 12:
                RI = (float) 1.54;
                break;
            case 13:
                RI = (float) 1.56;
                break;
            case 14:
                RI = (float) 1.58;
                break;
            case 15:
                RI = (float) 1.59;
                break;
            default:
                break;
        }

        CR = CI / RI;
        String msg = "Cálculo de consistência: " + String.valueOf(CR);
        alerta(this, msg);

    }

    public void alerta(final Activity activity, String mensag) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alertdialog, null);

        TextView mensagem = alertLayout.findViewById(R.id.txtmensagem);
        mensagem.setText(mensag);
        Button yes = alertLayout.findViewById(R.id.yes);
        ImageView close = alertLayout.findViewById(R.id.close);

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(alertLayout);
        alert.setCancelable(true);

        final AlertDialog dialog = alert.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                calculaAlternativas();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void calculaAlternativas() {
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
        qtdAlt = new AlternativaDao(this).retornaQtd();
        List<PesoCriteriosBean> pesosAlternativas = new PesoCriteriosDao(this).somaLinhasAlternativa(qtdAlt);

        for (int i = 0; i < pesosAlternativas.size(); i++) {
           double pesocriterio = new PesoCriteriosDao(this).retornaPeso(pesosAlternativas.get(i).getIdcrit());


           double multi = pesocriterio * pesosAlternativas.get(i).getSoma();
           pesosAlternativas.get(i).setTotaldivisao((float) multi);
           new PesoCriteriosDao(this).atualizaTotal(pesosAlternativas.get(i));

        }

        List<PesoCriteriosBean> resultado = new PesoCriteriosDao(this).retornaResultado();

        geraGrafico(resultado);
    }

    private void geraGrafico(List<PesoCriteriosBean> resultado) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i = 0; i<resultado.size(); i++) {
            entries.add(new BarEntry(i, (float)resultado.get(i).getPerc()));
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
    }

    private ArrayList<IBarDataSet> getDataSet(List<PesoCriteriosBean> resultado) {
        ArrayList<IBarDataSet> dataSets;
        dataSets = new ArrayList<>();

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        for (int i = 0; i < resultado.size(); i++) {
            BarEntry v1e1 = new BarEntry(i, (float)resultado.get(i).getPerc());
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
        Intent intent = new Intent(this, TelaPrincipal.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
