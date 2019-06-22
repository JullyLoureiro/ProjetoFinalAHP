package br.com.juliana.loureiro.projetofinalahp.Util;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class FormatterGrah implements IValueFormatter {
    private DecimalFormat mFormat;

    public FormatterGrah() {
        mFormat = new DecimalFormat("###,###,##0.00"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        if (value == 0) {
            return "";
        } else {
            return "R$ ".concat(mFormat.format(value)); // e.g. append a dollar-sign
        }
    }
}