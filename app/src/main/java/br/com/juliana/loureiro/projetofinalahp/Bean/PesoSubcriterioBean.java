package br.com.juliana.loureiro.projetofinalahp.Bean;

public class PesoSubcriterioBean {
    public static String ID = "ID";
    public static String IDCRIT = "IDCRIT";
    public static String IDSUBCRIT = "IDSUBCRITERIO";
    public static String SOMA= "PESO";
    public static String YMAX= "YMAX";
    public static String TOTALDIVISAO= "TOTAL";
    public static String TOTAL= "TOTAL";
    public static String TABELA = "PESO_SUBCRITERIOS";


    private int id;
    private int idcrit;
    private float peso;
    private float ymax;
    private float totaldivisao;
    private double perc;
    private Integer idsubcrit;

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

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getYmax() {
        return ymax;
    }

    public void setYmax(float ymax) {
        this.ymax = ymax;
    }

    public float getTotaldivisao() {
        return totaldivisao;
    }

    public void setTotaldivisao(float totaldivisao) {
        this.totaldivisao = totaldivisao;
    }

    public double getPerc() {
        return perc;
    }

    public void setPerc(double perc) {
        this.perc = perc;
    }

    public Integer getIdsubcrit() {
        return idsubcrit;
    }

    public void setIdsubcrit(Integer idsubcrit) {
        this.idsubcrit = idsubcrit;
    }
}
