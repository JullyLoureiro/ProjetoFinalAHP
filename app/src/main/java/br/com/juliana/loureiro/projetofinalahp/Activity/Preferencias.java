package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.app.Activity;
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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import br.com.juliana.loureiro.projetofinalahp.Bean.MatrizCriterioNormalizadaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ObjetivoBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.PesoCriteriosBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.AlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaAlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaCriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.MatrizCriterioNormalizadaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ObjetivoDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.PesoCriteriosDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.SomaColunaDao;
import br.com.juliana.loureiro.projetofinalahp.R;
import br.com.juliana.loureiro.projetofinalahp.Util.OnSwip;
import br.com.juliana.loureiro.projetofinalahp.Util.Utils;

public class Preferencias extends AppCompatActivity {
    private Button criterio1, criterio2, alternativa1, alternativa2;
    private SeekBar seekBar, seekBar2;
    private RelativeLayout rltpreferencia, rltpreferencia2;
    private ImageView anterior, proximo, anterior2, proximo2;
    private List<ComparaCriterioBean> listaComp;
    private List<ComparaAlternativaBean> listaCompAlt;
    private TextView txvtitulo2;
    int i = 0;
    int critImportancia = 1, altimportancia = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        declaraObjetos();
    }

    @Override
    public void onBackPressed() {
        Utils.alerta(this, "Tem certeza que deseja voltar?");
    }

    private void declaraObjetos() {
        criterio1 = findViewById(R.id.criterio1);
        criterio2 = findViewById(R.id.criterio2);
        alternativa1 = findViewById(R.id.alternativa1);
        alternativa2 = findViewById(R.id.alternativa2);
        seekBar = findViewById(R.id.seekBar);
        seekBar2 = findViewById(R.id.seekBar2);
        rltpreferencia = findViewById(R.id.rltpreferencia);
        rltpreferencia2 = findViewById(R.id.rltpreferencia2);
        txvtitulo2 = findViewById(R.id.txvtitulo2);

        anterior = findViewById(R.id.anterior);
        anterior2 = findViewById(R.id.anterior2);
        proximo = findViewById(R.id.proximo);
        proximo2 = findViewById(R.id.proximo2);

        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 0) {
                    i--;
                    criterio1.setText(new CriterioDao(Preferencias.this).retornaDescricao(listaComp.get(i).getIdcrit1()));
                    criterio2.setText(new CriterioDao(Preferencias.this).retornaDescricao(listaComp.get(i).getIdcrit2()));

                    int imp = new ComparaCriterioDao(Preferencias.this).retornaImportancia(listaComp.get(i).getIdcrit1(), listaComp.get(i).getIdcrit2());
                    seekBar.setProgress(imp);
                }
            }
        });


        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i < listaComp.size()) {
                    criterio1.setText(new CriterioDao(Preferencias.this).retornaDescricao(listaComp.get(i).getIdcrit1()));
                    criterio2.setText(new CriterioDao(Preferencias.this).retornaDescricao(listaComp.get(i).getIdcrit2()));

                    int imp = new ComparaCriterioDao(Preferencias.this).retornaImportancia(listaComp.get(i).getIdcrit1(), listaComp.get(i).getIdcrit2());
                    seekBar.setProgress(imp);
                }
            }
        });

        anterior2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 0) {
                    i--;
                    alternativa1.setText(new AlternativaDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdalternativa1()));
                    alternativa2.setText(new AlternativaDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdalternativa2()));

                    int imp = new ComparaAlternativaDao(Preferencias.this).retornaImportancia(listaCompAlt.get(i).getIdalternativa1(), listaCompAlt.get(i).getIdalternativa2(), listaCompAlt.get(i).getIdcriterio());
                    seekBar2.setProgress(imp);

                    txvtitulo2.setText("Entre a alternativa " + alternativa1.getText().toString() + " e a alternativa " + alternativa2.getText().toString() +
                            ", qual possui maior relevância em relação ao critério " +
                            new CriterioDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdcriterio()) + "?");
                }
            }
        });


        proximo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((i + 1) < listaCompAlt.size()) {
                    i++;
                    alternativa1.setText(new AlternativaDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdalternativa1()));
                    alternativa2.setText(new AlternativaDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdalternativa2()));
                    int imp = new ComparaAlternativaDao(Preferencias.this).retornaImportancia(listaCompAlt.get(i).getIdalternativa1(), listaCompAlt.get(i).getIdalternativa2(), listaCompAlt.get(i).getIdcriterio());
                    seekBar2.setProgress(imp);

                    txvtitulo2.setText("Entre a alternativa " + alternativa1.getText().toString() + " e a alternativa " + alternativa2.getText().toString() +
                            ", qual possui maior relevância em relação ao critério " +
                            new CriterioDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdcriterio()) + "?");
                }
            }
        });

        rltpreferencia.setOnTouchListener(new OnSwip(this) {
            public void onSwipeTop() {

            }

            public void onSwipeLeft() {
                i++;
                criterio1.setText(new CriterioDao(Preferencias.this).retornaDescricao(listaComp.get(i).getIdcrit1()));
                criterio2.setText(new CriterioDao(Preferencias.this).retornaDescricao(listaComp.get(i).getIdcrit2()));
                int imp = new ComparaCriterioDao(Preferencias.this).retornaImportancia(listaComp.get(i).getIdcrit1(), listaComp.get(i).getIdcrit2());
                seekBar.setProgress(imp);
            }

            public void onSwipeRight() {
                if (i > 0) {
                    i--;
                    criterio1.setText(new CriterioDao(Preferencias.this).retornaDescricao(listaComp.get(i).getIdcrit1()));
                    criterio2.setText(new CriterioDao(Preferencias.this).retornaDescricao(listaComp.get(i).getIdcrit2()));
                    int imp = new ComparaCriterioDao(Preferencias.this).retornaImportancia(listaComp.get(i).getIdcrit1(), listaComp.get(i).getIdcrit2());
                    seekBar.setProgress(imp);
                }
            }

            public void onSwipeBottom() {

            }

        });

        rltpreferencia2.setOnTouchListener(new OnSwip(this) {
            public void onSwipeTop() {

            }

            public void onSwipeLeft() {
                i++;
                alternativa1.setText(new AlternativaDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdalternativa1()));
                alternativa2.setText(new AlternativaDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdalternativa2()));

                int imp = new ComparaAlternativaDao(Preferencias.this).retornaImportancia(listaCompAlt.get(i).getIdalternativa1(), listaCompAlt.get(i).getIdalternativa2(), listaCompAlt.get(i).getIdcriterio());
                seekBar2.setProgress(imp);

                txvtitulo2.setText("Entre a alternativa " + alternativa1.getText().toString() + " e a alternativa " + alternativa2.getText().toString() +
                        ", qual possui maior relevância em relação ao critério " +
                        new CriterioDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdcriterio()) + "?");
            }

            public void onSwipeRight() {
                if (i > 0) {
                    i--;
                    alternativa1.setText(new AlternativaDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdalternativa1()));
                    alternativa2.setText(new AlternativaDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdalternativa2()));

                    int imp = new ComparaAlternativaDao(Preferencias.this).retornaImportancia(listaCompAlt.get(i).getIdalternativa1(), listaCompAlt.get(i).getIdalternativa2(), listaCompAlt.get(i).getIdcriterio());
                    seekBar2.setProgress(imp);

                    txvtitulo2.setText("Entre a alternativa " + alternativa1.getText().toString() + " e a alternativa " + alternativa2.getText().toString() +
                            ", qual possui maior relevância em relação ao critério " +
                            new CriterioDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdcriterio()) + "?");
                }
            }

            public void onSwipeBottom() {

            }

        });

        carregaDados();
    }

    private void carregaDados() {
        listaComp = new ComparaCriterioDao(this).carregaComparacoes();
        listaCompAlt = new ComparaAlternativaDao(this).carregaComparacoes();

        final String crit1 = new CriterioDao(this).retornaDescricao(listaComp.get(i).getIdcrit1());
        String crit2 = new CriterioDao(this).retornaDescricao(listaComp.get(i).getIdcrit2());
        criterio1.setText(crit1);
        criterio2.setText(crit2);

        final String alt1 = new AlternativaDao(this).retornaDescricao(listaCompAlt.get(i).getIdalternativa1());
        String alt2 = new AlternativaDao(this).retornaDescricao(listaCompAlt.get(i).getIdalternativa2());
        alternativa1.setText(alt1);
        alternativa2.setText(alt2);
        txvtitulo2.setText("Entre a alternativa " + alt1 + " e a alternativa " + alt2 +
                ", qual possui maior relevância em relação ao critério " +
                new CriterioDao(this).retornaDescricao(listaCompAlt.get(i).getIdcriterio()) + "?");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int importancia = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                switch (progresValue) {
                    case 0:
                        importancia = 9;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.one));
                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio2.setTextColor(getResources().getColor(R.color.branco));

                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 2;
                        break;
                    case 1:
                        importancia = 8;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.two));
                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio2.setTextColor(getResources().getColor(R.color.branco));

                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 2;
                        break;
                    case 2:
                        importancia = 7;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.three));
                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio2.setTextColor(getResources().getColor(R.color.branco));

                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 2;
                        break;
                    case 3:
                        importancia = 6;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.four));
                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio2.setTextColor(getResources().getColor(R.color.branco));

                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 2;
                        break;
                    case 4:
                        importancia = 5;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio2.setTextColor(getResources().getColor(R.color.branco));

                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 2;
                        break;
                    case 5:
                        importancia = 4;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.six));
                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio2.setTextColor(getResources().getColor(R.color.branco));

                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 2;
                        break;
                    case 6:
                        importancia = 3;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio2.setTextColor(getResources().getColor(R.color.branco));

                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 2;
                        break;
                    case 7:
                        importancia = 2;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio2.setTextColor(getResources().getColor(R.color.branco));

                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 2;
                        break;
                    case 8:
                        importancia = 1;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio1.setTextColor(getResources().getColor(R.color.branco));

                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 1;
                        break;
                    case 9:
                        importancia = 2;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio1.setTextColor(getResources().getColor(R.color.branco));

                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 1;
                        break;
                    case 10:
                        importancia = 3;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio1.setTextColor(getResources().getColor(R.color.branco));

                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 1;
                        break;
                    case 11:
                        importancia = 4;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio1.setTextColor(getResources().getColor(R.color.branco));

                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 1;
                        break;
                    case 12:
                        importancia = 5;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio1.setTextColor(getResources().getColor(R.color.branco));

                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 1;
                        break;
                    case 13:
                        importancia = 6;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio1.setTextColor(getResources().getColor(R.color.branco));

                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 1;
                        break;
                    case 14:
                        importancia = 7;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio1.setTextColor(getResources().getColor(R.color.branco));

                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 1;
                        break;
                    case 15:
                        importancia = 8;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio1.setTextColor(getResources().getColor(R.color.branco));

                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 1;
                        break;
                    case 16:
                        importancia = 9;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        criterio1.setTextColor(getResources().getColor(R.color.branco));

                        criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        criterio2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        critImportancia = 1;
                        break;
                }

                listaComp.get(i).setImportancia(importancia);
                //Toast.makeText(Preferencias.this, String.valueOf(importancia), Toast.LENGTH_LONG).show();
                new ComparaCriterioDao(Preferencias.this).atualizaImportancia(listaComp.get(i), critImportancia);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int importancia = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                switch (progresValue) {
                    case 0:
                        importancia = 9;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.one));
                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa2.setTextColor(getResources().getColor(R.color.branco));

                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 2;
                        break;
                    case 1:
                        importancia = 8;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.two));
                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa2.setTextColor(getResources().getColor(R.color.branco));

                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 2;
                        break;
                    case 2:
                        importancia = 7;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.three));
                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa2.setTextColor(getResources().getColor(R.color.branco));

                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 2;
                        break;
                    case 3:
                        importancia = 6;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.four));
                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa2.setTextColor(getResources().getColor(R.color.branco));

                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 2;
                        break;
                    case 4:
                        importancia = 5;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa2.setTextColor(getResources().getColor(R.color.branco));

                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 2;
                        break;
                    case 5:
                        importancia = 4;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.six));
                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa2.setTextColor(getResources().getColor(R.color.branco));

                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 2;
                        break;
                    case 6:
                        importancia = 3;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa2.setTextColor(getResources().getColor(R.color.branco));

                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 2;
                        break;
                    case 7:
                        importancia = 2;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa2.setTextColor(getResources().getColor(R.color.branco));

                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 2;
                        break;
                    case 8:
                        importancia = 1;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa1.setTextColor(getResources().getColor(R.color.branco));

                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 1;
                        break;
                    case 9:
                        importancia = 2;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa1.setTextColor(getResources().getColor(R.color.branco));

                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 1;
                        break;
                    case 10:
                        importancia = 3;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa1.setTextColor(getResources().getColor(R.color.branco));

                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 1;
                        break;
                    case 11:
                        importancia = 4;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa1.setTextColor(getResources().getColor(R.color.branco));

                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 1;
                        break;
                    case 12:
                        importancia = 5;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa1.setTextColor(getResources().getColor(R.color.branco));

                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 1;
                        break;
                    case 13:
                        importancia = 6;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa1.setTextColor(getResources().getColor(R.color.branco));

                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 1;
                        break;
                    case 14:
                        importancia = 7;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa1.setTextColor(getResources().getColor(R.color.branco));

                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 1;
                        break;
                    case 15:
                        importancia = 8;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa1.setTextColor(getResources().getColor(R.color.branco));

                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 1;
                        break;
                    case 16:
                        importancia = 9;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        alternativa1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                        alternativa1.setTextColor(getResources().getColor(R.color.branco));

                        alternativa2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                        alternativa2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                        altimportancia = 1;
                        break;
                }

                listaCompAlt.get(i).setImportancia(importancia);
                // Toast.makeText(Preferencias.this, String.valueOf(importancia), Toast.LENGTH_LONG).show();
                new ComparaAlternativaDao(Preferencias.this).atualizaImportancia(listaCompAlt.get(i), altimportancia);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.avancar:
                if (rltpreferencia.getVisibility() == View.VISIBLE) {
                    rltpreferencia.setVisibility(View.GONE);
                    rltpreferencia2.setVisibility(View.VISIBLE);
                    altimportancia = 1;
                    critImportancia = 1;
                    i = 0;
                } else {
                    ObjetivoBean objetivoBean = new ObjetivoDao(this).carregaObjetivosTemp();
                    int id = new ObjetivoDao(this).insereObjetivo2(objetivoBean);


                    List<CriterioBean> criterios = new CriterioDao(this).carregaCriterios();
                    for (int i = 0; i < criterios.size(); i++) {
                        new CriterioDao(this).insereCriterio2(criterios.get(i), id);
                    }

                    List<AlternativaBean> alternativas = new AlternativaDao(this).carregaAlternativas();
                    for (int i = 0; i < alternativas.size(); i++) {
                        new AlternativaDao(this).insereAlternativa2(alternativas.get(i), id);
                    }


                    List<ComparaCriterioBean> compcriterios = new ComparaCriterioDao(this).carregaComparacoesTemp();
                    for(int i = 0; i < compcriterios.size(); i++) {
                        new ComparaCriterioDao(this).insereComparacoes2(compcriterios.get(i));
                    }

                    List<ComparaAlternativaBean> compalternativas = new ComparaAlternativaDao(this).carregaComparacoesTemp();
                    for(int i = 0; i < compcriterios.size(); i++) {
                        new ComparaAlternativaDao(this).insereComparacoes2(compalternativas.get(i));
                    }

                    calculaResultados();
                }

                break;
            default:
                onBackPressed();
                break;
        }
        return true;
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
        int  qtd = new CriterioDao(this).retornaQtdCriterios();
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
        if(CR>0.1) {
            String msg = "Cálculo de consistência: " + String.valueOf(CR) + "\nVolte e revise seus julgamentos!";
            alerta(this, msg, CR);
        } else {
            String msg = "Cálculo de consistência: " + String.valueOf(CR);
            alerta(this, msg, CR);
        }


    }

    public void alerta(final Activity activity, String mensag, final float cr) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alertdialog, null);

        TextView mensagem = alertLayout.findViewById(R.id.txtmensagem);
        mensagem.setText(mensag);
        Button yes = alertLayout.findViewById(R.id.yes);
        ImageView close = alertLayout.findViewById(R.id.close);

        if(cr > 0.1){
            yes.setVisibility(View.GONE);
        }

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
                if(cr > 0.1){
                    dialog.dismiss();
                } else {
                    Intent intent = new Intent(Preferencias.this, Resultados.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
