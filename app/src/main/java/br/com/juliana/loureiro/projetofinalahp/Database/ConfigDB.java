package br.com.juliana.loureiro.projetofinalahp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConfigDB extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "banco.db";
    private static final int VERSAO = 1;

    public ConfigDB(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_OBJETIVO = "CREATE TABLE OBJETIVOS ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TITULO TEXT, " +
                "DATA DATE, " +
                "DESCRICAO TEXT " +
                " )";

        db.execSQL(CREATE_OBJETIVO);

        String CREATE_OBJETIVO_TEMP = "CREATE TABLE OBJETIVOS_TEMP ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TITULO TEXT, " +
                "DESCRICAO TEXT " +
                " )";

        db.execSQL(CREATE_OBJETIVO_TEMP);

        String CREATE_CRITERIO = "CREATE TABLE CRITERIOS ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDOBJETIVO INTEGER, " +
                "DESCRICAO TEXT " +
                " )";

        db.execSQL(CREATE_CRITERIO);


        String CREATE_SUBCRITERIO = "CREATE TABLE SUBCRITERIOS ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDOBJETIVO INTEGER, " +
                "IDCRITERIO INTEGER, " +
                "DESCRICAO TEXT " +
                " )";

        db.execSQL(CREATE_SUBCRITERIO);

        String CREATE_SUBCRITERIOTEMP = "CREATE TABLE SUBCRITERIOS_TEMP ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDOBJETIVO INTEGER, " +
                "IDCRITERIO INTEGER, " +
                "DESCRICAO TEXT " +
                " )";

        db.execSQL(CREATE_SUBCRITERIOTEMP);

        String CREATE_CRITERIO_TEMP = "CREATE TABLE CRITERIOS_TEMP ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDOBJETIVO INTEGER, " +
                "DESCRICAO TEXT " +
                " )";

        db.execSQL(CREATE_CRITERIO_TEMP);

        String CREATE_ALTERNATIVAS = "CREATE TABLE ALTERNATIVAS ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDOBJETIVO INTEGER, " +
                "DESCRICAO TEXT " +
                " )";

        db.execSQL(CREATE_ALTERNATIVAS);

        String CREATE_ALTERNATIVAS_TEMP = "CREATE TABLE ALTERNATIVAS_TEMP ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDOBJETIVO INTEGER, " +
                "DESCRICAO TEXT " +
                " )";

        db.execSQL(CREATE_ALTERNATIVAS_TEMP);

        String CREATE_COMPARACRITERIO = "CREATE TABLE COMPARA_CRITERIOS ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDCRIT1 INTEGER, " +
                "IDCRIT2 INTEGER, " +
                "IDOBJETIVO INTEGER, " +
                "IMPORTANCIA FLOAT " +
                " )";

        db.execSQL(CREATE_COMPARACRITERIO);

        String CREATE_SUBCOMPARACRITERIO = "CREATE TABLE COMPARA_SUBCRITERIOS ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDCRITERIO NTEGER, " +
                "IDSUBCRIT1 NTEGER, " +
                "IDSUBCRIT2 INTEGER, " +
                "IDOBJETIVO INTEGER, " +
                "IMPORTANCIA FLOAT " +
                " )";

        db.execSQL(CREATE_SUBCOMPARACRITERIO);

        String CREATE_COMPARAALTERNATIVA = "CREATE TABLE COMPARA_ALTERNATIVA ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDALTERNATIVA1 INTEGER, " +
                "IDALTERNATIVA2 INTEGER, " +
                "IDCRITERIO INTEGER, " +
                "IDOBJETIVO INTEGER, " +
                "IMPORTANCIA FLOAT " +
                " )";

        db.execSQL(CREATE_COMPARAALTERNATIVA);

        String CREATE_COMPARACRITERIO_TEMP = "CREATE TABLE COMPARA_CRITERIOS_TEMP ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDCRIT1 INTEGER, " +
                "IDCRIT2 INTEGER, " +
                "IDOBJETIVO INTEGER, " +
                "IMPORTANCIA FLOAT " +
                " )";

        db.execSQL(CREATE_COMPARACRITERIO_TEMP);

        String CREATE_SUBCOMPARACRITERIOTEMP = "CREATE TABLE COMPARA_SUBCRITERIOSTEMP ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDCRITERIO NTEGER, " +
                "IDSUBCRIT1 NTEGER, " +
                "IDSUBCRIT2 INTEGER, " +
                "IDOBJETIVO INTEGER, " +
                "IMPORTANCIA FLOAT " +
                " )";

        db.execSQL(CREATE_SUBCOMPARACRITERIOTEMP);

        String CREATE_COMPARAALTERNATIVATEMP = "CREATE TABLE COMPARA_ALTERNATIVATEMP ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDALTERNATIVA1 INTEGER, " +
                "IDALTERNATIVA2 INTEGER, " +
                "IDCRITERIO INTEGER, " +
                "IDOBJETIVO INTEGER, " +
                "IMPORTANCIA FLOAT " +
                " )";

        db.execSQL(CREATE_COMPARAALTERNATIVATEMP);

        String CREATE_SOMACOLUNA = "CREATE TABLE SOMA_COLUNA ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDCRIT INTEGER, " +
                "SOMA FLOAT " +
                " )";

        db.execSQL(CREATE_SOMACOLUNA);

        String SOMA_COLUNA_ALTERNATIVA = "CREATE TABLE SOMA_COLUNA_ALTERNATIVA ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDALT INTEGER, " +
                "IDCRIT INTEGER, " +
                "SOMA FLOAT " +
                " )";

        db.execSQL(SOMA_COLUNA_ALTERNATIVA);

        String SOMA_COLUNA_SUBCRITERIO = "CREATE TABLE SOMA_COLUNA_SUBCRITERIO ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDSUBCRIT INTEGER, " +
                "IDCRIT INTEGER, " +
                "SOMA FLOAT " +
                " )";

        db.execSQL(SOMA_COLUNA_SUBCRITERIO);

        //PESO DOS CRITERIOS
        String CREATE_SOMALINHA = "CREATE TABLE PESO_CRITERIOS ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDCRIT INTEGER, " +
                "YMAX FLOAT, " +
                "TOTALDIVISAO FLOAT, " +
                "PESO FLOAT " +
                " )";

        db.execSQL(CREATE_SOMALINHA);

        String CREATE_SOMALINHA_ALTERNATIVA = "CREATE TABLE PESO_ALTERNATIVAS ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDALTERNATIVA INTEGER, " +
                "IDCRITERIO INTEGER, " +
                "YMAX FLOAT, " +
                "TOTAL FLOAT, " +
                "PESO FLOAT " +
                " )";

        db.execSQL(CREATE_SOMALINHA_ALTERNATIVA);

        String CREATE_SOMALINHA_SUBCRITERIO = "CREATE TABLE PESO_SUBCRITERIOS ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDSUBCRITERIO INTEGER, " +
                "IDALTERNATIVA INTEGER, " +
                "YMAX FLOAT, " +
                "TOTAL FLOAT, " +
                "PESO FLOAT " +
                " )";

        db.execSQL(CREATE_SOMALINHA_SUBCRITERIO);

        String CREATE_MATRIZCRITERIO_NORMALIZADA = "CREATE TABLE CRITERIO_NORMALIZADA ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDCRIT1 INTEGER, " +
                "IDCRIT2 INTEGER, " +
                "IMPORTANCIA FLOAT " +
                " )";
        db.execSQL(CREATE_MATRIZCRITERIO_NORMALIZADA);

        String CREATE_MATRIZALTERNARTIVA_NORMALIZADA = "CREATE TABLE ALTERNATIVA_NORMALIZADA ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDALTERNATIVA1 INTEGER, " +
                "IDALTERNATIVA2 INTEGER, " +
                "IDCRITERIO INTEGER, " +
                "IMPORTANCIA FLOAT " +
                " )";
        db.execSQL(CREATE_MATRIZALTERNARTIVA_NORMALIZADA);

        String CREATE_MATRIZSUBCRITERIO_NORMALIZADA = "CREATE TABLE SUBCRITERIO_NORMALIZADA ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDSUBCRITERIO1 INTEGER, " +
                "IDSUBCRITERIO2 INTEGER, " +
                "IDCRITERIO INTEGER, " +
                "IMPORTANCIA FLOAT " +
                " )";
        db.execSQL(CREATE_MATRIZSUBCRITERIO_NORMALIZADA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
