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
                "IMPORTANCIA FLOAT " +
                " )";

        db.execSQL(CREATE_COMPARACRITERIO);

        String CREATE_COMPARACRITERIO_TEMP = "CREATE TABLE COMPARA_CRITERIOS_TEMP ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDCRIT1 INTEGER, " +
                "IDCRIT2 INTEGER, " +
                "IMPORTANCIA FLOAT " +
                " )";

        db.execSQL(CREATE_COMPARACRITERIO_TEMP);

        String CREATE_SOMACOLUNA = "CREATE TABLE SOMA_COLUNA ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDCRIT INTEGER, " +
                "SOMA FLOAT " +
                " )";

        db.execSQL(CREATE_SOMACOLUNA);

        //PESO DOS CRITERIOS
        String CREATE_SOMALINHA = "CREATE TABLE PESO_CRITERIOS ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDCRIT INTEGER, " +
                "PESO FLOAT " +
                " )";

        db.execSQL(CREATE_SOMALINHA);

        String CREATE_MATRIZCRITERIO_NORMALIZADA = "CREATE TABLE CRITERIO_NORMALIZADA ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IDCRIT1 INTEGER, " +
                "IDCRIT2 INTEGER, " +
                "IMPORTANCIA FLOAT " +
                " )";
        db.execSQL(CREATE_MATRIZCRITERIO_NORMALIZADA);

        db.execSQL("INSERT INTO OBJETIVOS (TITULO, DESCRICAO) VALUES ('Exemplo', 'Bla bla bla')");
        db.execSQL("INSERT INTO OBJETIVOS (TITULO, DESCRICAO) VALUES ('Exemplo', 'Bla bla bla')");
        db.execSQL("INSERT INTO OBJETIVOS (TITULO, DESCRICAO) VALUES ('Exemplo', 'Bla bla bla')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
