package br.com.juliana.loureiro.projetofinalahp.ListAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.SubcriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.SubcriteriosDao;
import br.com.juliana.loureiro.projetofinalahp.R;
import br.com.juliana.loureiro.projetofinalahp.Util.Utils;

public class SubcriterioList extends BaseAdapter {
    private final List<SubcriterioBean> criterios;
    private Activity activity;

    SubcriterioList(List<SubcriterioBean> criterios, Activity act) {
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
                .inflate(R.layout.card_subcriterios, viewGroup, false);

        final TextView titulo = v.findViewById(R.id.titulo);
        final EditText edttitulo = v.findViewById(R.id.edttitulo);
        final ImageView editar = v.findViewById(R.id.editar);
        final ImageView apagar = v.findViewById(R.id.apagar);


        titulo.setText(criterios.get(position).getDescricao());

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = activity.getLayoutInflater();
                @SuppressLint("ViewHolder") View alertLayout = inflater.inflate(R.layout.layoutddsubcriterio, null);

                Button btnsub = alertLayout.findViewById(R.id.btnsub);
                ImageView close = alertLayout.findViewById(R.id.close);

                final EditText edtsub = alertLayout.findViewById(R.id.edtsub);
                edtsub.setText(titulo.getText());
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
                        titulo.setText(edtsub.getText());
                        criterios.get(position).setDescricao(edtsub.getText().toString());
                        new SubcriteriosDao(activity).atualizaCriterio(criterios.get(position));
                        dialog.dismiss();
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                /*if (titulo.getVisibility() == View.VISIBLE) {
                    titulo.setVisibility(View.GONE);
                    edttitulo.setVisibility(View.VISIBLE);
                    edttitulo.setText(titulo.getText());
                    edttitulo.requestFocus();
                    editar.setImageDrawable(ActivityCompat.getDrawable(activity, R.drawable.checked));
                    Utils.ahowKeyboard(activity, edttitulo);
                } else {
                    edttitulo.setVisibility(View.GONE);
                    titulo.setText(edttitulo.getText());
                    criterios.get(position).setDescricao(edttitulo.getText().toString());
                    titulo.setVisibility(View.VISIBLE);
                    new SubcriteriosDao(activity).atualizaCriterio(criterios.get(position));
                    editar.setImageDrawable(ActivityCompat.getDrawable(activity, R.drawable.pencil));
                }*/
            }
        });

        apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SubcriteriosDao(activity).deletaSubCriterio(criterios.get(position).getId());
                criterios.remove(position);
                notifyDataSetChanged();

            }
        });

        return v;
    }
}
