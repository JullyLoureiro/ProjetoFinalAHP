package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaAlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
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

    public List<PesoCriteriosBean> somaLinhasAlternativa(float qtdcriterios) {
        List<PesoCriteriosBean> pesos = new ArrayList<>();

        cursor = db.rawQuery("SELECT IDALTERNATIVA1, SUM(IMPORTANCIA) AS SOMA FROM COMPARA_ALTERNATIVATEMP  GROUP BY IDALTERNATIVA1", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                try {
                    ContentValues valores;

                    db = banco.getWritableDatabase();
                    valores = new ContentValues();
                    valores.put(PesoCriteriosBean.IDALTERNATIVA, cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDALTERNATIVA1)));
                    valores.put(PesoCriteriosBean.SOMA, cursor.getFloat(cursor.getColumnIndex("SOMA")) / qtdcriterios);

                    db.insert(PesoCriteriosBean.PESO_ALTERNATIVAS, null, valores);

                    PesoCriteriosBean pesoCriteriosBean = new PesoCriteriosBean();
                    pesoCriteriosBean.setIdalternativa(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDALTERNATIVA1)));
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

    public void atualizaYMax(int id, float ymax) {
        try {
            cursor = db.rawQuery("SELECT YMAX FROM " + PesoCriteriosBean.TABELA + " WHERE " + PesoCriteriosBean.IDCRIT +
                    " = " + id, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                ymax = ymax + cursor.getFloat(cursor.getColumnIndex(PesoCriteriosBean.YMAX));
            }

            ContentValues content = new ContentValues();
            content.put(PesoCriteriosBean.YMAX, ymax);
            String where = "IDCRIT = ?";
            String argumentos[] = {String.valueOf(id)};
            db.update(PesoCriteriosBean.TABELA, content, where, argumentos);
        } catch (Exception ignored) {

        }
    }

    public void atualizaYMaxAlternativa(int id, double ymax) {
        try {
            cursor = db.rawQuery("SELECT YMAX FROM " + PesoCriteriosBean.PESO_ALTERNATIVAS + " WHERE " + PesoCriteriosBean.IDALTERNATIVA +
                    " = " + id, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                ymax = ymax + cursor.getFloat(cursor.getColumnIndex(PesoCriteriosBean.YMAX));
            }

            ContentValues content = new ContentValues();
            content.put(PesoCriteriosBean.YMAX, ymax);
            String where = "IDALTERNATIVA = ?";
            String argumentos[] = {String.valueOf(id)};
            db.update(PesoCriteriosBean.PESO_ALTERNATIVAS, content, where, argumentos);
        } catch (Exception ignored) {

        }
    }

    public void atualizaTotalDivisao(int id, float totaldiv) {
        try {
            ContentValues content = new ContentValues();
            content.put(PesoCriteriosBean.TOTALDIVISAO, totaldiv);
            String where = "IDCRIT = ?";
            String argumentos[] = {String.valueOf(id)};
            db.update(PesoCriteriosBean.TABELA, content, where, argumentos);
        } catch (Exception ignored) {

        }
    }

    public void atualizaTotalDivisaoAlternativa(int id, float totaldiv) {
        try {
            ContentValues content = new ContentValues();
            content.put(PesoCriteriosBean.TOTALDIVISAO, totaldiv);
            String where = "IDALTERNATIVA = ?";
            String argumentos[] = {String.valueOf(id)};
            db.update(PesoCriteriosBean.PESO_ALTERNATIVAS, content, where, argumentos);
        } catch (Exception ignored) {

        }
    }

    public List<PesoCriteriosBean> carregaYMax() {
        List<PesoCriteriosBean> lista = new ArrayList<>();
        try {
            cursor = db.rawQuery("SELECT * FROM " + PesoCriteriosBean.TABELA, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    PesoCriteriosBean pesoCriteriosBean = new PesoCriteriosBean();
                    pesoCriteriosBean.setYmax(cursor.getFloat(cursor.getColumnIndex(PesoCriteriosBean.YMAX)));
                    pesoCriteriosBean.setIdcrit(cursor.getInt(cursor.getColumnIndex(PesoCriteriosBean.IDCRIT)));
                    pesoCriteriosBean.setSoma(cursor.getFloat(cursor.getColumnIndex(PesoCriteriosBean.SOMA)));
                    lista.add(pesoCriteriosBean);

                } while (cursor.moveToNext());
            }
        } catch (Exception ignored) {

        }
        return lista;
    }

    public List<PesoCriteriosBean> carregaYMaxAlternativa() {
        List<PesoCriteriosBean> lista = new ArrayList<>();
        try {
            cursor = db.rawQuery("SELECT * FROM " + PesoCriteriosBean.PESO_ALTERNATIVAS, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    PesoCriteriosBean pesoCriteriosBean = new PesoCriteriosBean();
                    pesoCriteriosBean.setYmax(cursor.getFloat(cursor.getColumnIndex(PesoCriteriosBean.YMAX)));
                    pesoCriteriosBean.setIdalternativa(cursor.getInt(cursor.getColumnIndex(PesoCriteriosBean.IDALTERNATIVA)));
                    pesoCriteriosBean.setSoma(cursor.getFloat(cursor.getColumnIndex(PesoCriteriosBean.SOMA)));
                    lista.add(pesoCriteriosBean);

                } while (cursor.moveToNext());
            }
        } catch (Exception ignored) {

        }
        return lista;
    }

    public float calculaMedia() {
        try {
            cursor = db.rawQuery("SELECT AVG(TOTALDIVISAO) AS MEDIA FROM " + PesoCriteriosBean.TABELA, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getFloat(cursor.getColumnIndex("MEDIA"));
            }
        } catch (Exception ignored) {

        }
        return 0;
    }

    public void deleta() {
        db.execSQL("DELETE FROM " + PesoCriteriosBean.TABELA);
    }
}
