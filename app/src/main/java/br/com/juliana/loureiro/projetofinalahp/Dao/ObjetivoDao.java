package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.AlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ObjetivoBean;
import br.com.juliana.loureiro.projetofinalahp.Database.ConfigDB;
import br.com.juliana.loureiro.projetofinalahp.Util.Utils;

public class ObjetivoDao {

    private Context context;
    private Cursor cursor;
    private SQLiteDatabase db;
    private ConfigDB banco;

    public ObjetivoDao(Context context) {
        this.context = context;
        banco = new ConfigDB(context);
        db = banco.getReadableDatabase();
    }

    public boolean deletaTemp() {
        try {
            db.execSQL("DELETE FROM " + ObjetivoBean.TABELA_TEMP);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;

    }

    public boolean insereObjetivo(ObjetivoBean objetivoBean) {
        try {
            ContentValues valores;

            db = banco.getWritableDatabase();
            valores = new ContentValues();
            valores.put(ObjetivoBean.TITULO, objetivoBean.getTitulo());
            valores.put(ObjetivoBean.DESCRICAO, objetivoBean.getDescricao());

            db.insert(ObjetivoBean.TABELA_TEMP, null, valores);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;

    }

    public int insereObjetivo2(ObjetivoBean objetivoBean) {
        try {
            ContentValues valores;

            db = banco.getWritableDatabase();
            valores = new ContentValues();
            valores.put(ObjetivoBean.TITULO, objetivoBean.getTitulo());
            valores.put("DATA", Utils.DataHojeSemHorasBR());
            valores.put(ObjetivoBean.DESCRICAO, objetivoBean.getDescricao());

            db.insert(ObjetivoBean.TABELA, null, valores);
            return Utils.returnLastId(db);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return 0;
    }

    public boolean deletaObjetivo(int id) {
        try {
            db.execSQL("DELETE FROM  " + ObjetivoBean.TABELA + "WHERE ID = " + id);
            db.execSQL("DELETE FROM  " + CriterioBean.TABELA + "WHERE IDOBJETIVO = " + id);
            db.execSQL("DELETE FROM  " + AlternativaBean.TABELA + "WHERE IDOBJETIVO = " + id);
            db.execSQL("DELETE FROM  " + ComparaCriterioBean.TABELA + "WHERE IDOBJETIVO = " + id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;

    }

    public List<ObjetivoBean> carregaObjetivos() {
        List<ObjetivoBean> lista = new ArrayList<>();
        try {
            cursor = db.rawQuery("SELECT * FROM " + ObjetivoBean.TABELA + " ORDER BY DATA", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    ObjetivoBean objetivoBean = new ObjetivoBean();
                    objetivoBean.setId(cursor.getInt(cursor.getColumnIndex(ObjetivoBean.ID)));
                    objetivoBean.setDescricao(cursor.getString(cursor.getColumnIndex(ObjetivoBean.DESCRICAO)));
                    objetivoBean.setTitulo(cursor.getString(cursor.getColumnIndex(ObjetivoBean.TITULO)));
                    objetivoBean.setData(cursor.getString(cursor.getColumnIndex(ObjetivoBean.DATA)));

                    lista.add(objetivoBean);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ObjetivoBean carregaObjetivosTemp() {
        ObjetivoBean objetivoBean = new ObjetivoBean();
        try {
            cursor = db.rawQuery("SELECT * FROM " + ObjetivoBean.TABELA_TEMP, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();


                    objetivoBean.setId(cursor.getInt(cursor.getColumnIndex(ObjetivoBean.ID)));
                    objetivoBean.setDescricao(cursor.getString(cursor.getColumnIndex(ObjetivoBean.DESCRICAO)));
                    objetivoBean.setTitulo(cursor.getString(cursor.getColumnIndex(ObjetivoBean.TITULO)));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objetivoBean;
    }
}
