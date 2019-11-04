package br.com.fatec.mercado_lib.model;

public class Mercado extends Pessoa {

    private int idMercado;

    public Mercado() {
        this.idMercado = 0;
    }

    public Mercado(int idMercado) {
        this.idMercado = idMercado;
    }

    public int getIdMercado() {
        return idMercado;
    }

    public void setIdMercado(int idMercado) {
        this.idMercado = idMercado;
    }

}
