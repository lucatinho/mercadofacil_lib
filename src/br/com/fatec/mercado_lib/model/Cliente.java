package br.com.fatec.mercado_lib.model;

public class Cliente extends Pessoa {

    private int idCliente;

    public Cliente() {
        this.idCliente = 0;
    }

    public Cliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
