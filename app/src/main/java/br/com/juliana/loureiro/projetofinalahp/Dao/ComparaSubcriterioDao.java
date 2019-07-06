package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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
        if(cursor!=null) {
            cursor.close();
        }
        db.close();

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
            String where = "IDSUBCRIT1 = ? AND IDSUBCRIT2 = ? AND IDCRITERIO = ?";
            String argumentos[] = {String.valueOf(comparaCriterioBean.getIdsubcrit1()), String.valueOf(comparaCriterioBean.getIdsubcrit2()), String.valueOf(comparaCriterioBean.getIdcriterio())};
            db.update(ComparaSubCriterioBean.TABELA, content, where, argumentos);


            content = new ContentValues();
            if (critimport == 1) {
                content.put(ComparaSubCriterioBean.IMPORTANCIA, (1 / comparaCriterioBean.getImportancia()));
            } else {
                content.put(ComparaSubCriterioBean.IMPORTANCIA, comparaCriterioBean.getImportancia());
            }
            where = "IDSUBCRIT2 = ? AND IDSUBCRIT1 = ? AND IDCRITERIO = ?";
            String argumentos2[] = {String.valueOf(comparaCriterioBean.getIdsubcrit1()), String.valueOf(comparaCriterioBean.getIdsubcrit2()), String.valueOf(comparaCriterioBean.getIdcriterio())};
            db.update(ComparaSubCriterioBean.TABELA, content, where, argumentos2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }


    public boolean insereComparacoes(ComparaSubCriterioBean comparaCriterioBean) {
        try {

            cursor = db.rawQuery("SELECT * FROM " + ComparaSubCriterioBean.TABELA + " WHERE "
                    + ComparaSubCriterioBean.IDSUBCRIT1 + " = " + comparaCriterioBean.getIdsubcrit1() +
                    " AND " + ComparaSubCriterioBean.IDSUBCRIT2 + " = " + comparaCriterioBean.getIdsubcrit2()
                    + " AND IDCRITERIO = " + comparaCriterioBean.getIdcriterio(), null);

            if (cursor.getCount() <= 0) {
                ContentValues valores;

                db = banco.getWritableDatabase();
                valores = new ContentValues();
                valores.put(ComparaSubCriterioBean.IDSUBCRIT1, comparaCriterioBean.getIdsubcrit1());
                valores.put(ComparaSubCriterioBean.IDSUBCRIT2, comparaCriterioBean.getIdsubcrit2());
                valores.put(ComparaSubCriterioBean.IDCRITERIO, comparaCriterioBean.getIdcriterio());
                valores.put(ComparaSubCriterioBean.IMPORTANCIA, comparaCriterioBean.getImportancia());
                db.insert(ComparaSubCriterioBean.TABELA, null, valores);
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

    public void deletaTemp() {
        db.execSQL("DELETE FROM " + ComparaSubCriterioBean.TABELA);
    }

    public List<ComparaSubCriterioBean> carregaComparacoes() {
        List<ComparaSubCriterioBean> lista = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM " + ComparaSubCriterioBean.TABELA + " WHERE (" +
                ComparaSubCriterioBean.IDSUBCRIT2 + " <> " + ComparaSubCriterioBean.IDSUBCRIT1 + ") AND " +
                ComparaSubCriterioBean.IDSUBCRIT1 + " < " + ComparaSubCriterioBean.IDSUBCRIT2, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                ComparaSubCriterioBean comparaCriterioBean = new ComparaSubCriterioBean();
                comparaCriterioBean.setIdsubcrit1(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT1)));
                comparaCriterioBean.setIdsubcrit2(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT2)));
                comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.ID)));
                comparaCriterioBean.setImportancia(cursor.getFloat(cursor.getColumnIndex(ComparaSubCriterioBean.IMPORTANCIA)));
                comparaCriterioBean.setIdcriterio(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDCRITERIO)));
                lista.add(comparaCriterioBean);


            } while (cursor.moveToNext());
        }

        if(cursor!=null) {
            cursor.close();
        }
        db.close();
        return lista;
    }

    public List<ComparaSubCriterioBean> carregaComparacoes(int crit2) {
        List<ComparaSubCriterioBean> lista = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM " + ComparaSubCriterioBean.TABELA + " WHERE " + ComparaSubCriterioBean.IDSUBCRIT2 +
                "= " + crit2, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                ComparaSubCriterioBean comparaCriterioBean = new ComparaSubCriterioBean();
                comparaCriterioBean.setIdsubcrit1(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT1)));
                comparaCriterioBean.setIdsubcrit2(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT2)));
                comparaCriterioBean.setIdcriterio(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDCRITERIO)));
                comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.ID)));
                comparaCriterioBean.setImportancia(cursor.getFloat(cursor.getColumnIndex(ComparaSubCriterioBean.IMPORTANCIA)));
                lista.add(comparaCriterioBean);

            } while (cursor.moveToNext());
        }

        if(cursor!=null) {
            cursor.close();
        }
        db.close();
        return lista;
    }

    public List<ComparaSubCriterioBean> carregaComparacoes2(int crit2) {
        List<ComparaSubCriterioBean> lista = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM " + ComparaSubCriterioBean.TABELA2+ " WHERE " + ComparaSubCriterioBean.IDSUBCRIT2 +
                "= " + crit2, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                ComparaSubCriterioBean comparaCriterioBean = new ComparaSubCriterioBean();
                comparaCriterioBean.setIdsubcrit1(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT1)));
                comparaCriterioBean.setIdsubcrit2(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT2)));
                comparaCriterioBean.setIdcriterio(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDCRITERIO)));
                comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.ID)));
                comparaCriterioBean.setImportancia(cursor.getFloat(cursor.getColumnIndex(ComparaSubCriterioBean.IMPORTANCIA)));
                lista.add(comparaCriterioBean);

            } while (cursor.moveToNext());
        }

        if(cursor!=null) {
            cursor.close();
        }
        db.close();
        return lista;
    }

    public List<ComparaSubCriterioBean> carregaComparacoes2() {
        List<ComparaSubCriterioBean> lista = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM " + ComparaSubCriterioBean.TABELA, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                ComparaSubCriterioBean comparaCriterioBean = new ComparaSubCriterioBean();
                comparaCriterioBean.setIdsubcrit2(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT2)));
                comparaCriterioBean.setIdsubcrit1(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT1)));
                comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.ID)));
                comparaCriterioBean.setImportancia(cursor.getFloat(cursor.getColumnIndex(ComparaSubCriterioBean.IMPORTANCIA)));
                comparaCriterioBean.setIdcriterio(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDCRITERIO)));
                lista.add(comparaCriterioBean);


            } while (cursor.moveToNext());
        }
        if(cursor!=null) {
            cursor.close();
        }
        db.close();

        return lista;
    }

    public List<ComparaSubCriterioBean> carregaComparacoes3() {
        List<ComparaSubCriterioBean> lista = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM " + ComparaSubCriterioBean.TABELA2, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                ComparaSubCriterioBean comparaCriterioBean = new ComparaSubCriterioBean();
                comparaCriterioBean.setIdsubcrit2(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT2)));
                comparaCriterioBean.setIdsubcrit1(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT1)));
                comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.ID)));
                comparaCriterioBean.setImportancia(cursor.getFloat(cursor.getColumnIndex(ComparaSubCriterioBean.IMPORTANCIA)));
                comparaCriterioBean.setIdcriterio(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDCRITERIO)));
                lista.add(comparaCriterioBean);


            } while (cursor.moveToNext());
        }
        if(cursor!=null) {
            cursor.close();
        }
        db.close();

        return lista;
    }
}
