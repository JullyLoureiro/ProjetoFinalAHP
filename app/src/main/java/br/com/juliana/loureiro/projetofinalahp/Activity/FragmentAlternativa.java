package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import br.com.juliana.loureiro.projetofinalahp.R;

public class FragmentAlternativa extends Fragment {
    private Activity activity;

    public FragmentAlternativa() {
        // Required empty public constructor
    }

    public FragmentAlternativa(Activity activity) {
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

        return v;
    }

}