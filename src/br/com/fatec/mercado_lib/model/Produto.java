package br.com.fatec.mercado_lib.model;

/**
 *
 * @author not
 */
public class Produto {
    
    private int idProduto;
    private String nomeProduto;
    private String descricao;
    private Marca marca;
    private Cliente cliente;
    private double preco;
    
    
    public Produto() {
        this.idProduto =0;
        this.nomeProduto="";
        this.descricao="";
        Marca oMarca = new Marca();
        this.marca = oMarca;
        Cliente oCliente = new Cliente();
        this.cliente = oCliente;
        this.preco = 0.00;
    }

    public Produto(int idProduto, String nomeProduto, String descricao, Marca marca, Cliente cliente, double preco) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.marca = marca;
        this.cliente = cliente;
        this.descricao=descricao;
        this.preco=preco;
    }

     public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
     public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }
 
    public void setPreco(double preco) {
        this.preco = preco;
    }
 
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
}
