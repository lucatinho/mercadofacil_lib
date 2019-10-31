package br.com.fatec.mercado_lib.model;

public class Pedido {

    private int idPedido;
    private Produto produto;
    private int qtdProduto;
    
    public Pedido() {
        this.idPedido =0;
        Produto oProduto = new Produto();
        this.produto = oProduto;
        this.qtdProduto =0;
    }

    public Pedido(int idPedido, Produto produto, int qtdProduto) {
        this.idPedido = idPedido;
        this.produto = produto;
        this.qtdProduto = qtdProduto;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
        public int getQtdProduto() {
        return qtdProduto;
    }

    public void setQtdProduto(int qtdProduto) {
        this.qtdProduto = qtdProduto;
    }
    
}
