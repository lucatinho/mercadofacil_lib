package br.com.fatec.mercado_lib.model;

/**
 *
 * @author not
 */
public class Endereco {

    private int idEndereco;
    private Cidade cidade;
    private String rua;
    private int numeroCasa;
    private double CEP;

    public Endereco() {
        this.idEndereco = 0;
        Cidade oCidade = new Cidade();
        this.cidade = oCidade;
        this.rua = "";
        this.numeroCasa = 0;
        this.CEP = 0;
    }

    public Endereco(int idEndereco, Cidade cidade, String rua,int numeroCasa, double CEP) {
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

    public double getCEP() {
        return CEP;
    }

    public void setCEP(double CEP) {
        this.CEP = CEP;
    }
}
