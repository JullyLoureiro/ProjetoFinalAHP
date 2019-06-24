package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.SubcriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Database.ConfigDB;
import br.com.juliana.loureiro.projetofinalahp.Util.Utils;

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

    public int insereCriterio(CriterioBean criterioBean, int id) {
        try {
            ContentValues valores;

            db = banco.getWritableDatabase();
            valores = new ContentValues();
            valores.put(CriterioBean.DESCRICAO, criterioBean.getDescricao());
            valores.put(CriterioBean.IDOBJETIVO, id);
            db.insert(CriterioBean.TABELA_temp, null, valores);
            return Utils.returnLastId(db);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return 0;

    }

    public int insereCriterio2(CriterioBean criterioBean, int id) {
        try {
            ContentValues valores;

            db = banco.getWritableDatabase();
            valores = new ContentValues();
            valores.put(CriterioBean.DESCRICAO, criterioBean.getDescricao());
            valores.put(CriterioBean.IDOBJETIVO, id);
            db.insert(CriterioBean.TABELA, null, valores);

            return Utils.returnLastId(db);
           // return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return 0;

    }

    public boolean deletaCriterio(int id) {
        try {
            db.execSQL("DELETE FROM " + CriterioBean.TABELA_temp + " WHERE ID = " + id);
            db.execSQL("DELETE FROM " + SubcriterioBean.TABELA_temp + " WHERE IDCRITERIO = " + id);
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
            db.execSQL("DELETE FROM " + CriterioBean.TABELA_temp );
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

        if(cursor!=null) {
            cursor.close();
        }
        db.close();
        return lista;
    }

    public List<CriterioBean> carregaCriteriosObjetivo(int idobjetivo) {
        List<CriterioBean> lista = new ArrayList<>();
        try {
            cursor = db.rawQuery("SELECT * FROM " + CriterioBean.TABELA + " WHERE IDOBJETIVO = " + idobjetivo, null);
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

        if(cursor!=null) {
            cursor.close();
        }
        db.close();
        return lista;
    }

    public List<CriterioBean> carregaCriterios2(int idobjetivo) {
        List<CriterioBean> lista = new ArrayList<>();
        try {
            cursor = db.rawQuery("SELECT * FROM " + CriterioBean.TABELA + " WHERE " + CriterioBean.IDOBJETIVO + " = " + idobjetivo, null);
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

        if(cursor!=null) {
            cursor.close();
        }
        db.close();
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

        if(cursor!=null) {
            cursor.close();
        }
        db.close();
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
        db.close();
    }

    public int retornaQtdCriterios(){
        try {
            cursor = db.rawQuery("SELECT * FROM " + CriterioBean.TABELA_temp, null);
           return cursor.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return 0;
    }

    public int retornaQtdCriterios2(int idobjetivo){
        try {
            cursor = db.rawQuery("SELECT * FROM " + CriterioBean.TABELA +  " WHERE IDOBJETIVO = " + idobjetivo, null);
            return cursor.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return 0;
    }
}
