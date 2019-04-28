package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Activity.TelaFuncaoAHP;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Database.ConfigDB;

public class ComparaCriterioDao {

    private Context context;
    private Cursor cursor;
    private SQLiteDatabase db;
    private ConfigDB banco;

    public ComparaCriterioDao(Context context) {
        this.context = context;
        banco = new ConfigDB(context);
        db = banco.getReadableDatabase();
    }

    public boolean deletaTemp() {
        try {
            db.execSQL("DELETE FROM " + ComparaCriterioBean.TABELA_temp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;

    }

    public boolean insereComparacoes(ComparaCriterioBean comparaCriterioBean) {
        try {

            cursor = db.rawQuery("SELECT * FROM " + ComparaCriterioBean.TABELA_temp + " WHERE "
                    + ComparaCriterioBean.IDCRIT1 + " = " + comparaCriterioBean.getIdcrit1() +
                    " AND " + ComparaCriterioBean.IDCRIT2 + " = " + comparaCriterioBean.getIdcrit2(), null);

            if (cursor.getCount() <= 0) {
                ContentValues valores;

                db = banco.getWritableDatabase();
                valores = new ContentValues();
                valores.put(ComparaCriterioBean.IDCRIT1, comparaCriterioBean.getIdcrit1());
                valores.put(ComparaCriterioBean.IDCRIT2, comparaCriterioBean.getIdcrit2());
                valores.put(ComparaCriterioBean.IMPORTANCIA, comparaCriterioBean.getImportancia());
                db.insert(ComparaCriterioBean.TABELA_temp, null, valores);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;
    }

    public List<ComparaCriterioBean> carregaComparacoes() {
        List<ComparaCriterioBean> lista = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM " + ComparaCriterioBean.TABELA_temp, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                ComparaCriterioBean comparaCriterioBean = new ComparaCriterioBean();
                comparaCriterioBean.setIdcrit1(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT1)));
                comparaCriterioBean.setIdcrit2(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT2)));
                comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.ID)));
                comparaCriterioBean.setImportancia(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IMPORTANCIA)));

                if (comparaCriterioBean.getIdcrit1() != comparaCriterioBean.getIdcrit2()) {
                    lista.add(comparaCriterioBean);
                }

            } while (cursor.moveToNext());
        }


        return lista;
    }

    public void atualizaImportancia(ComparaCriterioBean comparaCriterioBean, int critimport) {
        try {
            ContentValues content = new ContentValues();
            if (critimport == 1) {
                content.put(ComparaCriterioBean.IMPORTANCIA, comparaCriterioBean.getImportancia());
                // TelaFuncaoAHP.matrizCrit[comparaCriterioBean.getI()][comparaCriterioBean.getJ()] = comparaCriterioBean.getImportancia();
            } else {
                content.put(ComparaCriterioBean.IMPORTANCIA, (1 / comparaCriterioBean.getImportancia()));
                // TelaFuncaoAHP.matrizCrit[comparaCriterioBean.getI()][comparaCriterioBean.getJ()] = (1/comparaCriterioBean.getImportancia());
            }
            String where = "IDCRIT1 = ? AND IDCRIT2 = ?";
            String argumentos[] = {String.valueOf(comparaCriterioBean.getIdcrit1()), String.valueOf(comparaCriterioBean.getIdcrit2())};
            db.update(ComparaCriterioBean.TABELA_temp, content, where, argumentos);


            content = new ContentValues();
            if (critimport == 1) {
                content.put(ComparaCriterioBean.IMPORTANCIA, (1 / comparaCriterioBean.getImportancia()));
                //   TelaFuncaoAHP.matrizCrit[comparaCriterioBean.getJ()][comparaCriterioBean.getI()] = (1/comparaCriterioBean.getImportancia());
            } else {
                content.put(ComparaCriterioBean.IMPORTANCIA, comparaCriterioBean.getImportancia());
                // TelaFuncaoAHP.matrizCrit[comparaCriterioBean.getJ()][comparaCriterioBean.getI()] = comparaCriterioBean.getImportancia();
            }
            where = "IDCRIT2 = ? AND IDCRIT1 = ?";
            String argumentos2[] = {String.valueOf(comparaCriterioBean.getIdcrit1()), String.valueOf(comparaCriterioBean.getIdcrit2())};
            db.update(ComparaCriterioBean.TABELA_temp, content, where, argumentos2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ComparaCriterioBean> carregaComparacoes(int crit2) {
        List<ComparaCriterioBean> lista = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM " + ComparaCriterioBean.TABELA_temp + " WHERE " + ComparaCriterioBean.IDCRIT2 +
                "= " + crit2, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                ComparaCriterioBean comparaCriterioBean = new ComparaCriterioBean();
                comparaCriterioBean.setIdcrit1(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT1)));
                comparaCriterioBean.setIdcrit2(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT2)));
                comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.ID)));
                comparaCriterioBean.setImportancia(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IMPORTANCIA)));
                lista.add(comparaCriterioBean);

            } while (cursor.moveToNext());
        }


        return lista;
    }
}
