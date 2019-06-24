package br.com.juliana.loureiro.projetofinalahp.ListAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Activity.TelaFuncaoAHP;
import br.com.juliana.loureiro.projetofinalahp.Activity.ThreeFragment;
import br.com.juliana.loureiro.projetofinalahp.Bean.AlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.AlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.R;
import br.com.juliana.loureiro.projetofinalahp.Util.Utils;

public class AlternativasList extends BaseAdapter {
    private final List<AlternativaBean> alternativaBeans;
    private Activity activity;

    public AlternativasList(List<AlternativaBean> alternativaBeans, Activity act) {
        this.alternativaBeans = alternativaBeans;
        this.activity = act;
    }

    @Override
    public int getCount() {
        return alternativaBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return alternativaBeans.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View v = activity.getLayoutInflater()
                .inflate(R.layout.card_criterios, viewGroup, false);

        final TextView titulo = v.findViewById(R.id.titulo);
        final EditText edttitulo = v.findViewById(R.id.edttitulo);
        final ImageView editar = v.findViewById(R.id.editar);
        ImageView apagar = v.findViewById(R.id.apagar);
        final ImageView options = v.findViewById(R.id.options);
        final ImageView save = v.findViewById(R.id.save);

        /*save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edttitulo.setVisibility(View.GONE);
                titulo.setText(edttitulo.getText());
                alternativaBeans.get(position).setDescricao(edttitulo.getText().toString());
                titulo.setVisibility(View.VISIBLE);
                new AlternativaDao(activity).atualizaAlternativa(alternativaBeans.get(position));
                save.setVisibility(View.GONE);
                options.setVisibility(View.VISIBLE);
            }
        });*/

        titulo.setText(alternativaBeans.get(position).getDescricao());

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(activity, options);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());

                MenuItem add = popupMenu.getMenu().findItem(R.id.add);
                add.setVisible(false);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editar:
                                /*titulo.setVisibility(View.GONE);
                                edttitulo.setVisibility(View.VISIBLE);
                                edttitulo.setText(titulo.getText());
                                edttitulo.requestFocus();
                                save.setVisibility(View.VISIBLE);
                                options.setVisibility(View.GONE);*/


                                LayoutInflater inflater2 = activity.getLayoutInflater();
                                @SuppressLint("ViewHolder") View alertLayout2 = inflater2.inflate(R.layout.layoutddsubcriterio, null);

                                Button btnsub2 = alertLayout2.findViewById(R.id.btnsub);
                                ImageView close2= alertLayout2.findViewById(R.id.close);

                                final EditText edtsub2 = alertLayout2.findViewById(R.id.edtsub);
                                edtsub2.setText(titulo.getText());
                                edtsub2.requestFocus();

                                AlertDialog.Builder alert2 = new AlertDialog.Builder(activity);
                                alert2.setView(alertLayout2);
                                alert2.setCancelable(true);

                                final AlertDialog dialog2 = alert2.create();
                                if (dialog2.getWindow() != null) {
                                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                }
                                dialog2.show();


                                btnsub2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog2.dismiss();

                                        titulo.setText(edtsub2.getText());
                                        alternativaBeans.get(position).setDescricao(edtsub2.getText().toString());
                                        titulo.setVisibility(View.VISIBLE);
                                        new AlternativaDao(activity).atualizaAlternativa(alternativaBeans.get(position));

                                        Utils.hideKeyboard(activity, edtsub2);
                                    }
                                });

                                close2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog2.dismiss();
                                    }
                                });


                                break;
                            case R.id.apagar:
                                new AlternativaDao(activity).deletaAlternativa(alternativaBeans.get(position).getId());
                                alternativaBeans.remove(position);
                                ThreeFragment.listAlternativas.setAdapter(new AlternativasList(alternativaBeans, activity));
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        return v;
    }
}
