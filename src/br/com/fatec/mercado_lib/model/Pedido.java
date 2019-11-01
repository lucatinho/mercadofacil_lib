package br.com.fatec.mercado_lib.model;

public class Pedido {

    private int idPedido;
    private Produto produto;
    private int qtdProduto;
    private Cliente cliente;
    
    public Pedido() {
        this.idPedido =0;
        Produto oProduto = new Produto();
        this.produto = oProduto;
        this.qtdProduto =0;
        Cliente oCliente = new Cliente();
        this.cliente = oCliente;
    }

    public Pedido(int idPedido, Produto produto, int qtdProduto, Cliente cliente) {
        this.idPedido = idPedido;
        this.produto = produto;
        this.qtdProduto = qtdProduto;
        this.cliente = cliente;
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
        public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
