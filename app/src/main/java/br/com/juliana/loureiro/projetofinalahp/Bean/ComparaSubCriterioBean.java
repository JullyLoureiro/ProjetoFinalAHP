package br.com.juliana.loureiro.projetofinalahp.Bean;

public class ComparaSubCriterioBean {
    public static String ID = "ID";
    public static String IDSUBCRIT1 = "IDSUBCRIT1";
    public static String IDSUBCRIT2 = "IDSUBCRIT2";
    public static String IDCRITERIO = "IDCRITERIO";
    public static String IMPORTANCIA = "IMPORTANCIA";
    public static String TABELA = "COMPARA_SUBCRITERIOSTEMP";

    private int id;
    private int idsubcrit1;
    private int idsubcrit2;
    private int idcriterio;
    private float importancia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdsubcrit1() {
        return idsubcrit1;
    }

    public void setIdsubcrit1(int idsubcrit1) {
        this.idsubcrit1 = idsubcrit1;
    }

    public int getIdsubcrit2() {
        return idsubcrit2;
    }

    public void setIdsubcrit2(int idsubcrit2) {
        this.idsubcrit2 = idsubcrit2;
    }

    public int getIdcriterio() {
        return idcriterio;
    }

    public void setIdcriterio(int idcriterio) {
        this.idcriterio = idcriterio;
    }

    public float getImportancia() {
        return importancia;
    }

    public void setImportancia(float importancia) {
        this.importancia = importancia;
    }
}
