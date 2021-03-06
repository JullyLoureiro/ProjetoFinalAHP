package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.app.Activity;
import android.content.Intent;
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

import br.com.juliana.loureiro.projetofinalahp.Bean.AlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.AlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaCriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
import br.com.juliana.loureiro.projetofinalahp.ListAdapter.AlternativasList;
import br.com.juliana.loureiro.projetofinalahp.R;
import br.com.juliana.loureiro.projetofinalahp.Util.Utils;

public class ThreeFragment extends Fragment {
    private Activity activity;
    public static ListView listAlternativas;

    public ThreeFragment() {
        // Required empty public constructor
    }

    public ThreeFragment(Activity activity) {
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
        final View view =  inflater.inflate(R.layout.tela_alternativas, container, false);
        FloatingActionButton add = view.findViewById(R.id.btnaddalternativa);
        listAlternativas = view.findViewById(R.id.listAlternativas);
        List<AlternativaBean> lista = new AlternativaDao(activity).carregaAlternativas();
        listAlternativas.setAdapter(new AlternativasList(lista, activity));
        final EditText edtalternativa = view.findViewById(R.id.edtalternativa);

        ImageView help = view.findViewById(R.id.help);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.alerta(activity, "Possibilidades pelas quais se pode optar para se alcançar seu objetivo final.", "ALTERNATIVA");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtalternativa.getText().length() > 0) {
                    AlternativaBean alternativaBean = new AlternativaBean();
                    alternativaBean.setDescricao(edtalternativa.getText().toString());
                    new AlternativaDao(activity).insereAlternativa(alternativaBean);
                    List<AlternativaBean> lista = new AlternativaDao(activity).carregaAlternativas();
                    listAlternativas.setAdapter(new AlternativasList(lista, activity));
                    edtalternativa.setText("");
                } else {
                    edtalternativa.setError("Informe a descrição!");
                    edtalternativa.requestFocus();
                }

                Utils.hideKeyboard(activity, edtalternativa);
            }
        });

        return view;
    }

}