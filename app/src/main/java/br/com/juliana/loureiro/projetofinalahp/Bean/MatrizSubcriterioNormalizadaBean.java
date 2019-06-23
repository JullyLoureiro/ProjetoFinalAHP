package br.com.juliana.loureiro.projetofinalahp.Bean;

public class MatrizSubcriterioNormalizadaBean {
    public static String ID = "ID";
    public static String IDSUBCRIT1 = "IDSUBCRIT1";
    public static String IDSUBCRIT2 = "IDSUBCRIT2";
    public static String IMPORTANCIA = "IMPORTANCIA";
    public static String IDCRITERIO = "IDCRITERIO";
    public static String TABELA = "SUBCRITERIO_NORMALIZADA";

    private int id;
    private int idsubcrit1;
    private int idsubcrit2;
    private float importancia;
    private Integer idcriterio;

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

    public float getImportancia() {
        return importancia;
    }

    public void setImportancia(float importancia) {
        this.importancia = importancia;
    }

    public Integer getIdcriterio() {
        return idcriterio;
    }

    public void setIdcriterio(Integer idcriterio) {
        this.idcriterio = idcriterio;
    }
}
