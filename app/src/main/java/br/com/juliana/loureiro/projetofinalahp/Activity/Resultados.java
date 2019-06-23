package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.juliana.loureiro.projetofinalahp.Bean.AlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaAlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.MatrizCriterioNormalizadaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ObjetivoBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.PesoCriteriosBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.SubcriterioBean;
import br.com.juliana.loureiro.projetofinalahp.BuildConfig;
import br.com.juliana.loureiro.projetofinalahp.Dao.AlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaAlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaCriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.MatrizCriterioNormalizadaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ObjetivoDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.SomaColunaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.PesoCriteriosDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.SubcriteriosDao;
import br.com.juliana.loureiro.projetofinalahp.ListAdapter.ObjetivosList;
import br.com.juliana.loureiro.projetofinalahp.ListAdapter.ResultadosList;
import br.com.juliana.loureiro.projetofinalahp.R;
import br.com.juliana.loureiro.projetofinalahp.Util.FormatGraph;
import br.com.juliana.loureiro.projetofinalahp.Util.FormatterGrah;
import br.com.juliana.loureiro.projetofinalahp.Util.OnSwip;
import br.com.juliana.loureiro.projetofinalahp.Util.TransparentProgressDialog;
import br.com.juliana.loureiro.projetofinalahp.Util.Utils;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Resultados extends AppCompatActivity {
    private int idobjetivo = 0;
    private ListView listResultado;
    private List<PesoCriteriosBean> resultado;
    private Handler handler;
    private TransparentProgressDialog pd;
    private LinearLayout lnr;
    private TextView titulo, titulo2, titulo3;
    private CardView cardlista, cardbarra, cardpizza;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        declaraObjetos();

        Intent intent = getIntent();
        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {
                if (params.getInt("verResult", 0) != 0) {
                    idobjetivo = params.getInt("verResult", 0);
                    titulo.setText(new ObjetivoDao(this).carregaObjetivo(idobjetivo).getTitulo().toUpperCase());
                    titulo2.setText(new ObjetivoDao(this).carregaObjetivo(idobjetivo).getTitulo().toUpperCase());
                    titulo3.setText(new ObjetivoDao(this).carregaObjetivo(idobjetivo).getTitulo().toUpperCase());

                    calculaCriterios();
                } else {
                    calculaAlternativasTemp();
                }
            } else {
                calculaAlternativasTemp();
            }
        } else {
            calculaAlternativasTemp();
        }


    }

    private void declaraObjetos() {

        listResultado = findViewById(R.id.listResultado);
        lnr = findViewById(R.id.lnr);
        titulo = findViewById(R.id.titulo);
        titulo2 = findViewById(R.id.titulo2);
        titulo3 = findViewById(R.id.titulo3);
        cardlista = findViewById(R.id.cardlista);
        cardbarra = findViewById(R.id.cardbarra);
        cardpizza = findViewById(R.id.cardpizza);
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

        resultado = new PesoCriteriosDao(this).retornaResultado(idobjetivo);

        carregaGrafico();
        geraGraphPie();
        geraTabela();
    }

    private void calculaAlternativasTemp() {
        titulo.setText(new ObjetivoDao(this).carregaObjetivoTemp(idobjetivo).getTitulo());
        titulo2.setText(new ObjetivoDao(this).carregaObjetivoTemp(idobjetivo).getTitulo());
        titulo3.setText(new ObjetivoDao(this).carregaObjetivoTemp(idobjetivo).getTitulo());

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

        resultado = new PesoCriteriosDao(this).retornaResultadoTemp(idobjetivo);

        if (idobjetivo == 0) {
            salvaDados();
        }

        Utils.deletaTemp(this);
        geraTabela();

    }

    private void geraTabela() {
        listResultado.setAdapter(new ResultadosList(resultado, this));
    }

    private void geraGrafico() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < resultado.size(); i++) {
            entries.add(new BarEntry(i, (float) resultado.get(i).getPerc()));
        }

        BarDataSet dataset = new BarDataSet(entries, "");

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


        if (idobjetivo == 0) {
            salvaDados();
        } /*else {
            ObjetivoBean objetivoBean = new ObjetivoDao(this).carregaObjetivoTemp(idobjetivo);
            new ObjetivoDao(this).atualizaObjetivo(objetivoBean);

            new ObjetivoDao(this).deletaVinculadosObjetivo(idobjetivo);

            List<CriterioBean> criterios = new CriterioDao(this).carregaCriterios();
            for (int i = 0; i < criterios.size(); i++) {
                int idcriterio = new CriterioDao(this).insereCriterio2(criterios.get(i), idobjetivo);
                new ComparaCriterioDao(this).atualizaIdCriterio(idcriterio, criterios.get(i).getId());
                new ComparaAlternativaDao(this).atualizaIdCriterio(idcriterio, criterios.get(i).getId());
            }


            List<SubcriterioBean> subcriterios = new SubcriteriosDao(this).carregaCriterios();
            for (int i = 0; i < subcriterios.size(); i++) {
                new SubcriteriosDao(this).insereCriterio(subcriterios.get(i), idobjetivo);
            }

            List<AlternativaBean> alternativas = new AlternativaDao(this).carregaAlternativas();
            for (int i = 0; i < alternativas.size(); i++) {
                int idalt = new AlternativaDao(this).insereAlternativa2(alternativas.get(i), idobjetivo);
                new ComparaAlternativaDao(this).atualizaIdAlternativa(idalt, alternativas.get(i).getId());
            }


            List<ComparaCriterioBean> compcriterios = new ComparaCriterioDao(this).carregaComparacoesTemp();
            for (int i = 0; i < compcriterios.size(); i++) {
                new ComparaCriterioDao(this).insereComparacoes2(compcriterios.get(i), idobjetivo);
            }

            List<ComparaAlternativaBean> compalternativas = new ComparaAlternativaDao(this).carregaComparacoesTemp();
            for (int i = 0; i < compalternativas.size(); i++) {
                new ComparaAlternativaDao(this).insereComparacoes2(compalternativas.get(i), idobjetivo);
            }

            List<ObjetivoBean> listaObjetivos = new ObjetivoDao(this).carregaObjetivos();
            TelaPrincipal.listObjetivos.setAdapter(new ObjetivosList(listaObjetivos, this));
        }*/
        Utils.deletaTemp(this);
    }

    private void carregaGrafico() {
        BarChart chart = findViewById(R.id.barchart);
        chart.clear();
        chart.clearAnimation();
        chart.clearAllViewportJobs();

        BarData data = new BarData(getDataSet());
        chart.setData(data);
        chart.invalidate();

        Description d = new Description();
        d.setText("");
        chart.setDrawGridBackground(false);
        chart.setDescription(d);

        chart.animateXY(1000, 1000);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);
        chart.setFitBars(false);
        chart.setHighlightFullBarEnabled(false);
        chart.getLegend().setEnabled(false);

        final ArrayList<String> xAxisLabel = new ArrayList<>();
        for (int i = 0; i < resultado.size(); i++) {
            if (resultado.get(i).getAlternativa().length() > 20) {
                xAxisLabel.add(resultado.get(i).getAlternativa().substring(0, 19).concat("..."));
            } else {
                xAxisLabel.add(resultado.get(i).getAlternativa());
            }
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(1);
        leftAxis.setDrawLabels(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);

        chart.setVisibleXRangeMaximum(2); // allow 20 values to be displayed at once on the x-axis, not more
        chart.moveViewToX(-1);

        Legend l = chart.getLegend();


    }

    private ArrayList<IBarDataSet> getDataSet() {
        ArrayList<IBarDataSet> dataSets;
        dataSets = new ArrayList<>();

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        for (int i = 0; i < resultado.size(); i++) {
            BigDecimal v = new BigDecimal(resultado.get(i).getPerc()).setScale(2, BigDecimal.ROUND_HALF_UP);
            BarEntry v1e1 = new BarEntry(i, (float) v.doubleValue());
            valueSet1.add(v1e1);
            BarDataSet barDataSet1;
            barDataSet1 = new BarDataSet(valueSet1, "");
            barDataSet1.setColor(getResources().getColor(R.color.bg_screen3));
            //barDataSet1.setValueFormatter(new FormatterGrah());
            barDataSet1.setValueTextSize(17);
            dataSets.add(barDataSet1);
        }

        return dataSets;
    }

    private void geraGraphPie() {
        PieChart pieChart = findViewById(R.id.piechart);
        pieChart.clear();
        pieChart.clearAnimation();
        pieChart.clearAllViewportJobs();

        Description d = new Description();
        d.setText("");
        pieChart.setDescription(d);

        List<PieEntry> lista2 = new ArrayList<>();


        for(int i =0; i< resultado.size(); i++) {
            BigDecimal v = new BigDecimal(resultado.get(i).getPerc()).setScale(2, BigDecimal.ROUND_HALF_UP);
            lista2.add(new PieEntry((float)v.doubleValue(),resultado.get(i).getAlternativa()));
        }


        PieDataSet dataSet = new PieDataSet(lista2, "");

        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueFormatter(new IValueFormatter2());

        PieData data = new PieData(dataSet);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(30);

        pieChart.setData(data);

        int[] VORDIPLOM_COLORS = {
                Color.rgb(205,58,87),
                Color.rgb(39,186,209),
                Color.rgb(239,202,54),
                Color.rgb(134,19,192),
                Color.rgb(55,168,96)
        };

        dataSet.setColors(VORDIPLOM_COLORS);

        pieChart.animateXY(1000, 1000);

        Legend l = pieChart.getLegend();
        l.setTextSize(20f);
        l.setDrawInside(false);
        l.setTextColor(Color.BLACK);
        l.setEnabled(false);

        pieChart.setHoleRadius(20);
        pieChart.setTransparentCircleRadius(25);

        pieChart.invalidate();

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

        List<ObjetivoBean> listaObjetivos = new ObjetivoDao(this).carregaObjetivos();
        TelaPrincipal.listObjetivos.setAdapter(new ObjetivosList(listaObjetivos, this));
    }

    private ArrayList<IBarDataSet> getDataSet(List<PesoCriteriosBean> resultado) {
        ArrayList<IBarDataSet> dataSets;
        dataSets = new ArrayList<>();

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        for (int i = 0; i < resultado.size(); i++) {
            BarEntry v1e1 = new BarEntry(i, (float) resultado.get(i).getPerc());
            valueSet1.add(v1e1);
            BarDataSet barDataSet1;
            barDataSet1 = new BarDataSet(valueSet1, new AlternativaDao(this).retornaDescricao2(resultado.get(i).getIdalternativa()));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_resultados, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    public class IValueFormatter2 extends ValueFormatter {

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return "";

        }
    }


    public void editarObjetivo(MenuItem item) {
        Intent intent = new Intent(this, TelaFuncaoAHP.class);
        Bundle params = new Bundle();
        params.putInt("idobjetivo", idobjetivo);
        intent.putExtras(params);
        startActivity(intent);
        finish();
    }

    public void mudarVisualizacao(MenuItem item) {
        if(cardlista.getVisibility()==View.VISIBLE) {
            item.setIcon(ActivityCompat.getDrawable(this, R.drawable.pie));
            cardlista.setVisibility(View.GONE);
            cardbarra.setVisibility(View.VISIBLE);
            cardpizza.setVisibility(View.GONE);
            carregaGrafico();
        } else if(cardbarra.getVisibility()==View.VISIBLE) {
            item.setIcon(ActivityCompat.getDrawable(this, R.drawable.list));
            cardlista.setVisibility(View.GONE);
            cardbarra.setVisibility(View.GONE);
            cardpizza.setVisibility(View.VISIBLE);
           geraGraphPie();
        } else {
            item.setIcon(ActivityCompat.getDrawable(this, R.drawable.graph));
            cardlista.setVisibility(View.VISIBLE);
            cardbarra.setVisibility(View.GONE);
            cardpizza.setVisibility(View.GONE);
        }
    }

    public void compartilharResultado(MenuItem item) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            handler = new Handler();
            pd = new TransparentProgressDialog(this, R.drawable.loading, "Gerando arquivo...");
            pd.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    gerarExcel();
                    //todo: PDF
                }
            }).start();
        }
    }

    public void gerarExcel() {
        String csvFile = "resultados_" + idobjetivo + ".xls";

        File directory = new File(Environment.getExternalStorageDirectory() + "/appAHP/");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        final File file = new File(directory, csvFile);

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook;
        try {
            workbook = Workbook.createWorkbook(file, wbSettings);
            WritableSheet sheet = workbook.createSheet("Resultados AHP", 0);

            sheet.addCell(new Label(0, 0, "ALTERNATIVA"));
            sheet.addCell(new Label(1, 0, "PERCENTUAL"));

            for (int i = 0; i < resultado.size(); i++) {
                sheet.addCell(new Label(0, i + 1, new AlternativaDao(this).retornaDescricao2(resultado.get(i).getIdalternativa())));
                sheet.addCell(new Label(1, i + 1, String.valueOf(new BigDecimal(resultado.get(i).getPerc()).setScale(2, BigDecimal.ROUND_HALF_UP))));
            }

            workbook.write();
            try {
                workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    pd.cancel();
                    Intent intentShareFile = new Intent(Intent.ACTION_SEND_MULTIPLE);

                    intentShareFile.setType("application/x-excel");
                    Uri uri = Uri.fromFile(file);
                    intentShareFile.putExtra(Intent.EXTRA_STREAM, uri);

                    intentShareFile.putExtra(Intent.EXTRA_TEXT, "");

                    startActivity(Intent.createChooser(intentShareFile, "Onde deseja compartilhar?"));

                }
            });
        } catch (IOException e) {
            e.printStackTrace();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Resultados.this, "Ocorreu uma falha ao gerar a planilha, tente novamente!", Toast.LENGTH_LONG).show();
                }
            });

        } catch (RowsExceededException e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Resultados.this, "Ocorreu uma falha ao gerar a planilha, tente novamente!", Toast.LENGTH_LONG).show();
                }
            });
        } catch (WriteException e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Resultados.this, "Ocorreu uma falha ao gerar a planilha, tente novamente!", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            //Usuario marcou para não ser incomodado com a requisição
            if (!showRationale) {
                Toast.makeText(this, "Dê permissão de armazanamento para continuar!", Toast.LENGTH_LONG).show();
            }
        } else {
            compartilharResultado(null);
        }

    }

}
