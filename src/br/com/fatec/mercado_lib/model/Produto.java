package br.com.fatec.mercado_lib.model;

/**
 *
 * @author not
 */
public class Produto {
    
    private int idProduto;
    private String nomeProduto;
    private Marca marca;
    
    
    public Produto() {
        this.idProduto =0;
        this.nomeProduto="";
        Marca oMarca = new Marca();
        this.marca = oMarca;
    }

    public Produto(int idProduto, String nomeProduto, Marca marca) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.marca = marca;
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
    
    
}
