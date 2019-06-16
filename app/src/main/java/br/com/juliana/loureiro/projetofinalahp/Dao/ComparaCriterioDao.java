package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Activity.TelaFuncaoAHP;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Database.ConfigDB;

public class ComparaCriterioDao {

    private Context context;
    private Cursor cursor;
    private SQLiteDatabase db;
    private ConfigDB banco;

    public ComparaCriterioDao(Context context) {
        this.context = context;
        banco = new ConfigDB(context);
        db = banco.getReadableDatabase();
    }

    public boolean deletaTemp() {
        try {
            db.execSQL("DELETE FROM " + ComparaCriterioBean.TABELA_temp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;

    }

    public boolean insereComparacoes(ComparaCriterioBean comparaCriterioBean) {
        try {

            cursor = db.rawQuery("SELECT * FROM " + ComparaCriterioBean.TABELA_temp + " WHERE "
                    + ComparaCriterioBean.IDCRIT1 + " = " + comparaCriterioBean.getIdcrit1() +
                    " AND " + ComparaCriterioBean.IDCRIT2 + " = " + comparaCriterioBean.getIdcrit2(), null);

            if (cursor.getCount() <= 0) {
                ContentValues valores;

                db = banco.getWritableDatabase();
                valores = new ContentValues();
                valores.put(ComparaCriterioBean.IDCRIT1, comparaCriterioBean.getIdcrit1());
                valores.put(ComparaCriterioBean.IDCRIT2, comparaCriterioBean.getIdcrit2());
                valores.put(ComparaCriterioBean.IMPORTANCIA, comparaCriterioBean.getImportancia());
                db.insert(ComparaCriterioBean.TABELA_temp, null, valores);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;
    }

    public boolean insereComparacoes2(ComparaCriterioBean comparaCriterioBean, int id) {
        try {

                ContentValues valores;

                db = banco.getWritableDatabase();
                valores = new ContentValues();
                valores.put(ComparaCriterioBean.IDCRIT1, comparaCriterioBean.getIdcrit1());
                valores.put(ComparaCriterioBean.IDCRIT2, comparaCriterioBean.getIdcrit2());
                valores.put(ComparaCriterioBean.IMPORTANCIA, comparaCriterioBean.getImportancia());
                valores.put("IDOBJETIVO", id);
                db.insert(ComparaCriterioBean.TABELA, null, valores);
                return true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;
    }

    public boolean insereComparacoesTemp(ComparaCriterioBean comparaCriterioBean, int id) {
        try {

            ContentValues valores;

            db = banco.getWritableDatabase();
            valores = new ContentValues();
            valores.put(ComparaCriterioBean.IDCRIT1, comparaCriterioBean.getIdcrit1());
            valores.put(ComparaCriterioBean.IDCRIT2, comparaCriterioBean.getIdcrit2());
            valores.put(ComparaCriterioBean.IMPORTANCIA, comparaCriterioBean.getImportancia());
            valores.put("IDOBJETIVO", id);
            db.insert(ComparaCriterioBean.TABELA_temp, null, valores);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;
    }

    public List<ComparaCriterioBean> carregaComparacoes() {
        List<ComparaCriterioBean> lista = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM " + ComparaCriterioBean.TABELA_temp + " WHERE (" +
                ComparaCriterioBean.IDCRIT2 + " <> " + ComparaCriterioBean.IDCRIT1 + ") AND " +
                ComparaCriterioBean.IDCRIT1 + " < " + ComparaCriterioBean.IDCRIT2, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                ComparaCriterioBean comparaCriterioBean = new ComparaCriterioBean();
                comparaCriterioBean.setIdcrit1(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT1)));
                comparaCriterioBean.setIdcrit2(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT2)));
                comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.ID)));
                comparaCriterioBean.setImportancia(cursor.getFloat(cursor.getColumnIndex(ComparaCriterioBean.IMPORTANCIA)));
                lista.add(comparaCriterioBean);


            } while (cursor.moveToNext());
        }


        return lista;
    }

    public List<ComparaCriterioBean> carregaComparacoes2() {
        List<ComparaCriterioBean> lista = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM " + ComparaCriterioBean.TABELA_temp, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                ComparaCriterioBean comparaCriterioBean = new ComparaCriterioBean();
                comparaCriterioBean.setIdcrit1(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT1)));
                comparaCriterioBean.setIdcrit2(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT2)));
                comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.ID)));
                comparaCriterioBean.setImportancia(cursor.getFloat(cursor.getColumnIndex(ComparaCriterioBean.IMPORTANCIA)));
                lista.add(comparaCriterioBean);


            } while (cursor.moveToNext());
        }


        return lista;
    }

    public List<ComparaCriterioBean> carregaComparacoes3(int idobjetivo) {
        List<ComparaCriterioBean> lista = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM " + ComparaCriterioBean.TABELA + " WHERE IDOBJETIVO = " + idobjetivo, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                ComparaCriterioBean comparaCriterioBean = new ComparaCriterioBean();
                comparaCriterioBean.setIdcrit1(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT1)));
                comparaCriterioBean.setIdcrit2(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT2)));
                comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.ID)));
                comparaCriterioBean.setImportancia(cursor.getFloat(cursor.getColumnIndex(ComparaCriterioBean.IMPORTANCIA)));
                lista.add(comparaCriterioBean);


            } while (cursor.moveToNext());
        }


        return lista;
    }

    public void atualizaImportancia(ComparaCriterioBean comparaCriterioBean, int critimport) {
        try {
            ContentValues content = new ContentValues();
            if (critimport == 1) {
                content.put(ComparaCriterioBean.IMPORTANCIA, comparaCriterioBean.getImportancia());
            } else {
                content.put(ComparaCriterioBean.IMPORTANCIA, (1 / comparaCriterioBean.getImportancia()));
            }
            String where = "IDCRIT1 = ? AND IDCRIT2 = ?";
            String argumentos[] = {String.valueOf(comparaCriterioBean.getIdcrit1()), String.valueOf(comparaCriterioBean.getIdcrit2())};
            db.update(ComparaCriterioBean.TABELA_temp, content, where, argumentos);


            content = new ContentValues();
            if (critimport == 1) {
                content.put(ComparaCriterioBean.IMPORTANCIA, (1 / comparaCriterioBean.getImportancia()));
            } else {
                content.put(ComparaCriterioBean.IMPORTANCIA, comparaCriterioBean.getImportancia());
            }
            where = "IDCRIT2 = ? AND IDCRIT1 = ?";
            String argumentos2[] = {String.valueOf(comparaCriterioBean.getIdcrit1()), String.valueOf(comparaCriterioBean.getIdcrit2())};
            db.update(ComparaCriterioBean.TABELA_temp, content, where, argumentos2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ComparaCriterioBean> carregaComparacoes(int crit2) {
        List<ComparaCriterioBean> lista = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM " + ComparaCriterioBean.TABELA_temp + " WHERE " + ComparaCriterioBean.IDCRIT2 +
                "= " + crit2, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                ComparaCriterioBean comparaCriterioBean = new ComparaCriterioBean();
                comparaCriterioBean.setIdcrit1(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT1)));
                comparaCriterioBean.setIdcrit2(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT2)));
                comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.ID)));
                comparaCriterioBean.setImportancia(cursor.getFloat(cursor.getColumnIndex(ComparaCriterioBean.IMPORTANCIA)));
                lista.add(comparaCriterioBean);

            } while (cursor.moveToNext());
        }


        return lista;
    }

    public List<ComparaCriterioBean> carregaComparacoes2(int crit2) {
        List<ComparaCriterioBean> lista = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM " + ComparaCriterioBean.TABELA + " WHERE " + ComparaCriterioBean.IDCRIT2 +
                "= " + crit2, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                ComparaCriterioBean comparaCriterioBean = new ComparaCriterioBean();
                comparaCriterioBean.setIdcrit1(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT1)));
                comparaCriterioBean.setIdcrit2(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT2)));
                comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.ID)));
                comparaCriterioBean.setImportancia(cursor.getFloat(cursor.getColumnIndex(ComparaCriterioBean.IMPORTANCIA)));
                lista.add(comparaCriterioBean);

            } while (cursor.moveToNext());
        }


        return lista;
    }

    public List<ComparaCriterioBean> carregaComparacoesTemp() {
        List<ComparaCriterioBean> lista = new ArrayList<>();
        try {


            cursor = db.rawQuery("SELECT * FROM " + ComparaCriterioBean.TABELA_temp, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {

                    ComparaCriterioBean comparaCriterioBean = new ComparaCriterioBean();
                    comparaCriterioBean.setIdcrit1(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT1)));
                    comparaCriterioBean.setIdcrit2(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT2)));
                    comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.ID)));
                    comparaCriterioBean.setImportancia(cursor.getFloat(cursor.getColumnIndex(ComparaCriterioBean.IMPORTANCIA)));
                    lista.add(comparaCriterioBean);

                } while (cursor.moveToNext());
            }
        }catch (Exception ignored) {

        }


        return lista;
    }

    public List<ComparaCriterioBean> carregaComparacoesObjetivo(int idobjetivo) {
        List<ComparaCriterioBean> lista = new ArrayList<>();
        try {
            cursor = db.rawQuery("SELECT * FROM " + ComparaCriterioBean.TABELA + " WHERE IDOBJETIVO " +
                    "= " + idobjetivo, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {

                    ComparaCriterioBean comparaCriterioBean = new ComparaCriterioBean();
                    comparaCriterioBean.setIdcrit1(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT1)));
                    comparaCriterioBean.setIdcrit2(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT2)));
                    comparaCriterioBean.setId(cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.ID)));
                    comparaCriterioBean.setImportancia(cursor.getFloat(cursor.getColumnIndex(ComparaCriterioBean.IMPORTANCIA)));
                    lista.add(comparaCriterioBean);

                } while (cursor.moveToNext());
            }
        }catch (Exception ignored) {

        }

        return lista;
    }

    public int retornaImportancia(int id1, int id2) {
        try {
            cursor = db.rawQuery("SELECT IMPORTANCIA FROM " + ComparaCriterioBean.TABELA_temp + " WHERE " + ComparaCriterioBean.IDCRIT1
                    + " = " + id1 + " AND " + ComparaCriterioBean.IDCRIT2 + " = " + id2, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                double importancia = cursor.getDouble(cursor.getColumnIndex("IMPORTANCIA"));
                if (importancia < 1) {
                    cursor = db.rawQuery("SELECT IMPORTANCIA FROM " + ComparaCriterioBean.TABELA_temp + " WHERE " + ComparaCriterioBean.IDCRIT1
                            + " = " + id2 + " AND " + ComparaCriterioBean.IDCRIT2 + " = " + id1, null);
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

    public void atualizaIdCriterio(int novoid, int antigoid) {
        try{
            db.execSQL("UPDATE " + ComparaCriterioBean.TABELA_temp + " SET " + ComparaCriterioBean.IDCRIT1 + " = "+ novoid +
                    " WHERE IDCRIT1 = " + antigoid);
            db.execSQL("UPDATE " + ComparaCriterioBean.TABELA_temp + " SET " + ComparaCriterioBean.IDCRIT2 + " = "+ novoid +
                    " WHERE IDCRIT2 = " + antigoid);
        }catch (Exception ignored) {

        }
    }
}
