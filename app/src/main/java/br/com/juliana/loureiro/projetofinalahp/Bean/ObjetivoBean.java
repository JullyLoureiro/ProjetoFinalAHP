package br.com.juliana.loureiro.projetofinalahp.Bean;

public class ObjetivoBean {
    public static String ID = "ID";
    public static String TITULO = "TITULO";
    public static String DESCRICAO= "DESCRICAO";
    public static String TABELA = "OBJETIVOS";
    public static String TABELA_TEMP = "OBJETIVOS_TEMP";
    public static String DATA = "DATA";

    private int id;
    private String titulo;
    private String descricao;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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
