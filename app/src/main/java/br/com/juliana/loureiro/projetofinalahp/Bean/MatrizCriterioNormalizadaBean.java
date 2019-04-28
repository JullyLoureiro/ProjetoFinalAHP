package br.com.juliana.loureiro.projetofinalahp.Bean;

public class MatrizCriterioNormalizadaBean {
    public static String ID = "ID";
    public static String IDCRIT1 = "IDCRIT1";
    public static String IDCRIT2 = "IDCRIT2";
    public static String IMPORTANCIA = "IMPORTANCIA";
    public static String TABELA = "CRITERIO_NORMALIZADA";

    private int id;
    private int idcrit1;
    private int idcrit2;
    private float importancia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdcrit1() {
        return idcrit1;
    }

    public void setIdcrit1(int idcrit1) {
        this.idcrit1 = idcrit1;
    }

    public int getIdcrit2() {
        return idcrit2;
    }

    public void setIdcrit2(int idcrit2) {
        this.idcrit2 = idcrit2;
    }

    public float getImportancia() {
        return importancia;
    }

    public void setImportancia(float importancia) {
        this.importancia = importancia;
    }
}
