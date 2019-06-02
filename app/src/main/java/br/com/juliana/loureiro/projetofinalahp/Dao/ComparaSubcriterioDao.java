package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaSubCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Database.ConfigDB;

public class ComparaSubcriterioDao {

    private Context context;
    private Cursor cursor;
    private SQLiteDatabase db;
    private ConfigDB banco;

    public ComparaSubcriterioDao(Context context) {
        this.context = context;
        banco = new ConfigDB(context);
        db = banco.getReadableDatabase();
    }

    public int retornaImportancia(int idsub1, int idsub2, int idcrit) {
        try {
            cursor = db.rawQuery("SELECT IMPORTANCIA FROM " + ComparaSubCriterioBean.TABELA + " WHERE " + ComparaSubCriterioBean.IDSUBCRIT1
                    + " = " + idsub1 + " AND " + ComparaSubCriterioBean.IDSUBCRIT2 + " = " + idsub2 + " AND " + ComparaSubCriterioBean.IDCRITERIO +
                    " = " + idcrit, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                double importancia = cursor.getDouble(cursor.getColumnIndex("IMPORTANCIA"));
                if (importancia < 1) {
                    cursor = db.rawQuery("SELECT IMPORTANCIA FROM " + ComparaSubCriterioBean.TABELA + " WHERE " + ComparaSubCriterioBean.IDSUBCRIT1
                            + " = " + idsub1 + " AND " + ComparaSubCriterioBean.IDSUBCRIT2 + " = " + idsub2 + " AND " + ComparaSubCriterioBean.IDCRITERIO +
                            " = " + idcrit, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        double importancia2 = cursor.getDouble(cursor.getColumnIndex("IMPORTANCIA"));
                        switch ((int) importancia2) {
                            case 0:
                                return 8;
                            case 1:
                                return 8;
                            case 2:
                                return 7;
                            case 3:
                                return 6;
                            case 4:
                                return 5;
                            case 5:
                                return 4;
                            case 6:
                                return 3;
                            case 7:
                                return 2;
                            case 8:
                                return 1;
                            case 9:
                                return 0;
                        }
                    }
                } else {
                    switch ((int) importancia) {
                        case 0:
                            return 8;
                        case 1:
                            return 8;
                        case 2:
                            return 9;
                        case 3:
                            return 10;
                        case 4:
                            return 11;
                        case 5:
                            return 12;
                        case 6:
                            return 13;
                        case 7:
                            return 14;
                        case 8:
                            return 15;
                        case 9:
                            return 16;
                        default:
                            break;
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }


    public void atualizaImportancia(ComparaSubCriterioBean comparaCriterioBean, int critimport) {
        try {
            ContentValues content = new ContentValues();
            if (critimport == 1) {
                content.put(ComparaSubCriterioBean.IMPORTANCIA, comparaCriterioBean.getImportancia());
            } else {
                content.put(ComparaSubCriterioBean.IMPORTANCIA, (1 / comparaCriterioBean.getImportancia()));
            }
            String where = "IDSUBCRIT1 = ? AND IDSUBCRIT2 = ?";
            String argumentos[] = {String.valueOf(comparaCriterioBean.getIdsubcrit1()), String.valueOf(comparaCriterioBean.getIdsubcrit2())};
            db.update(ComparaSubCriterioBean.TABELA, content, where, argumentos);


            content = new ContentValues();
            if (critimport == 1) {
                content.put(ComparaSubCriterioBean.IMPORTANCIA, (1 / comparaCriterioBean.getImportancia()));
            } else {
                content.put(ComparaSubCriterioBean.IMPORTANCIA, comparaCriterioBean.getImportancia());
            }
            where = "IDCRIT2 = ? AND IDCRIT1 = ?";
            String argumentos2[] = {String.valueOf(comparaCriterioBean.getIdsubcrit1()), String.valueOf(comparaCriterioBean.getIdsubcrit2())};
            db.update(ComparaSubCriterioBean.TABELA, content, where, argumentos2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
