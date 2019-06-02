package br.com.juliana.loureiro.projetofinalahp.Bean;

public class SubcriterioBean {

    public static String ID = "ID";
    public static String IDCRITERIO = "IDCRITERIO";
    public static String IDOBJETIVO = "IDOBJETIVO";
    public static String DESCRICAO= "DESCRICAO";
    public static String TABELA = "SUBCRITERIOS";
    public static String TABELA_temp = "SUBCRITERIOS_TEMP";

    private int id;
    private String descricao;
    private int idobjetivo;
    private int idcriterio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdobjetivo() {
        return idobjetivo;
    }

    public void setIdobjetivo(int idobjetivo) {
        this.idobjetivo = idobjetivo;
    }

    public int getIdcriterio() {
        return idcriterio;
    }

    public void setIdcriterio(int idcriterio) {
        this.idcriterio = idcriterio;
    }
}
