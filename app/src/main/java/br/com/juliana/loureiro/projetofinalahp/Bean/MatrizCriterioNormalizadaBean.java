package br.com.juliana.loureiro.projetofinalahp.Bean;

public class MatrizCriterioNormalizadaBean {
    public static String ID = "ID";
    public static String IDCRIT1 = "IDCRIT1";
    public static String IDCRIT2 = "IDCRIT2";
    public static String IMPORTANCIA = "IMPORTANCIA";
    public static String IDALTERNATIVA1 = "IDALTERNATIVA1";
    public static String IDALTERNATIVA2 = "IDALTERNATIVA2";
    public static String IDCRITERIO = "IDCRITERIO";
    public static String IDSUBCRITERIO = "IDSUBCRITERIO";
    public static String TABELA = "CRITERIO_NORMALIZADA";

    private int id;
    private int idcrit1;
    private int idcrit2;
    private float importancia;
    private Integer idalternativa1;
    private Integer idalternativa2;
    private Integer idcriterio;
    private Integer idsubcriterio;

    public Integer getIdsubcriterio() {
        return idsubcriterio;
    }

    public void setIdsubcriterio(Integer idsubcriterio) {
        this.idsubcriterio = idsubcriterio;
    }

    public Integer getIdalternativa1() {
        return idalternativa1;
    }

    public void setIdalternativa1(Integer idalternativa1) {
        this.idalternativa1 = idalternativa1;
    }

    public Integer getIdalternativa2() {
        return idalternativa2;
    }

    public void setIdalternativa2(Integer idalternativa2) {
        this.idalternativa2 = idalternativa2;
    }

    public Integer getIdcriterio() {
        return idcriterio;
    }

    public void setIdcriterio(Integer idcriterio) {
        this.idcriterio = idcriterio;
    }

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
