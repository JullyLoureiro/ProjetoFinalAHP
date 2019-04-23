package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaCriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
import br.com.juliana.loureiro.projetofinalahp.R;

public class FragmentCriterio extends Fragment {
    private Activity activity;

    public FragmentCriterio() {
        // Required empty public constructor
    }

    public FragmentCriterio(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_criterio, container, false);

        List<ComparaCriterioBean> lista = new ComparaCriterioDao(activity).carregaComparacoes();

        TextView criterio1, criterio2, txvpergunta;
        SeekBar seekBar;
        ImageView left, right;

        criterio1 = v.findViewById(R.id.criterio1);
        criterio2 = v.findViewById(R.id.criterio2);
        txvpergunta = v.findViewById(R.id.txvpergunta);
        seekBar = v.findViewById(R.id.seekBar);
        left = v.findViewById(R.id.left);
        right = v.findViewById(R.id.right);

        String crit1, crit2;
        crit1 = new CriterioDao(activity).retornaDescricao(lista.get(0).getIdcrit1());
        crit2 = new CriterioDao(activity).retornaDescricao(lista.get(0).getIdcrit2());

        criterio1.setText(crit1);
        criterio2.setText(crit2);


        return v;
    }

}
