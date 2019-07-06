package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.SubcriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Database.ConfigDB;

public class SubcriteriosDao {

    private Context context;
    private Cursor cursor;
    private SQLiteDatabase db;
    private ConfigDB banco;

    public SubcriteriosDao(Context context) {
        this.context = context;
        banco = new ConfigDB(context);
        db = banco.getReadableDatabase();
    }

    public String retornaDescricao(int id) {
        try {
            cursor = db.rawQuery("SELECT " + SubcriterioBean.DESCRICAO + " FROM " + SubcriterioBean.TABELA_temp +
                    " WHERE " + SubcriterioBean.ID + " = " + id, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getString(cursor.getColumnIndex(SubcriterioBean.DESCRICAO));

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

    public boolean insereCriterioTemp(SubcriterioBean criterioBean) {
        try {
            ContentValues valores;

            db = banco.getWritableDatabase();
            valores = new ContentValues();
            valores.put(SubcriterioBean.DESCRICAO, criterioBean.getDescricao());
            valores.put(SubcriterioBean.IDCRITERIO, criterioBean.getIdcriterio());
            db.insert(SubcriterioBean.TABELA_temp, null, valores);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;

    }

    public boolean insereCriterio(SubcriterioBean criterioBean, int id) {
        try {
            ContentValues valores;

            db = banco.getWritableDatabase();
            valores = new ContentValues();
            valores.put(SubcriterioBean.DESCRICAO, criterioBean.getDescricao());
            valores.put(SubcriterioBean.IDOBJETIVO, id);
            valores.put(SubcriterioBean.IDCRITERIO, criterioBean.getIdcriterio());
            db.insert(SubcriterioBean.TABELA, null, valores);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;

    }

    public boolean insereCriterio2(SubcriterioBean criterioBean, int id) {
        try {
            ContentValues valores;

            db = banco.getWritableDatabase();
            valores = new ContentValues();
            valores.put(SubcriterioBean.DESCRICAO, criterioBean.getDescricao());
            valores.put(SubcriterioBean.IDOBJETIVO, id);
            valores.put(SubcriterioBean.IDCRITERIO, criterioBean.getIdcriterio());
            db.insert(SubcriterioBean.TABELA_temp, null, valores);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;

    }

    public boolean deletaSubCriterio(int id) {
        try {
            db.execSQL("DELETE FROM " + SubcriterioBean.TABELA_temp + " WHERE ID = " + id);
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
            db.execSQL("DELETE FROM " + SubcriterioBean.TABELA_temp );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }


        return false;

    }

    public List<SubcriterioBean> carregaCriterios(int idcriterio) {
        List<SubcriterioBean> lista = new ArrayList<>();
        try {
            cursor = db.rawQuery("SELECT * FROM " + SubcriterioBean.TABELA_temp + " where " + SubcriterioBean.IDCRITERIO + "=" + idcriterio, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    SubcriterioBean criterioBean = new SubcriterioBean();
                    criterioBean.setDescricao(cursor.getString(cursor.getColumnIndex(SubcriterioBean.DESCRICAO)));
                    criterioBean.setId(cursor.getInt(cursor.getColumnIndex(SubcriterioBean.ID)));
                    criterioBean.setIdcriterio(cursor.getInt(cursor.getColumnIndex(SubcriterioBean.IDCRITERIO)));

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

    public List<SubcriterioBean> carregaCriterios() {
        List<SubcriterioBean> lista = new ArrayList<>();
        try {
            cursor = db.rawQuery("SELECT * FROM " + SubcriterioBean.TABELA_temp, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    SubcriterioBean criterioBean = new SubcriterioBean();
                    criterioBean.setDescricao(cursor.getString(cursor.getColumnIndex(SubcriterioBean.DESCRICAO)));
                    criterioBean.setId(cursor.getInt(cursor.getColumnIndex(SubcriterioBean.ID)));
                    criterioBean.setIdcriterio(cursor.getInt(cursor.getColumnIndex(SubcriterioBean.IDCRITERIO)));

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

    public List<SubcriterioBean> carregaCriterio(int idobjetivo) {
        List<SubcriterioBean> lista = new ArrayList<>();
        try {
            cursor = db.rawQuery("SELECT * FROM " + SubcriterioBean.TABELA + " WHERE " + SubcriterioBean.IDOBJETIVO + "=" + idobjetivo, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    SubcriterioBean criterioBean = new SubcriterioBean();
                    criterioBean.setDescricao(cursor.getString(cursor.getColumnIndex(SubcriterioBean.DESCRICAO)));
                    criterioBean.setId(cursor.getInt(cursor.getColumnIndex(SubcriterioBean.ID)));
                    criterioBean.setId(cursor.getInt(cursor.getColumnIndex(SubcriterioBean.IDCRITERIO)));

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


    public void atualizaCriterio(SubcriterioBean criterioBean) {
        try {
            ContentValues content = new ContentValues();
            content.put(SubcriterioBean.DESCRICAO, criterioBean.getDescricao());
            String where = "ID = ?";
            String argumentos[] = {String.valueOf(criterioBean.getId())};
            db.update(SubcriterioBean.TABELA_temp, content, where, argumentos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
    }

    public int retornaQtd(){
        try {
            cursor = db.rawQuery("SELECT * FROM " + SubcriterioBean.TABELA_temp, null);
            return cursor.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor!=null) {
            cursor.close();
        }
        db.close();

        return 0;
    }

    public int retornaQtd2(){
        try {
            cursor = db.rawQuery("SELECT * FROM " + SubcriterioBean.TABELA, null);
            return cursor.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor!=null) {
            cursor.close();
        }
        db.close();

        return 0;
    }

    public void atualizaIdCriterio(int novoid, int antigoid) {
        try{
            db.execSQL("UPDATE " + SubcriterioBean.TABELA_temp + " SET " + SubcriterioBean.IDCRITERIO + " = "+ novoid +
                    " WHERE IDCRITERIO = " + antigoid);
        }catch (Exception ignored) {

        }
        db.close();
    }
}
