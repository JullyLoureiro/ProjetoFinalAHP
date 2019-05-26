package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
import br.com.juliana.loureiro.projetofinalahp.ListAdapter.CriteriosList;
import br.com.juliana.loureiro.projetofinalahp.R;
import br.com.juliana.loureiro.projetofinalahp.Util.Utils;

public class TwoFragment extends Fragment {
    private Activity activity;
    public static ListView listCriterios;

    public TwoFragment() {
        // Required empty public constructor
    }

    public TwoFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.tela_criterios, container, false);

        FloatingActionButton add = v.findViewById(R.id.btnaddcriterio);
        listCriterios = v.findViewById(R.id.listCriterios);
        final EditText edtcriterio = v.findViewById(R.id.edtcriterio);

        ImageView help = v.findViewById(R.id.help);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.alerta(activity, "Atributos que servem de base para serem comparadas e julgadas a partir de alternativas.", "CRITÉRIO");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtcriterio.getText().length() > 0) {
                    CriterioBean criterioBean = new CriterioBean();
                    criterioBean.setDescricao(edtcriterio.getText().toString());
                    new CriterioDao(activity).insereCriterio(criterioBean);
                    List<CriterioBean> lista = new CriterioDao(activity).carregaCriterios();
                    listCriterios.setAdapter(new CriteriosList(lista, activity));
                    edtcriterio.setText("");
                } else {
                    edtcriterio.setError("Informe a descrição!");
                    edtcriterio.requestFocus();
                }

                Utils.hideKeyboard(activity, edtcriterio);
            }
        });

        return v;
    }


}