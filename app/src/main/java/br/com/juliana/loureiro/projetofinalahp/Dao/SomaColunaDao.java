package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaAlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaSubCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ObjetivoBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.SomaColunaBean;
import br.com.juliana.loureiro.projetofinalahp.Database.ConfigDB;

public class SomaColunaDao {
    private Context context;
    private Cursor cursor;
    private SQLiteDatabase db;
    private ConfigDB banco;

    public SomaColunaDao(Context context) {
        this.context = context;
        banco = new ConfigDB(context);
        db = banco.getReadableDatabase();
    }

    public void somaColunas() {
        try {
            cursor = db.rawQuery("SELECT IDCRIT2, SUM(IMPORTANCIA) AS SOMA FROM " + ComparaCriterioBean.TABELA_temp +
                    " GROUP BY IDCRIT2", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    try {
                        int idcrit = cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT2));
                        Cursor cursor2 = db.rawQuery("SELECT *  FROM " + SomaColunaBean.TABELA +
                                " WHERE  IDCRIT = " + idcrit, null);
                        db = banco.getWritableDatabase();
                        if (cursor2.getCount() > 0) {

                            ContentValues valores = new ContentValues();
                            valores.put(SomaColunaBean.IDCRIT, cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT2)));
                            valores.put(SomaColunaBean.SOMA, cursor.getFloat(cursor.getColumnIndex("SOMA")));

                            String where = "IDCRIT = ?";
                            String argumentos2[] = {String.valueOf(idcrit)};
                            db.update(SomaColunaBean.TABELA, valores, where, argumentos2);

                        } else {
                            ContentValues valores;


                            valores = new ContentValues();
                            valores.put(SomaColunaBean.IDCRIT, cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT2)));
                            valores.put(SomaColunaBean.SOMA, cursor.getFloat(cursor.getColumnIndex("SOMA")));

                            db.insert(SomaColunaBean.TABELA, null, valores);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } while (cursor.moveToNext());
            }
        }catch (Exception ignored) {

        }

        if(cursor!=null) {
            cursor.close();
        }
        db.close();
    }

    public void somaColunas2(int idobjetivo) {
        try {
            cursor = db.rawQuery("SELECT IDCRIT2, SUM(IMPORTANCIA) AS SOMA FROM " + ComparaCriterioBean.TABELA +
                    " WHERE IDOBJETIVO = " + idobjetivo + " GROUP BY IDCRIT2", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    try {
                        int idcrit = cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT2));
                        Cursor cursor2 = db.rawQuery("SELECT *  FROM " + SomaColunaBean.TABELA +
                                " WHERE  IDCRIT = " + idcrit, null);
                        db = banco.getWritableDatabase();
                        if (cursor2.getCount() > 0) {

                            ContentValues valores = new ContentValues();
                            valores.put(SomaColunaBean.IDCRIT, cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT2)));
                            valores.put(SomaColunaBean.SOMA, cursor.getFloat(cursor.getColumnIndex("SOMA")));

                            String where = "IDCRIT = ?";
                            String argumentos2[] = {String.valueOf(idcrit)};
                            db.update(SomaColunaBean.TABELA, valores, where, argumentos2);

                        } else {
                            ContentValues valores;


                            valores = new ContentValues();
                            valores.put(SomaColunaBean.IDCRIT, cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT2)));
                            valores.put(SomaColunaBean.SOMA, cursor.getFloat(cursor.getColumnIndex("SOMA")));

                            db.insert(SomaColunaBean.TABELA, null, valores);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } while (cursor.moveToNext());
            }
        }catch (Exception ignored) {

        }

        if(cursor!=null) {
            cursor.close();
        }
        db.close();
    }

    public void somaColunas3() {
        try {
            cursor = db.rawQuery("SELECT IDSUBCRIT2, SUM(IMPORTANCIA) AS SOMA FROM " + ComparaSubCriterioBean.TABELA +
                    " GROUP BY IDSUBCRIT2", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    try {
                        int idcrit = cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT2));

                        Cursor cursor2 = db.rawQuery("SELECT *  FROM " + SomaColunaBean.SOMA_COLUNA_SUBCRITERIO +
                                " WHERE  IDCRIT = " + idcrit, null);

                        db = banco.getWritableDatabase();
                        if (cursor2.getCount() > 0) {

                            ContentValues valores = new ContentValues();
                            valores.put(SomaColunaBean.IDCRIT, cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT2)));
                            valores.put(SomaColunaBean.SOMA, cursor.getFloat(cursor.getColumnIndex("SOMA")));

                            String where = "IDCRIT = ?";
                            String argumentos2[] = {String.valueOf(idcrit)};
                            db.update(SomaColunaBean.SOMA_COLUNA_SUBCRITERIO, valores, where, argumentos2);

                        } else {
                            ContentValues valores;


                            valores = new ContentValues();
                            valores.put(SomaColunaBean.IDCRIT, cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT2)));
                            valores.put(SomaColunaBean.SOMA, cursor.getFloat(cursor.getColumnIndex("SOMA")));

                            db.insert(SomaColunaBean.SOMA_COLUNA_SUBCRITERIO, null, valores);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } while (cursor.moveToNext());
            }
        }catch (Exception ignored) {

        }

        if(cursor!=null) {
            cursor.close();
        }
        db.close();
    }

    public void somaColunasAlternativas(CriterioBean criterio) {

        try {
            if(criterio.isTemsub()) {
                cursor = db.rawQuery("SELECT IDALTERNATIVA2, SUM(IMPORTANCIA) AS SOMA FROM COMPARA_ALTERNATIVATEMP " +
                        "WHERE IDSUBCRITERIO = " + criterio.getId() + " GROUP BY IDALTERNATIVA2 ", null);
            } else {
                cursor = db.rawQuery("SELECT IDALTERNATIVA2, SUM(IMPORTANCIA) AS SOMA FROM COMPARA_ALTERNATIVATEMP " +
                        "WHERE IDCRITERIO = " + criterio.getId() + " GROUP BY IDALTERNATIVA2 ", null);
            }
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    try {
                        ContentValues valores;

                        db = banco.getWritableDatabase();
                        valores = new ContentValues();
                        valores.put("IDALT", cursor.getInt(cursor.getColumnIndex("IDALTERNATIVA2")));
                        valores.put("IDCRIT", criterio.getId());
                        valores.put(SomaColunaBean.SOMA, cursor.getFloat(cursor.getColumnIndex("SOMA")));

                        db.insert(SomaColunaBean.SOMA_COLUNA_ALTERNATIVA, null, valores);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } while (cursor.moveToNext());
            }
        }catch (Exception ignored) {

        }
        if(cursor!=null) {
            cursor.close();
        }
        db.close();
    }

    public void somaColunasAlternativas2(int idcriterio, int idobjetivo) {
        try {
            cursor = db.rawQuery("SELECT IDALTERNATIVA2, SUM(IMPORTANCIA) AS SOMA FROM COMPARA_ALTERNATIVA " +
                    "WHERE IDOBJETIVO = " + idobjetivo + " AND IDCRITERIO = " + idcriterio + " GROUP BY IDALTERNATIVA2 ", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    try {
                        ContentValues valores;

                        db = banco.getWritableDatabase();
                        valores = new ContentValues();
                        valores.put("IDALT", cursor.getInt(cursor.getColumnIndex("IDALTERNATIVA2")));
                        valores.put("IDCRIT", idcriterio);
                        valores.put(SomaColunaBean.SOMA, cursor.getFloat(cursor.getColumnIndex("SOMA")));

                        db.insert(SomaColunaBean.SOMA_COLUNA_ALTERNATIVA, null, valores);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } while (cursor.moveToNext());
            }
        }catch (Exception ignored) {

        }

        if(cursor!=null) {
            cursor.close();
        }
        db.close();
    }

    public float retornaSoma(int id) {
        try {
            cursor = db.rawQuery("SELECT " + SomaColunaBean.SOMA + " FROM " + SomaColunaBean.TABELA +
                    " WHERE " + SomaColunaBean.IDCRIT + " = " + id, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getFloat(cursor.getColumnIndex(SomaColunaBean.SOMA));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor!=null) {
            cursor.close();
        }
        db.close();
        return 0;
    }

    public float retornaSomaSubCriterio(int id) {
        try {
            cursor = db.rawQuery("SELECT " + SomaColunaBean.SOMA + " FROM " + SomaColunaBean.SOMA_COLUNA_SUBCRITERIO +
                    " WHERE " + SomaColunaBean.IDCRIT + " = " + id, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getFloat(cursor.getColumnIndex(SomaColunaBean.SOMA));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor!=null) {
            cursor.close();
        }
        db.close();
        return 0;
    }

    public float retornaSomaAlternativa(ComparaAlternativaBean compalt) {
        try {
            if(compalt.getIdsubcriterio()==0) {
                cursor = db.rawQuery("SELECT " + SomaColunaBean.SOMA + " FROM " + SomaColunaBean.SOMA_COLUNA_ALTERNATIVA +
                        " WHERE IDALT = " + compalt.getIdalternativa2() + " AND IDCRIT = " + compalt.getIdcriterio(), null);
            } else {
                cursor = db.rawQuery("SELECT " + SomaColunaBean.SOMA + " FROM " + SomaColunaBean.SOMA_COLUNA_ALTERNATIVA +
                        " WHERE IDALT = " + compalt.getIdalternativa2() + " AND IDCRIT = " + compalt.getIdsubcriterio(), null);
            }

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getFloat(cursor.getColumnIndex(SomaColunaBean.SOMA));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor!=null) {
            cursor.close();
        }
        db.close();
        return 0;
    }

    public void deleta() {
        db.execSQL("DELETE FROM " + SomaColunaBean.TABELA);
    }

    public void deletaAlternativa() {
        db.execSQL("DELETE FROM " + SomaColunaBean.SOMA_COLUNA_ALTERNATIVA);
    }

    public void deletaSubcriterio() {
        db.execSQL("DELETE FROM " + SomaColunaBean.SOMA_COLUNA_SUBCRITERIO);
    }
}
