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

import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaAlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.AlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaAlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaCriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
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
                    txvtitulo2.setText("Entre a alternativa " + alternativa1.getText().toString() + " e a alternativa " + alternativa2.getText().toString() +
                            ", qual possui maior relevância em relação ao critério " +
                            new CriterioDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdcriterio()) + "?");
                }
            }
        });


        proximo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((i+1) < listaCompAlt.size()) {
                    i++;
                    alternativa1.setText(new AlternativaDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdalternativa1()));
                    alternativa2.setText(new AlternativaDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdalternativa2()));
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

            }

            public void onSwipeRight() {
                if (i > 0) {
                    i--;
                    criterio1.setText(new CriterioDao(Preferencias.this).retornaDescricao(listaComp.get(i).getIdcrit1()));
                    criterio2.setText(new CriterioDao(Preferencias.this).retornaDescricao(listaComp.get(i).getIdcrit2()));
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
                txvtitulo2.setText("Entre a alternativa " + alternativa1.getText().toString() + " e a alternativa " + alternativa2.getText().toString() +
                        ", qual possui maior relevância em relação ao critério " +
                        new CriterioDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdcriterio()) + "?");
            }

            public void onSwipeRight() {
                if (i > 0) {
                    i--;
                    alternativa1.setText(new AlternativaDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdalternativa1()));
                    alternativa2.setText(new AlternativaDao(Preferencias.this).retornaDescricao(listaCompAlt.get(i).getIdalternativa2()));
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
                Toast.makeText(Preferencias.this, String.valueOf(importancia), Toast.LENGTH_LONG).show();
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
                Toast.makeText(Preferencias.this, String.valueOf(importancia), Toast.LENGTH_LONG).show();
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
                    Intent intent = new Intent(this, Resultados.class);
                    startActivity(intent);
                    //finish();
                }

                break;
            default:
                onBackPressed();
                break;
        }
        return true;
    }
}
