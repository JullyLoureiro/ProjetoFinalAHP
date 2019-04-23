package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaCriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
import br.com.juliana.loureiro.projetofinalahp.R;

public class FragmentCriterio extends Fragment {
    private Activity activity;
    int i = 0;
    int critImportancia = 1;

    public FragmentCriterio() {
        // Required empty public constructor
    }

    public FragmentCriterio(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_criterio, container, false);

        final TextView txvpergunta;
        final Button criterio1, criterio2;
        SeekBar seekBar;
        final ImageView left, right;

        criterio1 = v.findViewById(R.id.criterio1);
        criterio2 = v.findViewById(R.id.criterio2);
        txvpergunta = v.findViewById(R.id.txvpergunta);
        seekBar = v.findViewById(R.id.seekBar);


        left = v.findViewById(R.id.left);
        right = v.findViewById(R.id.right);

        List<CriterioBean> listaCriterios = new CriterioDao(activity).carregaCriterios();

        final List<ComparaCriterioBean> listaComp = new ArrayList<>();

        for (int i = 0; i < listaCriterios.size(); i++) {
            for (int j = 0; j < listaCriterios.size(); j++) {
                ComparaCriterioBean comparaCriterioBean = new ComparaCriterioBean();
                comparaCriterioBean.setIdcrit1(listaCriterios.get(i).getId());
                comparaCriterioBean.setIdcrit2(listaCriterios.get(j).getId());
                comparaCriterioBean.setImportancia(0);
                if (j > i) {
                    listaComp.add(comparaCriterioBean);
                }
            }
        }

        final String crit1 = new CriterioDao(activity).retornaDescricao(listaComp.get(i).getIdcrit1());
        String crit2 = new CriterioDao(activity).retornaDescricao(listaComp.get(i).getIdcrit2());
        criterio1.setText(crit1);
        criterio2.setText(crit2);

        txvpergunta.setText(crit1 + " x " + crit2);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 0) {
                    i--;
                    String crit1 = new CriterioDao(activity).retornaDescricao(listaComp.get(i).getIdcrit1());
                    String crit2 = new CriterioDao(activity).retornaDescricao(listaComp.get(i).getIdcrit2());
                    criterio1.setText(crit1);
                    criterio2.setText(crit2);
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i < listaComp.size()) {
                    i++;
                    String crit1 = new CriterioDao(activity).retornaDescricao(listaComp.get(i).getIdcrit1());
                    String crit2 = new CriterioDao(activity).retornaDescricao(listaComp.get(i).getIdcrit2());
                    criterio1.setText(crit1);
                    criterio2.setText(crit2);
                }
            }
        });

        criterio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criterio1.setBackground(ActivityCompat.getDrawable(activity, R.drawable.shape_button));
                criterio1.setTextColor(getResources().getColor(R.color.branco));

                criterio2.setBackground(ActivityCompat.getDrawable(activity, R.drawable.shape_button_cinza));
                criterio2.setTextColor(getResources().getColor(R.color.cinzaescuro));
                critImportancia = 1;
            }
        });

        criterio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criterio2.setBackground(ActivityCompat.getDrawable(activity, R.drawable.shape_button));
                criterio2.setTextColor(getResources().getColor(R.color.branco));

                criterio1.setBackground(ActivityCompat.getDrawable(activity, R.drawable.shape_button_cinza));
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

                new ComparaCriterioDao(activity).atualizaImportancia(listaComp.get(i), critImportancia);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return v;
    }
}
