package br.com.fatec.mercado_lib.model;

public class Pessoa {

    private int idPessoa;
    private String nomePessoa;
    private double CPF;
    private Endereco endereco;

    public Pessoa() {
        this.idPessoa=0;
        this.nomePessoa = "";
        this.CPF = 0;
        Endereco oEndereco = new Endereco();
        this.endereco = oEndereco;
    }

    public Pessoa(int idPessoa, String nomePessoa, double CPF, Endereco endereco) {
        this.idPessoa = idPessoa;
        this.nomePessoa = nomePessoa;
        this.CPF = CPF;
        this.endereco = endereco;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }


    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }
        
    public double getCPF() {
        return CPF;
    }

    public void setCPF(double CPF) {
        this.CPF = CPF;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
