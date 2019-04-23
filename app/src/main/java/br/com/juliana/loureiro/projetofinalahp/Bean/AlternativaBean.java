package br.com.juliana.loureiro.projetofinalahp.Bean;

public class AlternativaBean {
    public static String ID = "ID";
    public static String IDOBJETIVO = "IDOBJETIVO";
    public static String DESCRICAO= "DESCRICAO";
    public static String TABELA = "ALTERNATIVAS";
    public static String TABELA_temp = "ALTERNATIVAS_TEMP";

    private int id;
    private String descricao;
    private int idobjetivo;

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
}
