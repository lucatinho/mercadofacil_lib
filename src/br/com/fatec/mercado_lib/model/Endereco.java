package br.com.fatec.mercado_lib.model;

public class Endereco {

    private int idEndereco;
    private Cidade cidade;
    private String rua;
    private int numeroCasa;
    private String CEP;

    public Endereco() {
        this.idEndereco = 0;
        Cidade oCidade = new Cidade();
        this.cidade = oCidade;
        this.rua = "";
        this.numeroCasa = 0;
        this.CEP = "";
    }

    public Endereco(int idEndereco, Cidade cidade, String rua,int numeroCasa, String CEP) {
        this.idEndereco = idEndereco;
        this.cidade = cidade;
        this.rua = rua;
        this.numeroCasa = numeroCasa;
        this.CEP = CEP;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }
    
        public int getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(int numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }
}
