package br.com.fatec.mercado_lib.model;

public class Cliente {

    private int idCliente;
    private String nomeCliente;
    private double CPF;

    public Cliente() {
        this.idCliente = 0;
        this.nomeCliente = "";
        this.CPF = 0;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public double getCPF() {
        return CPF;
    }

    public void setCPF(double CPF) {
        this.CPF = CPF;
    }

}
