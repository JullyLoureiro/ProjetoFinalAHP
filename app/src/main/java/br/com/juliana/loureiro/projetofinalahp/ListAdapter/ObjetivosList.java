package br.com.juliana.loureiro.projetofinalahp.ListAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Activity.Resultados;
import br.com.juliana.loureiro.projetofinalahp.Bean.ObjetivoBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.ObjetivoDao;
import br.com.juliana.loureiro.projetofinalahp.R;
import br.com.juliana.loureiro.projetofinalahp.Util.Utils;

public class ObjetivosList extends BaseAdapter {

    private final List<ObjetivoBean> objetivos;
    private Activity activity;
    private String data;

    public ObjetivosList(List<ObjetivoBean> objetivos, Activity act) {
        this.objetivos = objetivos;
        this.activity = act;
    }

    @Override
    public int getCount() {
        return objetivos.size();
    }

    @Override
    public Object getItem(int position) {
        return objetivos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = activity.getLayoutInflater()
                .inflate(R.layout.card_objetivos, parent, false);

        CardView card = view.findViewById(R.id.card);
        //final ImageView opcoes = view.findViewById(R.id.opcoes);
        TextView titulo = view.findViewById(R.id.titulo);
        TextView descricao = view.findViewById(R.id.descricao);
        TextView txvdata = view.findViewById(R.id.txvdata);
        LinearLayout lnr = view.findViewById(R.id.lnr);


        if(position == 0) {
            lnr.setVisibility(View.VISIBLE);
            data = objetivos.get(position).getData();
            txvdata.setText(objetivos.get(position).getData());
        } else {
            if(objetivos.get(position).getData().equals(data)){
                lnr.setVisibility(View.GONE);
            } else {
                lnr.setVisibility(View.VISIBLE);
                data = objetivos.get(position).getData();
                txvdata.setText(objetivos.get(position).getData());
            }
        }

        titulo.setText(objetivos.get(position).getTitulo());
        //descricao.setText(objetivos.get(position).getDescricao());

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Resultados.class);
                Bundle params = new Bundle();
                params.putInt("verResult", objetivos.get(position).getId());
                intent.putExtras(params);
                activity.startActivity(intent);
                //activity.finish();

            }
        });

        card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Utils.alertaExcluir(activity, objetivos.get(position).getId());

                return false;
            }
        });


        return view;
    }
}
