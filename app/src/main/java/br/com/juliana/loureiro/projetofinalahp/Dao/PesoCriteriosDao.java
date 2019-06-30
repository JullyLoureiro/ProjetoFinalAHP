package br.com.juliana.loureiro.projetofinalahp.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.juliana.loureiro.projetofinalahp.Bean.AlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaAlternativaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaSubCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.CriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.MatrizCriterioNormalizadaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.MatrizSubcriterioNormalizadaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.PesoCriteriosBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.PesoSubcriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.SomaColunaBean;
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
                    int idcrit = cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT1));
                    Cursor cursor2 = db.rawQuery("SELECT * FROM " + PesoCriteriosBean.TABELA +
                            " WHERE IDCRIT = " + idcrit, null);

                    if (cursor2.getCount() > 0) {
                        ContentValues valores;

                        db = banco.getWritableDatabase();
                        valores = new ContentValues();
                        valores.put(PesoCriteriosBean.SOMA, cursor.getFloat(cursor.getColumnIndex("SOMA")) / qtdcriterios);

                        String where = "IDCRIT = ?";
                        String[] argumentos = {String.valueOf(idcrit)};
                        db.update(PesoCriteriosBean.TABELA, valores, where, argumentos);
                    } else {
                        ContentValues valores;

                        db = banco.getWritableDatabase();
                        valores = new ContentValues();
                        valores.put(PesoCriteriosBean.IDCRIT, cursor.getInt(cursor.getColumnIndex(ComparaCriterioBean.IDCRIT1)));
                        valores.put(PesoCriteriosBean.SOMA, cursor.getFloat(cursor.getColumnIndex("SOMA")) / qtdcriterios);

                        db.insert(PesoCriteriosBean.TABELA, null, valores);
                    }


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

    public List<PesoSubcriterioBean> somaLinhasSubcriterios(float qtdcriterios) {
        List<PesoSubcriterioBean> pesos = new ArrayList<>();

        cursor = db.rawQuery("SELECT IDSUBCRIT1, SUM(IMPORTANCIA) AS SOMA FROM " + MatrizSubcriterioNormalizadaBean.TABELA +
                " GROUP BY IDSUBCRIT1", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                try {
                    int idcrit = cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT1));
                    Cursor cursor2 = db.rawQuery("SELECT * FROM " + PesoSubcriterioBean.TABELA +
                            " WHERE IDSUBCRITERIO = " + idcrit, null);

                    if (cursor2.getCount() > 0) {
                        ContentValues valores;

                      //  db = banco.getWritableDatabase();
                        valores = new ContentValues();
                        valores.put(PesoSubcriterioBean.SOMA, cursor.getFloat(cursor.getColumnIndex("SOMA")) / qtdcriterios);

                        String where = "IDSUBCRITERIO = ?";
                        String[] argumentos = {String.valueOf(idcrit)};
                        db.update(PesoSubcriterioBean.TABELA, valores, where, argumentos);
                    } else {
                        ContentValues valores;

                       // SQLiteDatabase db = banco.getWritableDatabase();

                        /*db.execSQL("INSERT INTO PESO_SUBCRITERIOS (IDSUBCRIT, PESO) VALUES ("+cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT1))
                                +", "+ (cursor.getFloat(cursor.getColumnIndex("SOMA")) / qtdcriterios)+")");*/

                        valores = new ContentValues();
                        valores.put(PesoSubcriterioBean.IDSUBCRIT, cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT1)));
                        valores.put(PesoSubcriterioBean.SOMA, cursor.getFloat(cursor.getColumnIndex("SOMA")) / qtdcriterios);

                        db.insert(PesoSubcriterioBean.TABELA, null, valores);
                    }


                    PesoSubcriterioBean pesoCriteriosBean = new PesoSubcriterioBean();
                    pesoCriteriosBean.setIdsubcrit(cursor.getInt(cursor.getColumnIndex(ComparaSubCriterioBean.IDSUBCRIT1)));
                    pesoCriteriosBean.setPeso(cursor.getFloat(cursor.getColumnIndex("SOMA")) / qtdcriterios);
                    pesos.add(pesoCriteriosBean);

                }catch (SQLException e){
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
        }
        if(cursor!=null) {
            cursor.close();
        }
        db.close();
        return pesos;
    }

    public List<PesoCriteriosBean> somaLinhasAlternativa(float qtdcriterios) {
        List<PesoCriteriosBean> pesos = new ArrayList<>();

        cursor = db.rawQuery("SELECT IDALTERNATIVA1, IDSUBCRITERIO,  IDCRITERIO, SUM(IMPORTANCIA) AS SOMA FROM ALTERNATIVA_NORMALIZADA  GROUP BY IDALTERNATIVA1, IDSUBCRITERIO, IDCRITERIO", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                try {
                    ContentValues valores;

                    db = banco.getWritableDatabase();
                    valores = new ContentValues();
                    valores.put(PesoCriteriosBean.IDALTERNATIVA, cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDALTERNATIVA1)));
                    valores.put("IDCRITERIO", cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDCRITERIO)));
                    valores.put("IDSUBCRITERIO", cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDSUBCRITERIO)));
                    valores.put(PesoCriteriosBean.SOMA, cursor.getFloat(cursor.getColumnIndex("SOMA")) / qtdcriterios);

                    db.insert(PesoCriteriosBean.PESO_ALTERNATIVAS, null, valores);

                    PesoCriteriosBean pesoCriteriosBean = new PesoCriteriosBean();
                    pesoCriteriosBean.setIdcrit(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDCRITERIO)));
                    pesoCriteriosBean.setIdsubcrit(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDSUBCRITERIO)));
                    pesoCriteriosBean.setIdalternativa(cursor.getInt(cursor.getColumnIndex(ComparaAlternativaBean.IDALTERNATIVA1)));
                    pesoCriteriosBean.setSoma(cursor.getFloat(cursor.getColumnIndex("SOMA")) / qtdcriterios);
                    pesos.add(pesoCriteriosBean);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
        }
        if(cursor!=null) {
            cursor.close();
        }
        db.close();
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
        if(cursor!=null) {
            cursor.close();
        }
        db.close();
        return 0;
    }

    public float retornaPeso(int id) {
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
        if(cursor!=null) {
            cursor.close();
        }
        db.close();
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
        if(cursor!=null) {
            cursor.close();
        }
        db.close();
    }

    public void atualizaYMaxSubcriterio(int id, float ymax) {
        try {
            cursor = db.rawQuery("SELECT YMAX FROM " + PesoSubcriterioBean.TABELA + " WHERE " + PesoSubcriterioBean.IDSUBCRIT +
                    " = " + id, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                ymax = ymax + cursor.getFloat(cursor.getColumnIndex(PesoSubcriterioBean.YMAX));
            }

            ContentValues content = new ContentValues();
            content.put(PesoSubcriterioBean.YMAX, ymax);
            String where = "IDSUBCRITERIO = ?";
            String argumentos[] = {String.valueOf(id)};
            db.update(PesoSubcriterioBean.TABELA, content, where, argumentos);
        } catch (Exception ignored) {

        }
        if(cursor!=null) {
            cursor.close();
        }
        db.close();
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
        if(cursor!=null) {
            cursor.close();
        }
        db.close();
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

        db.close();
    }

    public void atualizaTotalDivisaoSub(int id, float totaldiv) {
        try {
            ContentValues content = new ContentValues();
            content.put(PesoSubcriterioBean.TOTALDIVISAO, totaldiv);
            String where = "IDSUBCRITERIO = ?";
            String argumentos[] = {String.valueOf(id)};
            db.update(PesoSubcriterioBean.TABELA, content, where, argumentos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }


    public void atualizaTotal(PesoCriteriosBean pesoCriteriosBean) {
        try {
            ContentValues content = new ContentValues();
            content.put(PesoCriteriosBean.TOTAL, pesoCriteriosBean.getTotaldivisao());
            String where = "IDCRITERIO = ? AND IDALTERNATIVA = ?";
            String argumentos[] = {String.valueOf(pesoCriteriosBean.getIdcrit()), String.valueOf(pesoCriteriosBean.getIdalternativa())};
            db.update(PesoCriteriosBean.PESO_ALTERNATIVAS, content, where, argumentos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();
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
        db.close();
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
        if(cursor!=null) {
            cursor.close();
        }
        db.close();
        return lista;
    }

    public List<PesoSubcriterioBean> carregaYMaxSub() {
        List<PesoSubcriterioBean> lista = new ArrayList<>();
        try {
            cursor = db.rawQuery("SELECT * FROM " + PesoSubcriterioBean.TABELA, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    PesoSubcriterioBean pesoCriteriosBean = new PesoSubcriterioBean();
                    pesoCriteriosBean.setYmax(cursor.getFloat(cursor.getColumnIndex(PesoSubcriterioBean.YMAX)));
                    pesoCriteriosBean.setIdcrit(cursor.getInt(cursor.getColumnIndex(PesoSubcriterioBean.IDSUBCRIT)));
                    pesoCriteriosBean.setPeso(cursor.getFloat(cursor.getColumnIndex(PesoSubcriterioBean.SOMA)));
                    lista.add(pesoCriteriosBean);

                } while (cursor.moveToNext());
            }
        } catch (Exception ignored) {

        }
        if(cursor!=null) {
            cursor.close();
        }
        db.close();
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

        if(cursor!=null) {
            cursor.close();
        }
        db.close();

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

        if(cursor!=null) {
            cursor.close();
        }
        db.close();

        return 0;
    }

    public float calculaMediaSub() {
        try {
            cursor = db.rawQuery("SELECT AVG(TOTAL) AS MEDIA FROM PESO_SUBCRITERIOS", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getFloat(cursor.getColumnIndex("MEDIA"));
            }
        } catch (Exception ignored) {

        }

        if(cursor!=null) {
            cursor.close();
        }
        db.close();

        return 0;
    }

    public float carregaPesoCrit(int idcrit) {
        try {
            cursor = db.rawQuery("SELECT TOTALDIVISAO FROM PESO_SUBCRITERIOS WHERE IDCRIT = " + idcrit, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getFloat(cursor.getColumnIndex("TOTALDIVISAO"));
            }
        } catch (Exception ignored) {

        }

        if(cursor!=null) {
            cursor.close();
        }
        db.close();

        return 0;
    }

    public void deleta() {
        db.execSQL("DELETE FROM " + PesoCriteriosBean.TABELA);
    }

    public void deletaAlternativa() {
        db.execSQL("DELETE FROM " + PesoCriteriosBean.PESO_ALTERNATIVAS);
    }

    public void deletaSubcriterio() {
        db.execSQL("DELETE FROM " + PesoSubcriterioBean.TABELA);
    }

    public String retornaDescricaoAlternativa(int id) {
        try {
            Cursor cursor = db.rawQuery("SELECT " + AlternativaBean.DESCRICAO + " FROM " + AlternativaBean.TABELA +
                    " WHERE " + AlternativaBean.ID + " = " + id, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getString(cursor.getColumnIndex(AlternativaBean.DESCRICAO));

            }
            if(cursor!=null) {
                cursor.close();
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public String retornaDescricaoAlternativaTemp(int id) {
        try {
            Cursor cursor = db.rawQuery("SELECT " + AlternativaBean.DESCRICAO + " FROM " + AlternativaBean.TABELA_temp +
                    " WHERE " + AlternativaBean.ID + " = " + id, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getString(cursor.getColumnIndex(AlternativaBean.DESCRICAO));

            }
            if(cursor!=null) {
                cursor.close();
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public List<PesoCriteriosBean> retornaResultado(int idobjetivo) {
        List<PesoCriteriosBean> lista = new ArrayList<>();
        try {
            cursor = db.rawQuery("SELECT IDALTERNATIVA, SUM(TOTAL * 100) AS PERC FROM PESO_ALTERNATIVAS  GROUP BY IDALTERNATIVA ORDER BY PERC DESC", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    PesoCriteriosBean pesoCriteriosBean = new PesoCriteriosBean();
                    pesoCriteriosBean.setIdobjetivo(idobjetivo);
                    pesoCriteriosBean.setPerc(cursor.getDouble(cursor.getColumnIndex("PERC")));
                    pesoCriteriosBean.setIdalternativa(cursor.getInt(cursor.getColumnIndex("IDALTERNATIVA")));
                    pesoCriteriosBean.setAlternativa(retornaDescricaoAlternativa(pesoCriteriosBean.getIdalternativa()));
                    lista.add(pesoCriteriosBean);
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

    public List<PesoCriteriosBean> retornaResultadoTemp(int idobjetivo) {
        List<PesoCriteriosBean> lista = new ArrayList<>();
        try {
            cursor = db.rawQuery("SELECT IDALTERNATIVA, SUM(TOTAL * 100) AS PERC FROM PESO_ALTERNATIVAS  GROUP BY IDALTERNATIVA ORDER BY PERC DESC", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    PesoCriteriosBean pesoCriteriosBean = new PesoCriteriosBean();
                    pesoCriteriosBean.setIdobjetivo(idobjetivo);
                    pesoCriteriosBean.setPerc(cursor.getDouble(cursor.getColumnIndex("PERC")));
                    pesoCriteriosBean.setIdalternativa(cursor.getInt(cursor.getColumnIndex("IDALTERNATIVA")));
                    pesoCriteriosBean.setAlternativa(retornaDescricaoAlternativaTemp(pesoCriteriosBean.getIdalternativa()));
                    lista.add(pesoCriteriosBean);
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

}
