package br.com.juliana.loureiro.projetofinalahp.ListAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Activity.TelaFuncaoAHP;
import br.com.juliana.loureiro.projetofinalahp.Activity.TwoFragment;
import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.SubcriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.SubcriteriosDao;
import br.com.juliana.loureiro.projetofinalahp.R;
import br.com.juliana.loureiro.projetofinalahp.Util.Utils;

public class CriteriosList extends BaseAdapter {
    private final List<CriterioBean> criterios;
    private Activity activity;
    public static ListView listasubcriterios;


    public CriteriosList(List<CriterioBean> criterios, Activity act) {
        this.criterios = criterios;
        this.activity = act;
    }

    @Override
    public int getCount() {
        return criterios.size();
    }

    @Override
    public Object getItem(int position) {
        return criterios.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, final View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") final View v = activity.getLayoutInflater()
                .inflate(R.layout.card_criterios, viewGroup, false);

        final TextView titulo = v.findViewById(R.id.titulo);
        final EditText edttitulo = v.findViewById(R.id.edttitulo);
        final ImageView editar = v.findViewById(R.id.editar);
        final ImageView apagar = v.findViewById(R.id.apagar);
        final ImageView add = v.findViewById(R.id.add);
        listasubcriterios = v.findViewById(R.id.listsubcriterios);

        titulo.setText(criterios.get(position).getDescricao());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = activity.getLayoutInflater();
                @SuppressLint("ViewHolder") View alertLayout = inflater.inflate(R.layout.layoutddsubcriterio, null);

                Button btnsub = alertLayout.findViewById(R.id.btnsub);
                ImageView close = alertLayout.findViewById(R.id.close);

                final EditText edtsub = alertLayout.findViewById(R.id.edtsub);

                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setView(alertLayout);
                alert.setCancelable(true);

                final AlertDialog dialog = alert.create();
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                dialog.show();

                btnsub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(edtsub.getText().length()>0) {

                            dialog.dismiss();

                            SubcriterioBean subcriterioBean = new SubcriterioBean();
                            subcriterioBean.setIdcriterio(criterios.get(position).getId());
                            subcriterioBean.setDescricao(edtsub.getText().toString());

                            new SubcriteriosDao(activity).insereCriterioTemp(subcriterioBean);
                            List<SubcriterioBean> lista = new SubcriteriosDao(activity).carregaCriterios();
                            listasubcriterios.setAdapter(new SubcriteriosList(lista, activity));
                            listasubcriterios.setVisibility(View.VISIBLE);
                        } else {
                            edtsub.setError("Informe o subcritério!");
                            edtsub.requestFocus();
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
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(titulo.getVisibility() == View.VISIBLE) {
                    titulo.setVisibility(View.GONE);
                    edttitulo.setVisibility(View.VISIBLE);
                    edttitulo.setText(titulo.getText());
                    edttitulo.requestFocus();
                    editar.setImageDrawable(ActivityCompat.getDrawable(activity, R.drawable.checked));
                } else {
                    edttitulo.setVisibility(View.GONE);
                    titulo.setText(edttitulo.getText());
                    criterios.get(position).setDescricao(edttitulo.getText().toString());
                    titulo.setVisibility(View.VISIBLE);
                    new CriterioDao(activity).atualizaCriterio(criterios.get(position));
                    editar.setImageDrawable(ActivityCompat.getDrawable(activity, R.drawable.pencil));
                }
            }
        });

        apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CriterioDao(activity).deletaCriterio(criterios.get(position).getId());
                criterios.remove(position);
                TwoFragment.listCriterios.setAdapter(new CriteriosList(criterios, activity));
            }
        });

        return v;
    }
}
