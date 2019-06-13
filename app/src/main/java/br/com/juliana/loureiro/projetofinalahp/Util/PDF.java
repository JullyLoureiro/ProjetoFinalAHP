package br.com.juliana.loureiro.projetofinalahp.Util;

import android.content.Context;

import com.itextpdf.text.pdf.BaseFont;

public class PDF {
    private BaseFont bfBold, bf;
    private int pageNumber = 0;
    private Context context;

    public PDF(Context context) {
        this.context = context;
    }
}
