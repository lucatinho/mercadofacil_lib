package br.com.fatec.mercado_lib.model;

public class Pessoa {

    private int idPessoa;
    private String nomePessoa;
    private double CPF;
    private Cidade cidade;

    public Pessoa() {
        this.idPessoa=0;
        this.nomePessoa = "";
        this.CPF = 0;
        Cidade oCidade = new Cidade();
        this.cidade = oCidade;
    }

    public Pessoa(int idPessoa, String nomePessoa, double CPF, Cidade cidade) {
        this.idPessoa = idPessoa;
        this.nomePessoa = nomePessoa;
        this.CPF = CPF;
        this.cidade = cidade;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
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
}
