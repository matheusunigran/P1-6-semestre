package br.unigran.p1.Entidades;

public class abastecimento {
    private String kmAtual;
    private String qtdAbastecida;
    private String dia;
    private String valor;
    private Integer id;

    public String getKmAtual() {
        return kmAtual;
    }

    public void setKmAtual(String kmAtual) {
        this.kmAtual = kmAtual;
    }

    public String getQtdAbastecida() {
        return qtdAbastecida;
    }

    public void setQtdAbastecida(String qtdAbastecida) {
        this.qtdAbastecida = qtdAbastecida;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                "kmAtual - " + kmAtual + " Km " +
                        " | qtdAbastecida - " + qtdAbastecida + " L" +
                        " | dia - " + dia +
                        " | valor - " + valor;
    }
}
