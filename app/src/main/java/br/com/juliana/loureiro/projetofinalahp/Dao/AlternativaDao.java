package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.AlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Database.ConfigDB;

public class AlternativaDao {
    private Context context;
    private Cursor cursor;
    private SQLiteDatabase db;
    private ConfigDB banco;

    public AlternativaDao(Context context) {
        this.context = context;
        banco = new ConfigDB(context);
        db = banco.getReadableDatabase();
    }

    public boolean insereAlternativa(AlternativaBean alternativaBean) {
        try {
            ContentValues valores;

            db = banco.getWritableDatabase();
            valores = new ContentValues();
            valores.put(AlternativaBean.DESCRICAO, alternativaBean.getDescricao());
            db.insert(AlternativaBean.TABELA_temp, null, valores);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;

    }

    public boolean deletaAlternativa(int id) {
        try {
            db.execSQL("DELETE FROM " + AlternativaBean.TABELA_temp + " WHERE ID = " + id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;

    }

    public boolean deletaTemp() {
        try {
            db.execSQL("DELETE FROM " + AlternativaBean.TABELA_temp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;

    }

    public List<AlternativaBean> carregaAlternativas() {
        List<AlternativaBean> lista = new ArrayList<>();
        try {
            cursor = db.rawQuery("SELECT * FROM " + AlternativaBean.TABELA_temp, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    AlternativaBean alternativaBean= new AlternativaBean();
                    alternativaBean.setDescricao(cursor.getString(cursor.getColumnIndex(AlternativaBean.DESCRICAO)));
                    alternativaBean.setId(cursor.getInt(cursor.getColumnIndex(CriterioBean.ID)));

                    lista.add(alternativaBean);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void atualizaAlternativa(AlternativaBean alternativaBean) {
        try {
            ContentValues content = new ContentValues();
            content.put(AlternativaBean.DESCRICAO, alternativaBean.getDescricao());
            String where = "ID = ?";
            String argumentos[] = {String.valueOf(alternativaBean.getId())};
            db.update(AlternativaBean.TABELA_temp, content, where, argumentos);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String retornaDescricao(int id) {
        try {
            cursor = db.rawQuery("SELECT " + AlternativaBean.DESCRICAO + " FROM " + AlternativaBean.TABELA_temp +
                    " WHERE " + AlternativaBean.ID + " = " + id, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getString(cursor.getColumnIndex(AlternativaBean.DESCRICAO));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
