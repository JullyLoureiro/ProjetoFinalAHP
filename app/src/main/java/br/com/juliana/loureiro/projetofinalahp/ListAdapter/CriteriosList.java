package br.com.juliana.loureiro.projetofinalahp.ListAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
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
       //final ImageView editar = v.findViewById(R.id.editar);
        final ImageView apagar = v.findViewById(R.id.apagar);
        final ImageView add = v.findViewById(R.id.add);
        final ImageView options = v.findViewById(R.id.options);
        final ImageView save = v.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edttitulo.setVisibility(View.GONE);
                titulo.setText(edttitulo.getText());
                criterios.get(position).setDescricao(edttitulo.getText().toString());
                titulo.setVisibility(View.VISIBLE);
                new CriterioDao(activity).atualizaCriterio(criterios.get(position));
                save.setVisibility(View.GONE);
                options.setVisibility(View.VISIBLE);
            }
        });

        listasubcriterios = v.findViewById(R.id.listsubcriterios);
        final List<SubcriterioBean> lista = new SubcriteriosDao(activity).carregaCriterios(criterios.get(position).getId());
        listasubcriterios.setVisibility(View.VISIBLE);
        listasubcriterios.setAdapter(new SubcriterioList(lista, activity));
        setListViewHeightBasedOnItems(listasubcriterios);

        titulo.setText(criterios.get(position).getDescricao());


        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(activity, options);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.add:
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

                                        if (edtsub.getText().length() > 0) {

                                            dialog.dismiss();

                                            SubcriterioBean subcriterioBean = new SubcriterioBean();
                                            subcriterioBean.setIdcriterio(criterios.get(position).getId());
                                            subcriterioBean.setDescricao(edtsub.getText().toString());

                                            new SubcriteriosDao(activity).insereCriterioTemp(subcriterioBean);
                                            final List<SubcriterioBean> lista = new SubcriteriosDao(activity).carregaCriterios(criterios.get(position).getId());
                                            listasubcriterios.setVisibility(View.VISIBLE);
                                            listasubcriterios.setAdapter(new SubcriterioList(lista, activity));
                                            setListViewHeightBasedOnItems(listasubcriterios);


                                            List<CriterioBean> lista2 = new CriterioDao(activity).carregaCriterios();
                                            TwoFragment.listCriterios.setAdapter(new CriteriosList(lista2, activity));

                                            Utils.hideKeyboard(activity, edtsub);

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
                                break;
                            case R.id.editar:
                                if (options.getVisibility() == View.VISIBLE) {
                                    titulo.setVisibility(View.GONE);
                                    edttitulo.setVisibility(View.VISIBLE);
                                    edttitulo.setText(titulo.getText());
                                    edttitulo.requestFocus();
                                  // options.setImageDrawable(ActivityCompat.getDrawable(activity, R.drawable.checked));
                                    save.setVisibility(View.VISIBLE);
                                    options.setVisibility(View.GONE);
                                }

                                  //  options.setImageDrawable(ActivityCompat.getDrawable(activity, R.drawable.pencil));

                                break;
                            case R.id.apagar:
                                new CriterioDao(activity).deletaCriterio(criterios.get(position).getId());
                                criterios.remove(position);
                                TwoFragment.listCriterios.setAdapter(new CriteriosList(criterios, activity));
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

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;

        } else {
            return false;
        }

    }
}
