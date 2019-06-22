package br.com.juliana.loureiro.projetofinalahp.Bean;

public class PesoCriteriosBean {
    public static String ID = "ID";
    public static String IDCRIT = "IDCRIT";
    public static String IDALTERNATIVA = "IDALTERNATIVA";
    public static String SOMA= "PESO";
    public static String YMAX= "YMAX";
    public static String TOTALDIVISAO= "TOTALDIVISAO";
    public static String TOTAL= "TOTAL";
    public static String TABELA = "PESO_CRITERIOS";
    public static String PESO_ALTERNATIVAS = "PESO_ALTERNATIVAS";

    private int id;
    private int idobjetivo;
    private int idcrit;
    private float peso;
    private float ymax;
    private float totaldivisao;
    private double perc;
    private Integer idalternativa;
    private String alternativa;

    public String getAlternativa() {
        return alternativa;
    }

    public void setAlternativa(String alternativa) {
        this.alternativa = alternativa;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public int getIdobjetivo() {
        return idobjetivo;
    }

    public void setIdobjetivo(int idobjetivo) {
        this.idobjetivo = idobjetivo;
    }

    public double getPerc() {
        return perc;
    }

    public void setPerc(double perc) {
        this.perc = perc;
    }

    public Integer getIdalternativa() {
        return idalternativa;
    }

    public void setIdalternativa(Integer idalternativa) {
        this.idalternativa = idalternativa;
    }

    public float getTotaldivisao() {
        return totaldivisao;
    }

    public void setTotaldivisao(float totaldivisao) {
        this.totaldivisao = totaldivisao;
    }

    public float getYmax() {
        return ymax;
    }

    public void setYmax(float ymax) {
        this.ymax = ymax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdcrit() {
        return idcrit;
    }

    public void setIdcrit(int idcrit) {
        this.idcrit = idcrit;
    }

    public float getSoma() {
        return peso;
    }

    public void setSoma(float soma) {
        this.peso = soma;
    }
}
