package br.com.juliana.loureiro.projetofinalahp.ListAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.PesoCriteriosBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.AlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.R;

public class ResultadosList extends BaseAdapter {

    private final List<PesoCriteriosBean> resultados;
    private Activity activity;
    private String data;

    public ResultadosList(List<PesoCriteriosBean> resultados, Activity act) {
        this.resultados = resultados;
        this.activity = act;
    }

    @Override
    public int getCount() {
        return resultados.size();
    }

    @Override
    public Object getItem(int position) {
        return resultados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = activity.getLayoutInflater()
                .inflate(R.layout.card_tabela_resultado, parent, false);

        TextView alternativa = view.findViewById(R.id.alternativa);
        TextView percentual = view.findViewById(R.id.percentual);

        if(position == 0) {
            alternativa.setTextColor(activity.getResources().getColor(R.color.colorTerc));
        } else {
            alternativa.setTextColor(activity.getResources().getColor(R.color.cinzaescuro));
        }

        alternativa.setText((position+1) + ") " + resultados.get(position).getAlternativa());


        percentual.setText(new BigDecimal(resultados.get(position).getPerc()).setScale(2, BigDecimal.ROUND_HALF_UP)+ "%");

        return view;
    }
}
