package br.com.juliana.loureiro.projetofinalahp.Bean;

public class CriterioBean {

    public static String ID = "ID";
    public static String DESCRICAO= "DESCRICAO";
    public static String TABELA = "CRITERIOS";

    private int id;
    private String descricao;

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
}
