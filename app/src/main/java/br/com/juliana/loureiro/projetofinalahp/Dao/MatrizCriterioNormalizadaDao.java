package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.MatrizCriterioNormalizadaBean;
import br.com.juliana.loureiro.projetofinalahp.Database.ConfigDB;

public class MatrizCriterioNormalizadaDao {
    private Context context;
    private Cursor cursor;
    private SQLiteDatabase db;
    private ConfigDB banco;

    public MatrizCriterioNormalizadaDao(Context context) {
        this.context = context;
        banco = new ConfigDB(context);
        db = banco.getReadableDatabase();
    }

    public boolean insereMatrizNormalizada(MatrizCriterioNormalizadaBean matrizCriterioNormalizadaBean) {
        try {

            cursor = db.rawQuery("SELECT * FROM " + MatrizCriterioNormalizadaBean.TABELA + " WHERE "
                    + MatrizCriterioNormalizadaBean.IDCRIT1 + " = " + matrizCriterioNormalizadaBean.getIdcrit1() +
                    " AND " + MatrizCriterioNormalizadaBean.IDCRIT2 + " = " + matrizCriterioNormalizadaBean.getIdcrit2(), null);

            if (cursor.getCount() <= 0) {
                ContentValues valores;

                db = banco.getWritableDatabase();
                valores = new ContentValues();
                valores.put(MatrizCriterioNormalizadaBean.IDCRIT1, matrizCriterioNormalizadaBean.getIdcrit1());
                valores.put(MatrizCriterioNormalizadaBean.IDCRIT2, matrizCriterioNormalizadaBean.getIdcrit2());
                valores.put(MatrizCriterioNormalizadaBean.IMPORTANCIA, matrizCriterioNormalizadaBean.getImportancia());
                db.insert(MatrizCriterioNormalizadaBean.TABELA, null, valores);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;
    }

    public void deleta() {
        db.execSQL("DELETE FROM " + MatrizCriterioNormalizadaBean.TABELA);
    }
}
