package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Database.ConfigDB;

public class CriterioDao {
    private Context context;
    private Cursor cursor;
    private SQLiteDatabase db;
    private ConfigDB banco;

    public CriterioDao(Context context) {
        this.context = context;
        banco = new ConfigDB(context);
        db = banco.getReadableDatabase();
    }

    public boolean insereCriterio(CriterioBean criterioBean) {
        try {
            ContentValues valores;

            db = banco.getWritableDatabase();
            valores = new ContentValues();
            valores.put(CriterioBean.DESCRICAO, criterioBean.getDescricao());
            db.insert(CriterioBean.TABELA_temp, null, valores);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;

    }

    public boolean deletaCriterio(int id) {
        try {
            db.execSQL("DELETE FROM " + CriterioBean.TABELA_temp + " WHERE ID = " + id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;

    }

    public List<CriterioBean> carregaCriterios() {
        List<CriterioBean> lista = new ArrayList<>();
        try {
            cursor = db.rawQuery("SELECT * FROM " + CriterioBean.TABELA_temp, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    CriterioBean criterioBean = new CriterioBean();
                    criterioBean.setDescricao(cursor.getString(cursor.getColumnIndex(CriterioBean.DESCRICAO)));
                    criterioBean.setId(cursor.getInt(cursor.getColumnIndex(CriterioBean.ID)));

                    lista.add(criterioBean);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public String retornaDescricao(int id) {
        try {
            cursor = db.rawQuery("SELECT " + CriterioBean.DESCRICAO + " FROM " + CriterioBean.TABELA_temp +
                    " WHERE " + CriterioBean.ID + " = " + id, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getString(cursor.getColumnIndex(CriterioBean.DESCRICAO));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void atualizaCriterio(CriterioBean criterioBean) {
        try {
            ContentValues content = new ContentValues();
            content.put(CriterioBean.DESCRICAO, criterioBean.getDescricao());
            String where = "ID = ?";
            String argumentos[] = {String.valueOf(criterioBean.getId())};
            db.update(CriterioBean.TABELA_temp, content, where, argumentos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
