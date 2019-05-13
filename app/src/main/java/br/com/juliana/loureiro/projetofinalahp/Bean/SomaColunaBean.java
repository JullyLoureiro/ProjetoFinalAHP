package br.com.juliana.loureiro.projetofinalahp.Bean;

public class SomaColunaBean {
    public static String ID = "ID";
    public static String IDCRIT = "IDCRIT";
    public static String SOMA= "SOMA";
    public static String TABELA = "SOMA_COLUNA";
    public static String SOMA_COLUNA_ALTERNATIVA = "SOMA_COLUNA_ALTERNATIVA";

    private int id;
    private int idcrit;
    private float soma;

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
        return soma;
    }

    public void setSoma(float soma) {
        this.soma = soma;
    }
}
