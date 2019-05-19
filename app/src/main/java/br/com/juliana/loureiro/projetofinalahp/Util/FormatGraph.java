package br.com.juliana.loureiro.projetofinalahp.Util;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class FormatGraph implements IValueFormatter {
    private DecimalFormat mFormat;

    public FormatGraph() {
        mFormat = new DecimalFormat("###,###,##0.00"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        if (value == 0) {
            return "";
        } else {
            return mFormat.format(value).concat(" %"); // e.g. append a dollar-sign
        }
    }
}