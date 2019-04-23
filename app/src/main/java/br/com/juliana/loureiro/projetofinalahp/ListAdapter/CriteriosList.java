package br.com.juliana.loureiro.projetofinalahp.ListAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Activity.TelaFuncaoAHP;
import br.com.juliana.loureiro.projetofinalahp.Activity.TwoFragment;
import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
import br.com.juliana.loureiro.projetofinalahp.R;

public class CriteriosList extends BaseAdapter {
    private final List<CriterioBean> criterios;
    private Activity activity;

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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View v = activity.getLayoutInflater()
                .inflate(R.layout.card_criterios, viewGroup, false);

        TextView titulo = v.findViewById(R.id.titulo);
        ImageView editar = v.findViewById(R.id.editar);
        ImageView apagar = v.findViewById(R.id.apagar);

        titulo.setText(criterios.get(position).getDescricao());

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
