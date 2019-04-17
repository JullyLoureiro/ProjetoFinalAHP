package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.ObjetivoBean;
import br.com.juliana.loureiro.projetofinalahp.Database.ConfigDB;

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

    public boolean insereObjetivo(ObjetivoBean objetivoBean){
        try {
            ContentValues valores;

            db = banco.getWritableDatabase();
            valores = new ContentValues();
            valores.put(ObjetivoBean.TITULO, objetivoBean.getTitulo());
            valores.put(ObjetivoBean.DESCRICAO, objetivoBean.getDescricao());

            db.insert(ObjetivoBean.TABELA, null, valores);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;

    }

    public boolean deletaObjetivo(int id){
        try {
            db.execSQL("DELETE FROM OBJETIVOS WHERE ID = " + id);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;

    }

    public List<ObjetivoBean> carregaObjetivos(){
        List<ObjetivoBean> lista = new ArrayList<>();
        try{
            cursor = db.rawQuery("SELECT * FROM OBJETIVOS", null);
            if(cursor.getCount()>0) {
                cursor.moveToFirst();
                do{
                    ObjetivoBean objetivoBean = new ObjetivoBean();
                    objetivoBean.setDescricao(cursor.getString(cursor.getColumnIndex(ObjetivoBean.DESCRICAO)));
                    objetivoBean.setTitulo(cursor.getString(cursor.getColumnIndex(ObjetivoBean.TITULO)));

                    lista.add(objetivoBean);
                }while (cursor.moveToNext());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
