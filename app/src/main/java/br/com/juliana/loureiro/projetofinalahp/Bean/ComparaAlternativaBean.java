package br.com.juliana.loureiro.projetofinalahp.Bean;

public class ComparaAlternativaBean {
    public static String ID = "ID";
    public static String IDALTERNATIVA1 = "IDALTERNATIVA1";
    public static String IDALTERNATIVA2 = "IDALTERNATIVA2";
    public static String IDOBJETIVO = "IDOBJETIVO";
    public static String IDCRITERIO = "IDCRITERIO";
    public static String IDSUBCRITERIO = "IDSUBCRITERIO";
    public static String IMPORTANCIA = "IMPORTANCIA";
    public static String TABELA = "COMPARA_ALTERNATIVA";
    public static String TABELA_temp = "COMPARA_ALTERNATIVATEMP";

    private Integer id;
    private Integer idalternativa1;
    private Integer idalternativa2;
    private Integer idobjetivo;
    private Integer idcriterio;
    private Integer idsubcriterio;
    private double importancia;

    public Integer getIdsubcriterio() {
        return idsubcriterio;
    }

    public void setIdsubcriterio(Integer idsubcriterio) {
        this.idsubcriterio = idsubcriterio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getIdobjetivo() {
        return idobjetivo;
    }

    public void setIdobjetivo(Integer idobjetivo) {
        this.idobjetivo = idobjetivo;
    }

    public Integer getIdcriterio() {
        return idcriterio;
    }

    public void setIdcriterio(Integer idcriterio) {
        this.idcriterio = idcriterio;
    }

    public double getImportancia() {
        return importancia;
    }

    public void setImportancia(double importancia) {
        this.importancia = importancia;
    }
}
