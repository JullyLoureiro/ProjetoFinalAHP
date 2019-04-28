package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.MatrizCriterioNormalizadaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.PesoCriteriosBean;
import br.com.juliana.loureiro.projetofinalahp.Database.ConfigDB;

public class PesoCriteriosDao {
    private Context context;
    private Cursor cursor;
    private SQLiteDatabase db;
    private ConfigDB banco;

    public PesoCriteriosDao(Context context) {
        this.context = context;
        banco = new ConfigDB(context);
        db = banco.getReadableDatabase();
    }

    public List<PesoCriteriosBean> somaLinhas(float qtdcriterios) {
        List<PesoCriteriosBean> pesos = new ArrayList<>();

                cursor = db.rawQuery("SELECT IDCRIT1, SUM(IMPORTANCIA) AS SOMA FROM " + MatrizCriterioNormalizadaBean.TABELA +
                " GROUP BY IDCRIT1", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                try {
                    ContentValues valores;

                    db = banco.getWritableDatabase();
                    valores = new ContentValues();
                    valores.put(PesoCriteriosBean.IDCRIT, cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT1)));
                    valores.put(PesoCriteriosBean.SOMA, cursor.getFloat(cursor.getColumnIndex("SOMA")) / qtdcriterios);

                    db.insert(PesoCriteriosBean.TABELA, null, valores);

                    PesoCriteriosBean pesoCriteriosBean = new PesoCriteriosBean();
                    pesoCriteriosBean.setIdcrit(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT1)));
                    pesoCriteriosBean.setSoma(cursor.getFloat(cursor.getColumnIndex("SOMA")) / qtdcriterios);
                    pesos.add(pesoCriteriosBean);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
        }
        return pesos;
    }

    public float retornaSoma(int id) {
        try {
            cursor = db.rawQuery("SELECT " + PesoCriteriosBean.SOMA + " FROM " + PesoCriteriosBean.TABELA +
                    " WHERE " + PesoCriteriosBean.IDCRIT + " = " + id, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getFloat(cursor.getColumnIndex(PesoCriteriosBean.SOMA));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
