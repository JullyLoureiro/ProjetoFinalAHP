package br.com.juliana.loureiro.projetofinalahp.ListAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.ObjetivoBean;
import br.com.juliana.loureiro.projetofinalahp.R;

public class ObjetivosList extends BaseAdapter {

    private final List<ObjetivoBean> objetivos;
    private Activity activity;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = activity.getLayoutInflater()
                .inflate(R.layout.card_objetivos, parent, false);

        CardView card = view.findViewById(R.id.card);
        final ImageView opcoes = view.findViewById(R.id.opcoes);
        TextView titulo = view.findViewById(R.id.titulo);
        TextView descricao = view.findViewById(R.id.descricao);

        opcoes.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ViewHolder")
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(activity, opcoes);

                popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editar:
                                break;
                            case R.id.resultados:
                                break;
                                case R.id.excluir:
                                    break;
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });

        return view;
    }
}
