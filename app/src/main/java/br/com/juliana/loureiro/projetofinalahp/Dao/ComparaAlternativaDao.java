package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaAlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Database.ConfigDB;

public class ComparaAlternativaDao {
    private Context context;
    private Cursor cursor;
    private SQLiteDatabase db;
    private ConfigDB banco;

    public ComparaAlternativaDao(Context context) {
        this.context = context;
        banco = new ConfigDB(context);
        db = banco.getReadableDatabase();
    }

    public boolean insereComparacoes(ComparaAlternativaBean comparaAlternativaBean) {
        try {

            cursor = db.rawQuery("SELECT * FROM " + ComparaAlternativaBean.TABELA_temp + " WHERE "
                    + ComparaAlternativaBean.IDALTERNATIVA1 + " = " + comparaAlternativaBean.getIdalternativa1() +
                    " AND " + ComparaAlternativaBean.IDALTERNATIVA2 + " = " + comparaAlternativaBean.getIdalternativa2() +
                    " AND " + ComparaAlternativaBean.IDCRITERIO + " = " + comparaAlternativaBean.getIdcriterio(), null);

            if (cursor.getCount() <= 0) {
                ContentValues valores;

                db = banco.getWritableDatabase();
                valores = new ContentValues();
                valores.put(ComparaAlternativaBean.IDALTERNATIVA1, comparaAlternativaBean.getIdalternativa1());
                valores.put(ComparaAlternativaBean.IDALTERNATIVA2, comparaAlternativaBean.getIdalternativa2());
                valores.put(ComparaAlternativaBean.IMPORTANCIA, comparaAlternativaBean.getImportancia());
                valores.put(ComparaAlternativaBean.IDCRITERIO, comparaAlternativaBean.getIdcriterio());
                valores.put(ComparaAlternativaBean.IDOBJETIVO, comparaAlternativaBean.getIdobjetivo());
                db.insert(ComparaAlternativaBean.TABELA_temp, null, valores);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;
    }

    public List<ComparaAlternativaBean> carregaComparacoes() {
        List<ComparaAlternativaBean> lista = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM " + ComparaAlternativaBean.TABELA_temp + " WHERE (" +
                ComparaAlternativaBean.IDALTERNATIVA2 + " <> " + ComparaAlternativaBean.IDALTERNATIVA1 + ") AND " +
                ComparaAlternativaBean.IDALTERNATIVA1 + " < " + ComparaAlternativaBean.IDALTERNATIVA2, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                ComparaAlternativaBean comparaCriterioBean = new ComparaAlternativaBean();
                comparaCriterioBean.setIdalternativa1(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDALTERNATIVA1)));
                comparaCriterioBean.setIdalternativa2(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDALTERNATIVA2)));
                comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.ID)));
                comparaCriterioBean.setImportancia(cursor.getDouble(cursor.getColumnIndex(ComparaAlternativaBean.IMPORTANCIA)));
                comparaCriterioBean.setIdobjetivo(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDOBJETIVO)));
                comparaCriterioBean.setIdcriterio(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDCRITERIO)));
                lista.add(comparaCriterioBean);


            } while (cursor.moveToNext());
        }


        return lista;
    }

    public void atualizaImportancia(ComparaAlternativaBean comparaCriterioBean, int altimport) {
        try {
            ContentValues content = new ContentValues();
            if (altimport == 1) {
                content.put(ComparaAlternativaBean.IMPORTANCIA, comparaCriterioBean.getImportancia());
            } else {
                content.put(ComparaAlternativaBean.IMPORTANCIA, (1 / comparaCriterioBean.getImportancia()));
            }
                String where = "IDALTERNATIVA1 = ? AND IDALTERNATIVA2 = ? AND IDCRITERIO = ?";
            String argumentos[] = {String.valueOf(comparaCriterioBean.getIdalternativa1()),
                    String.valueOf(comparaCriterioBean.getIdalternativa2()), String.valueOf(comparaCriterioBean.getIdcriterio())};
            db.update(ComparaAlternativaBean.TABELA_temp, content, where, argumentos);


            content = new ContentValues();
            if (altimport == 1) {
                content.put(ComparaAlternativaBean.IMPORTANCIA, (1 / comparaCriterioBean.getImportancia()));
            } else {
                content.put(ComparaAlternativaBean.IMPORTANCIA, comparaCriterioBean.getImportancia());
            }
            where = "IDALTERNATIVA2 = ? AND IDALTERNATIVA1 = ? AND IDCRITERIO = ?";
            String argumentos2[] = {String.valueOf(comparaCriterioBean.getIdalternativa1()),
                    String.valueOf(comparaCriterioBean.getIdalternativa2()), String.valueOf(comparaCriterioBean.getIdcriterio())};
            db.update(ComparaAlternativaBean.TABELA_temp, content, where, argumentos2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletaTemp() {
        db.execSQL("DELETE FROM " + ComparaAlternativaBean.TABELA_temp);
    }

    public List<ComparaAlternativaBean> carregaComparacoes2() {
        List<ComparaAlternativaBean> lista = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM " + ComparaAlternativaBean.TABELA_temp, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                ComparaAlternativaBean comparaCriterioBean = new ComparaAlternativaBean();
                comparaCriterioBean.setIdalternativa2(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDALTERNATIVA2)));
                comparaCriterioBean.setIdalternativa1(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDALTERNATIVA1)));
                comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.ID)));
                comparaCriterioBean.setImportancia(cursor.getDouble(cursor.getColumnIndex(ComparaAlternativaBean.IMPORTANCIA)));
                comparaCriterioBean.setIdcriterio(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDCRITERIO)));
                lista.add(comparaCriterioBean);


            } while (cursor.moveToNext());
        }


        return lista;
    }

    public List<ComparaAlternativaBean> carregaComparacoes(int alt2) {
        List<ComparaAlternativaBean> lista = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM " + ComparaAlternativaBean.TABELA_temp + " WHERE " + ComparaAlternativaBean.IDALTERNATIVA2 +
                "= " + alt2, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                ComparaAlternativaBean comparaCriterioBean = new ComparaAlternativaBean();
                comparaCriterioBean.setIdalternativa1(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDALTERNATIVA1)));
                comparaCriterioBean.setIdalternativa2(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDALTERNATIVA2)));
                comparaCriterioBean.setIdcriterio(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDCRITERIO)));
                comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.ID)));
                comparaCriterioBean.setImportancia(cursor.getFloat(cursor.getColumnIndex(ComparaAlternativaBean.IMPORTANCIA)));
                lista.add(comparaCriterioBean);

            } while (cursor.moveToNext());
        }


        return lista;
    }

}
