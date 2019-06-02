package br.com.juliana.loureiro.projetofinalahp.ListAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.SubcriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.SubcriteriosDao;
import br.com.juliana.loureiro.projetofinalahp.R;

public class SubcriteriosList extends BaseAdapter {
    private final List<SubcriterioBean> criterios;
    private Activity activity;

    public SubcriteriosList(List<SubcriterioBean> criterios, Activity act) {
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
    public long getItemId(int position) {
        return criterios.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layout = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View v = null;
        if (layout != null) {
            v = layout.inflate(R.layout.card_subcriterios, null);
        }


        final TextView titulo = v.findViewById(R.id.titulo);
        final EditText edttitulo = v.findViewById(R.id.edttitulo);
        final ImageView editar = v.findViewById(R.id.editar);
        final ImageView apagar = v.findViewById(R.id.apagar);

        titulo.setText(criterios.get(position).getDescricao());

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
                    new SubcriteriosDao(activity).atualizaCriterio(criterios.get(position));
                    editar.setImageDrawable(ActivityCompat.getDrawable(activity, R.drawable.pencil));
                }
            }
        });

        apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SubcriteriosDao(activity).deletaSubCriterio(criterios.get(position).getId());
                criterios.remove(position);
                CriteriosList.listasubcriterios.setAdapter(new SubcriteriosList(criterios, activity));
            }
        });

        return v;
    }
}
