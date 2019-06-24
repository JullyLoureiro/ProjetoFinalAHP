package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.MatrizCriterioNormalizadaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.MatrizSubcriterioNormalizadaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.SomaColunaBean;
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
            } else {
                ContentValues valores = new ContentValues();
                valores.put(MatrizCriterioNormalizadaBean.IMPORTANCIA, matrizCriterioNormalizadaBean.getImportancia());
                String where = "IDCRIT1 = ? AND IDCRIT2 = ?";
                String argumentos2[] = {String.valueOf(matrizCriterioNormalizadaBean.getIdcrit1()), String.valueOf(matrizCriterioNormalizadaBean.getIdcrit2())};
                db.update(MatrizCriterioNormalizadaBean.TABELA, valores, where, argumentos2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;
    }

    public boolean insereMatrizNormalizadaSubcriterio(MatrizSubcriterioNormalizadaBean matrizCriterioNormalizadaBean) {
        try {

            cursor = db.rawQuery("SELECT * FROM " + MatrizSubcriterioNormalizadaBean.TABELA + " WHERE "
                    + MatrizSubcriterioNormalizadaBean.IDSUBCRIT1 + " = " + matrizCriterioNormalizadaBean.getIdsubcrit1() +
                    " AND " + MatrizSubcriterioNormalizadaBean.IDSUBCRIT2 + " = " + matrizCriterioNormalizadaBean.getIdsubcrit2(), null);

            if (cursor.getCount() <= 0) {
                ContentValues valores;

                db = banco.getWritableDatabase();
                valores = new ContentValues();
                valores.put(MatrizSubcriterioNormalizadaBean.IDSUBCRIT1, matrizCriterioNormalizadaBean.getIdsubcrit1());
                valores.put(MatrizSubcriterioNormalizadaBean.IDSUBCRIT2, matrizCriterioNormalizadaBean.getIdsubcrit2());
                valores.put(MatrizSubcriterioNormalizadaBean.IDCRITERIO, matrizCriterioNormalizadaBean.getIdcriterio());
                valores.put(MatrizSubcriterioNormalizadaBean.IMPORTANCIA, matrizCriterioNormalizadaBean.getImportancia());
                db.insert(MatrizSubcriterioNormalizadaBean.TABELA, null, valores);
                return true;
            } else {
                ContentValues valores = new ContentValues();
                valores.put(MatrizCriterioNormalizadaBean.IMPORTANCIA, matrizCriterioNormalizadaBean.getImportancia());
                String where = "IDSUBCRIT1 = ? AND IDSUBCRIT2 = ?";
                String argumentos2[] = {String.valueOf(matrizCriterioNormalizadaBean.getIdsubcrit1()), String.valueOf(matrizCriterioNormalizadaBean.getIdsubcrit2())};
                db.update(MatrizSubcriterioNormalizadaBean.TABELA, valores, where, argumentos2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor!=null) {
                cursor.close();
            }
            db.close();
        }
        return false;
    }


    public boolean insereMatrizNormalizadaAlternativa(MatrizCriterioNormalizadaBean matrizCriterioNormalizadaBean) {
        try {

            cursor = db.rawQuery("SELECT * FROM ALTERNATIVA_NORMALIZADA WHERE "
                    + MatrizCriterioNormalizadaBean.IDALTERNATIVA1 + " = " + matrizCriterioNormalizadaBean.getIdcrit1() +
                    " AND " + MatrizCriterioNormalizadaBean.IDALTERNATIVA2 + " = " + matrizCriterioNormalizadaBean.getIdcrit2()
                    + " AND " + MatrizCriterioNormalizadaBean.IDCRITERIO + " = " + matrizCriterioNormalizadaBean.getIdcriterio(), null);

            if (cursor.getCount() <= 0) {
                ContentValues valores;

                db = banco.getWritableDatabase();
                valores = new ContentValues();
                valores.put(MatrizCriterioNormalizadaBean.IDALTERNATIVA1, matrizCriterioNormalizadaBean.getIdalternativa1());
                valores.put(MatrizCriterioNormalizadaBean.IDALTERNATIVA2, matrizCriterioNormalizadaBean.getIdalternativa2());
                valores.put(MatrizCriterioNormalizadaBean.IDCRITERIO, matrizCriterioNormalizadaBean.getIdcriterio());
                valores.put(MatrizCriterioNormalizadaBean.IMPORTANCIA, matrizCriterioNormalizadaBean.getImportancia());
                db.insert("ALTERNATIVA_NORMALIZADA", null, valores);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor!=null) {
                cursor.close();
            }
            db.close();
        }
        return false;
    }

    public void deleta() {
        db.execSQL("DELETE FROM " + MatrizCriterioNormalizadaBean.TABELA);
        db.close();
    }

    public void deletaAlternativa() {
        db.execSQL("DELETE FROM ALTERNATIVA_NORMALIZADA");
        db.close();
    }

    public void deletaSubcriterio() {
        db.execSQL("DELETE FROM SUBCRITERIO_NORMALIZADA");
        db.close();
    }
}
