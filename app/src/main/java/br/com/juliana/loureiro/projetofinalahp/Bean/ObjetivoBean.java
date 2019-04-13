package br.com.juliana.loureiro.projetofinalahp.Bean;

public class ObjetivoBean {
    public static String ID = "ID";
    public static String TITULO = "TITULO";
    public static String DESCRICAO= "DESCRICAO";
    public static String TABELA = "OBJETIVOS";

    private int id;
    private String titulo;
    private String descricao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
