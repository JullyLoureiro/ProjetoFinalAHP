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

        db.execSQL("INSERT INTO OBJETIVOS (TITULO, DESCRICAO) VALUES ('Exemplo', 'Bla bla bla')");
        db.execSQL("INSERT INTO OBJETIVOS (TITULO, DESCRICAO) VALUES ('Exemplo', 'Bla bla bla')");
        db.execSQL("INSERT INTO OBJETIVOS (TITULO, DESCRICAO) VALUES ('Exemplo', 'Bla bla bla')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
