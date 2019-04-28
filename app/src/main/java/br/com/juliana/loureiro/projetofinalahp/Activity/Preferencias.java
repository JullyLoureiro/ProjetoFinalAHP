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

import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaCriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
import br.com.juliana.loureiro.projetofinalahp.R;
import br.com.juliana.loureiro.projetofinalahp.Util.OnSwip;

public class Preferencias extends AppCompatActivity {
    Button criterio1, criterio2;
    SeekBar seekBar;
    RelativeLayout rltpreferencia, rltpreferencia2;
    private List<ComparaCriterioBean> listaComp;
    int i = 0;
    int critImportancia = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        declaraObjetos();
    }

    @Override
    public void onBackPressed() {
        alerta();
    }

    private void alerta() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alertdialog, null);

        TextView mensagem = alertLayout.findViewById(R.id.txtmensagem);
        mensagem.setText("Tem certeza que deseja voltar?");
        Button yes = alertLayout.findViewById(R.id.yes);
        ImageView close = alertLayout.findViewById(R.id.close);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
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
                finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void declaraObjetos() {
        criterio1 = findViewById(R.id.criterio1);
        criterio2 = findViewById(R.id.criterio2);
        seekBar = findViewById(R.id.seekBar);
        rltpreferencia = findViewById(R.id.rltpreferencia);
        rltpreferencia2 = findViewById(R.id.rltpreferencia2);


        rltpreferencia.setOnTouchListener(new OnSwip(this) {
            public void onSwipeTop() {

            }

            public void onSwipeRight() {
                i++;
                criterio1.setText(new CriterioDao(Preferencias.this).retornaDescricao(listaComp.get(i).getIdcrit1()));
                criterio2.setText(new CriterioDao(Preferencias.this).retornaDescricao(listaComp.get(i).getIdcrit2()));
            }

            public void onSwipeLeft() {
               if(i>0){
                   i--;
                   criterio1.setText(new CriterioDao(Preferencias.this).retornaDescricao(listaComp.get(i).getIdcrit1()));
                   criterio2.setText(new CriterioDao(Preferencias.this).retornaDescricao(listaComp.get(i).getIdcrit2()));
               }
            }

            public void onSwipeBottom() {

            }

        });

        carregaDados();
    }

    private void carregaDados() {
        listaComp = new ComparaCriterioDao(this).carregaComparacoes();

        final String crit1 = new CriterioDao(this).retornaDescricao(listaComp.get(i).getIdcrit1());
        String crit2 = new CriterioDao(this).retornaDescricao(listaComp.get(i).getIdcrit2());
        criterio1.setText(crit1);
        criterio2.setText(crit2);

        criterio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                criterio1.setTextColor(getResources().getColor(R.color.branco));

                criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                criterio2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                critImportancia = 1;
            }
        });

        criterio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criterio2.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button));
                criterio2.setTextColor(getResources().getColor(R.color.branco));

                criterio1.setBackground(ActivityCompat.getDrawable(Preferencias.this, R.drawable.shape_button_cinza));
                criterio1.setTextColor(getResources().getColor(R.color.cinzaescuro));
                critImportancia = 2;
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int importancia = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                switch (progresValue) {
                    case 0:
                        importancia = 1;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.one));
                        break;
                    case 1:
                        importancia = 2;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.two));
                        break;
                    case 2:
                        importancia = 3;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.three));
                        break;
                    case 3:
                        importancia = 4;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.four));
                        break;
                    case 4:
                        importancia = 5;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        break;
                    case 5:
                        importancia = 6;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.six));
                        break;
                    case 6:
                        importancia = 7;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        break;
                    case 7:
                        importancia = 8;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        break;
                    case 8:
                        importancia = 9;
                        seekBar.setThumb(getResources().getDrawable(R.drawable.five));
                        break;
                }

                listaComp.get(i).setImportancia(importancia);

                new ComparaCriterioDao(Preferencias.this).atualizaImportancia(listaComp.get(i), critImportancia);

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
                if(rltpreferencia.getVisibility()==View.VISIBLE) {
                    rltpreferencia.setVisibility(View.GONE);
                    rltpreferencia2.setVisibility(View.VISIBLE);
                    //TODO: CARREGAR PRFERENCIAS ALTERNATIVA
                }else {
                    Intent intent = new Intent(this, Resultados.class);
                    startActivity(intent);
                    finish();
                }

                break;
            default:
                onBackPressed();
                break;
        }
        return true;
    }
}
